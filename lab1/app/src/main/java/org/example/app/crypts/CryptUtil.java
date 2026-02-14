package org.example.app.crypts;

public class CryptUtil {
    private CryptUtil(){}
    public static String playfairEncrypt(String key, String planeText){
        return Playfair.encrypt(key, planeText);
    }
    public static String playfairDecrypt(String key, String chiperText){
        return Playfair.decrypt(key, chiperText);
    }
}
