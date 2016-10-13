package br.com.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.view.Janelas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable{
	@FXML
	TextField txtUsuario;
	@FXML
	Button btnEntrar;
	@FXML
	PasswordField txtSenha;
	@FXML AnchorPane anpLogin;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnEntrar.setOnAction(x -> login());
		
		btnEntrar.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.ENTER) {
		        login();
		    }
		});
		
		txtUsuario.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.ENTER) {
		        login();
		    }
		});
		
		txtSenha.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.ENTER) {
		        login();
		    }
		});
		
	}
	
	@FXML
	public void login() {

		if(txtUsuario.getText().equals("teste") && txtSenha.getText().equals("123")){
			
			System.out.println();
			System.out.println("USUARIO " + txtUsuario.getText());
			System.out.println("SENHA " + txtSenha.getText());
			System.out.println("LOGIN EFETUADO COM SUCESSO...");

			limpar();
			
			Janelas layout = new Janelas();
			layout.abrir("Layout.fxml", new Stage(), "", true);
			
			Stage login = (Stage)btnEntrar.getScene().getWindow();
			login.close();
		}
		else{
			
		
			
			PopUpController erro = new PopUpController("ERRO DE LOGIN", "Usu�rio e Senha incorretos!", "Fechar");

			Janelas erroLogin = new Janelas();
			erroLogin.abrirPopup("PopUp.fxml", new Stage(), "Erro de Login", false, erro);
			
			limpar();
			System.out.println();
			System.out.println("ERRO DE LOGIN");
		}
	}
	public void limpar(){
		txtUsuario.clear();
		txtSenha.clear();
	}
}
