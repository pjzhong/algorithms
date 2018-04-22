package nio;

import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Random;

public class PipeTest {

    public static void main(String[] args) throws Exception {
        //Wrap a channel around stdout
        WritableByteChannel out = Channels.newChannel(System.out);

        //Start worker and get read end of channel
        ReadableByteChannel workerChannel = startWorker(10);
        ByteBuffer buffer = ByteBuffer.allocate(100);

        while( 0 <= workerChannel.read(buffer)) {
            buffer.flip();
            out.write(buffer);
            buffer.clear();
        }
    }

    private static ReadableByteChannel startWorker(int reps) throws Exception {
        Pipe pipe = Pipe.open();
        Worker  worker = new Worker(pipe.sink(), reps);
        new Thread(worker).start();//This is demo code
        return pipe.source();
    }

    private static class Worker implements Runnable {
        WritableByteChannel channel;
        private int reps;

        Worker(WritableByteChannel channel, int reps) {
            this.channel = channel;
            this.reps = reps;
        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(100);

            try {
                for(int i = 0; i < this.reps; i++) {
                    doSomeWork(buffer);
                    while (0 < channel.write(buffer));
                }
            } catch (Exception e) {
                e.printStackTrace();//easy way out; this is demo code
            }
        }

        private String[] products = {
                "No good deed goes unpunished",
                "To be, or what?",
                "No matter where you go, there your are",
                "Just say \"No\"",
                "My karma ran over dogma"
        };
        private Random rand = new Random();

        private void doSomeWork(ByteBuffer buffer) throws Exception {
            int product = rand.nextInt(products.length);

            buffer.clear();
            buffer.put(products[product].getBytes("UTF-8"));
            buffer.put("\r\n".getBytes("UTF-8"));
            buffer.flip();
        }
    }
}
