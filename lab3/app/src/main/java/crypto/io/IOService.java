package crypto.io;

import crypto.elgamal.CipherPair;
import crypto.elgamal.ElGamalService;
import crypto.math.Factorization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public final class IOService {

    private IOService() {}

    private static final ThreadLocalRandom RAND = ThreadLocalRandom.current();

    private static int generateK(int phi, List<Integer> factors){
        int k;
        do{
            k = RAND.nextInt(1, phi);
        } while(!Factorization.isCoprimeWithPhi(k, factors));
        return k;
    }

    /**
     * Шифрование файла
     */
    public static void encryptFile(Path inputPath, OutputStream out,
                                   int p, int g, int y, Integer startK, List<Integer> factors) throws IOException {
        CipherPair pair = new CipherPair();
        byte[] twoNumbers = new byte[4];
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(inputPath));
             out) {
            byte[] buffer = new byte[8192];
            int k = Objects.requireNonNullElseGet(startK, () -> generateK(p - 1, factors));
            int read;
            while ((read = in.read(buffer)) != -1) {
                for (int i = 0; i < read; i++) {
                    int m = buffer[i] & 0xFF;
                    ElGamalService.encrypt(m, p, g, y, k, pair);
                    k = generateK(p - 1, factors);
                    twoNumbers[0] = (byte) (pair.getA() >>> 8);
                    twoNumbers[1] = (byte) (pair.getA());
                    twoNumbers[2] = (byte) (pair.getB() >>> 8);
                    twoNumbers[3] = (byte) (pair.getB());
                    out.write(twoNumbers);
                }
            }
        }
    }

    /**
     * Расшифровка файла
     */
    public static void decryptFile(Path inputPath, OutputStream out,
                                   int p, int x) throws IOException {

        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(inputPath));
             out) {
            final byte[] buffer = new byte[8192];
            final byte[] block = new byte[4];
            int blockLen = 0;

            int read;
            while ((read = in.read(buffer)) != -1) {
                int i = 0;

                if (blockLen > 0) {
                    int toCopy = Math.min(4 - blockLen, read);
                    System.arraycopy(buffer, 0, block, blockLen, toCopy);
                    blockLen += toCopy;
                    i = toCopy;

                    if (blockLen == 4) {
                        int a = ((block[0] & 0xFF) << 8) | (block[1] & 0xFF);
                        int b = ((block[2] & 0xFF) << 8) | (block[3] & 0xFF);
                        int res = ElGamalService.decrypt(a, b, p, x);
                        out.write(res);
                        blockLen = 0;
                    }
                }

                for (; i + 3 < read; i += 4){
                    int a = ((buffer[i] & 0xFF) << 8) | (buffer[i + 1] & 0xFF);
                    int b = ((buffer[i + 2] & 0xFF) << 8) | (buffer[i + 3] & 0xFF);
                    int res = ElGamalService.decrypt(a, b, p, x);
                    out.write(res);
                }

                if (i < read) {
                    blockLen = read - i;
                    System.arraycopy(buffer, i, block, 0, blockLen);
                }
            }

            if (blockLen != 0) {
                throw new IOException("Поврежденный файл: неполный блок шифртекста");
            }
        }
    }
}
