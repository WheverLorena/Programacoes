package br.pharma.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import br.pharma.connection.ConnectionFactory;
import br.pharma.model.ComprasModel;
import br.pharma.model.ItemCompra;
import br.pharma.model.Situacao;

/**
 * Classe contendo os dados da compra
 *
 * @author Inocencio
 */

public class CompraDAO implements IDAO<ComprasModel>{
	private final String INSERTCOMPRA = "INSERT INTO TBCOMPRAS "
			+ "(CODIGOFORNECEDOR,DATACOMPRA,VALORTTAL,SITUACAO) VALUES (?,?,?,?)";
	private final String INSERTITEM = "INSERT INTO TBITEMCOMPRAS (CODIGOPRODUTO, CODIGOCOMPRA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
	private final String SELECT = "SELECT *FROM TBCOMPRAS ORDER BY DATACOMPRA DESC";
	private final String SELECTCOD = "SELECT *FROM TBCOMPRAS WHERE CODIGO = ?";
	private String UPDATE = "UPDATE TBCOMPRAS SET CODIGOFORNECEDOR=?, DATACOMPRA=?, VALORTOTAL=?, SITUACAO=?, "
			+ "WHERE CODIGO = ?";
	
	@Override
	public void inserir(ComprasModel compras) throws Exception {
		if(compras != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(INSERTCOMPRA, Statement.RETURN_GENERATED_KEYS);
				pstm.setLong(1, compras.getFornecedor().getCodigo());
				pstm.setDate(2, new Date(compras.getDataCompras().getTimeInMillis()));
				pstm.setDouble(3, compras.getValorTotal());
				pstm.setLong(4, compras.getSituacao().getId());
				pstm.execute();
				
				rs = pstm.getGeneratedKeys();
				rs.next();
				
				int idcompra = rs.getInt(1);
				
				for (ItemCompra i : compras.getItens()) {
					pstm = conn.prepareStatement(INSERTITEM);
					pstm.setLong(1, i.getMedicamento().getCodigo());
					pstm.setLong(2, idcompra);
					pstm.setLong(3, i.getQuantidade());
					pstm.setDouble(4, i.getValorUnitario());
					
					pstm.execute();
					
					if (compras.getSituacao() == Situacao.FINALIZADA) {
						ProdutoDAO med = new ProdutoDAO();
						med.entradaEstoque(i.getMedicamento().getCodigo(), i.getQuantidade());
					}
				}
				conn.commit();
				
				JOptionPane.showMessageDialog(null, "Compra cadastrada!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir Compra no banco de "
					+ "dados " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	} else {
		System.out.println("O registro de Compra enviado por parámetro está vazio");
		return;
	}
	}

	@Override
	public void atualizar(ComprasModel compras) throws Exception {
		if (compras != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(UPDATE);
				pstm.setLong(1, compras.getFornecedor().getCodigo());
				pstm.setDate(2,new java.sql.Date(compras.getDataCompras().getTimeInMillis()));
				pstm.setDouble(3,compras.getValorTotal());
				pstm.setInt(4, compras.getSituacao().getId());
				pstm.setLong(5, compras.getCodigo());
			    pstm.execute();
				JOptionPane.showMessageDialog(null, "Cadastro de compras alterado!");
				
				for (ItemCompra i : compras.getItensRemover()) {
		            UPDATE = "DELETE FROM TBITEMCOMPRA WHERE CODITEM = ?";
		            conn = ConnectionFactory.getConexao();
		            
					pstm = conn.prepareStatement(UPDATE);
		            pstm.setLong(1, i.getCodigo());
		            pstm.execute();
		        }
				
				for (ItemCompra iv : compras.getItens()) {
		            if (iv.getCodigo() == 0) {
		                UPDATE = "INSERT INTO TBITEMCOMPRA (CODIGOPRODUTO, CODIGOCOMPRA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
		                conn = ConnectionFactory.getConexao();
		                pstm = conn.prepareStatement(UPDATE);
		                pstm.setLong(1, iv.getMedicamento().getCodigo());
		                pstm.setLong(2, iv.getCompra().getCodigo());
		                pstm.setInt(3, iv.getQuantidade());
		                pstm.setDouble(4, iv.getValorUnitario());
		                
		                pstm.execute();
		                
		            } else {
		            	UPDATE = "UPDATE TBITEMCOMPRA SET CODIGOPRODUTO=?, CODIGOCOMPRA=?, QUANTIDADE=?, VALORUNITARIO=? WHERE CODIGO=?";
		            	conn = ConnectionFactory.getConexao();
		                pstm = conn.prepareStatement(UPDATE);
		                pstm.setLong(1, iv.getMedicamento().getCodigo());
		                pstm.setLong(2, iv.getCompra().getCodigo());
		                pstm.setInt(3, iv.getQuantidade());
		                pstm.setDouble(4, iv.getValorUnitario());
		                pstm.setLong(5, iv.getCodigo());
		                
		                pstm.execute();
		            }

		            if (compras.getSituacao() == Situacao.FINALIZADA) {
		                ProdutoDAO mDAO = new ProdutoDAO();
		                mDAO.entradaEstoque(iv.getMedicamento().getCodigo(), iv.getQuantidade());
		            }
		        }	
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar compras no banco de dados " + e.getMessage());
				e.printStackTrace();
			}
			finally {
				ConnectionFactory.fechaConexao(conn, pstm);
			}
		} else {
			JOptionPane.showMessageDialog(null, "O registro de compras enviado por parámetro está vazio");
		}
	}

