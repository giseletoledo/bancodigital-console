package br.com.bancodigital.service;

import br.com.bancodigital.dao.TransacaoDAO;
import br.com.bancodigital.model.*;
import java.time.LocalDateTime;

public class TransacaoService {

    private TransacaoDAO transacaoDAO;

    public TransacaoService(TransacaoDAO transacaoDAO) {
        this.transacaoDAO = transacaoDAO;
    }

    public void registrarTransacao(Conta conta, TipoTransacao tipo, double valor, String descricao) {
        Transacao transacao = new Transacao(
            LocalDateTime.now(),
            tipo,
            valor,
            descricao
        );
        transacaoDAO.adicionarTransacao(transacao);
    }
}