package br.com.bancodigital.dao;

import br.com.bancodigital.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private List<Cliente> clientes = new ArrayList<>();

    public void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarClientePorCpf(String cpf) {
    	cpf.replaceAll("[^0-9]", ""); 
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(clientes); 
    }

    public boolean deletarClientePorCpf(String cpf) {
        return clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
    }

    public boolean atualizarCliente(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cliente.getCpf())) { 
                return true;
            }
        }
        return false; // Cliente não encontrado
    }
}