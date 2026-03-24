package com.example.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CryptoRequest {
    private String text;
    private String polynomial;
    private long seed;

}