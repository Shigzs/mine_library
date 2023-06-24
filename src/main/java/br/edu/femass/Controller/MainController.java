package br.edu.femass.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    @FXML
    private Label label;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @FXML
    private void handleBtn_Aluno(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAluno.fxml"));
            
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.setTitle("Aluno");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void handleBtn_Autor(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroAutor.fxml"));
            
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.setTitle("Autor");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void handleBtn_Genero(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroGenero.fxml"));
            
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.setTitle("Genero");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void handleBtn_Livro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroGenero.fxml"));
            
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.setTitle("Livro");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void handleBtn_Professor(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/CadastroProfessor.fxml"));
            
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.setTitle("Professor");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    private void handleBtn_Emprestimo(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Emprestimo.fxml"));
            
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            
            stage.setTitle("Emprestimo");
            stage.setScene(scene);
            stage.show();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    
        
}
