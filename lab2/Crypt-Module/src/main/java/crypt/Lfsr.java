package crypt;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Lfsr implements Generator{
    private long reg;
    private final long tapsMask;
    private final long firstBitMask;

    public Lfsr(final String polynome, final long start){
        this(getPowers(polynome), start);
    }

    public Lfsr(final int[] powers, final long start){
        reg = start;
        tapsMask = makeTapsMask(powers);
        firstBitMask = Long.highestOneBit(tapsMask);
    }

    private static final Pattern PART_PATTERN =
            Pattern.compile("1|x(\\^[1-9]\\d*)?");

    public static void checkPart(final String part){
        if (!PART_PATTERN.matcher(part).matches())
            throw new IllegalArgumentException("Неверное значение полинома");
    }

    public static int getPower(String part){
        if(part.equals("1"))
            return 0;
        else if (part.equals("x"))
            return 1;
        else
            return Integer.parseInt(part.substring(2));
    }

    public static int[] getPowers(String polynome){
        polynome = polynome.replaceAll("\\s+", "");
        if (polynome.isEmpty())
            throw new IllegalArgumentException("Пустой полином");
        String[] parts = polynome.split("\\+");
        Set<Integer> powers = new HashSet<>();
        boolean hasOne = false;
        for (String part : parts) {
            checkPart(part);
            if (part.equals("1")) {
                hasOne = true;
            }
            else if (!powers.add(getPower(part)))
                    throw new IllegalArgumentException("Повторяющаяся степень");
        }
        if (!hasOne || powers.isEmpty())
            throw new IllegalArgumentException("Неверное значение полинома");
        return powers.stream().mapToInt(Integer::intValue).toArray();
    }

    public static long makeTapsMask(final int[] powers){
        long result = 0;
        for (int power : powers) {
            if (power > Long.SIZE)
                throw new IllegalArgumentException("Превышена допустимая степень полинома");
            result |= 1L << (power - 1);
        }
        return result;
    }

    public byte generateByte(){
        byte result = 0;
        for (int i = 0; i < Byte.SIZE; i++){
            result <<= 1;
            result |= (byte) ((reg & firstBitMask) == 0 ? 0 : 1);
            int newBit = Long.bitCount(reg & tapsMask) & 1;
            reg <<= 1;
            reg |= newBit;
        }
        return result;
    }

    public byte[] generateKey(final int byteCount){
        final byte[] result = new byte[byteCount];
        for (int i = 0; i < result.length; i++)
            result[i] = generateByte();
        return result;
    }

    @Override
    public byte nextByte() {
        return generateByte();
    }
}
