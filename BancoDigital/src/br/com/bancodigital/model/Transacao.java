package br.com.bancodigital.model;


import java.time.LocalDateTime;

public class Transacao {
    private LocalDateTime data;
    private TipoTransacao tipo;
    private double valor;
    private String descricao;

    public Transacao(LocalDateTime data, TipoTransacao tipo, double valor, String descricao) {
        this.data = data;
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }
}
