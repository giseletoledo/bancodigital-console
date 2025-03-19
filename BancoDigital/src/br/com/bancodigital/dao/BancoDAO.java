package br.com.bancodigital.dao;

public class BancoDAO {
    private ContaDAO contaDAO;
    private ClienteDAO clienteDAO;
    private CartaoDAO cartaoDAO;
    private TransacaoDAO transacaoDAO;

    public BancoDAO() {
        this.contaDAO = new ContaDAO();
        this.clienteDAO = new ClienteDAO();
        this.cartaoDAO = new CartaoDAO();
        this.transacaoDAO = new TransacaoDAO();
    }

    public TransacaoDAO getTransacaoDAO() {
		return transacaoDAO;
	}

	public void setTransacaoDAO(TransacaoDAO transacaoDAO) {
		this.transacaoDAO = transacaoDAO;
	}

	public ContaDAO getContaDAO() {
        return contaDAO;
    }

    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    public CartaoDAO getCartaoDAO() {
        return cartaoDAO;
    }
}
