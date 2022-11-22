/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxfitnutrition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLPrincipalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clickIrAdminMedicos(ActionEvent event) throws IOException {
        try{
            Parent vistaAdminMedico = FXMLLoader.load(getClass().getResource("FXMLAdminMedicos.fxml"));
            Scene scenaAdminMedico = new Scene (vistaAdminMedico);
            Stage esenarioBase = new Stage();
            esenarioBase.setScene(scenaAdminMedico);
            esenarioBase.initModality(Modality.APPLICATION_MODAL);
            esenarioBase.showAndWait();
            
        }catch(Exception ex){
            Utilidades.mostrarAlertaSimple("Error", "No se puede cargar la ventana principal.", Alert.AlertType.NONE);
        }
    }

    @FXML
    private void clickIrAdminPacientes(ActionEvent event) {
    }

    @FXML
    private void clickIrAdminAlimentos(ActionEvent event) {
    }

    @FXML
    private void clickCerrarSesion(ActionEvent event) {
    }
    
}
