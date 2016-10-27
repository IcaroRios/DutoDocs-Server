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
public class Arquivo {
    
    private String nome;

    public Arquivo(String nome) {
        this.nome = nome;
        
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Arquivo) {
            Arquivo outro = (Arquivo) o ;
            return outro.getNome().equalsIgnoreCase(this.nome);
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
