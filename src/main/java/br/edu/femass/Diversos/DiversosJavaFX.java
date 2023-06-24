package br.edu.femass.Diversos;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DiversosJavaFX {
    
    public static void exibirMensagem(String mensagem){
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle(mensagem);
            alerta.show();
    }
}