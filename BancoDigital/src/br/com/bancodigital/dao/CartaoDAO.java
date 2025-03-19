package br.com.bancodigital.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.bancodigital.model.Cartao;
import br.com.bancodigital.model.CartaoCredito;
import br.com.bancodigital.model.CartaoDebito;

public class CartaoDAO {
    private List<Cartao> cartoes = new ArrayList<>();
    private Set<String> numerosCartao = new HashSet<>(); // Armazena números de cartão únicos

    // Adiciona um cartão à lista
    public void adicionarCartao(Cartao cartao) {
        if (numerosCartao.contains(cartao.getNumero())) {
            throw new IllegalArgumentException("Número de cartão já existe!");
        }
        cartoes.add(cartao);
        numerosCartao.add(cartao.getNumero());
        System.out.println("Cartão adicionado com sucesso!");
    }

    // Aplica taxas a todos os cartões de crédito
    public void aplicarTaxasCartoes() {
        System.out.println("Aplicando taxas dos cartões de crédito...");
        for (Cartao cartao : cartoes) {
            if (cartao instanceof CartaoCredito) {
                CartaoCredito cartaoCredito = (CartaoCredito) cartao;
                cartaoCredito.aplicarTaxaUtilizacao(); // Aplica a taxa de utilização
            }
        }
    }

    // Aplica seguros a todos os cartões de crédito
    public void aplicarSeguroCartoes() {
        System.out.println("Aplicando seguros dos cartões...");
        for (Cartao cartao : cartoes) {
            if (cartao instanceof CartaoCredito) {
                CartaoCredito cartaoCredito = (CartaoCredito) cartao;
                cartaoCredito.aplicarSeguroViagem(); // Aplica o seguro viagem
                cartaoCredito.aplicarSeguroFraude(); // Aplica o seguro de fraude
            }
        }
    }

    // Gera o extrato de todos os cartões
    public void gerarExtratoCartoes() {
        System.out.println("Gerando extrato de cartões...");
        for (Cartao cartao : cartoes) {
            if (cartao instanceof CartaoCredito) {
                System.out.println(((CartaoCredito) cartao).gerarExtrato());
            } else if (cartao instanceof CartaoDebito) {
                System.out.println(((CartaoDebito) cartao).gerarExtrato());
            }
        }
    }

    // Retorna todos os cartões
    public List<Cartao> getTodosCartoes() {
        return cartoes;
    }

    // Retorna os números de cartão únicos
    public Set<String> getNumerosCartao() {
        return numerosCartao;
    }
}