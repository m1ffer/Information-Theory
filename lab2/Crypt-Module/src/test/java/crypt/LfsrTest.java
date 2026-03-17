package crypt;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class LfsrTest {
    public static final String p = "x^31 + x^3 + 1";

    private static final StringBuilder sbBytes = new StringBuilder();

    public static String byteToBinString(byte b){
        String binString = Integer.toBinaryString(Byte.toUnsignedInt(b));
        int size = binString.length();
        int cnt = Byte.SIZE - binString.length();
        String f = "0".repeat(Byte.SIZE - binString.length());
        return f + binString;
    }

    public static String byteArrayToBinString(byte[] a){
        sbBytes.delete(0, sbBytes.length());
        sbBytes.ensureCapacity(a.length * Byte.SIZE);
        for (byte i : a)
            sbBytes.append(byteToBinString(i));
        return sbBytes.toString();
    }

    @Test
    public void testCheckPartCorrect(){
        assertDoesNotThrow(() -> Lfsr.checkPart("x^1"));
        assertDoesNotThrow(() -> Lfsr.checkPart("x^12"));
        assertDoesNotThrow(() -> Lfsr.checkPart("x"));
        assertDoesNotThrow(() -> Lfsr.checkPart("1"));
    }

    @Test
    public void testCheckPartThrows(){
        assertThrows(
                IllegalArgumentException.class,
                () -> Lfsr.checkPart("x^ 1")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> Lfsr.checkPart("12")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> Lfsr.checkPart("slzef")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> Lfsr.checkPart(" x")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> Lfsr.checkPart("")
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> Lfsr.checkPart("x^2+")
        );
    }

    @Test
    public void testGetPowersCorrect(){
        var ar = Lfsr.getPowers("  x   ^ 4 + x^2+1+x");
        Arrays.sort(ar);
        assertArrayEquals(new int[]{1, 2, 4}, ar);
        ar = Lfsr.getPowers("1+x");
        Arrays.sort(ar);
        assertArrayEquals(new int[]{1}, ar);
        ar = Lfsr.getPowers(p);
        Arrays.sort(ar);
        assertArrayEquals(new int[]{3, 31}, ar);
    }

    @Test
    public void testGetPowersThrows(){
        assertThrows(IllegalArgumentException.class,
                () -> Lfsr.getPowers("1"));
        assertThrows(IllegalArgumentException.class,
                () -> Lfsr.getPowers("      "));
        assertThrows(IllegalArgumentException.class,
                () -> Lfsr.getPowers("x ++ 1"));
        assertThrows(IllegalArgumentException.class,
                () -> Lfsr.getPowers("x ^ 02"));
        assertThrows(IllegalArgumentException.class,
                () -> Lfsr.getPowers("  x   ^ 4 + x^2+1+x +     x ^ 4"));
        assertThrows(IllegalArgumentException.class,
                () -> Lfsr.getPowers("  x   ^ 4 + x^2+    x"));
    }

    @Test
    public void testGetKey(){
        var l = new Lfsr(p, Long.parseLong("0110101110110011010011011011111", 2));
        var ar = l.generateKey(50);
        assertEquals(
                "0110101110110011010011011011111100110001010011110111010111000110101101000001110101000011111100110000100100011001101100011101110110100110111011101011010100011000010001010111001100001011010110111111010001101011011110011000011100001001111010111000001101100010010110001100111011011101011010011000000110101000101011110011010110110101111011001100011010111100111101010111011101100001010100111001100111010100",
                byteArrayToBinString(ar)
        );
        l = new Lfsr(p, Long.parseLong("1010101011111111001001000011100", 2));
        ar = l.generateKey(50);
        assertEquals(
                "1010101011111111001001000011100001011110001110010110010011101101000111111000001010011010100010110101010110110011010111101100111101000011000001100111001111100010110111011011101110010101011010001010111010100011010000101000000101110011001000101101111011011001110111011111101011100001100000111100001110000101011011101101110000000111111101000101011101010010010001110000100110011101000001000000111111101110",
                byteArrayToBinString(ar)
        );
        l = new Lfsr(p, Long.parseLong("0000000000000000000000000000000", 2));
        ar = l.generateKey(50);
        assertEquals(
                "0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
                byteArrayToBinString(ar)
        );
    }
}
