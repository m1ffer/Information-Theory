package crypt;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static crypt.LfsrTest.byteArrayToBinString;
import static crypt.LfsrTest.p;
import static org.junit.jupiter.api.Assertions.*;

public class CryptTest {
    private static final Path PATH_TO_TEST_FILE = Path.of("D:/graph.txt");
    private static final byte[] inB;
    private static final byte[] outB;

    static {
        try {
            inB = Files.readAllBytes(PATH_TO_TEST_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        outB = new byte[inB.length];
    }

    @Test
    public void testCrypt(){
        try(
                ByteArrayInputStream in = new ByteArrayInputStream(inB);
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ){
            var c = new StreamCipher(
                    in,
                    out,
                    new Lfsr(p, Long.parseLong("0110101110110011010011011011111", 2))
            );
            c.crypt();
            var res = out.toByteArray();
            assertEquals(
                    "0101101010000001011111101000101100000100011110010100001011111110100011010010110101110010110000010011101000101101100001001110101110010001110101101000110000101000011101000100000100111000011011111100000101011101010011101011111100110000110110111011001001010000011010111111101011101000010111111011011010010000100101100000010110000100110111101111010110001000110000000100000101010110011010111010000011100100",
                    byteArrayToBinString(res)
            );
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }


        try(
                ByteArrayInputStream in = new ByteArrayInputStream(inB);
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ){
            var c = new StreamCipher(
                    in,
                    out,
                    new Lfsr(p, Long.parseLong("1111000010010100010110011100010", 2))
            );
            c.crypt();
            var res = out.toByteArray();
            assertEquals(
                     "1100000110100110011010101111000101111101000110110010011010000101000011111011101101111011000011111110010011011111110111000110011110101011011001000110000010100001001010101111001010010100111011010010000000010011110001101011101001011111101010001100110110100100001111111001000101100110011100101011111101111010110000001000111011111000100000011011000000101010011011010101110001111000111111000000111100100110",
                     byteArrayToBinString(res)
            );
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
