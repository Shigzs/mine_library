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
import br.edu.femass.model.Genero;

public class GeneroController implements Initializable {
    @FXML
    public TextField Txt_Id;
    @FXML
    public ListView<Genero> Lista_Genero;
    @FXML
    public TextField Txt_Nome;
    private Dao<Genero> generoDao = new Dao<>(Genero.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaGenero();
    }

    private void preencherListaGenero() {
        try {
            ObservableList<Genero> list = FXCollections.observableArrayList(
                    generoDao.findAll()
            );
            Lista_Genero.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void exibeDadosGeneroSelecionado() {
        Genero genero = Lista_Genero.getSelectionModel().getSelectedItem();
        if (genero == null) return;

        Txt_Id.setText(genero.getId().toString());
        Txt_Nome.setText(genero.getNome());
    }

    private void LimparCampos() {
        Txt_Id.setText("");
        Txt_Nome.setText("");
        Lista_Genero.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_genero_keypressed(KeyEvent keyEvent) {
        exibeDadosGeneroSelecionado();
    }

    @FXML
    public void Lista_genero_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosGeneroSelecionado();
    }

    @FXML
    public void Btn_Deletar(ActionEvent actionEvent) {
        Genero genero = Lista_Genero.getSelectionModel().getSelectedItem();
        if (genero == null) return;
        try {
            generoDao.delete(genero.getId());
            LimparCampos();
            preencherListaGenero();
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private boolean camposEstaoValidos() {
        boolean GeneroValido =
                Txt_Nome.getText().trim().length() > 0;

        return GeneroValido;
    }

    private void criarGenero() {
        Genero genero = new Genero(
            Txt_Nome.getText().trim()
        );
        generoDao.create(genero);
    }

    @FXML
    public void Btn_Gravar(ActionEvent actionEvent) {
        try {
            if (camposEstaoValidos()) {
                if (Txt_Id.getText().trim().length() == 0) {
                    criarGenero();
                }
                preencherListaGenero();
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