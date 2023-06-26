package br.edu.femass.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import br.edu.femass.Dao.Dao;
import br.edu.femass.Diversos.DiversosJavaFX;
import br.edu.femass.model.Autor;
import br.edu.femass.model.Copia;
import br.edu.femass.model.Genero;
import br.edu.femass.model.Livro;

public class LivroController implements Initializable {
    @FXML
    public TextField Txt_Id;
    @FXML
    public ListView<Livro> Lista_Livro;
    @FXML
    public TextField Txt_Ano;
    @FXML
    public TextField Txt_Nome;
    @FXML
    public TextField Txt_Edicao;
    @FXML
    public TextField Txt_Copias;
    @FXML
    public ComboBox<Genero> ComboBox_Genero;
    @FXML
    public ComboBox<Autor> ComboBox_Autor;

    public Integer numCopias = 1;

    private Dao<Autor> autorDao = new Dao<>(Autor.class);
    private Dao<Genero> generoDao = new Dao<>(Genero.class);
    private Dao<Livro> livroDao = new Dao<>(Livro.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaLivro();
        preencherComboBoxAutor();
        preencherComboBoxGenero();
    }

    private void preencherListaLivro() {
        try {
            ObservableList<Livro> list = FXCollections.observableArrayList(
                    livroDao.findAll()
            );
            Lista_Livro.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }
    private void preencherComboBoxGenero() {
        try {
            ObservableList<Genero> list = FXCollections.observableArrayList(
                    generoDao.findAll()
            );
            ComboBox_Genero.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void preencherComboBoxAutor() {
        try {
            ObservableList<Autor> list = FXCollections.observableArrayList(
                    autorDao.findAll()
            );
            ComboBox_Autor.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void exibeDadosLivroSelecionado() {
        Livro livro = Lista_Livro.getSelectionModel().getSelectedItem();
        if (livro == null) return;

        numCopias = livro.getCopias().size();
        Txt_Id.setText(livro.getId().toString());
        Txt_Nome.setText(livro.getNome());
        Txt_Edicao.setText(livro.getEdicao());
        Txt_Copias.setText(numCopias.toString());
        Txt_Ano.setText(livro.getAno().toString());
    }

    private void LimparCampos() {
        Txt_Id.setText("");
        Txt_Nome.setText("");
        Txt_Edicao.setText("");
        Txt_Copias.setText("");
        Txt_Ano.setText("");
        numCopias = 1;
        Lista_Livro.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_livro_keypressed(KeyEvent keyEvent) {
        exibeDadosLivroSelecionado();
    }

    @FXML
    public void Lista_livro_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosLivroSelecionado();
    }

    @FXML
    public void Btn_Deletar(ActionEvent actionEvent) {
        Livro Livro = Lista_Livro.getSelectionModel().getSelectedItem();
        if (Livro == null) return;
        try {
            livroDao.delete(Livro.getId());
            LimparCampos();
            preencherListaLivro();
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private boolean camposEstaoValidos() {
        boolean LivroValido =
                Txt_Nome.getText().trim().length() > 0 &&
                        Txt_Edicao.getText().trim().length() > 0 &&
                        Txt_Copias.getText().trim().length() > 0 && Txt_Ano.getText().trim().length() > 0;

        return LivroValido;
    }

    private void criarLivro() {
        Livro livro = new Livro();
            livro.setNome(Txt_Nome.getText().trim()); 
            livro.setEdicao(Txt_Edicao.getText().trim());
            livro.setAno(Integer.parseInt(Txt_Ano.getText().trim()));
            livro.setGenero(ComboBox_Genero.getSelectionModel().getSelectedItem());
            livro.setAutor(ComboBox_Autor.getSelectionModel().getSelectedItem());
        for (int i = 0; i < Integer.parseInt(Txt_Copias.getText().trim()); i++){
            livro.addCopia(new Copia(livro));
        }

        livroDao.create(livro);
    }

    @FXML
    public void Btn_Gravar(ActionEvent actionEvent) {
        try {
            if (camposEstaoValidos()) {
                if (Txt_Id.getText().trim().length() == 0) {
                    criarLivro();
                }
                preencherListaLivro();
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