	@Override
	public void remover(ComprasModel compras) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(UPDATE);
			pstm.setLong(1, compras.getFornecedor().getCodigo());
			pstm.setDate(2, new Date(compras.getDataCompras().getTimeInMillis()));
	        pstm.setDouble(3, compras.getValorTotal());
	        pstm.setInt(4, Situacao.CANCELADA.getId());
	        pstm.setLong(5, compras.getCodigo());
			
	        pstm.execute();
			conn.commit();
			
			JOptionPane.showMessageDialog(null, "Compra cancelada!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir compra!"
				+ e.getMessage());
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	}

	@Override
	public ArrayList<ComprasModel> listarTodos() throws Exception {
		ProdutoDAO mdao = new ProdutoDAO();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		ArrayList<ComprasModel> comprasList = new ArrayList<ComprasModel>();
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECT);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				FornecedorDAO fdao = new FornecedorDAO();
				ComprasModel compra = new ComprasModel();
				
				compra.setCodigo(rs.getLong("CODCOMPRAS"));
				compra.setFornecedor(fdao.recuperar(rs.getLong("CODIGOFORNECEDOR")));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("DATACOMPRA"));
				compra.setDataCompras(data);
				compra.setSituacao(rs.getInt("SITUACAO"));
				
				while (rs.next()) {
	                ItemCompra i = new ItemCompra();
	                i.setCodigo(rs.getInt("CODIGOITEM"));
	                i.setMedicamento(mdao.recuperar(rs.getInt("CODIGOPRODUTO")));
	                i.setCompra(compra);
	                i.setQuantidade(rs.getInt("QUANTIDADE"));
	                i.setValorUnitario(rs.getDouble("VALORUNITARIO"));
	                compra.addItem(i);
	            }
				comprasList.add(compra);			
			}
		} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao listar compras! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}	
		return comprasList;
	}

	@SuppressWarnings("resource")
	@Override
	public ComprasModel recuperar(long codigo) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FornecedorDAO fdao = new FornecedorDAO();
		ProdutoDAO mdao = new ProdutoDAO();
		ComprasModel compra = new ComprasModel();
		
		try {
			conn = ConnectionFactory.getConexao();	
			pstm = conn.prepareStatement(SELECTCOD);
			rs = pstm.executeQuery();
			
			if(rs.next()){
				compra.setCodigo(rs.getInt("CODIGO"));
	            compra.setFornecedor(fdao.recuperar(rs.getLong("CODIGOFORNECEDOR")));
	            
	            Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("DATACOMPRA"));
				compra.setDataCompras(data);
				
	            compra.setSituacao(rs.getInt("SITUACAO"));
	            
	            String sqlItem = "SELECT * FROM TBITEMCOMPRA WHERE CODIGOCOMPRA=?";
	            conn = ConnectionFactory.getConexao();
	            pstm = conn.prepareStatement(sqlItem);
	            pstm.setLong(1, compra.getCodigo());
	            rs = pstm.executeQuery();
	            
	            while (rs.next()) {
	            	ItemCompra i = new ItemCompra();
	                i.setCodigo(rs.getLong("CODIGO"));
	                i.setMedicamento(mdao.recuperar(rs.getLong("CODIGOPRODUTO")));
	                i.setCompra(compra);
	                i.setQuantidade(rs.getInt("QUANTIDADE"));
	                i.setValorUnitario(rs.getDouble("VALORUNITARIO"));
	                compra.addItem(i);				
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao recuperar compras! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}
		return compra;
	}

	public static void main(String[] args) {
		
	}
}
