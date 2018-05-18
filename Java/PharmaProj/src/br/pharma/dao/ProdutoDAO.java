package br.pharma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.pharma.connection.ConnectionFactory;
import br.pharma.model.ProdutoModel;

public class ProdutoDAO implements IDAO<ProdutoModel>{
	
	private final String INSERT = "INSERT INTO TBPRODUTO "
			+ "(DESCRICAO,PRECOCOMPRA, PRECOVENDA, QTDESTOQUE) VALUES (?,?,?,?)";
	private final String SELECT = "SELECT *FROM TBPRODUTO";
	private final String SELECTCOD = "SELECT *FROM TBPRODUTO WHERE CODPRODUTO = ?";
	private final String UPDATEADD = "UPDATE TBPRODUTO SET QTDESTOQUE = QTDESTOQUE + ? "
			+ "WHERE CODIGO = ?";
	private final String UPDATESUB = "UPDATE TBPRODUTO SET QTDESTOQUE = QTDESTOQUE - ?"
			+ "WHERE CODIGO = ?";
	private final String UPDATE = "UPDATE TBPRODUTO SET DESCRICAO = ?, PRECOCOMPRA = ?, PRECOVENDA = ?,  QTDESTOQUE = ? WHERE CODPRODUTO = ?";
	private final String DELETE = "DELETE FROM TBPRODUTO WHERE CODPRODUTO = ?";
	
	@Override
	public void inserir (ProdutoModel prod){
		if (prod != null){
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(INSERT);
				pstm.setString(1, prod.getDescriçao());
				pstm.setDouble(2, prod.getPrecoCompra());
				pstm.setDouble(3, prod.getprecoVenda());
				pstm.setInt(4, prod.getQtd());
				pstm.execute();
				JOptionPane.showMessageDialog(null, "produto cadastrado!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir produto no banco de "
						+ "dados " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public ArrayList<ProdutoModel> listarTodos(){
		ProdutoModel prod = new ProdutoModel();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<ProdutoModel> prodList = new ArrayList<ProdutoModel>();
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECT);
			rs = pstm.executeQuery();
			while (rs.next()) {
				prod.setCodigo( rs.getLong("CODPRODUTO"));
				prod.setDescriçao(rs.getString("DESCRICAO"));
				prod.setprecoVenda(rs.getDouble("PRECOCOMPRA"));
				prod.setprecoVenda(rs.getDouble("PRECOVENDA"));
				prod.setQtd(rs.getInt("QTDESTOQUE"));
				
				prodList.add(prod);
				new ProdutoModel().produtos(prod);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar produtos! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
		return prodList;
	}
	
	public void entradaEstoque(long codigo, int quantidade) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(UPDATEADD);
			pstm.setLong(1, quantidade);
			pstm.setLong(2, codigo);
			pstm.execute();
		} catch (Exception e) {
		e.printStackTrace();
		}
    }

    public void saidaEstoque(long codigo, int quantidade) throws Exception {
    	Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(UPDATESUB);
			pstm.setLong(1, quantidade);
			pstm.setLong(2, codigo);
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void atualizar(ProdutoModel prod) throws Exception {
		if (prod != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(UPDATE);
				pstm.setString(1, prod.getDescriçao());
		        pstm.setDouble(2, prod.getPrecoCompra());
		        pstm.setDouble(3, prod.getPrecoCompra());
		        pstm.setLong(4, prod.getCodigo());
		        pstm.execute();
		        JOptionPane.showMessageDialog(null, "Cadastro de produto alterado!");
			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar produto no banco de dados " + e.getMessage());
				e.printStackTrace();
			}
			finally {
				ConnectionFactory.fechaConexao(conn, pstm);
			}
		} else {
			JOptionPane.showMessageDialog(null, "O registro de produto enviado por parámetro está vazio");
		}
	}

	@Override
	public void remover(ProdutoModel prod) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(DELETE);
			pstm.setLong(1, prod.getCodigo());
	        pstm.execute();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Erro ao deletar produto no banco de dados " + e.getMessage());
		e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	}

	@Override
	public ProdutoModel recuperar(long codigo) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ProdutoModel prod = new ProdutoModel();
		
		try {
			conn = ConnectionFactory.getConexao();	
			pstm = conn.prepareStatement(SELECTCOD);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				 prod.setCodigo(rs.getLong("CODIGO"));
				 prod.setDescriçao(rs.getString("DESCRICAO"));
				 prod.setPrecoCompra(rs.getDouble("PRECOCOMPRA"));
				 prod.setprecoVenda(rs.getDouble("PRECOVENDA"));
				 prod.setQtd(rs.getInt("QUANTIDADEESTOQUE"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar produto! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
		return null;
	}
	
	public static void main(String[] args) {
		ProdutoModel md = new ProdutoModel("Flora Compestre", 29.59, 45.90, 2);
		new ProdutoDAO().inserir(md);
		new ProdutoDAO().listarTodos();
	}

}
