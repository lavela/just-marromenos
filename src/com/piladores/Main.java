package com.piladores;

import java.io.*;
import java.util.List;

public class Main {
    
    public static void main(String[] args) throws IOException {
        /*sintatico = new AnalisadorSintatico(lexical.getTokens());
	sintatico.start();*/
        
        File inputStream = new File("entrada.txt");
        
        AnalisadorLexico lexico = new AnalisadorLexico(inputStream);
	lexico.start();
        
        if (lexico.hasError()) {
            lexico.getErros().stream().forEach((e) -> {
                System.out.println(e.toString());                
            });
        }
        
        List<Token> tokens = lexico.getTokens();
         
        System.out.println("Tokens Gerados");
        tokens.stream().map((t) -> {
            return t;
        }).forEach((t) -> {
            System.out.println(t.toString());
        });
    }
}
