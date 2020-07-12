package it.polito.tdp.flightdelays;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private boolean flag = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private ComboBox<Airline> cmbBoxLineaAerea;

    @FXML
    private Button caricaVoliBtn;

    @FXML
    private TextField numeroPasseggeriTxtInput;

    @FXML
    private TextField numeroVoliTxtInput;

    @FXML
    void doCaricaVoli(ActionEvent event) {
    	
    	Airline air = this.cmbBoxLineaAerea.getValue();
    	
    	if(air==null) {
    		this.txtResult.setText("DEvi selezionare una linea Aerea");
    		return;
    	}
    	
    	this.model.creaGrafo(air);
    	this.txtResult.setText("Grafo creato\n#VERTICI: "+this.model.getNumeroVertici()+"\n#ARCHI: "+this.model.getNumeroArchi()+"\n");
    	
    	
    	for(int i = 0; i<10; i++) {
    		this.txtResult.appendText(this.model.getPeggiori().get(i)+"\n");
    	}
    	
    	this.flag = true;

    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	if(this.flag == false) {
    		this.txtResult.appendText("Creare prima il grafo");
    		return;
    	}
    	
    	int k;
    	
    	try {
    	    		
    	    k = Integer.parseInt(this.numeroPasseggeriTxtInput.getText());
    	    	    		
    	}catch(NumberFormatException ne) {
    	    this.txtResult.setText("Formato errato di passeggeri\n");
    	    return;
    	}
    	
    	
    	int voli;
    	
    	try {
    	    		
    	  voli = Integer.parseInt(this.numeroVoliTxtInput.getText());
    	    	    		
    	}catch(NumberFormatException ne) {
    	    this.txtResult.setText("Formato numero voli errato\n");
    	    return;
    	}
    	
    	if(k<=0 || voli<=0) {
    		this.txtResult.setText("I numeri inseriti devono essere maggiori di zero");
    		return;
    	}
    	
    	this.txtResult.clear();
    	Map<Integer,Double> map = new TreeMap<>(this.model.simula(k, voli));
    	
    	for(Integer i: map.keySet()) {
    		this.txtResult.appendText(i+"  ritardo: "+map.get(i)+"\n");
    	}

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert cmbBoxLineaAerea != null : "fx:id=\"cmbBoxLineaAerea\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert caricaVoliBtn != null : "fx:id=\"caricaVoliBtn\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroPasseggeriTxtInput != null : "fx:id=\"numeroPasseggeriTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'FlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.cmbBoxLineaAerea.getItems().addAll(this.model.getAirlines());
	}
}
