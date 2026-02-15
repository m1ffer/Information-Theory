package org.example.app.crypts;

public class CryptUtil {
    private CryptUtil(){}
    public static String playfairEncrypt(String key, String planeText){
        return Playfair.encrypt(key, planeText);
    }
    public static String playfairDecrypt(String key, String ciperText){
        return Playfair.decrypt(key, ciperText);
    }
    public static String vigenereEncrypt(String key, String planeText){
        if (key.isEmpty())
            throw new IllegalArgumentException("Ключ не может быть пустым");
        return Vigenere.encrypt(key, planeText);
    }
    public static String vigenereDecrypt(String key, String ciperText){
        if (key.isEmpty())
            throw new IllegalArgumentException("Ключ не может быть пустым");
        return Vigenere.decrypt(key, ciperText);
    }
}
