package com.ifsp.Auroraprojeto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


 @Entity
@Table(name = "conteudos")
public class Conteudo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String titulo;
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private String descricao;
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private String urlLink; // Link do YouTube ou PDF no Drive

    @Enumerated(EnumType.STRING)
    private Disciplina disciplina; // MATEMATICA ou PORTUGUES

    @Enumerated(EnumType.STRING)
    private TipoConteudo tipo; // VIDEO, PROVA, GABARITO

   
}
    

