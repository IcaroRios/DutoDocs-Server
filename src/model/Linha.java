/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Neida
 */
public class Linha {

    private Usuario usuario;
    private Arquivo arquivo;
    private int posicao;

    public Linha(Usuario usuario, Arquivo arquivo, int posicao) {
        this.usuario = usuario;
        this.arquivo = arquivo;
        this.posicao = posicao;
    }

    public Linha(Usuario usuario, Arquivo arquivo) {
        this.usuario = usuario;
        this.arquivo = arquivo;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Linha) {
            Linha outro = (Linha) o;
            return outro.getUsuario().equals(this.getUsuario())
                    && outro.getArquivo().equals(this.arquivo);

        }
        return false;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public int getPosicao() {
        return posicao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }

}
