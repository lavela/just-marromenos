/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author leandro
 */
public class ArquivoUtilitario {
    public static final int END_OF_FILE = -1;

    public static String openFile(File file) throws FileNotFoundException, IOException {
        StringBuilder stb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        
        int read = br.read();
        while(read != END_OF_FILE) {
            stb.append((char) read);
            read = br.read();
        }
        
        return stb.toString();
    }

}
