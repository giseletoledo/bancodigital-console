package br.com.bancodigital.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.bancodigital.model.Conta;


public class ContaDAO {
    private List<Conta> contas = new ArrayList<>();

    public List<Conta> getTodasContas() {
        return contas;
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }
    
    public void salvar(Conta conta) {
        adicionarConta(conta);
    }

    

    public Conta buscarConta(String numero) {
        for (Conta conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public boolean deletarConta(String numero) {
        return contas.removeIf(conta -> conta.getNumero().equals(numero));
    }

    public Conta buscarContaPorPix(String chavePix) {
        for (Conta conta : contas) {
            if (chavePix.equals(conta.getChavePix())) {
                return conta;
            }
        }
        return null;
    }
}
