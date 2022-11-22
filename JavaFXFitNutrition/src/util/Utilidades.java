/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;

/**
 *
 * @author andre
 */
public class Utilidades {
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipoAlerta){
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }
}
