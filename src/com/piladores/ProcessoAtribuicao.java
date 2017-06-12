/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.util.ArrayList;

/**
 *
 * @author leandro
 */
public class ProcessoAtribuicao implements Processo {
    private ArrayList<Object> list;

    public ProcessoAtribuicao() {
        list = new ArrayList<>();
    }

    @Override
    public ArrayList<Object> verify(Token token) {
        if (token.getType() == TipoToken.TK_ID) {
            list.add(TipoToken.TK_ID);
            list.add(TipoToken.TK_ATRIBUICAO);
            list.add(new ProcessoExpressao());
            return this.list;
        }
        
        return null;
    }

      @Override
    public String tokenExpected() {
        return "Esperado um IDENTIFICADOR";
    }
}
