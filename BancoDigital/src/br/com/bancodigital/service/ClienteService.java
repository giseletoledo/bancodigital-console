package br.com.bancodigital.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import br.com.bancodigital.dao.ClienteDAO;
import br.com.bancodigital.model.Cliente;
import br.com.bancodigital.model.ContaCorrente;
import br.com.bancodigital.model.ContaPoupanca;
import br.com.bancodigital.model.Endereco;
import br.com.bancodigital.model.TipoCliente;
import br.com.bancodigital.utils.CPFValidator;

public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    // Método para cadastrar um cliente
    public Cliente cadastrarCliente(String cpf, String nome, String dataNascimentoStr, Endereco endereco, TipoCliente tipoCliente) {
    	cpf = cpf.replaceAll("[^0-9]", ""); 
    	// Validações
        if (!validarCPF(cpf)) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (!validarNome(nome)) {
            throw new IllegalArgumentException("Nome inválido.");
        }
        LocalDate dataNascimento = validarDataNascimento(dataNascimentoStr);
        if (dataNascimento == null) {
            throw new IllegalArgumentException("Data de nascimento inválida ou cliente menor de idade.");
        }
        if (!validarEndereco(endereco)) {
            throw new IllegalArgumentException("Endereço inválido.");
        }

        // Cria o cliente
        Cliente cliente = new Cliente(cpf, nome, dataNascimento, endereco, tipoCliente);
        clienteDAO.adicionarCliente(cliente);
        return cliente;
    }

    // Validação de CPF
    private boolean validarCPF(String cpf) {
        return CPFValidator.validarCPF(cpf);
    }

    // Validação de nome
    private boolean validarNome(String nome) {
    	return nome != null && nome.matches("[a-zA-ZÀ-ÿ\\s-]{2,100}");
    }

    // Validação de data de nascimento
    private LocalDate validarDataNascimento(String dataNascimentoStr) {
        try {
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            LocalDate hoje = LocalDate.now();
            Period periodo = Period.between(dataNascimento, hoje);
            if (periodo.getYears() >= 18) {
                return dataNascimento;
            }
        } catch (DateTimeParseException e) {
            // Data inválida
        }
        return null;
    }

    // Validação de endereço
    public boolean validarEndereco(Endereco endereco) {
        if (endereco == null) {
            return false;
        }

        // Validação dos campos obrigatórios
        boolean ruaValida = endereco.getRua() != null && !endereco.getRua().isBlank();
        boolean numeroValido = endereco.getNumero() != null && !endereco.getNumero().isBlank();
        boolean cidadeValida = endereco.getCidade() != null && !endereco.getCidade().isBlank();
        boolean estadoValido = endereco.getEstado() != null && endereco.getEstado().matches("[A-Z]{2}");
        boolean cepValido = validarCEP(endereco.getCep());

        return ruaValida && numeroValido && cidadeValida && estadoValido && cepValido;
    }
    
    public boolean validarCEP(String cep) {
        return cep != null && cep.matches("\\d{5}-\\d{3}");
    }
    
    public void cadastrarClientesIniciais() {
        try {
            Endereco endereco1 = new Endereco("Rua A", "123", "", "São Paulo", "SP", "01000-000");
            cadastrarCliente("17401340011", "João Silva", "15/05/1985", endereco1, TipoCliente.COMUM);

            Endereco endereco2 = new Endereco("Rua B", "456", "Apto 12", "Rio de Janeiro", "RJ", "20000-000");
            cadastrarCliente("44182399013", "Maria Souza", "22/08/1990", endereco2, TipoCliente.SUPER);

            Endereco endereco3 = new Endereco("Avenida C", "789", "Bloco 2", "Belo Horizonte", "MG", "30000-000");
            cadastrarCliente("00888267088", "Carlos Oliveira", "10/12/1978", endereco3, TipoCliente.PREMIUM);

            System.out.println("Clientes iniciais cadastrados.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar clientes iniciais: " + e.getMessage());
        }
    }

    
    // Aplica a taxa de manutenção mensal na conta corrente
    public void aplicarTaxaManutencao(ContaCorrente conta) {
        TipoCliente tipoCliente = conta.getCliente().getTipoCliente();
        double taxa = 0.0;

        switch (tipoCliente) {
            case COMUM -> taxa = 12.00;
            case SUPER -> taxa = 8.00;
            case PREMIUM -> taxa = 0.00; // Isento
        }

        if (taxa > 0) {
            conta.sacar(taxa);
            System.out.println("Taxa de manutenção mensal aplicada: R$ " + taxa);
        }
    }
    
    public Cliente buscarCliente(String cpf) {
        return clienteDAO.buscarClientePorCpf(cpf);
    }

    // Aplica o rendimento mensal na conta poupança
    public void aplicarRendimentoPoupanca(ContaPoupanca conta) {
        TipoCliente tipoCliente = conta.getCliente().getTipoCliente();
        double taxaRendimentoAnual = 0.0;

        switch (tipoCliente) {
            case COMUM -> taxaRendimentoAnual = 0.5;
            case SUPER -> taxaRendimentoAnual = 0.7;
            case PREMIUM -> taxaRendimentoAnual = 0.9;
        }

        double rendimentoMensal = conta.getSaldo() * (taxaRendimentoAnual / 100 / 12);
        conta.depositar(rendimentoMensal);
        System.out.println("Rendimento mensal aplicado: R$ " + rendimentoMensal);
    }
}