/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author cleyb
 */
public class Modificacao {
    
    private String operacao;
    private char conteudo;
    private int carent;
    private long chegada;

    public Modificacao(String operacao, char conteudo, int carent) {
        this.operacao = operacao;
        this.conteudo = conteudo;
        this.carent = carent;
        chegada = System.currentTimeMillis();
    }

    public String getOperacao() {
        return operacao;
    }

    public char getConteudo() {
        return conteudo;
    }

    public int getCarent() {
        return carent;
    }

    public long getChegada() {
        return chegada;
    }
    
    
    
}
