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
import br.pharma.model.ClienteModel;
import br.pharma.model.ItemVenda;
import br.pharma.model.ProdutoModel;
import br.pharma.model.Situacao;
import br.pharma.model.VendaModel;

public class VendaDAO implements IDAO<VendaModel>{
	
	private final String INSERTVENDA = "INSERT INTO TBVENDA "
			+ "(CODIGOCLIENTE,DATAVENDA,VALORTOTAL,SITUACAO) VALUES (?,?,?,?)";
	private final String INSERTITEM = "INSERT INTO TBITEMVENDA (CODIGOPRODUTO, CODIGOVENDA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
	private final String SELECT = "SELECT *FROM TBVENDA ORDER BY DATACOMPRA DESC";
	private final String SELECTITEM = "SELECT *FROM TBITEMVENDA WHERE CODIGOVENDA=?";
	private final String SELECTCOD = "SELECT *FROM TBVENDA WHERE CODIGO = ?";
	private String UPDATE = "UPDATE TBVENDA SET CODIGOFORNECEDOR=?, DATACOMPRA=?, VALORTOTAL=?, SITUACAO=?, "
			+ "WHERE CODIGO = ?";

	@Override
	public void inserir(VendaModel venda) throws Exception {
		if(venda != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;		
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(INSERTVENDA, Statement.RETURN_GENERATED_KEYS);
				pstm.setLong(1, venda.getCliente().getCodigo());
				pstm.setDate(2, new Date(venda.getDataVenda().getTimeInMillis()));
				pstm.setDouble(3, venda.getValorTotal());
		        pstm.setInt(4, venda.getSituacao().getId());
		        pstm.execute();

		        rs = pstm.getGeneratedKeys();
		        rs.next();
		        long idVenda = rs.getLong(1);

		        for (ItemVenda iv : venda.getItens()) {
		        	pstm = conn.prepareStatement(INSERTITEM);
					pstm.setLong(1, iv.getProduto().getCodigo());
					pstm.setLong(2, idVenda);
					pstm.setLong(3, iv.getQuantidade());
					pstm.setDouble(4, iv.getValorUnitario());
					
					pstm.execute();
					
					if (venda.getSituacao() == Situacao.FINALIZADA) {
		                ProdutoDAO mdao = new ProdutoDAO();
		                mdao.saidaEstoque(iv.getProduto().getCodigo(), iv.getQuantidade());
		            }
		        }
		        JOptionPane.showMessageDialog(null, "Venda cadastrada!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir venda no banco de "
					+ "dados " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	} else {
		System.out.println("O registro de venda enviado por parámetro está vazio");
		return;
	}
	}

	@Override
	public void atualizar(VendaModel venda) throws Exception {
		if (venda != null) {
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(UPDATE);
				pstm.setLong(1, venda.getCliente().getCodigo());
		        pstm.setDate(2, new Date(venda.getDataVenda().getTimeInMillis()));
		        pstm.setDouble(3, venda.getValorTotal());
		        pstm.setInt(4, venda.getSituacao().getId());
		        pstm.setInt(5, venda.getCodigo());
		        pstm.execute();
		        
		        for (ItemVenda iv : venda.getItensRemover()) {
		            UPDATE = "DELETE FROM TBITEMVENDA WHERE CODIGO=?";
		            conn = ConnectionFactory.getConexao();
		            pstm = conn.prepareStatement(UPDATE);
		            pstm.setLong(1, iv.getCodigo());
		            pstm.execute();
		        }
		        for (ItemVenda iv : venda.getItens()) {
		            if (iv.getCodigo() == 0) {
		                UPDATE = "INSERT INTO TBITEMVENDA (CODIGOPRODUTO, CODIGOVENDA, QUANTIDADE, VALORUNITARIO) VALUES (?, ?, ?, ?)";
		                conn = ConnectionFactory.getConexao();
			            pstm = conn.prepareStatement(UPDATE);
			            pstm.setLong(1, iv.getProduto().getCodigo());
		                pstm.setLong(2, iv.getVenda().getCodigo());
		                pstm.setInt(3, iv.getQuantidade());
		                pstm.setDouble(4, iv.getValorUnitario());
		                pstm.execute();
		            } else {
		                UPDATE = "UPDATE TBITEMVENDA SET CODIGOPRODUTO=?, CODIGOVENDA=?, QUANTIDADE=?, VALORUNITARIO=? WHERE CODIGO=?";
		                pstm = conn.prepareStatement(UPDATE);
			            pstm.setLong(1, iv.getProduto().getCodigo());
		                pstm.setLong(2, iv.getVenda().getCodigo());
		                pstm.setInt(3, iv.getQuantidade());
		                pstm.setDouble(4, iv.getValorUnitario());
		                pstm.setLong(5, iv.getCodigo());
		                pstm.execute();
		            }
		            if (venda.getSituacao() == Situacao.FINALIZADA) {
		                ProdutoDAO mDAO = new ProdutoDAO();
		                mDAO.saidaEstoque(iv.getProduto().getCodigo(), iv.getQuantidade());
		            }
		        }	
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao atualizar vendas no banco de dados " + e.getMessage());
				e.printStackTrace();
			}
			finally {
				ConnectionFactory.fechaConexao(conn, pstm);
			}
		} else {
			JOptionPane.showMessageDialog(null, "O registro de vendas enviado por parámetro está vazio");
		}
	}

