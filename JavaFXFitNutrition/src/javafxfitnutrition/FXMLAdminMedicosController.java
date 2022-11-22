/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxfitnutrition;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.ConexionServiciosWeb;
import pojos.Medico;
import util.Constante;
import util.Utilidades;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLAdminMedicosController implements Initializable {

    @FXML
    private TextField tfBusquedaMedicos;
    @FXML
    private TableView<Medico> tbMedicos;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoPaterno;
    @FXML
    private TableColumn colApellidoMaterno;
    @FXML
    private TableColumn colFechaNacimiento;
    @FXML
    private TableColumn colRol;
    
    private ObservableList<Medico> ListaMedicos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarColumnasTabla();
        cargarInformacionMedicos();
    }    
    
    private void inicializarColumnasTabla(){
        ListaMedicos = FXCollections.observableArrayList();
        //Asosciar columnas con atributos
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("noPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
    }
    
    private void cargarInformacionMedicos(){
        String urlWS = Constante.URL_BASE + "medicos/all";
        try{
            String resultadoWS = ConexionServiciosWeb.peticionServicioGET(urlWS);
            Gson gson = new Gson();
            Type listaTipoMedico = new TypeToken<ArrayList<Medico>>(){}.getType();
            ArrayList medicosWS = gson.fromJson(resultadoWS, listaTipoMedico);
            ListaMedicos.addAll(medicosWS);
            tbMedicos.setItems(ListaMedicos);
        }catch(Exception ex){
            Utilidades.mostrarAlertaSimple("Error de conexión", "Por el momento no se puede obtener la informacion de los médicos.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clickAgregarMedico(ActionEvent event) {
    }

    @FXML
    private void clickModificarMedico(ActionEvent event) {
        int filaSeleccionada = tbMedicos.getSelectionModel().getFocusedIndex();
        if(filaSeleccionada>=0){
            try{
                int idMedicoSeleccionado = ListaMedicos.get(filaSeleccionada).getIdMedico();
                FXMLLoader loadControler = new FXMLLoader(getClass().getResource("FXMLFormularioMedico.fxml"));
                Parent vistaFormulario = loadControler.load();
                FXMLFormularioMedicoController controladorFormulario = loadControler.getController();
                //controladorFormulario.InicializarInformacionVentana(idMedicoSeleccionado);
                Scene escenaFormulario = new Scene(vistaFormulario);
                Stage escenarioFormulario = new Stage();
                escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
                escenarioFormulario.showAndWait();
            }catch(Exception ex){
                Utilidades.mostrarAlertaSimple("Error de navegación", "Error al cargar el formulario", Alert.AlertType.ERROR);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un registro", "Debes seleccionar un medico para su modificación...", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickEliminarMedico(ActionEvent event) {
    }
    
}
