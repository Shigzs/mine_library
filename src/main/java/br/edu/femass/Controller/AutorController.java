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
import br.edu.femass.model.Livro.Autor;

public class AutorController implements Initializable {
    @FXML
    public TextField Txt_Id;
    @FXML
    public ListView<Autor> Lista_Autor;
    @FXML
    public TextField Txt_Nome;
    @FXML
    public TextField Txt_Sobrenome;
    private Dao<Autor> autorDao = new Dao<>(Autor.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaAutor();
    }

    private void preencherListaAutor() {
        try {
            ObservableList<Autor> list = FXCollections.observableArrayList(
                    autorDao.findAll()
            );
            Lista_Autor.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void exibeDadosAlunoSelecionado() {
        Autor autor = Lista_Autor.getSelectionModel().getSelectedItem();
        if (autor == null) return;

        Txt_Id.setText(autor.getId().toString());
        Txt_Nome.setText(autor.getNome());
        Txt_Sobrenome.setText(autor.getSobrenome());
    }

    private void LimparCampos() {
        Txt_Id.setText("");
        Txt_Nome.setText("");
        Txt_Sobrenome.setText("");
        Lista_Autor.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_autor_keypressed(KeyEvent keyEvent) {
        exibeDadosAlunoSelecionado();
    }

    @FXML
    public void Lista_autor_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosAlunoSelecionado();
    }

    @FXML
    public void ExcluirButton(ActionEvent actionEvent) {
        Autor autor = Lista_Autor.getSelectionModel().getSelectedItem();
        if (autor == null) return;
        try {
            autorDao.delete(autor.getId());
            LimparCampos();
            preencherListaAutor();
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private boolean camposEstaoValidos() {
        boolean AutorValido =
                Txt_Nome.getText().trim().length() > 0 &&
                        Txt_Sobrenome.getText().trim().length() > 0;
        return AutorValido;
    }

    private void criarAutor() {
        Autor autor = new Autor(
            Txt_Nome.getText().trim(), 
            Txt_Sobrenome.getText().trim() 
        );
        autorDao.create(autor);
    }

    @FXML
    public void GravarButton(ActionEvent actionEvent) {
        try {
            if (camposEstaoValidos()) {
                if (Txt_Id.getText().trim().length() == 0) {
                    criarAutor();
                }
                preencherListaAutor();
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