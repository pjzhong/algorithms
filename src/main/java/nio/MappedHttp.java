package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Dummy HTTP server using MappedByteBuffers.
 * Given a filename on the command line, pretend to be
 * a web server and generate an HTTP response containing the file content
 * preceded by appropriate header. The data is sent with a gather write.
 * */
public class MappedHttp {

    private static final String OUTPUT_FILE = "MappedHttp.out";
    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server:Dummy Server";
    private static final String HTTP_HDR =
            "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR =
            "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 = "Could not open file: ";

    public static void main(String[] args) throws Exception {
        if(args.length < 1) {
            System.err.println("Usage: filename");
            return;
        }

        String file = args[0];
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = {header, dynhdrs, null};
        String contentType = "unknown/unknown";
        long contentLength = -1;

        try {
            FileInputStream fils = new FileInputStream(file);
            FileChannel fc = fils.getChannel();
            MappedByteBuffer filedata = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

            gather[2] = filedata;
        } catch (IOException e) {
            //file could not be opened; report problem
            ByteBuffer buf = ByteBuffer.allocate(1024);
            String msg = MSG_404 + e + LINE_SEP;
            buf.put(bytes(msg));
            buf.flip();
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buf;

            contentLength = msg.length();
            contentType = "text/plain";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Content-Length:").append(contentLength).append(LINE_SEP);
        builder.append("Content-Type:").append(contentLength).append(LINE_SEP);
        builder.append(LINE_SEP).append(LINE_SEP);

        dynhdrs.put(bytes(builder.toString()));
        dynhdrs.flip();

        File out = new File(OUTPUT_FILE);
        FileOutputStream fos = new FileOutputStream(out);
        FileChannel oc = fos.getChannel();
        while(oc.write(gather) > 0) {}
        oc.close();

        System.out.println("output written to " + out.getAbsolutePath());
    }

    private static byte[] bytes(String str) throws Exception {
        return (str.getBytes("UTF-8"));
    }

}
