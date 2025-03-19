package br.com.bancodigital.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.bancodigital.model.Transacao;


public class TransacaoDAO {
    private List<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> getTransacoesPorConta(String numeroConta) {
        List<Transacao> transacoesConta = new ArrayList<>();
        for (Transacao transacao : transacoes) {
            if (transacao.getDescricao().contains(numeroConta)) {
                transacoesConta.add(transacao);
            }
        }
        return transacoesConta;
    }
}