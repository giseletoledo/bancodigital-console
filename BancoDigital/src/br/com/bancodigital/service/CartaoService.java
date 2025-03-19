package br.com.bancodigital.service;

import br.com.bancodigital.model.Cartao;
import br.com.bancodigital.model.CartaoCredito;
import br.com.bancodigital.model.TipoCliente;
import br.com.bancodigital.dao.CartaoDAO;

import java.util.List;
import java.util.Random;

public class CartaoService {
    private CartaoDAO cartaoDAO;

    public CartaoService(CartaoDAO cartaoDAO) {
        this.cartaoDAO = cartaoDAO;
    }
    
 // Método para calcular o limite do cartão de crédito com base no tipo de cliente
    public double calcularLimiteCartao(TipoCliente tipoCliente) {
        switch (tipoCliente) {
            case COMUM:
                return 1000.00; // Limite para clientes comuns
            case SUPER:
                return 5000.00; // Limite para clientes super
            case PREMIUM:
                return 10000.00; // Limite para clientes premium
            default:
                throw new IllegalArgumentException("Tipo de cliente inválido: " + tipoCliente);
        }
    }
    
 // Gera um número de cartão único
    public String gerarNumeroCartaoUnico() {
        Random random = new Random();
        String numeroGerado;
        do {
            numeroGerado = String.format("%016d", Math.abs(random.nextLong() % 1_000_000_000_000_000L));
        } while (cartaoDAO.getNumerosCartao().contains(numeroGerado)); // Verifica se o número já existe
        return numeroGerado;
    }
    
 // Adiciona um cartão ao sistema
    public void adicionarCartao(Cartao cartao) {
        cartaoDAO.adicionarCartao(cartao);
    }

    // Aplica a taxa de utilização do cartão de crédito
    public void aplicarTaxaUtilizacao(CartaoCredito cartao) {
        double totalGastos = cartao.getFatura().calcularTotalGastos();
        double limite = cartao.getLimite();

        if (totalGastos > 0.8 * limite) {
            double taxa = totalGastos * 0.05; // 5% de taxa
            cartao.getFatura().adicionarTaxa(taxa);
            System.out.println("Taxa de utilização aplicada: R$ " + taxa);
        }
    }

    // Aplica o seguro viagem no cartão de crédito
    public void aplicarSeguroViagem(CartaoCredito cartao) {
        TipoCliente tipoCliente = cartao.getCliente().getTipoCliente();

        if (tipoCliente == TipoCliente.PREMIUM) {
            System.out.println("Seguro viagem gratuito aplicado para cliente Premium.");
        } else {
            double taxaSeguro = 50.00; // R$ 50,00 por mês
            cartao.getFatura().adicionarTaxa(taxaSeguro);
            System.out.println("Seguro viagem aplicado: R$ " + taxaSeguro);
        }
    }

    // Aplica o seguro de fraude no cartão de crédito
    public void aplicarSeguroFraude(CartaoCredito cartao) {
        double valorApolice = 5000.00; // R$ 5.000,00 de cobertura
        System.out.println("Seguro de fraude aplicado com apólice de R$ " + valorApolice);
    }

    // Retorna a lista de todos os cartões
    public List<Cartao> getTodosCartoes() {
        return cartaoDAO.getTodosCartoes();
    }
}