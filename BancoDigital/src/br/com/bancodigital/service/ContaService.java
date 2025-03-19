package br.com.bancodigital.service;

import br.com.bancodigital.dao.ContaDAO;
import br.com.bancodigital.model.Cliente;
import br.com.bancodigital.model.Conta;
import br.com.bancodigital.model.ContaCorrente;
import br.com.bancodigital.model.ContaPoupanca;
import br.com.bancodigital.model.TipoTransacao;

public class ContaService {

    private ContaDAO contaDAO;
    private TransacaoService transacaoService;

    public ContaService(ContaDAO contaDAO, TransacaoService transacaoService) {
        this.contaDAO = contaDAO;
        this.transacaoService = transacaoService;
    }
    
    public Conta buscarConta(String numeroConta) {
        return contaDAO.buscarConta(numeroConta);
    }

    // Aplica taxa de manutenção para todas as contas correntes
    public void aplicarTaxaManutencao() {
        for (Conta conta : contaDAO.getTodasContas()) {
            if (conta instanceof ContaCorrente) {
                double taxa = calcularTaxaManutencao(conta.getCliente());
                if (taxa > 0) {
                    conta.sacar(taxa);
                    
                    // Registra a taxa como transação
                    transacaoService.registrarTransacao(
                        conta,
                        TipoTransacao.TAXA,
                        -taxa,
                        "Taxa de manutenção mensal aplicada"
                    );

                    System.out.println("💰 Taxa de manutenção de R$ " + taxa +
                        " aplicada à conta " + conta.getNumero());
                }
            }
        }
    }

    // Aplica rendimento para todas as contas poupança
    public void aplicarRendimentoPoupanca() {
        for (Conta conta : contaDAO.getTodasContas()) {
            if (conta instanceof ContaPoupanca) {
                double rendimento = calcularRendimentoMensal(conta);
                if (rendimento > 0) {
                    conta.depositar(rendimento);

                    // Registra o rendimento como transação
                    transacaoService.registrarTransacao(
                        conta,
                        TipoTransacao.TAXA,
                        rendimento,
                        "Rendimento mensal aplicado"
                    );

                    System.out.println("📈 Rendimento de R$ " + rendimento +
                        " aplicado à conta poupança " + conta.getNumero());
                }
            }
        }
    }

    // Calcula a taxa de manutenção mensal da conta corrente
    private double calcularTaxaManutencao(Cliente cliente) {
        return switch (cliente.getTipoCliente()) {
            case COMUM -> 12.00;
            case SUPER -> 8.00;
            case PREMIUM -> 0.00; // Premium é isento
        };
    }

    // Calcula o rendimento mensal da conta poupança usando juros compostos
    private double calcularRendimentoMensal(Conta conta) {
        double taxaAnual = conta.getCliente().getTaxaRendimento(); // Em %
        double taxaMensal = Math.pow(1 + (taxaAnual / 100), 1.0 / 12) - 1; // Fórmula de juros compostos mensais
        return conta.getSaldo() * taxaMensal; // Aplica o rendimento sobre o saldo
    }

    public boolean transferirPorPix(String chavePix, String numeroOrigem, double valor) {
        // Busca a conta de origem
        Conta origem = contaDAO.buscarConta(numeroOrigem);
        if (origem == null) {
            System.out.println("Conta de origem não encontrada.");
            return false;
        }

        // Busca a conta de destino pela chave Pix
        Conta destino = contaDAO.buscarContaPorPix(chavePix);
        if (destino == null) {
            System.out.println("Conta de destino não encontrada.");
            return false;
        }

        // Realiza o saque na conta de origem
        if (origem.sacar(valor)) {
            // Realiza o depósito na conta de destino
            destino.depositar(valor);

            // Registra a transação de saque na conta de origem
            transacaoService.registrarTransacao(
                origem,
                TipoTransacao.TRANSFERENCIA,
                -valor, // Valor negativo para saque
                "Transferência via Pix para conta " + destino.getNumero()
            );

            // Registra a transação de depósito na conta de destino
            transacaoService.registrarTransacao(
                destino,
                TipoTransacao.TRANSFERENCIA,
                valor,
                "Transferência via Pix da conta " + origem.getNumero()
            );

            System.out.println("Transferência via Pix de R$ " + valor + " realizada com sucesso.");
            return true;
        } else {
            System.out.println("Saldo insuficiente na conta de origem.");
            return false;
        }
    }

    public Conta criarConta(String numero, Cliente cliente, String tipoConta, String chavePix) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }

        Conta conta;

        switch (tipoConta.toUpperCase()) {
            case "CORRENTE":
                conta = new ContaCorrente(numero, cliente, chavePix, 1000);
                break;
            case "POUPANCA":
                conta = new ContaPoupanca(numero, cliente, chavePix);
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta inválido. Use 'CORRENTE' ou 'POUPANCA'.");
        }

        // Configura a chave Pix se fornecida
        if (chavePix != null && !chavePix.isBlank()) {
            conta.setChavePix(chavePix);
        }

        // Aqui você pode salvar a conta em um repositório ou DAO, se necessário.
        contaDAO.adicionarConta(conta);

        return conta;
    }


	public boolean sacar(String numeroConta, double valor) {
	    Conta conta = contaDAO.buscarConta(numeroConta);
	    if (conta != null && conta.sacar(valor)) {

	        // Registra a transação de saque
	        transacaoService.registrarTransacao(
	            conta,
	            TipoTransacao.SAQUE,
	            valor,
	            "Saque na conta " + numeroConta
	        );

	        System.out.println("Saque de R$ " + valor + " realizado com sucesso na conta " + numeroConta);
	        return true;
	    } else {
	        System.out.println("Saque falhou. Verifique o saldo ou a existência da conta.");
	        return false;
	    }
	}

	public boolean depositar(String numeroConta, double valor) {
	    Conta conta = contaDAO.buscarConta(numeroConta);
	    if (conta != null) {
	        conta.depositar(valor);

	        // Registra a transação de depósito
	        transacaoService.registrarTransacao(
	            conta,
	            TipoTransacao.DEPOSITO,
	            valor,
	            "Depósito na conta " + numeroConta
	        );

	        System.out.println("Depósito de R$ " + valor + " realizado com sucesso na conta " + numeroConta);
	        return true;
	    } else {
	        System.out.println("Conta não encontrada.");
	        return false;
	    }
	}
	
	public boolean transferir(String numeroOrigem, String numeroDestino, double valor) {
	    Conta origem = contaDAO.buscarConta(numeroOrigem);
	    Conta destino = contaDAO.buscarConta(numeroDestino);

	    if (origem == null || destino == null) {
	        System.out.println("Conta de origem ou destino não encontrada.");
	        return false;
	    }

	    if (origem.sacar(valor)) {
	        destino.depositar(valor);

	        // Registra a transação de saque na conta de origem
	        transacaoService.registrarTransacao(
	            origem,
	            TipoTransacao.TRANSFERENCIA,
	            -valor, // Valor negativo para saque
	            "Transferência para conta " + numeroDestino
	        );

	        // Registra a transação de depósito na conta de destino
	        transacaoService.registrarTransacao(
	            destino,
	            TipoTransacao.TRANSFERENCIA,
	            valor,
	            "Transferência da conta " + numeroOrigem
	        );

	        System.out.println("Transferência de R$ " + valor + " realizada com sucesso de " + numeroOrigem + " para " + numeroDestino);
	        return true;
	    } else {
	        System.out.println("Saldo insuficiente na conta de origem.");
	        return false;
	    }
	}

	

}
