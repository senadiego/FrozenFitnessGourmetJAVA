package br.com.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ajudantes.MySqlConexao;

public class Cliente {

	private int codCliente;
	private String nomeCliente;
	private String cpfCliente;
	private Date dtNascCliente;
	private float peso;
	private float altura;
	private String telefoneCliente;
	private String celularCliente;
	private String emailCliente;
	
	
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCpfCliente() {
		return cpfCliente;
	}
	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}
	public Date getDtNascCliente() {
		return dtNascCliente;
	}
	public void setDtNascCliente(Date dtNascCliente) {
		this.dtNascCliente = dtNascCliente;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public float getAltura() {
		return altura;
	}
	public void setAltura(float altura) {
		this.altura = altura;
	}
	public String getTelefoneCliente() {
		return telefoneCliente;
	}
	public void setTelefoneCliente(String telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}
	public String getCelularCliente() {
		return celularCliente;
	}
	public void setCelularCliente(String celularCliente) {
		this.celularCliente = celularCliente;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
	
	public static List<Cliente> selecionarTodosClientes() {
		
		Connection c = MySqlConexao.ConectarDb();
		
		String sqlSelect = "SELECT * FROM tblCliente ;";

		List <Cliente> lstCliente = new ArrayList<>(); 
		
		ResultSet rs;
		try {
			rs = c.createStatement().executeQuery(sqlSelect);

			while(rs.next()){
				
				Cliente cl = new Cliente();
				
				cl.setNomeCliente(rs.getString("nomeCliente"));
				cl.setCpfCliente(rs.getString("cpfCliente"));
				cl.setDtNascCliente(rs.getDate("dtNascCliente"));
				cl.setPeso(rs.getFloat("peso"));
				cl.setAltura(rs.getFloat("altura"));
				cl.setTelefoneCliente(rs.getString("telefoneCliente"));
				cl.setCelularCliente(rs.getString("celularCliente"));
				cl.setCelularCliente(rs.getString("celularCliente"));
				
				lstCliente.add(cl);			
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstCliente;
	}
	
	
	@Override
	public String toString() {
		return nomeCliente ;
	}
	
	
	
}