	@Override
	public void remover(VendaModel venda) throws Exception {
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(UPDATE);
			pstm.setLong(1, venda.getCliente().getCodigo());
	        pstm.setDate(2, new Date(venda.getDataVenda().getTimeInMillis()));
	        pstm.setDouble(3, venda.getValorTotal());
	        pstm.setInt(4, Situacao.CANCELADA.getId());
	        pstm.setInt(5, venda.getCodigo());
	        pstm.execute();
	        JOptionPane.showMessageDialog(null, "Compra cancelada!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir venda!"
				+ e.getMessage());
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm);
		}
	}

	@SuppressWarnings("resource")
	@Override
	public ArrayList<VendaModel> listarTodos() throws Exception {
		ProdutoDAO mdao = new ProdutoDAO();
		ClienteDAO clienteDAO = new ClienteDAO();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		
		ArrayList<VendaModel> vendaList = new ArrayList<VendaModel>();
		
		try {
			conn = ConnectionFactory.getConexao();
			pstm = conn.prepareStatement(SELECT);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				VendaModel venda = new VendaModel();
				venda.setCliente(clienteDAO.recuperar(rs.getLong("CODIGOCLIENTE")));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("DATAVENDA"));
				
				venda.setDataVenda(data);
				venda.setSituacao(rs.getInt("SITUACAO"));
				
				pstm = conn.prepareStatement(SELECTITEM);
				pstm.setLong(1, venda.getCodigo());
				rs = pstm.executeQuery();
				
				while (rs.next()) {
	                ItemVenda iv = new ItemVenda();
	                iv.setCodigo(rs.getLong("CODIGO"));
	                iv.setProduto(mdao.recuperar(rs.getLong("CODIGOPRODUTO")));
	                iv.setVenda(venda);
	                iv.setQuantidade(rs.getInt("QUANTIDADE"));
	                iv.setValorUnitario(rs.getDouble("VALORUNITARIO"));
	                venda.addItem(iv);
	            }
	            vendaList.add(venda);
	        }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao listar vendas! " + e.getMessage());
		}
		finally {
			ConnectionFactory.fechaConexao(conn, pstm, rs);
		}	
		return vendaList;
	}

	@Override
	public VendaModel recuperar(long codigo) throws Exception {
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		VendaModel vm = new VendaModel();
		ClienteModel cm = new ClienteModel("Inocencio", "1053356", "M", "Pastor Hugo", "23/04/2018", "234523", "ino@ccuec.com");
		
		vm.setCliente(cm);
		ClienteDAO cdao = new ClienteDAO();
		cdao.inserir(cm);
		
		Calendar data = Calendar.getInstance();
		data.setTime(new Date(vm.getDataVenda().getTimeInMillis()));
		
		vm.setDataVenda(data);
		vm.setValorTotal(135.67);
		vm.setSituacao(Situacao.ABERTA.getId());
		
		ProdutoModel mmodel = new ProdutoModel();
		mmodel.setDescriçao("Parasetamol");
		mmodel.setprecoVenda(90.89);
		mmodel.setQtd(3);
		
		ItemVenda itv = new ItemVenda();
		
		itv.setProduto(mmodel);
		itv.setQuantidade(3);
		itv.setValorUnitario(34.89);
		itv.setVenda(vm);
		
		new VendaDAO().inserir(vm);

	}

}
