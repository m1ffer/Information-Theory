package com.example.service;

import crypt.Lfsr;
import crypt.StreamCipher;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CryptoService {

    public byte[] process(InputStream in, String polynomial, long seed) throws IOException {
            Lfsr lfsr = new Lfsr(polynomial, seed);

        try (
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            StreamCipher cipher = new StreamCipher(in, out, lfsr);
            cipher.crypt();
            return out.toByteArray();
        }
    }
}