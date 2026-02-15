package org.example.app.crypts;

import java.util.ArrayList;
import java.util.HashMap;

public class Playfair{
    private record idx(int i, int j){}
    public static final int ALPHABET_SIZE = 26;
    public static final int TABLE_SIZE = (int) Math.sqrt(ALPHABET_SIZE);
    public static final char EMPTY_LETTER = 'x';
    public static final char SECOND_EMPTY_LETTER = 'q';
    private Playfair(){}
    public static String encrypt(String key, String planeText){
        HashMap<Character, idx> indexes = new HashMap<>(ALPHABET_SIZE);
        char[][] table = makeTable(key, indexes);
        ArrayList<Integer> originalIndexes = new ArrayList<>(planeText.length());
        String mainLetters = makeMainLetters(planeText, originalIndexes);
        if (mainLetters.isEmpty())
            return planeText;
        StringBuilder source = new StringBuilder();
        ArrayList<Integer> upperLetters = new ArrayList<>();
        ArrayList<Integer> emptyIndexes = new ArrayList<>();
        int i;
        for (i = 0; i < mainLetters.length() - 1; i += 2){
            i += analyzePair(source, mainLetters.charAt(i), mainLetters.charAt(i + 1), upperLetters, emptyIndexes);
        }
        if (i == mainLetters.length() - 1)
            analyzePair(source, mainLetters.charAt(i), mainLetters.charAt(i), upperLetters, emptyIndexes);
        String encrypted = doEncrypt(source.toString(), table, indexes);
        return backtrack(encrypted, upperLetters, originalIndexes, emptyIndexes, planeText);
    }

    // Добавьте этот метод в класс Playfair

    public static String decrypt(String key, String planeText) {
        HashMap<Character, idx> indexes = new HashMap<>(ALPHABET_SIZE);
        char[][] table = makeTable(key, indexes);
        ArrayList<Integer> originalIndexes = new ArrayList<>(planeText.length());
        String mainLetters = makeMainLetters(planeText, originalIndexes);
        if (mainLetters.isEmpty())
            return planeText;
        if (mainLetters.length() % 2 != 0)
            throw new IllegalArgumentException("Длина расшифруемого текста должны быть четной");
        StringBuilder source = new StringBuilder();
        ArrayList<Integer> upperLetters = new ArrayList<>();
        ArrayList<Integer> emptyIndexes = new ArrayList<>();
        for (int i = 0; i < mainLetters.length(); i++){
            char c = mainLetters.charAt(i);
            if (Character.isUpperCase(c))
                upperLetters.add(i);
            source.append(Character.toLowerCase(c));
        }
        String encrypted = doDecrypt(source.toString(), table, indexes);
        return backtrack(encrypted, upperLetters, originalIndexes, emptyIndexes, planeText);
    }

    public static String backtrack(String encrypted, ArrayList<Integer> upperLetters, ArrayList<Integer> originalIndexes, ArrayList<Integer> emptyIndexes, String planeText){
        StringBuilder res = new StringBuilder(encrypted);
        {
            int uppInd = 0, uppI = upperLetters.size() == 0 ? -1 : upperLetters.get(uppInd);
            for (int i = 0; uppI != -1; i++){
                if (i == uppI) {
                    res.setCharAt(i, Character.toUpperCase(res.charAt(i)));
                    uppInd++;
                    uppI = uppInd < upperLetters.size() ? upperLetters.get(uppInd) : -1;
                }
            }
        }
        encrypted = res.toString();
        res = new StringBuilder();
        int encI = 0,
            origI = 0, origVal = originalIndexes.get(origI),
            emptyI = 0, emptyVal = emptyIndexes.size() != 0 ? emptyIndexes.get(emptyI) : -1;
        for (int i = 0; i < planeText.length(); i++){
            if (i == origVal){
                res.append(encrypted.charAt(encI));
                encI++;
                origI++;
                origVal = origI < originalIndexes.size() ? originalIndexes.get(origI) : -1;
                if (encI == emptyVal){
                    res.append(encrypted.charAt(encI));
                    encI++;
                    emptyI++;
                    emptyVal = emptyI < emptyIndexes.size() ? emptyIndexes.get(emptyI) : -1;
                }
            }
            else
                res.append(planeText.charAt(i));
        }
        return res.toString();
    }

