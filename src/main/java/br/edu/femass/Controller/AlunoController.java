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
import br.edu.femass.model.Aluno;

public class AlunoController implements Initializable {
    @FXML
    public TextField Txt_Id;
    @FXML
    public ListView<Aluno> Lista_Aluno;
    @FXML
    public TextField Txt_Matricula;
    @FXML
    public TextField Txt_Nome;
    @FXML
    public TextField Txt_Telefone;
    @FXML
    public TextField Txt_Email;
    private Dao<Aluno> alunoDao = new Dao<>(Aluno.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaAluno();
    }

    private void preencherListaAluno() {
        try {
            ObservableList<Aluno> list = FXCollections.observableArrayList(
                    alunoDao.findAll()
            );
            Lista_Aluno.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void exibeDadosAlunoSelecionado() {
        Aluno aluno = Lista_Aluno.getSelectionModel().getSelectedItem();
        if (aluno == null) return;

        Txt_Id.setText(aluno.getId().toString());
        Txt_Nome.setText(aluno.getNome());
        Txt_Telefone.setText(aluno.getTelefone());
        Txt_Email.setText(aluno.getEmail());
        Txt_Matricula.setText(aluno.getMatricula());
    }

    private void LimparCampos() {
        Txt_Id.setText("");
        Txt_Nome.setText("");
        Txt_Telefone.setText("");
        Txt_Email.setText("");
        Txt_Matricula.setText("");
        Lista_Aluno.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_aluno_keypressed(KeyEvent keyEvent) {
        exibeDadosAlunoSelecionado();
    }

    @FXML
    public void Lista_aluno_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosAlunoSelecionado();
    }

    @FXML
    public void Btn_Deletar(ActionEvent actionEvent) {
        Aluno aluno = Lista_Aluno.getSelectionModel().getSelectedItem();
        if (aluno == null) return;
        try {
            alunoDao.delete(aluno.getId());
            LimparCampos();
            preencherListaAluno();
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private boolean camposEstaoValidos() {
        boolean AlunoValido =
                Txt_Nome.getText().trim().length() > 0 &&
                        Txt_Telefone.getText().trim().length() > 0 &&
                        Txt_Email.getText().trim().length() > 0 && Txt_Matricula.getText().trim().length() > 0;

        return AlunoValido;
    }

    private void criarAluno() {
        Aluno aluno = new Aluno(
            Txt_Nome.getText().trim(), 
            Txt_Telefone.getText().trim(), 
            Txt_Email.getText().trim(), 
            Txt_Matricula.getText().trim()
        );
        alunoDao.create(aluno);
    }

    @FXML
    public void Btn_Gravar(ActionEvent actionEvent) {
        try {
            if (camposEstaoValidos()) {
                if (Txt_Id.getText().trim().length() == 0) {
                    criarAluno();
                }
                preencherListaAluno();
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