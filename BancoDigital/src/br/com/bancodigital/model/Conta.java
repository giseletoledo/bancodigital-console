package br.com.bancodigital.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    private String numero;
    private Cliente cliente;
    protected double saldo;
    private String chavePix;
    private double limiteEspecial; // Limite especial para saques 
    private List<Transacao> transacoes; // Lista de transações

    public Conta(String numero, Cliente cliente, String chavePix, double limiteEspecial) {
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = 0.0;
        this.chavePix = chavePix;
        this.limiteEspecial = limiteEspecial;
        this.transacoes = new ArrayList<>();
    }
    // Getters
    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getChavePix() {
        return chavePix;
    }

    public double getLimiteEspecial() {
        return limiteEspecial;
    }

    // Método para definir a chave Pix
    public void setChavePix(String chavePix) {
        this.chavePix = chavePix;
    }

    // Métodos de operações bancárias
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }
        saldo += valor;
    }

    public boolean sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }

        // Verifica se o saque pode ser realizado com o saldo + limite especial
        if (saldo + limiteEspecial >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    public String gerarExtrato() {
        StringBuilder extrato = new StringBuilder();
        extrato.append("=== Extrato da Conta ===\n");
        extrato.append("Número da Conta: ").append(getNumero()).append("\n");
        extrato.append("Cliente: ").append(getCliente().getNome()).append("\n");
        extrato.append("Saldo Atual: R$ ").append(String.format("%.2f", getSaldo())).append("\n");

        extrato.append("\n=== Histórico de Transações ===\n");
        
        if (transacoes.isEmpty()) {
            extrato.append("Nenhuma transação realizada.\n");
        } else {
            for (Transacao t : transacoes) {
                extrato.append(t.getData()).append(" - ")
                       .append(t.getTipo()).append(": R$ ")
                       .append(String.format("%.2f", t.getValor()))
                       .append(" (").append(t.getDescricao()).append(")\n");
            }
        }

        return extrato.toString();
    }


}
