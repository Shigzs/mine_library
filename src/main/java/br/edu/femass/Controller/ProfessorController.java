package br.edu.femass.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.Dao.Dao;
import br.edu.femass.Diversos.DiversosJavaFX;
import br.edu.femass.model.Leitor.Professor;

public class ProfessorController implements Initializable {
    @FXML
    public TextField Txt_Id;
    @FXML
    public ListView<Professor> Lista_Professor;
    @FXML
    public TextField Txt_Formacao;
    @FXML
    public TextField Txt_Nome;
    @FXML
    public TextField Txt_Telefone;
    @FXML
    public TextField Txt_Email;
    private Dao<Professor> professorDao = new Dao<>(Professor.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaProfessor();
    }

    private void preencherListaProfessor() {
        try {
            ObservableList<Professor> list = FXCollections.observableArrayList(
                    professorDao.findAll()
            );
            Lista_Professor.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void exibeDadosProfessorSelecionado() {
        Professor professor = Lista_Professor.getSelectionModel().getSelectedItem();
        if (professor == null) return;

        Txt_Id.setText(professor.getId().toString());
        Txt_Nome.setText(professor.getNome());
        Txt_Telefone.setText(professor.getTelefone());
        Txt_Email.setText(professor.getEmail());
        Txt_Formacao.setText(professor.getFormacao());
    }

    private void LimparCampos() {
        Txt_Id.setText("");
        Txt_Nome.setText("");
        Txt_Telefone.setText("");
        Txt_Email.setText("");
        Txt_Formacao.setText("");
        Lista_Professor.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_professor_keypressed(KeyEvent keyEvent) {
        exibeDadosProfessorSelecionado();
    }

    @FXML
    public void Lista_professor_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosProfessorSelecionado();
    }

    @FXML
    public void ExcluirButton(ActionEvent actionEvent) {
        Professor professor = Lista_Professor.getSelectionModel().getSelectedItem();
        if (professor == null) return;
        try {
            professorDao.delete(professor.getId());
            LimparCampos();
            preencherListaProfessor();
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private boolean camposEstaoValidos() {
        boolean ProfessorValido =
                Txt_Nome.getText().trim().length() > 0 &&
                        Txt_Telefone.getText().trim().length() > 0 &&
                        Txt_Email.getText().trim().length() > 0 && Txt_Formacao.getText().trim().length() > 0;

        return ProfessorValido;
    }

    private void criarProfessor() {
        Professor professor = new Professor(
            Txt_Nome.getText().trim(), 
            Txt_Telefone.getText().trim(), 
            Txt_Email.getText().trim(), 
            Txt_Formacao.getText().trim()
        );
        professorDao.create(professor);
    }

    @FXML
    public void GravarButton(ActionEvent actionEvent) {
        try {
            if (camposEstaoValidos()) {
                if (Txt_Id.getText().trim().length() == 0) {
                    criarProfessor();
                }
                preencherListaProfessor();
                LimparCampos();
            } else {
                DiversosJavaFX.exibirMensagem("Verifique se os campos foram preenchidos corretamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }
}