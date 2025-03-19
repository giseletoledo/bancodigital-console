package br.com.bancodigital;

import br.com.bancodigital.dao.BancoDAO;
import br.com.bancodigital.service.ClienteService;
import br.com.bancodigital.service.ContaService;
import br.com.bancodigital.service.TransacaoService;
import br.com.bancodigital.view.Menu;


public class Main {
    public static void main(String[] args) {
        // Instanciando o DAO único
        BancoDAO bancoDAO = new BancoDAO();
        TransacaoService transacaoService = new TransacaoService(bancoDAO.getTransacaoDAO());

        // Instanciando os serviços
        ContaService contaService = new ContaService(bancoDAO.getContaDAO(), transacaoService);
        ClienteService clienteService = new ClienteService(bancoDAO.getClienteDAO());
        
     // Cadastra clientes automaticamente ao iniciar
        clienteService.cadastrarClientesIniciais();

        // Instanciando o Menu
        Menu menu = new Menu(contaService, clienteService);

        // Exibindo o menu
        menu.exibirMenu();
    }
}