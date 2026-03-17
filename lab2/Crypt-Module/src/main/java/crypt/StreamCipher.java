package crypt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamCipher{
    private static final int BUFFER_SIZE = 64 * 1024;
    private final byte[] buffer = new byte[BUFFER_SIZE];
    private final InputStream in;
    private final OutputStream out;
    private final Generator generator;

    public StreamCipher(InputStream in, OutputStream out, Generator generator){
        this.in = in;
        this.out = out;
        this.generator = generator;
    }

    public void crypt() throws IOException {
        int byteCount;
        while((byteCount = in.read(buffer)) != -1){
            for (int i = 0; i < byteCount; i++)
                buffer[i] ^= generator.nextByte();
            out.write(buffer, 0, byteCount);
        }
        out.flush();
    }
}
