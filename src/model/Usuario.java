package model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private final String nome;
    private final String senha;
    private boolean isOnline;

    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
        this.isOnline = false;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public boolean isIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Usuario) {
            Usuario aux = (Usuario) o;

            return aux.getNome().equals(this.nome)&& aux.getSenha().equalsIgnoreCase(this.senha);
        } else {
            return false;
        }
    }

}
