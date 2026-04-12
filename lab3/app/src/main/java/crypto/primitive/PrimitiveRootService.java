package crypto.primitive;

import crypto.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public final class PrimitiveRootService {

    private PrimitiveRootService() {}

    /**
     * Находит один первообразный корень по модулю p
     *
     * @param p       простое число
     * @param factors простые делители p-1
     */
    public static int findOnePrimitiveRoot(int p, List<Integer> factors) {
        int phi = p - 1;
        for (int g = 2; g < p; g++) {
            boolean isPrimitive = true;
            for (int q : factors) {
                long power = MathUtils.modPow(g, phi / q, p);
                if (power == 1) {
                    isPrimitive = false;
                    break;
                }
            }
            if (isPrimitive)
                return g;
        }
        throw new IllegalStateException("Primitive root not found");
    }

    public static List<Integer> findAllPrimitiveRoots(int p, int g, List<Integer> factors) {
        int phi = p - 1;
        boolean[] bad = new boolean[phi];
        for (int q : factors)
            for (int i = q; i < phi; i += q)
                bad[i] = true;
        List<Integer> roots = new ArrayList<>(phi / 2);
        long cur = 1;
        for (int i = 1; i < phi; i++) {
            cur = (cur * g) % p;
            if (!bad[i])
                roots.add((int) cur);
        }
        return roots;
    }
}