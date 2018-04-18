package nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Random;

/**
 * Test locking with FileChannel.
 * Run one copy of this code with arguments "-w /tmp/locktest.dat"
 * and one or more copies with "-r /tmp/locktest.dat" to see the interactions of exclusive and shared locks.
 * Note how too may reader can starve out the writer.
 * Note the filename you provide will be overwritten. Substitute an appropriate temp filename for your favorite OS.
 * */
public class LockTest {

    private static final int SIZEOF_INT = 4;
    private static final int INDEX_START = 0;
    private static final int INDEX_COUNT = 10;
    private static final int INDEX_SIZE = INDEX_COUNT * SIZEOF_INT;

    private ByteBuffer buffer = ByteBuffer.allocate (INDEX_SIZE);
    private IntBuffer indexBuffer = buffer.asIntBuffer();
    private Random rand = new Random();

    public static void main(String[] args) throws Exception {
        boolean writer = false;
        String filename;
        if(args.length != 2) {
            System.out.println ("Usage: [ -r | -w ] filename");
            return;
        }

        writer = args[0].equals("-w");
        filename = args[1];
        File file = new File(filename);
        RandomAccessFile raf = new RandomAccessFile(file, writer ? "rw" : "r");
        FileChannel fc = raf.getChannel();


        LockTest lockTest = new LockTest();
        if(writer) {
            lockTest.doUpdates(fc);
        } else {
            System.out.println(file.getPath());
            lockTest.doQueries(fc);
        }
    }

    //Simulate a series of read-only queries while
    void doQueries(FileChannel fc) throws Exception {
        while(true) {
            System.out.println("trying for shared lock...");
            FileLock lock = null;
            try {
                lock = fc.lock(INDEX_START, INDEX_SIZE, true);
                int reps = rand.nextInt(60) + 20;

                for(int i = 0; i < reps; i++) {
                    int n = rand.nextInt(INDEX_COUNT);
                    int position = INDEX_START + (n * SIZEOF_INT);

                    buffer.clear();
                    fc.read(buffer, 0);

                    int value = indexBuffer.get(position);

                    System.out.format("Index entry %d = %d\n", n, value);

                    Thread.sleep(100);
                }
            } finally {
                if(lock != null) { lock.release(); }
            }

            System.out.println("<sleeping>");
            Thread.sleep(rand.nextInt(3000) + 500);
        }
    }

    void doUpdates(FileChannel fc) throws Exception {
        while(true) {
            System.out.println("trying for exclusive lock...");

            FileLock lock = null;
            try {
                lock = fc.lock(INDEX_START, INDEX_SIZE, false);
                updateIndex(fc);
            } finally {
                if(lock != null) {
                    lock.release();
                }
            }

            System.out.println("<sleeping>");
            Thread.sleep(rand.nextInt(2000) + 500);
        }
    }

    private int idxval = 1;

    private void updateIndex(FileChannel fc) throws Exception {
        indexBuffer.clear();
        for(int i = 0; i < INDEX_COUNT; i++) {
            idxval++;
            System.out.format("Updating index %d=%d\n", i, idxval);
            indexBuffer.put(idxval);

            //Pretend that this is really hard work
            Thread.sleep(500);
        }
        buffer.clear();
        fc.write(buffer, INDEX_START);
    }
}
