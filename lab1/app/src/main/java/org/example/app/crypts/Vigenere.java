package org.example.app.crypts;

import java.util.ArrayList;
import java.util.HashMap;

public class Vigenere {
    private Vigenere(){}
    public static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    public static final int ALPHABET_SIZE = ALPHABET.length();
    public static final int TABLE_SIZE = ALPHABET_SIZE;
    private static final HashMap<Character, Integer> POSITION_OF_LETTER_IN_ALPHABET = new HashMap<Character, Integer>(ALPHABET_SIZE);
    private static final char[][] table = makeTable();
    public static String encrypt(String key, String planeText){
        ArrayList<Integer> originalIndexes = new ArrayList<>(planeText.length());
        String mainLetters = makeMainLetters(planeText, originalIndexes);
        if (mainLetters.isEmpty())
            return planeText;
        ArrayList<Integer> upperLetters = new ArrayList<>();
        ArrayList<Integer> emptyIndexes = new ArrayList<>();
        String source = makeSource(mainLetters, upperLetters);
        key = makeKey(key, planeText.length());
        String encrypted = doEncrypt(key, source);
        return Playfair.backtrack(encrypted, upperLetters, originalIndexes, emptyIndexes, planeText);
    }
    public static String decrypt(String key, String planeText){
        ArrayList<Integer> originalIndexes = new ArrayList<>(planeText.length());
        String mainLetters = makeMainLetters(planeText, originalIndexes);
        if (mainLetters.isEmpty())
            return planeText;
        ArrayList<Integer> upperLetters = new ArrayList<>();
        ArrayList<Integer> emptyIndexes = new ArrayList<>();
        String source = makeSource(mainLetters, upperLetters);
        key = makeKey(key, planeText.length());
        String encrypted = doDecrypt(key, source);
        return Playfair.backtrack(encrypted, upperLetters, originalIndexes, emptyIndexes, planeText);
    }
    private static char[][] makeTable(){
        char[][] res = new char[TABLE_SIZE][TABLE_SIZE];
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            for (int j = 0; j < ALPHABET_SIZE; j++)
                res[i][j] = ALPHABET.charAt((j + i) % ALPHABET_SIZE);
            POSITION_OF_LETTER_IN_ALPHABET.put(ALPHABET.charAt(i), i);
        }
        return res;
    }
    private static int getPos(char c){
        return POSITION_OF_LETTER_IN_ALPHABET.get(c);
    }
    private static String makeMainLetters(final String planeText, ArrayList<Integer> originalIndexes){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < planeText.length(); i++){
            char j = planeText.charAt(i);
            if((j >= 'а' && j <= 'я') || (j >= 'А' && j <= 'Я') || (j == 'ё') || (j == 'Ё')){
                res.append(j);
                originalIndexes.add(i);
            }
        }
        return res.toString();
    }
    private static boolean isLower(char c){
        return (c >= 'а' && c <= 'я') || (c == 'ё');
    }
    private static boolean isUpper(char c){
        return !isLower(c);
    }
    public static char toLower(char c) {
        // Основной диапазон заглавных русских букв А..Я
        if (c >= 'А' && c <= 'Я') {
            // Смещение между заглавной и строчной в Unicode = 32
            return (char) (c + 32);
        }
        // Отдельно обрабатываем букву Ё
        if (c == 'Ё') {
            return 'ё';
        }
        // Остальные символы не изменяем
        return c;
    }
    private static String makeKey(String key, int targetSize){
        StringBuilder sb = new StringBuilder();
        while (sb.length() < targetSize) {
            sb.append(key);
            key = updateKey(key);
        }
        return sb.toString();
    }
    private static String makeSource(String text, ArrayList<Integer> upper){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            if (isUpper(c))
                upper.add(i);
            sb.append(toLower(c));
        }
        return sb.toString();
    }
    private static String doEncrypt(String key, String text){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            char a = text.charAt(i), b = key.charAt(i);
            res.append(table[getPos(a)][getPos(b)]);
        }
        return res.toString();
    }
    private static String updateKey(String key){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < key.length(); i++){
            res.append(ALPHABET.charAt((getPos(key.charAt(i)) + 1) % ALPHABET_SIZE));
        }
        return res.toString();
    }
    private static String doDecrypt(String key, String text){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i++){
            char a = text.charAt(i), b = key.charAt(i);
            int j = 0;
            while (table[getPos(b)][j] != a)
                j++;
            res.append(table[0][j]);
        }
        return res.toString();
    }
}
