package br.pharma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import br.pharma.connection.ConnectionFactory;
import br.pharma.model.ClienteModel;

public class ClienteDAO implements IDAO<ClienteModel> {
	private final String INSERT = "INSERT INTO TBCLIENTE "
			+ "(NOME,CPF,SEXO,ENDERECO,DATANASCIMENTO,CELULAR,EMAIL) VALUES (?,?,?,?,?,?,?)";
	private final String SELECT = "SELECT *FROM TBCLIENTE";
	private final String SELECTCODIGO = "SELECT *FROM TBCLIENTE ORDER BY CODCLIENTE DESC";
	private final String UPDATE = "UPDATE TBCLIENTE SET NOME=?, SEXO=?, ENDERECO=?, DATANASCIMENTO=?, CELULAR=?,EMAIL=? "
			+ "WHERE CODCLIENTE = ?";
	private final String DELETE = "DELETE FROM TBCLIENTE WHERE CODCLIENTE = ?";
	private final String RECUPERAR = "SELECT *FROM TBCLIENTE WHERE CODCLIENTE = ?";
	
	@Override
	public void inserir(ClienteModel cliente) throws Exception{
		if (cliente != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(INSERT);

				pstm.setString(1, cliente.getNome());
				pstm.setString(2, cliente.getCpf());	
				pstm.setString(3, cliente.getSexo());
				pstm.setString(4, cliente.getEnd());
				pstm.setDate(5,new java.sql.Date(cliente.getDataNasc().getTimeInMillis()));
				pstm.setString(6, cliente.getCel());
				pstm.setString(7, cliente.getEmail());
			//	pstm.setString(8, cliente.getProbOsseo());
			//	pstm.setString(9, cliente.getFumante());
			//	pstm.setString(10, cliente.getAlcoolatra());
			//	pstm.setString(11, cliente.getGravida());
			//	pstm.setString(12, cliente.getCronica());
			//	pstm.setString(13, cliente.getAlergico());

				pstm.execute();
				conn.commit();

				JOptionPane.showMessageDialog(null, "Cliente cadastrado!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir Cliente no banco de "
						+ "dados " + e.getMessage());
				e.printStackTrace();
			}
			finally {
				ConnectionFactory.fechaConexao(conn, pstm);
			}
		} else {
			System.out.println("O registro de Cliente enviado por parámetro está vazio");
			return;
		}
	}
	
	@Override
	public ArrayList<ClienteModel> listarTodos() throws Exception{
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<ClienteModel> clienteList = new ArrayList<ClienteModel>();
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECT);
			rs = pstm.executeQuery();
			while (rs.next()) {
				ClienteModel cliente = new ClienteModel();
				
				cliente.setCodigo( rs.getLong("CODCLIENTE"));
				cliente.setNome(rs.getString("NOME"));
				cliente.setCpf(rs.getString("CPF"));
				cliente.setSexo(rs.getString("SEXO"));
				cliente.setEnd(rs.getString("ENDERECO"));
				cliente.setCel(rs.getString("CELULAR"));
				cliente.setEmail(rs.getString("EMAIL"));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("DATANASCIMENTO"));
				cliente.setDataNasc(data);
				
				clienteList.add(cliente);
				new ClienteModel().clientes(cliente);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar clientes! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
		return clienteList;
	}
	@Override
	public void atualizar(ClienteModel cliente) throws Exception {
		if (cliente != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(UPDATE);

				pstm.setString(1, cliente.getNome());
				pstm.setString(2, cliente.getSexo());
				pstm.setString(3, cliente.getEnd());
				pstm.setDate(4,new java.sql.Date(cliente.getDataNasc().getTimeInMillis()));
				pstm.setString(5, cliente.getCel());
				pstm.setString(6, cliente.getEmail());
				pstm.setLong(7, cliente.getCodigo());
				
				pstm.execute();
				conn.commit();
				JOptionPane.showMessageDialog(null, "Cadastro de cliente alterado!");

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente no banco de dados " + e.getMessage());
				e.printStackTrace();
			}
			finally {
				ConnectionFactory.fechaConexao(conn, pstm);
			}
		} else {
			JOptionPane.showMessageDialog(null, "O registro do cliente enviado por parámetro está vazio");
		}
	}
	
	@Override
	public void remover(ClienteModel cliente)throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionFactory.getConexao();
			
			pstm = conn.prepareStatement(DELETE);

			pstm.setLong(1, cliente.getCodigo());
			pstm.execute();
			conn.commit();
			JOptionPane.showMessageDialog(null, "Cliente removido!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir cliente no banco de"
					+ "dados " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	}
	
	@Override
	public ClienteModel recuperar(long codigo) throws Exception {
		ClienteModel cliente = new ClienteModel();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(RECUPERAR);
			pstm.setLong(1, cliente.getCodigo());
			rs = pstm.executeQuery();
			
			if(rs.next()) {
				cliente.setCodigo( rs.getLong("CODCLIENTE"));
				cliente.setNome(rs.getString("NOME"));
				cliente.setCpf(rs.getString("CPF"));
				cliente.setSexo(rs.getString("SEXO"));
				cliente.setEnd(rs.getString("ENDERECO"));
				cliente.setCel(rs.getString("CELULAR"));
				cliente.setEmail(rs.getString("EMAIL"));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("DATANASCIMENTO"));
				cliente.setDataNasc(data);
				new ClienteModel().clientes(cliente);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar cliente! " + e.getMessage());
		}
		return cliente;
	}
	
	public static void main(String[] args) throws Exception {
		//INSERT EXEMPLO
		ClienteModel cm = new ClienteModel("Inocencio", "1405556", "M", "Pastor Hugo", "23/04/2018", "234523", "ino@ccuec.com");
		new ClienteDAO().inserir(cm);
		
		//UPDATE EXEMPLO
	//	ClienteModel cm = new ClienteModel("Inocencio Cardoso", "M", "Pastor Hugo", "24/05/2017", "1234523", "inocencio@ccuec.com",1);
	//	new ClienteDAO().atualizar(cm);
	//	ClienteModel rm = new ClienteModel();
	//	rm.setId(3);
	//	new ClienteDAO().remover(rm);
		new ClienteDAO().listarTodos();
	}
}