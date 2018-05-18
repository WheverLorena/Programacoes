package br.pharma.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Calendar;

import javax.swing.JOptionPane;

import br.pharma.connection.ConnectionFactory;
import br.pharma.model.EstoqueModel;

public class EstoqueDAO {
	private final String INSERT = "INSERT INTO ESTOQUE "
			+ "(ESTRADA_NO_DIA, ENTRADATOTAL, SAIDA_NO_DIA, SAIDATOTAL, DATAENTRADA, PRODUTO) VALUES (?,?,?,?,?,?)";
	private final String SELECT = "SELECT *FROM ESTOQUE";
	
	public void inserir(EstoqueModel estoque){
		if(estoque != null){
			Connection conn = null;
			PreparedStatement pstm = null;
			try {
				conn = ConnectionFactory.getConexao();
				pstm = conn.prepareStatement(INSERT);
				pstm.setDouble(1, estoque.getEntrada_dia());
				pstm.setDouble(2, estoque.getEntrada_total());
				pstm.setDouble(3, estoque.getSaida_dia());
				pstm.setDouble(4, estoque.getSaida_total());
				pstm.setDate(5, new java.sql.Date(estoque.getData_entrada().getTimeInMillis()));
				pstm.setLong(6, estoque.getId_prod());
				pstm.execute();
				
				JOptionPane.showMessageDialog(null, "Estoque cadastrado!");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao inserir estoque no banco de "
						+ "dados " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
/*
	public static void main(String[] args) {
	EstoqueModel est = new EstoqueModel(23.00,34.67,56.65,Calendar.getInstance(),1);
	new EstoqueDAO().inserir(est);
	}
*/
}