    private static String doDecrypt(String source,
                                    char[][] table,
                                    HashMap<Character, idx> indexes){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < source.length() - 1; i += 2){
            char m1 = source.charAt(i), m2 = source.charAt(i + 1);
            idx idx1 = indexes.get(m1), idx2 = indexes.get(m2);
            if (idx1.i == idx2.i)
                res.append(getLeft(table, idx1)).append(getLeft(table, idx2));
            else if (idx1.j == idx2.j)
                res.append(getUp(table, idx1)).append(getUp(table, idx2));
            else
                res.append(getAngle(table, idx1, idx2)).append(getAngle(table, idx2, idx1));
        }
        return res.toString();
    }

    private static String doEncrypt(String source, char[][] table, HashMap<Character, idx> indexes){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < source.length() - 1; i += 2){
            char m1 = source.charAt(i), m2 = source.charAt(i + 1);
            idx idx1 = indexes.get(m1), idx2 = indexes.get(m2);
            if (idx1.i == idx2.i)
                res.append(getRight(table, idx1)).append(getRight(table, idx2));
            else if (idx1.j == idx2.j)
                res.append(getDown(table, idx1)).append(getDown(table, idx2));
            else
                res.append(getAngle(table, idx1, idx2)).append(getAngle(table, idx2, idx1));
        }
        return res.toString();
    }


    private static char getRight(char[][] table, idx ind){
        return table[ind.i][(ind.j + 1) % TABLE_SIZE];
    }

    private static char getDown(char[][] table, idx ind){
        return table[(ind.i + 1) % TABLE_SIZE][ind.j];
    }

    private static char getUp(char[][] table, idx ind){
        return table[(ind.i - 1 + TABLE_SIZE) % TABLE_SIZE][ind.j];
    }

    private static char getLeft(char[][] table, idx ind){
        return table[ind.i][(ind.j - 1 + TABLE_SIZE) % TABLE_SIZE];
    }

    private static char getAngle(char[][] table, idx idx1, idx idx2){
        return table[idx1.i][idx2.j];
    }

    private static String makeMainLetters(final String planeText, ArrayList<Integer> originalIndexes){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < planeText.length(); i++){
            char j = planeText.charAt(i);
            if((j >= 'a' && j <= 'z') || (j >= 'A' && j <= 'Z')){
                res.append(j);
                originalIndexes.add(i);
            }
        }
        return res.toString();
    }

    private static int analyzePair(StringBuilder source, char m1, char m2, ArrayList<Integer> upperLetters, ArrayList<Integer> emptyIndexes){
        boolean isM2Upper = Character.isUpperCase(m2);
        m2 = Character.toLowerCase(m2);
        if (Character.isUpperCase(m1)){
            upperLetters.add(source.length());
            m1 = Character.toLowerCase(m1);
        }
        if (m1 != m2){
            source.append(m1).append(m2);
            if (isM2Upper)
                upperLetters.add(source.length() - 1);
            return 0;
        }
        else{
            if (m1 != EMPTY_LETTER)
                source.append(m1).append(EMPTY_LETTER);
            else
                source.append(m1).append(SECOND_EMPTY_LETTER);
            emptyIndexes.add(source.length() - 1);
            return -1;
        }
    }

    private static char[][] makeTable(String key, HashMap<Character, idx> indexes){
        char[][] res = new char[TABLE_SIZE][TABLE_SIZE];
        int k = 0;
        for (int i = 0; i < key.length(); i++){
            char j = key.charAt(i);
            if (j == 'j')
                j = 'i';
            if (!indexes.containsKey(j)){
                res[k / TABLE_SIZE][k % TABLE_SIZE] = j;
                indexes.put(j, new idx(k / TABLE_SIZE, k % TABLE_SIZE));
                k++;
            }
        }
        if (indexes.size() != ALPHABET_SIZE){
            for (int i = 0; i < ALPHABET_SIZE; i++){
                char j = (char)('a' + i);
                if (!indexes.containsKey(j) && j != 'j'){
                    res[k / TABLE_SIZE][k % TABLE_SIZE] = j;
                    indexes.put(j, new idx(k / TABLE_SIZE, k % TABLE_SIZE));
                    k++;
                }
            }
        }
        indexes.put('j', indexes.get('i'));
        return res;
    }
}
