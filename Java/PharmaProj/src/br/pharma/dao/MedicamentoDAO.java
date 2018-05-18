package br.pharma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.pharma.connection.ConnectionFactory;
import br.pharma.model.MedicamentoModel;

public class MedicamentoDAO implements IDAO<MedicamentoModel>{
	
	private final String INSERT = "INSERT INTO MEDICAMENTO "
			+ "(DESCRICAO,QUANTIDADE,VALORUNITARIO) VALUES (?,?,?)";
	private final String SELECT = "SELECT *FROM MEDICAMENTO";
	private final String SELECTCOD = "SELECT *FROM MEDICAMENTO WHERE CODIGO = ?";
	private final String UPDATEADD = "UPDATE MEDICAMENTO SET QUANTIDADEESTOQUE = QUANTIDADEESTOQUE + ? "
			+ "WHERE CODIGO = ?";
	private final String UPDATESUB = "UPDATE MEDICAMENTO SET QUANTIDADEESTOQUE = QUANTIDADEESTOQUE - ?"
			+ "WHERE CODIGO = ?";
	private final String UPDATE = "UPDATE MEDICAMENTO SET NOME = ?, PRECOCOMPRA = ?, PRECOVENDA = ? WHERE CODIGO = ?";
	private final String DELETE = "DELETE FROM MEDICAMENTO WHERE CODIGO = ?";
	
	@Override
	public void inserir (MedicamentoModel medic){
		if (medic != null){
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(INSERT);
				pstm.setString(1, medic.getDescriçao());
				pstm.setInt(2, medic.getQtd());
				pstm.setDouble(3, medic.getprecoVenda());
				pstm.execute();
				JOptionPane.showMessageDialog(null, "Medicamento cadastrado!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir Cliente no banco de "
						+ "dados " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public ArrayList<MedicamentoModel> listarTodos(){
		MedicamentoModel medic = new MedicamentoModel();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ArrayList<MedicamentoModel> medicList = new ArrayList<MedicamentoModel>();
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECT);
			rs = pstm.executeQuery();
			while (rs.next()) {
				medic.setCodigo( rs.getLong("CODPRODUTO"));
				medic.setDescriçao(rs.getString("DESCRICAO"));
				medic.setQtd(rs.getInt("QUANTIDADE"));
				medic.setprecoVenda(rs.getDouble("VALORUNITARIO"));
				
				medicList.add(medic);
				new MedicamentoModel().medicamentos(medic);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar Medicamentos! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
		return medicList;
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
			// TODO: handle exception
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
			// TODO: handle exception
		}
    }

	@Override
	public void atualizar(MedicamentoModel medic) throws Exception {
		if (medic != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(UPDATE);
				pstm.setString(1, medic.getDescriçao());
		        pstm.setDouble(2, medic.getPrecoCompra());
		        pstm.setDouble(3, medic.getPrecoCompra());
		        pstm.setLong(4, medic.getCodigo());
		        pstm.execute();
		        JOptionPane.showMessageDialog(null, "Cadastro de medicamento alterado!");
			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar medicamento no banco de dados " + e.getMessage());
				e.printStackTrace();
			}
			finally {
				ConnectionFactory.fechaConexao(conn, pstm);
			}
		} else {
			JOptionPane.showMessageDialog(null, "O registro de medicamento enviado por parámetro está vazio");
		}
	}

	@Override
	public void remover(MedicamentoModel medic) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(DELETE);
			pstm.setLong(1, medic.getCodigo());
	        pstm.execute();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Erro ao deletar medicamento no banco de dados " + e.getMessage());
		e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	}

	@Override
	public MedicamentoModel recuperar(long codigo) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		MedicamentoModel medic = new MedicamentoModel();
		
		try {
			conn = ConnectionFactory.getConexao();	
			pstm = conn.prepareStatement(SELECTCOD);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				 medic.setCodigo(rs.getLong("CODIGO"));
				 medic.setDescriçao(rs.getString("DESCRICAO"));
				 medic.setPrecoCompra(rs.getDouble("PRECOCOMPRA"));
				 medic.setprecoVenda(rs.getDouble("PRECOVENDA"));
				 medic.setQtd(rs.getInt("QUANTIDADEESTOQUE"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar medicamento! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
		return null;
	}
	
	public static void main(String[] args) {
		MedicamentoModel md = new MedicamentoModel("Flora Compestre", 2, 29.59);
		new MedicamentoDAO().inserir(md);
		new MedicamentoDAO().listarTodos();
	}

}
