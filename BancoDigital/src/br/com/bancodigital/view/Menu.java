package br.com.bancodigital.view;

import br.com.bancodigital.model.*;
import br.com.bancodigital.service.ClienteService;
import br.com.bancodigital.service.ContaService;

import java.util.Locale;
import java.util.Scanner;

public class Menu {
	private final ContaService contaService;
    private final ClienteService clienteService;
    private final Scanner scanner;
    

    // Construtor que recebe os serviços necessários
    public Menu(ContaService contaService, ClienteService clienteService) {
        this.contaService = contaService;
        this.clienteService = clienteService;
        this.scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        while (true) {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Criar Conta");
            System.out.println("3. Depositar");
            System.out.println("4. Sacar");
            System.out.println("5. Transferir");
            System.out.println("6. Transferir por Pix");
            System.out.println("7. Simular Passagem de 30 Dias");
            System.out.println("8. Exibir Extrato");
            System.out.println("9. Sair");
            System.out.print("Escolha uma opção: ");
            
            scanner.useLocale(Locale.US);

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> criarConta();
                case 3 -> depositar();
                case 4 -> sacar();
                case 5 -> transferir();
                case 6 -> transferirPorPix();
                case 7 -> simularPassagemDeTempo();
                case 8 -> exibirExtrato();
                case 9 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
      
    private void cadastrarCliente() {
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a data de nascimento (DD/MM/AAAA): ");
        String dataNascimentoStr = scanner.nextLine();
      
        // Coleta do endereço
        System.out.print("Digite a rua: ");
        String rua = scanner.nextLine();

        System.out.print("Digite o número: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o complemento (ou deixe em branco): ");
        String complemento = scanner.nextLine();

        System.out.print("Digite a cidade: ");
        String cidade = scanner.nextLine();

        System.out.print("Digite o estado (sigla, ex: SP): ");
        String estado = scanner.nextLine();

        System.out.print("Digite o CEP (xxxxx-xxx): ");
        String cep = scanner.nextLine();

        // Cria o objeto Endereco
        Endereco endereco = new Endereco(rua, numero, complemento, cidade, estado, cep);

        // Valida o endereço usando o método da Service
        if (!clienteService.validarEndereco(endereco)) {
            System.out.println("Endereço inválido. Verifique os dados.");
            return;
        }

        System.out.print("Digite o tipo de cliente (COMUM, SUPER, PREMIUM): ");
        TipoCliente tipoCliente = TipoCliente.valueOf(scanner.nextLine().toUpperCase());

        // Cria o cliente
        Cliente cliente = clienteService.cadastrarCliente(cpf, nome, dataNascimentoStr, endereco, tipoCliente);
        if (cliente != null) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar cliente.");
        }
    }
    
    private void criarConta() {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente cliente = clienteService.buscarCliente(cpf);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.print("Digite o número da nova conta: ");
        String numero = scanner.nextLine();

        System.out.print("Digite o tipo de conta (CORRENTE ou POUPANCA): ");
        String tipoConta = scanner.nextLine().toUpperCase();

        System.out.print("Deseja cadastrar uma chave Pix? (S/N): ");
        String respostaPix = scanner.nextLine();
        String chavePix = null;
        if (respostaPix.equalsIgnoreCase("S")) {
            System.out.print("Digite a chave Pix: ");
            chavePix = scanner.nextLine();
        }

        // Cria a conta usando o ContaService
        Conta novaConta = contaService.criarConta(numero, cliente, tipoConta, chavePix);

        if (novaConta != null) {
            System.out.println("Conta criada com sucesso! Número: " + novaConta.getNumero());
        } else {
            System.out.println("Erro ao criar conta.");
        }
    }

    // Método para depositar dinheiro em uma conta
    private void depositar() {
        System.out.print("Digite o número da conta: ");
        String numeroConta = scanner.nextLine();

        System.out.print("Digite o valor do depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        // Chama o método depositar do ContaService
        if (contaService.depositar(numeroConta, valor)) {
            System.out.println("Depósito realizado com sucesso!");
        } else {
            System.out.println("Falha no depósito.");
        }
    }

    // Método para sacar dinheiro
    private void sacar() {
        System.out.print("Digite o número da conta: ");
        String numeroConta = scanner.nextLine();

        System.out.print("Digite o valor do saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        // Chama o método sacar do ContaService
        if (contaService.sacar(numeroConta, valor)) {
            System.out.println("Saque realizado com sucesso!");
        } else {
            System.out.println("Falha no saque.");
        }
    }

    // Método para transferir dinheiro entre contas
    private void transferir() {
        System.out.print("Digite o número da conta de origem: ");
        String numeroOrigem = scanner.nextLine();

        System.out.print("Digite o número da conta de destino: ");
        String numeroDestino = scanner.nextLine();

        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        // Chama o método transferir do ContaService
        if (contaService.transferir(numeroOrigem, numeroDestino, valor)) {
            System.out.println("Transferência realizada com sucesso!");
        } else {
            System.out.println("Falha na transferência.");
        }
    }

    // Método para transferir por Pix
    private void transferirPorPix() {
        System.out.print("Digite o número da conta de origem: ");
        String numeroOrigem = scanner.nextLine();

        System.out.print("Digite a chave Pix do destinatário: ");
        String chavePix = scanner.nextLine();

        System.out.print("Digite o valor da transferência: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (contaService.transferirPorPix(chavePix, numeroOrigem, valor)) {
            System.out.println("Transferência via Pix realizada com sucesso!");
        } else {
            System.out.println("Falha na transferência via Pix.");
        }
    }

    // Simula a passagem do tempo aplicando taxas e rendimentos de 30 dias
    private void simularPassagemDeTempo() {
        System.out.println("Simulando a passagem de 30 dias...");
        contaService.aplicarTaxaManutencao();
        contaService.aplicarRendimentoPoupanca();
        System.out.println("Simulação concluída!");
    }

    // Exibe o extrato da conta
    private void exibirExtrato() {
        System.out.print("Digite o número da conta: ");
        String numeroConta = scanner.nextLine();

        Conta conta = contaService.buscarConta(numeroConta);
        if (conta != null) {
            System.out.println(conta.gerarExtrato());
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
}
