package crypto.math;

public final class MathUtils {

    private MathUtils() {}

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;

        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; (long) i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }

        return true;
    }

    /**
     * Быстрое возведение в степень по модулю
     */
    public static long modPow(long base, long exp, long mod) {
        if (mod <= 1) {
            throw new IllegalArgumentException("mod must be > 1");
        }

        base %= mod;
        long result = 1;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            base = (base * base) % mod;
            exp >>= 1;
        }

        return result;
    }

    /**
     * НОД
     */
    public static long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return Math.abs(a);
    }

    /**
     * Результат расширенного алгоритма Евклида
     */
    public record EgcdResult(long gcd, long x, long y) {}

    /**
     * Итеративный расширенный алгоритм Евклида.
     * Находит x, y такие что:
     * a*x + b*y = gcd(a, b)
     */
    public static EgcdResult extendedGcd(long a, long b) {
        long oldR = a, r = b;
        long oldS = 1, s = 0;
        long oldT = 0, t = 1;

        while (r != 0) {
            long q = oldR / r;

            long tmpR = oldR - q * r;
            oldR = r;
            r = tmpR;

            long tmpS = oldS - q * s;
            oldS = s;
            s = tmpS;

            long tmpT = oldT - q * t;
            oldT = t;
            t = tmpT;
        }

        return new EgcdResult(oldR, oldS, oldT);
    }

    /**
     * Обратный элемент через расширенный Евклид
     */
    public static long modInverse(long a, long mod) {
        EgcdResult res = extendedGcd(a, mod);

        if (res.gcd() != 1) {
            throw new ArithmeticException("Inverse does not exist");
        }

        return (res.x() % mod + mod) % mod;
    }

    /**
     * Обратный элемент через малую теорему Ферма (mod — простое)
     */
    public static long modInversePrime(long a, long mod) {
        return modPow(a, mod - 2, mod);
    }
}