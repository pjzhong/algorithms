package generic;



/**
 * <? extends T> Wildcards - Upper Bounds Wildcards （上界通配符）
 * <? super T> Bounds - Lower Bounds Wildcard (下界通配符)
 */

class Food {}
class Fruit extends Food {}
class Apple extends Fruit {}
class Orange extends Fruit {}
class Banana extends Fruit {}
class RedApple extends Fruit {}

class Plate<T> {
    private T item;

    public Plate(T t) { item = t;}
    public void set(T t) { item = t; }
     public T get() { return item; }
}


public class GenericReview {

    public void test() {
        Plate<? super Fruit> p = new Plate<Food>(new Apple());
        p.set(new Fruit());
        p.set(new RedApple());
        p.set(new Fruit());

        Object p1 = p.get();

        Plate<? extends Fruit> p2 = new Plate<>(new Apple());


        Fruit f2 = p2.get();

    }
}
