package br.com.bancodigital.model;

import java.time.LocalDate;

public class Cliente {
    private Long id; 
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Endereco endereco; // Agora usa a classe Endereco
    private TipoCliente tipoCliente;

    // Construtor com ID
    public Cliente(Long id, String cpf, String nome, LocalDate dataNascimento, Endereco endereco, TipoCliente tipoCliente) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }

    // Construtor sem ID (para uso interno)
    public Cliente(String cpf, String nome, LocalDate dataNascimento, Endereco endereco, TipoCliente tipoCliente) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public double getTaxaRendimento() {
        return switch (tipoCliente) {
            case COMUM -> 0.5;
            case SUPER -> 0.7;
            case PREMIUM -> 0.9;
        };
    }
}