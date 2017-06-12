/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leandro
 */
public class Executavel {
    //Auxiliar para procurar fimse
    private int aux;

    //Index para incrementar lista
    private int index;

    //Lista de Tokens
    private final List<Token> tokens;

    private final Map<String, String> variaveis;

   // private final Console console;
    private final TabelaSimbolo tabelaSimbolo;
    private boolean hasError;

    //Construtor
    public Executavel(List<Token> tokens) {
        this.tokens = tokens;
        this.tabelaSimbolo = TabelaSimbolo.getInstance();
        this.variaveis = new HashMap<>();
    }

    //Iniciar execução do código
    public void start() {
        //console.setText("");
        while (hasNext()) {
            if (!hasError) {
                makeOperation(getNext());
            } else {
                break;
            }
        }
    }

    private void makeOperation(Token token) {
        if (null != token.getType()) {
            switch (token.getType()) {
                case TK_ESCREVA:
                    printScreen();
                    break;
                case TK_LEIA:
                    read();
                    break;
                case TK_SE:
                    Token op1 = getNext();
                    Token relop = getNext();
                    Token op2 = getNext();
                    //Verifica se o "se" é falso
                    if (!operationRelop(op1, relop, op2)) {
                        searchEndIf();
                    }
                    break;
                case TK_ID:
                    //obtem o valor do token (Variavel)
                    String var = tabelaSimbolo.getString(token.getPointer());
                    if (getNext().getType() == TipoToken.TK_ATRIBUICAO) {
                        Token tkValue1 = getNext();
                        if (tkValue1.getType() == TipoToken.TK_NUMERO) {
                            variaveis.put(var, tabelaSimbolo.getString(tkValue1.getPointer()));
                        } else if (tkValue1.getType() == TipoToken.TK_ID) {
                            Token tkOp = getNext();
                            Token tkValue2 = getNext();
                            String result = operationOp(tkValue1, tkOp, tkValue2);
                            variaveis.put(var, result);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void searchEndIf() {

        //Contador para o "se"
        aux = 1;

        //Continua até aux = 0
        //Aux é igual a 0 quando encontrar fimse para o "se"
        while (aux != 0) {

            Token nextTk = getNext();
            //Se encontrar outro "se" incrementa o aux
            if (nextTk.getType() == TipoToken.TK_SE) {
                aux++;
                continue;
            }
            //Se encontrar "fimse" reduz o aux
            if (nextTk.getType() == TipoToken.TK_FIM_SE) {
                aux--;
            }
        }
    }

    private boolean operationRelop(Token tkOp1, Token tkRelop, Token tkOp2) {

        try {
            double op1;
            double op2;
            String relop = tabelaSimbolo.getString(tkRelop.getPointer());

            if (tkOp1.getType() == TipoToken.TK_NUMERO) {
                op1 = tabelaSimbolo.getDouble(tkOp1.getPointer());
            } else {

                op1 = Double.parseDouble(variaveis.get(tabelaSimbolo.getString(
                        tkOp1.getPointer())));
            }

            if (tkOp2.getType() == TipoToken.TK_NUMERO) {
                op2 = tabelaSimbolo.getDouble(tkOp2.getPointer());
            } else {
                op2 = Double.parseDouble(variaveis.get(tabelaSimbolo.getString(
                        tkOp2.getPointer())));
            }

            boolean result = false;

            switch (relop) {
                case ">":
                    if (op1 > op2) {
                        result = true;
                    }
                    break;
                case "<":
                    if (op1 < op2) {
                        result = true;
                    }
                    break;
                case ">=":
                    if (op1 >= op2) {
                        result = true;
                    }
                    break;
                case "<=":
                    if (op1 <= op2) {
                        result = true;
                    }
                    break;
                case "==":
                    if (op1 == op2) {
                        result = true;
                    }
                    break;
                case "!=":
                    if (op1 != op2) {
                        result = true;
                    }
                    break;
            }

            return result;
        } catch (NumberFormatException e) {
            /*console.clear();
            console.setForeground(Color.RED);
            console.setText(e.getMessage());*/
            hasError = true;
            return false;
        }
    }

    private String operationOp(Token tkValue1, Token tkOp, Token tkValue2) {
        try {
            String operator = tabelaSimbolo.getString(tkOp.getPointer());
            Double value1;
            Double value2;
            if (tkValue1.getType() == TipoToken.TK_NUMERO) {
                value1 = tabelaSimbolo.getDouble(tkValue1.getPointer());
            } else {
                value1 = Double.parseDouble(variaveis.get(tabelaSimbolo.getString(tkValue1.getPointer())));
            }

            if (tkValue2.getType() == TipoToken.TK_NUMERO) {
                value2 = tabelaSimbolo.getDouble(tkValue2.getPointer());
            } else {
                value2 = Double.parseDouble(variaveis.get(tabelaSimbolo.getString(tkValue2.getPointer())));
            }

            double value = 0;

            switch (operator) {
                case "+":
                    value = value1 + value2;
                    break;
                case "-":
                    value = value1 - value2;
                    break;
                case "*":
                    value = value1 * value2;
                    break;
                case "/":
                    if (value1 == 0) {
                        value = 0;
                    } else {
                        value = value1 / value2;
                    }
                    break;
            }

            return String.valueOf(value);

        } catch (NumberFormatException e) {
            /*console.clear();
            console.setForeground(Color.RED);
            console.setText(e.getMessage());*/
            hasError = true;
            return null;
        }
    }

    private void printScreen() {
        Token nextTk = getNext();
        //Se for text ou número exibe na tela o que estiver na tabela de simbolos
        if (nextTk.getType() == TipoToken.TK_TEXTO
                || nextTk.getType() == TipoToken.TK_NUMERO) {
            String string = tabelaSimbolo.getString(nextTk.getPointer());
            //console.appendln(string.replaceAll("\"", ""));

        } else if (nextTk.getType() == TipoToken.TK_ID) {
            //Se for um id procura o valor dentro da tabela de variaveis
            //console.appendln(variaveis.get(tabelaSimbolo.getString(nextTk.getPointer())));
        }
    }

    private void read() {
        Token tk = getNext();
        //String value = console.read();
        //variaveis.put(tabelaSimbolo.getString(tk.getPointer()), value);
    }

    private boolean hasNext() {
        return tokens.size() > index;
    }

    private Token getNext() {
        index++;
        Token tk = tokens.get(index -1);
        return tk;
    }
}
