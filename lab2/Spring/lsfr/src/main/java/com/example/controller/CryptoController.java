package com.example.controller;

import com.example.dto.CryptoRequest;
import com.example.service.CryptoService;

import crypt.Lfsr;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    private final CryptoService service;

    public CryptoController(CryptoService service) {
        this.service = service;
    }

    @PostMapping("/encrypt")
    public byte[] encrypt(
            @RequestParam("file") MultipartFile file,
            @RequestParam("polynomial") String polynomial,
            @RequestParam("seed") long seed
    ) throws Exception {

        InputStream in = file.getInputStream();

        return service.process(
                in,
                polynomial,
                seed
        );
    }

    @PostMapping("/key")
    public byte[] generateKey(
            @RequestParam("polynomial") String polynomial,
            @RequestParam("seed") long seed,
            @RequestParam("length") int length
    ) {
        Lfsr lfsr = new Lfsr(polynomial, seed);
        return lfsr.generateKey(length);
    }

}