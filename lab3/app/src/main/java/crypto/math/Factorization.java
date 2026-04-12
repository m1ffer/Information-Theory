package crypto.math;

import java.util.ArrayList;
import java.util.List;

public final class Factorization {

    private Factorization() {}

    /**
     * Возвращает уникальные простые делители числа n
     * Например: 12 -> [2, 3]
     */
    public static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList<>();
        if ((n & 1) == 0) {
            factors.add(2);
            while ((n & 1) == 0)
                n >>>= 1;
        }
        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0) {
                factors.add(i);
                while (n % i == 0)
                    n /= i;
            }
        }
        if (n > 1)
            factors.add(n);
        return factors;
    }

    public static boolean isCoprimeWithPhi(int k, List<Integer> factors) {
        if (k == 1)
            return false;
        for (int q : factors) {
            if (k % q == 0) {
                return false;
            }
        }
        return true;
    }
}