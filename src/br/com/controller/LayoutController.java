package br.com.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import br.com.view.Alerta;
import br.com.view.Janelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LayoutController implements Initializable{
	@FXML
	private Label lblUsuario;
	@FXML
	private Label lblHora;
	@FXML
	private Label lblData;
	@FXML
	private Button btnSair;
	@FXML
	private DatePicker dpDataInicial;
	@FXML
	private DatePicker dpDataFinal;
	@FXML
	private Button btnFiltrar;
	@FXML
	private Label lblIni;
	@FXML
	private Label lblFim;
	@FXML
	private TextField txtBuscaTrans;
	@FXML
	private Button btnBuscaTrans;
	@FXML
	private Button btnNovaTransportadora;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblUsuario.setText(System.getProperty("user.name"));
		
		SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm");
		lblHora.setText(sdfh.format(new Date()));
		
		SimpleDateFormat sdfd = new SimpleDateFormat("dd/MM/YYYY");
		lblData.setText(sdfd.format(new Date()));
	}
	
	// Event Listener on Button[#btnSair].onAction
	@FXML
	public void sair(ActionEvent event) {
		Janelas login = new Janelas();
		login.abrir("Login.fxml", new Stage(), "Login", false);
		
		Stage layout = (Stage)btnSair.getScene().getWindow();
		layout.close();
	}
	// Event Listener on Button[#btnFiltrar].onAction
	@FXML
	public void filtrar(ActionEvent event) {
		System.out.println();
		System.out.println(dpDataInicial.getValue());
		System.out.println(dpDataFinal.getValue());
		
		lblIni.setText(dpDataInicial.getValue().toString());
		lblFim.setText(dpDataFinal.getValue().toString());
		
		dpDataInicial.setValue(null);
		dpDataFinal.setValue(null);
	}
	// Event Listener on Button[#btnBuscaTrans].onAction
	@FXML
	public void buscarTransportadora(ActionEvent event) {
		
		if(txtBuscaTrans.getText().isEmpty()){
			
			System.out.println("erro");
			txtBuscaTrans.clear();
			
			Alerta erroBuscaTrans = new Alerta();
			erroBuscaTrans.alerta("ERRO", "Preencha o nome da transportadora!");
			
		}else{
			System.out.println();
			System.out.println("Buscou Transportadora");
			System.out.println(txtBuscaTrans.getText());
			txtBuscaTrans.clear();
			System.out.println();
		}
	}

	// Event Listener on Button[#btnNovaTransportadora].onAction
	@FXML
	public void novaTransportadora(ActionEvent event) {
		Janelas novaTrans = new Janelas();
		novaTrans.abrir("NovaTransportadora.fxml", new Stage(), "Cadastrar nova Transportadora", false);
	}
	
}
