import crypt.Lfsr;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static final String POLYNOME = "x^27 + x^8 + x^7 + x + 1";
    public static final long SEED = Long.parseLong("1111111111111111111111111111111", 2);
    public static final Path PATH_TO_FILE = Paths.get("D:\\4sem\\ticopy\\Information-Theory\\lab2\\ReportInfo\\rows.txt");
    public static final int ROWS_NUMBER = 60;
    public static void main(String[] args) {
        Lfsr lfsr = new Lfsr(POLYNOME, SEED);
        try (BufferedWriter writer = Files.newBufferedWriter(
                PATH_TO_FILE,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)){
            for (int i = 0; i < ROWS_NUMBER; i++)
                writer.write(lfsr.makeRow() + "\n");
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
