/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leandro
 */
public class TabelaSimbolo {
    private final Map<Integer, String> tabelaSimbolo;
    private static TabelaSimbolo instancia;
    
    public TabelaSimbolo() {
        this.tabelaSimbolo = new HashMap<>();
    }
    
    public static TabelaSimbolo getInstance() {
        if(instancia == null) {
            instancia = new TabelaSimbolo();
        }
        
        return instancia;
    }
    /**
     * Seta um novo lexema na tabela de simbolos
     *
     * @param lexema Lexema a ser adiciondo na tabela de simbolos.
     * @return Retorna o identificador do lexema adicionado.
     */
    public int put(String lexema) {
        int id = this.tabelaSimbolo.size() + 1;
        this.tabelaSimbolo.put(id, lexema);
        
        return id;
    }

    public Map<Integer, String> getSymbolTable() {
        return tabelaSimbolo;
    }
    
    public int getInteger(Integer pointer) {
        return Integer.parseInt(tabelaSimbolo.get(pointer));
    }
    
    public String getString(Integer pointer) {
        return tabelaSimbolo.get(pointer);
    }
    
    public double getDouble(Integer pointer) {
        return Double.parseDouble(tabelaSimbolo.get(pointer));
    }
    
    public void clear() {
        if(tabelaSimbolo != null) {
            tabelaSimbolo.clear();
        }
    }
}
