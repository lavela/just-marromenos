/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leandro
 */
public class ProcessoExpressao implements Processo {
    private List<Object> list;

    public ProcessoExpressao() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_NUMERO) {
            list.add(TipoToken.TK_NUMERO);
            list.add(new ProcessoExpressao());
            return list;
        }
        
        if(token.getType() == TipoToken.TK_ID) {
            list.add(TipoToken.TK_ID);
            list.add(new ProcessoExpressaoD());
            return list;
        }
        

        return null;
    }

    @Override
    public String tokenExpected() {
        return "Esperado um NUMERO/IDENTIFICADOR";
    }
}
