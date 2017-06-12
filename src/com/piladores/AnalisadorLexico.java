/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leandro
 */
public class AnalisadorLexico {
    //Número da linha em que o cursor está posicionado
    private int line;

    //Número da coluna em que o cursor está posicionado
    private int column;

    //codigo fonte
    char[] sourceCode;

    //posição atual do cursor
    int index;

    //Lista com os possiveis tipos de tokens do lexema
    private List<TipoToken> possibleTokens;

    //Lista com todos os tokens que foram gerados
    private List<Token> tokens;

    //Erro gerado
    private ErroLexico error;

    //Armazena os erros gerados
    private final List<ErroLexico> erros;

    public AnalisadorLexico(File file) throws IOException {
        this.sourceCode = ArquivoUtilitario.openFile(file).toCharArray();
        TabelaSimbolo.getInstance().clear();
        this.tokens = new ArrayList<>();
        this.erros = new ArrayList<>();
        this.column = 0;
        this.line = 1;
        this.possibleTokens = new ArrayList<>();
    }

    public List<Token> getTokens() {
        return this.tokens;
    }

    public boolean hasError() {
        return this.erros.size() > 0;
    }

    public List<ErroLexico> getErros() {
        return this.erros;
    }

    public void start() throws IOException {
        StringBuilder buffer = new StringBuilder();

        while (this.index < this.sourceCode.length) {
            //adiciona o char obtido no buffer
            buffer.append(nextChar());

            //Verifica se tem um possivel tipo de token encontrado
            if (verifyPossibleTokens(buffer.toString())) {
                Token token;

                //Se for do tipo string gera o token com todos os caracteres do buffer
                //Caso contrário gera o token sem o utilmo caracter 
                //(caracter que produziu o erro e invalidou o token anterior)
                if (this.possibleTokens.get(0) == TipoToken.TK_TEXTO) {
                    token = gerarToken(buffer.toString());
                } else {
                    token = gerarToken(buffer.substring(0, buffer.length() - 1));
                    String s = buffer.substring(0, buffer.length()-1);
                    if(!(s.equals("\n") || s.equals("\t") || s.equals("\r"))){                        
                        this.column--;
                   }
                    this.index--;
                }

                //Adiciona o token na lista
                addToken(token);
                this.possibleTokens.clear();
                buffer = new StringBuilder();
            }

            //Se não for gerado um token, verifica se existe um erro. Se true
            //adiciona o erro à lista de erros
            if (this.error != null) {
                this.erros.add(this.error);
                this.error = null;
                buffer = new StringBuilder();
            }

            this.index++;
        }

        if (!this.possibleTokens.isEmpty()) {
            String lexema = buffer.toString();
            Token token = gerarToken(lexema);
            addToken(token);
        }
    }

    private void addToken(Token token) {
        if (token.getType() != TipoToken.TK_DELIM) {
            this.tokens.add(token);
        }
    }

    private Token gerarToken(String lexema) {
        TipoToken type = this.possibleTokens.get(0);

        //Se for do tipo não for palavra reservada adiciona o lexema na
        //tabela de simbolo e guarda seu id no novo token
        if (type == TipoToken.TK_ID || type == TipoToken.TK_TEXTO
                || type == TipoToken.TK_NUMERO || type == TipoToken.TK_OP
                || type == TipoToken.TK_RELOP) {

            int id = TabelaSimbolo.getInstance().put(lexema);
            return new Token(type, id, this.line, this.column);
        }

        //Retorna um token sem valor atribuido (palavras reservadas)
        return new Token(type);
    }

    private boolean verifyPossibleTokens(String lexema) throws IOException {
        List<TipoToken> temp = new ArrayList<>();
        if(lexema.length() == 1 && lexema.charAt(0) == '!') {
            temp.add(TipoToken.TK_RELOP);
        }
        if (lexema.charAt(0) == '\"') { //Aspas
            if (this.index + 1 == this.sourceCode.length
                    && lexema.charAt(lexema.length() - 1) != '\"') {
                this.error = new ErroLexico(line, column, "String não formado.");
                return false;
            }

            if (lexema.length() > 1 && lexema.charAt(lexema.length() - 1) == '\"') {
                return true;
            }

            temp.add(TipoToken.TK_TEXTO);
        } else { //Se é um texto não pode ser de outro tipo
            if (lexema.matches(TipoToken.TK_ESCREVA.padrao)) {
                temp.add(TipoToken.TK_ESCREVA);
            }
            if (lexema.matches(TipoToken.TK_LEIA.padrao)) {
                temp.add(TipoToken.TK_LEIA);
            }
            if (lexema.matches(TipoToken.TK_SE.padrao)) {
                temp.add(TipoToken.TK_SE);
            }
            if (lexema.matches(TipoToken.TK_FIM_SE.padrao)) {
                temp.add(TipoToken.TK_FIM_SE);
            }
            if (lexema.matches(TipoToken.TK_ATRIBUICAO.padrao)) {
                temp.add(TipoToken.TK_ATRIBUICAO);
            }
            if (lexema.matches(TipoToken.TK_OP.padrao)) {
                temp.add(TipoToken.TK_OP);
            }
            if (lexema.matches(TipoToken.TK_RELOP.padrao)) {
                temp.add(TipoToken.TK_RELOP);
            }
            if (lexema.matches(TipoToken.TK_NUMERO.padrao)) {
                temp.add(TipoToken.TK_NUMERO);
            }
            if (lexema.matches(TipoToken.TK_ID.padrao)) {
                temp.add(TipoToken.TK_ID);
            }
            if (lexema.matches(TipoToken.TK_DELIM.padrao)) {
                temp.add(TipoToken.TK_DELIM);
            }
        }

        if (temp.isEmpty() && this.possibleTokens.isEmpty()) {
            this.error = new ErroLexico(this.line, this.column - 1, "Caracter inválido");
            return false;
        } else if (temp.isEmpty() && this.possibleTokens.get(0) == TipoToken.TK_NUMERO
                && lexema.charAt(lexema.length() - 1) == '.') {
            this.error = new ErroLexico(this.line, this.column - 1, "Número mal formado.");
            return false;
        }
        //Se a lista temporária estiver vazia nenhum tipo de token foi 
        //associado ao lexema atual
        if (temp.isEmpty()) {
            return true;
        }

        this.possibleTokens = temp;
        return false;
    }

    /**
     * Obtém o próximo carachter a ser lido. Atualiza as posições das linhas e
     * das colunas
     *
     * @return valor o caracter lido;
     */
    private char nextChar() throws IOException {
        char ch = this.sourceCode[index];

        switch (ch) {
            case ' ': //espaco
                this.column++;
                break;
            case '\r': //para sistemas windows onde a quebra de linha é 13 e 10
                if (possibleTokens.isEmpty()) {
                    this.index++;
                    ch = this.sourceCode[index];
                }
            case '\n': //quebra de linha para sistemas unix
                if (possibleTokens.isEmpty()) {
                    this.column = 1;
                    this.line++;
                    break;
                }
            default: //cada caracter acrescenta uma coluna
                this.column++;
        }

        return ch;
    }
}
