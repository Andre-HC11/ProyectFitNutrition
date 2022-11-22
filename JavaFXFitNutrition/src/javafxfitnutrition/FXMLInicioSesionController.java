/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxfitnutrition;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.ConexionServiciosWeb;
import pojos.RespuestaLogin;
import util.Constante;
import util.Utilidades;

/**
 *
 * @author andre
 */
public class FXMLInicioSesionController implements Initializable {

    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField tfNumPersonal;
    @FXML
    private PasswordField pfPassword;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void clicIngresarSistema(ActionEvent event) {
        String noPersonal = tfNumPersonal.getText();
        String password = pfPassword.getText();
        
        if(noPersonal.isEmpty() || password.isEmpty()){
            Utilidades.mostrarAlertaSimple("Campos requeridos", "Debes ingresar el Número de personal y/o contraseña para ingresar ", Alert.AlertType.WARNING);
        }else{
            consumirServicioLogin(noPersonal, password);
            irPantallaPrincipal();
        }
    }

    private void consumirServicioLogin(String noPersonal, String password) {
        try {
            String urlServicio =  Constante.URL_BASE+"acceso/escritorio";
            String parametros = "noPersonal="+noPersonal+"&password="+password;
            String resultadoWS = ConexionServiciosWeb.peticionServicioPOST(urlServicio, parametros);
            Gson gson = new Gson();
            RespuestaLogin respuestaLogin = gson.fromJson(resultadoWS, RespuestaLogin.class);
            if(!respuestaLogin.getError()){
                Utilidades.mostrarAlertaSimple("Credenciales correctas", "Bienvenido "+respuestaLogin.getNombre()+" "+respuestaLogin.getApellidoPaterno()+" al sistema", Alert.AlertType.INFORMATION);
            }else{
                Utilidades.mostrarAlertaSimple("Respuesta incorrecta", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
            }
        }catch (Exception e){
            Utilidades.mostrarAlertaSimple("Error de conexión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void irPantallaPrincipal(){
        try{
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLPrincipal.fxml"));
            Scene scenarioPrincipal = new Scene(vista);
            Stage scenarioBase =  (Stage) tfNumPersonal.getScene().getWindow();
            scenarioBase.setScene(scenarioPrincipal);
            scenarioBase.show();
            
        }catch(Exception e){
            Utilidades.mostrarAlertaSimple("Error", "No se puede cargar la ventana principal.", Alert.AlertType.NONE);
        }
        
    }
}
