package crypto.elgamal;

import crypto.math.MathUtils;

public final class ElGamalService {

    private ElGamalService() {}

    /**
     * Генерация ключей
     */
    public static int generateY(int p, int g, int x) {
        return (int) MathUtils.modPow(g, x, p);
    }

    /**
     * Шифрование одного блока
     */
    public static void encrypt(int m, int p, int g, int y, int k, CipherPair result) {
        int a = (int) MathUtils.modPow(g, k, p);
        long s = MathUtils.modPow(y, k, p);
        long b = (m * s) % p;
        result.setA(a);
        result.setB((int) b);
    }

    /**
     * Расшифровка одного блока
     */
    public static int decrypt(int a, int b, int p, int x) {
        // s = a^x mod p
        long s = MathUtils.modPow(a, x, p);

        // s^-1 mod p
        long inv = MathUtils.modInverse(s, p);

        // m = b * s^-1 mod p
        return (int) ((b * inv) % p);
    }
}