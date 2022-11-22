/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxfitnutrition;

import com.google.gson.Gson;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import modelo.ConexionServiciosWeb;
import pojos.Medico;
import util.Constante;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLFormularioMedicoController implements Initializable {

    private Medico medico = null;
    private boolean isEdicion = false;
    private String[] roles = {"admin", "medico", "nutricionista"};
    
    private TextField nombre;
    private TextField apellidos;
    private TextField fechaNacimiento;
    private TextField genero;
    private TextField domicilio;
    private TextField noPersonal;
    private TextField cedula;
    private TextField pass;
    private TextField rol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarInformacionVentana(int idMedico){
        isEdicion = true;
        this.medico = this.fetchMedico(idMedico);
        this.loadData();
    }
    
    private Medico fetchMedico(int id) {
        Medico medico = null;
        Gson gson = new Gson();
        String urlWS = Constante.URL_BASE + "medicos/by/" + Integer.toString(id);
        
        try {
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            medico = gson.fromJson(resultadoWS, Medico.class);
        }
        catch(Exception e) {
            Utilidades.mostrarAlertaSimple(
                "Error de conexión", 
                "Por el momento no se puede cargar el médico seleccionado", 
                Alert.AlertType.ERROR
            );
        }
        
        return medico; 
    }

    private void loadData() {
        String apellidos = this.medico.getApellidoPaterno() + " " + this.medico.getApellidoMaterno();
        String rol = this.roles[ this.medico.getIdRol()-1 ];
        
        this.nombre.setText(this.medico.getNombre());
        this.apellidos.setText(apellidos);
        this.fechaNacimiento.setText(this.medico.getFechaNacimiento());
        this.genero.setText(this.medico.getGenero());
        this.domicilio.setText(this.medico.getDomicilio());
        this.noPersonal.setText(this.medico.getNoPersonal());
        this.cedula.setText(this.medico.getCedula());
        this.pass.setText(this.medico.getPassword());
        this.rol.setText(rol);
    }
}
