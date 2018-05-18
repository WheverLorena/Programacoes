package br.pharma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.pharma.connection.ConnectionFactory;
import br.pharma.model.FornecedorModel;

/**
 * Classe de acesso a dados do fornecedor
 *
 * @author Inocencio
 */
public class FornecedorDAO implements IDAO<FornecedorModel> {
	private final String INSERT = "INSERT INTO TBFORNECEDOR (NOME, CNPJ) VALUES (?, ?)";
	private final String UPDATE = "UPDATE TBFORNECEDOR SET NOME=?, CNPJ=? WHERE CODIGO=?";
	private final String SELECT = "DELETE FROM TBFORNECEDOR WHERE CODIGO=?";
    private final String SELECTCOD = "SELECT * FROM TBFORNECEDOR WHERE CODIGO=?";
	private final String DELETE = "DELETE FROM TBFORNECEDOR WHERE CODIGO=?";
    
	@Override
    public void inserir(FornecedorModel fornecedor) throws Exception {
       if (fornecedor != null){	   
    	   Connection conn = null;
    	   PreparedStatement pstm = null;
    	   try {
	    	  conn = ConnectionFactory.getConexao();
	    	  pstm = conn.prepareStatement(INSERT);
	    	  pstm.setString(1, fornecedor.getNome());
	    	  pstm.setString(2, fornecedor.getCnpj());
	    	  pstm.execute();
	    	  conn.commit();
	    	  JOptionPane.showMessageDialog(null, "Fornecedor cadastrado!");
		  } catch (Exception e) {
			  JOptionPane.showMessageDialog(null, "Erro ao inserir Fornecedor no banco de "
						+ "dados " + e.getMessage());
			  e.printStackTrace();
		  }
    	   finally {
    		   ConnectionFactory.fechaConexao(conn, pstm);
    	   }
		} else {
			System.out.println("O registro de Fornecedor enviado por parámetro está vazio");
			return;
		}
    }

    @Override
    public void atualizar(FornecedorModel fornecedor) throws Exception {
    	if (fornecedor != null){	   
     	   Connection conn = null;
     	   PreparedStatement pstm = null;
     	   try {
 	    	  conn = ConnectionFactory.getConexao();
 	    	  pstm = conn.prepareStatement(UPDATE);
 	    	  pstm.setString(1, fornecedor.getNome());
	    	  pstm.setString(2, fornecedor.getCnpj());
	    	  pstm.setLong(3, fornecedor.getCodigo());
	    	  pstm.execute();
	    	  conn.commit();
	    	  JOptionPane.showMessageDialog(null, "Fornecedor Atualizado!");
     	   } catch (Exception e) {
 			  JOptionPane.showMessageDialog(null, "Erro ao atualizar Fornecedor no banco de "
						+ "dados " + e.getMessage());
			  e.printStackTrace();
		  }
  	   finally {
  		   ConnectionFactory.fechaConexao(conn, pstm);
  	   }
		} else {
			System.out.println("O registro de Fornecedor enviado por parámetro está vazio");
			return;
		}
    }

    @Override
    public void remover(FornecedorModel fornecedor) throws Exception {
    	Connection conn = null;
		PreparedStatement pstm = null;
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(DELETE);
			pstm.setLong(1, fornecedor.getCodigo());
			pstm.execute();
			conn.commit();
			JOptionPane.showMessageDialog(null, "Fornecedor removido!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir fornecedor no banco de"
					+ "dados " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
    }

    @Override
    public ArrayList<FornecedorModel> listarTodos() throws Exception {
    	Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<FornecedorModel> fornecedorList = new ArrayList<FornecedorModel>();
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECT);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FornecedorModel fornecedor = new FornecedorModel();
				fornecedor.setCodigo(rs.getInt("CODIGO"));
	            fornecedor.setNome(rs.getString("NOME"));
	            fornecedor.setCnpj(rs.getString("CNPJ"));
	            fornecedorList.add(fornecedor);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar fornecedor! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
        return fornecedorList;
    }

	@Override
	public FornecedorModel recuperar(long codigo) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FornecedorModel fornecedor = new FornecedorModel();
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECTCOD);
			pstm.setLong(1, codigo);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				fornecedor.setCodigo(rs.getInt("CODIGO"));
	            fornecedor.setNome(rs.getString("NOME"));
	            fornecedor.setCnpj(rs.getString("CNPJ"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar cliente! " + e.getMessage());
		}
		return fornecedor;
	}
}