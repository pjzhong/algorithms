package concurrency.pattern;

import concurrency.anno.GuardeBy;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);//I am a expensive computation
        return new BigInteger(arg);
    }
}

class MemorizerWithSynchronized<A, V> implements Computable<A, V>  {
    @GuardeBy("this")
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public MemorizerWithSynchronized(Computable<A, V> c) { this.c = c; }

    /**
     * poor performance, serialization  access this method a time
     * */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }
}

class MemorizerWithConcurrencyHashMap<A, V> implements Computable<A, V>  {
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public MemorizerWithConcurrencyHashMap(Computable<A, V> c) { this.c = c; }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result == null) {
            /**
             * had the ability to compute same result twice or more
             * for example:thread X computing  f(27), but thread Y didn't X is computing , so Y would compute f(27) by
             * itself
             * */
            result = c.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }
}

class MemorizerWithConcurrencyHashMapAndFutureTask<A, V> implements Computable<A, V>  {
    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public MemorizerWithConcurrencyHashMapAndFutureTask(Computable<A, V> c) { this.c = c; }

    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if(f == null) {
            FutureTask<V> ft = new FutureTask<>(() -> c.compute(arg));
            /**
             * had the ability to compute same result twice or more but this time is smaller, because
             * the compound action(put-if-absent), but we fixed this using the putIfAbsent() method.
             * for example:thread X computing  f(27), but thread Y didn't X is computing , so Y would compute f(27) by
             * itself
             * */
            //cache.put(arg, ft);
            //ft.run();
            f = cache.putIfAbsent(arg, ft);
            if(f == null) { f = ft; ft.run(); }
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}


public class MomorizerExample {}
