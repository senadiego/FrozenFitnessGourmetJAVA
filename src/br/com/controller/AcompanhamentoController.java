package br.com.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import br.com.DAO.PedidosDAO;
import br.com.DAO.StatusDAO;
import br.com.DAO.TipoVeiculoDAO;
import br.com.DAO.TransportadoraDAO;
import br.com.ajudantes.Mascaras;
import br.com.model.Cliente;
import br.com.model.Pedidos;
import br.com.model.Status;
import br.com.model.TipoVeiculo;
import br.com.model.Transportadora;
import br.com.view.Alerta;
import br.com.view.Janelas;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AcompanhamentoController implements Initializable{
	
	@FXML private AnchorPane anpAcompanhamento;
	@FXML private TabPane tpAcomp;
	@FXML private Tab tabVisuaAcomp;
	@FXML private Tab tabEditAcomp;
	@FXML private TextField txtPediAcomp;
	@FXML private TextField txtCodPediAcomp;
	@FXML private TextField txtNomeClienAcomp;
	@FXML private TableView <Pedidos> tvPedidosAcompa;
	@FXML private TableColumn <Pedidos, Integer> tcCodPedAcomp;
	@FXML private TableColumn <Pedidos, Cliente> tcClientePedAcomp;
	@FXML private TableColumn <Pedidos, Status> tcStatusAcomp;
	@FXML private TableColumn <Pedidos, Transportadora> tcTranspAcomp;
	@FXML private TableColumn <Pedidos, TipoVeiculo> tcVeiculoAcomp;
	@FXML private TableColumn <Pedidos, Number>tcPrecoAcomp;
	@FXML private TableColumn <Pedidos, String>tcDtEntregaAcomp;
	@FXML private Label lblCodPed;
	@FXML private Label lblNomeClien;
	@FXML private Label lblStatus;
	@FXML private Label lblTransp;
	@FXML private Label lblVeículo;
	@FXML private ComboBox <Status> cboStatus;
	@FXML private ComboBox <Transportadora> cboTransp;
	@FXML private ComboBox <TipoVeiculo> cboVeiculo;
	@FXML private Button btnEditarPediAcom;
	@FXML private Button btnAtualizarPediAcomp;
	@FXML private Button btnCancelarPediAcomp;
	@FXML private Button btnConcPediAcomp;
	@FXML private Button btnAtualizarAcom;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Mascaras.mascaraNumeroInteiro(txtPediAcomp);
		
		preencherPedidosAcompanhamento();
		popularComboBox();
		
		btnAtualizarAcom.setOnAction(a -> preencherPedidosAcompanhamento());
		btnAtualizarAcom.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.ENTER) {
		    	preencherPedidosAcompanhamento();
		    	popularComboBox();
		    }
		});
		
		txtPediAcomp.textProperty().addListener(f ->{
			if(!txtPediAcomp.getText().isEmpty())
				buscarPedido();
			else
				preencherPedidosAcompanhamento();
		});
		
		tabEditAcomp.setDisable(true);
		cboVeiculo.setDisable(true);
		
		btnEditarPediAcom.setOnAction(c -> editarAcomp());
		btnCancelarPediAcomp.setOnAction(v -> cancelarAcompa());	
	} 
	
	private void buscarPedido() {
		
		int codPedido = 0;
		
		codPedido = Integer.parseInt(txtPediAcomp.getText());
		
		List<Pedidos> lstPediFilt = PedidosDAO.filtrarPedidos(codPedido);
		
		if (lstPediFilt.isEmpty()){
				
			PopUpController erro = new PopUpController("ERRO", "Nenhum registro encontrado!", "OK");
			Janelas j = new Janelas();
			j.abrirPopup("PopUp.fxml", new Stage(), "Transportadora", false, erro);
				
			lstPediFilt.clear();
			txtPediAcomp.clear();
			preencherPedidosAcompanhamento();
		}
		else{
			tvPedidosAcompa.getItems().clear();
			tvPedidosAcompa.getItems().addAll(lstPediFilt);
		}
		
	}

	private void preencherPedidosAcompanhamento(){
		
		tcCodPedAcomp.setCellValueFactory(new PropertyValueFactory<Pedidos, Integer>("codPedido"));
		tcClientePedAcomp.setCellValueFactory(new PropertyValueFactory<Pedidos, Cliente>("cliente"));
		tcStatusAcomp.setCellValueFactory(new PropertyValueFactory<Pedidos, Status>("status"));
		tcTranspAcomp.setCellValueFactory(new PropertyValueFactory<Pedidos, Transportadora>("transportadora"));
		tcPrecoAcomp.setCellValueFactory(new PropertyValueFactory<Pedidos, Number>("total"));
		tcVeiculoAcomp.setCellValueFactory(new PropertyValueFactory<Pedidos, TipoVeiculo>("tipoVeiculo"));
		
		tcDtEntregaAcomp.setCellValueFactory( 		
			dtEntrega -> {
				SimpleStringProperty propriedade = new SimpleStringProperty();
				DateFormat dF = new SimpleDateFormat("dd-MM-yyyy");
				propriedade.setValue(dF.format(dtEntrega.getValue().getDtEntrega()));
				
				return propriedade;
			}
		);
		
		List<Pedidos> lstPedidos = PedidosDAO.selecionarTodosPedidos();
		
		tvPedidosAcompa.getItems().clear();
		tvPedidosAcompa.getItems().addAll(lstPedidos);	
	}
	
	private void cancelarAcompa() {
		tpAcomp.getSelectionModel().select(0);
		tabEditAcomp.setDisable(true);
		tabVisuaAcomp.setDisable(false);
		
		limparAcompanhamento();
	}

	private void editarAcomp() {
		
		Pedidos pdAcom = tvPedidosAcompa.getSelectionModel().getSelectedItem();
		
		if(pdAcom == null){		
			Alerta alertaErro = new Alerta(); 
			alertaErro.alertaErro("Pedidos", "ERRO", "Nenhum item selecionado.");
		}
		else{
			tpAcomp.getSelectionModel().select(1);
			tabEditAcomp.setDisable(false);
			tabVisuaAcomp.setDisable(true);
			
			btnConcPediAcomp.setDisable(true);
			btnAtualizarPediAcomp.setDisable(false);
			btnCancelarPediAcomp.setDisable(false);
			
			lblStatus.setDisable(false);
			cboStatus.setDisable(false);
			
			popularComboBox();
		
			txtCodPediAcomp.setText(String.valueOf(pdAcom.getCodPedido()));
			txtNomeClienAcomp.setText(pdAcom.getCliente().getNomeCliente());
			
			for(int i=0 ; i < cboStatus.getItems().size();i++){
				
				Status s = cboStatus.getItems().get(i);
				
				if(s.getCodStatus() == pdAcom.getStatus().getCodStatus()){
					cboStatus.getSelectionModel().select(i);
					break;
				}
			}
		}
		btnAtualizarPediAcomp.setOnAction(c -> atualizarPedido());		
	}

	private void atualizarPedido() {
		
		Integer codStat = null;
		Integer	codVeic = null;
		Integer codPed = null;
		
		btnConcPediAcomp.setDisable(true);
		
		Pedidos codPedi = tvPedidosAcompa.getSelectionModel().getSelectedItem();
		Pedidos upPedi = new Pedidos();
		
		if(cboStatus.getSelectionModel().getSelectedItem() != null)
			codStat = cboStatus.getSelectionModel().getSelectedItem().getCodStatus();	
		
		if(cboVeiculo.getSelectionModel().getSelectedItem() != null)
			codVeic = cboVeiculo.getSelectionModel().getSelectedItem().getCodTipoVeiculo();	
		
		codPed = codPedi.getCodPedido();
		
		upPedi.setCodPedido(codPed);
		upPedi.setCodStatus(codStat);
		
		if(codVeic != null){
			upPedi.setCodVeiculoTransp(codVeic);
		}
		
		if(PedidosDAO.updatePedido(codVeic, codPed, codStat)){
			
			PopUpController sucesso = new PopUpController("SUCESSO", "Pedido Atualizado com sucesso!", "OK");
			Janelas j = new Janelas();
			j.abrirPopup("PopUp.fxml", new Stage(), "Pedidos", false, sucesso);	
			
			preencherPedidosAcompanhamento();
			
			btnCancelarPediAcomp.setDisable(true);
			btnAtualizarPediAcomp.setDisable(true);
			btnConcPediAcomp.setDisable(false);
			
			lblStatus.setDisable(true);
			cboStatus.setDisable(true);
			
			lblTransp.setDisable(true);
			cboTransp.setDisable(true);
			
			lblVeículo.setDisable(true);
			cboVeiculo.setDisable(true);
			
			btnConcPediAcomp.setOnAction(v -> acompConcluido());
			btnConcPediAcomp.setOnKeyPressed(f -> {
				if (f.getCode() == KeyCode.ENTER){
					acompConcluido();
				}
			});
		}
	}

	private void acompConcluido() {
		
		tpAcomp.getSelectionModel().select(0);
		tabEditAcomp.setDisable(true);
		tabVisuaAcomp.setDisable(false);
	}

	private void popularComboBox() {
		
		if(cboStatus != null){
			cboStatus.getItems().clear();
			cboStatus.getItems().addAll(StatusDAO.selecionarTodosStatus());
		}
		
		if(cboTransp != null){
			cboTransp.getItems().clear();
			cboTransp.getItems().addAll(TransportadoraDAO.selecionarTodas());
		}
		
		cboStatus.valueProperty().addListener(new ChangeListener<Status>() {
			@Override
			public void changed(ObservableValue<? extends Status> arg0, Status arg1, Status arg2) {
						
				if(cboStatus.getSelectionModel().getSelectedItem() != null){
				
					if(cboStatus.getSelectionModel().getSelectedItem().getStatusPedido().equals("Enviado para a Transportadora")){
							cboTransp.setDisable(false);
							lblTransp.setDisable(false);
					}
					else{
						lblTransp.setDisable(true);
						cboTransp.setDisable(true);
						cboTransp.getSelectionModel().clearSelection();
							
						lblVeículo.setDisable(true);
						cboVeiculo.setDisable(true);
						cboVeiculo.getSelectionModel().clearSelection();
					}	
				}	
			}
		});
		
		cboTransp.valueProperty().addListener(new ChangeListener<Transportadora>() {
			@Override
			public void changed(ObservableValue<? extends Transportadora> observable, Transportadora oldValue,
					Transportadora newValue) {
				
				if(cboTransp.getSelectionModel().getSelectedItem() != null){
					
					lblVeículo.setDisable(false);
					cboVeiculo.setDisable(false);
					
					List<TipoVeiculo> nomeVeiculo = TipoVeiculoDAO
							.filtrarTransp(cboTransp.getSelectionModel().getSelectedItem().getCodTransportadora());	
					
					cboVeiculo.getItems().clear();
					cboVeiculo.getItems().addAll(nomeVeiculo);
				}
				else{
					lblVeículo.setDisable(true);
					cboVeiculo.setDisable(true);
					cboVeiculo.getSelectionModel().clearSelection();
				}
			}
		});	
	}
	
	private void limparAcompanhamento() {
		
		cboVeiculo.getSelectionModel().clearSelection();
		cboTransp.getSelectionModel().clearSelection();
		cboStatus.getSelectionModel().clearSelection();
		
	}

}