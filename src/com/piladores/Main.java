package com.piladores;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader("entrada.txt");
            outputStream = new FileWriter("saida.txt");

            String lexema = "";

//            String pattern = "escreva ";
//            Pattern tk_escreva = Pattern.compile(pattern);
//
//            String tk_texto_pattern = "\"[^\"]*\"";
//            Pattern tk_texto = Pattern.compile(tk_texto_pattern);
//
//            Matcher m;

            int c;
            boolean stringOpen = false;
            while ((c = inputStream.read()) != -1) {

//                outputStream.write(c);


                lexema += (char) c;

                // decimal ascii
//                System.out.println("caractere atual -> '" + c + "'");


                System.out.println("lexema -> '" + lexema + "'");

                if (c == 10) {
                    System.out.println("tk_quebralinha");
                    lexema = "";
                }
                else
                if (lexema.equals("escreva ")) {
                    System.out.println("tk_escreva encontrado");
                    lexema = "";
                }
                else
                if (lexema.equals("leia ")) {
                    System.out.println("tk_leia encontrado");
                    lexema = "";

                }
                else
                if (lexema.matches("\".*\"")) {
                    System.out.println("tk_texto encontrado");
                    lexema = "";
                }
                else
                if (lexema.matches("[a-z]* "))  {
                    System.out.println("tk_variavel encontrado");
                    lexema = "";
                }



//                switch (lexema) {
//
//                    case "escreva":
//                        outputStream.write("tk_escreva ");
//                        System.out.println("token found");
//                        lexema = "";
//                        break;
//                    case "leia":
//                        outputStream.write("tk_leia ");
//                        System.out.println("token found");
//                        lexema = "";
//                        break;
//                    case "\n":
//                        outputStream.write("\n");
//                        lexema = "";
//                        break;
//                    case "\"":
//                        stringOpen = !stringOpen;
//                    default:
//                        if (lexema.matches("\"[^\"]*\"")) {
//                            System.out.println("token found");
//                            outputStream.write("tk_texto ");
//                            lexema = "";
//                            break;
//                        }
//                        else
//                        if (c == ' ' && stringOpen == false) {
//                                System.out.println("found a space");
//                                if (!lexema.matches("escreva|leia|\n|\"")) {
//                                    System.out.println("token found");
//                                    outputStream.write("tk_variavel");
//                                }
//                                lexema = "";
//                                break;
//                        }
//
//                }

//                m = tk_escreva.matcher(lexema);
//
//                if (m.find()) {
//                    System.out.println("\ntoken found " + m.group(0));
//                    outputStream.write("tk_escreva ");
//                    lexema = "";
//                }

//                if (lexema.equals("escreva ")) {
//                    outputStream.write("tk_escreva ");
//                    lexema = "";
//                }


//                if (lexema.equals("\"escre va\"")) {
//                    System.out.println("\nfound token: tk_texto");
//                    outputStream.write("tk_texto ");
//                    lexema = "";
//                }

//                if (lexema.equals("\n")) {
//                    System.out.println("linebreak found");
//                    outputStream.write("\n");
//                    lexema = "";
//                }

//                if (lexema.equals("leia ")) {
//                    outputStream.write("tk_leia ");
//                    lexema = "";
//                }

//                if (lexema.equals("variavel")) {
//                    outputStream.write("tk_id ");
//                    lexema = "";
//
//                }


            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
