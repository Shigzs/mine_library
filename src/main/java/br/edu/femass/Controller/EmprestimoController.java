package br.edu.femass.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.femass.Dao.CopiaDao;
import br.edu.femass.Dao.Dao;
import br.edu.femass.Diversos.DiversosJavaFX;
import br.edu.femass.model.Copia;
import br.edu.femass.model.Emprestimo;
import br.edu.femass.model.Genero;
import br.edu.femass.model.Leitor;
import br.edu.femass.model.Livro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class EmprestimoController implements Initializable {
    @FXML
    public TextField Txt_Id;
   @FXML
    public DatePicker DP_Data;
    @FXML
    public DatePicker DP_DataPrevistaEntrega;
    @FXML
    public DatePicker DP_DataEntrega;
     @FXML
    public ComboBox<Copia> ComboBox_Copias;
    @FXML
    public ComboBox<Livro> ComboBox_Livros;
    @FXML
    public ComboBox<Leitor> ComboBox_Leitores;
    @FXML
    public ListView<Emprestimo> Lista_Emprestimo;
    
    private Dao<Emprestimo> emprestimoDao = new Dao<>(Emprestimo.class);
    private Dao<Leitor> leitorDao = new Dao<>(Leitor.class);
    private Dao<Livro> livroDao = new Dao<>(Livro.class);
    private CopiaDao copiaDao = new CopiaDao(Copia.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaEmprestimos();
        preencherComboBox_Leitores();
        preencherComboBox_Livros();
        limparCampos();
    }
    
    public boolean validar(){
        if(
            ComboBox_Livros.getValue()==null ||
            ComboBox_Leitores.getValue()==null ||
            DP_Data.getValue()==null
        ){
            return false;
        }
        return true;
    }

    private void preencherListaEmprestimos() {
        try {
            ObservableList<Emprestimo> list = FXCollections.observableArrayList(
                    emprestimoDao.findAll()
            );
            Lista_Emprestimo
    .setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void preencherComboBox_Livros() {
        try {
            ObservableList<Livro> list = FXCollections.observableArrayList(
                    livroDao.findAll()
            );
            ComboBox_Livros.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void preencherComboBox_Leitores() {
        try {
            ObservableList<Leitor> list = FXCollections.observableArrayList(
                    leitorDao.findAll()
            );
            ComboBox_Leitores.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void preencherComboBox_Copias(Livro livro) {
        try {
            ObservableList<Copia> list = FXCollections.observableArrayList(
                    livro.getCopias()
            );
            ComboBox_Copias.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    @FXML
    private void ComboBox_Livros_Selecionar_Livro(ActionEvent actionEvent){
        preencherComboBox_Copias(ComboBox_Livros.getSelectionModel().getSelectedItem());
    }

    private void limparCampos() {
        Txt_Id.setText("");
        ComboBox_Copias.getSelectionModel().clearSelection();
        DP_Data.setValue(LocalDate.now());
        DP_DataPrevistaEntrega.setValue(null);
        DP_DataEntrega.setValue(null);
        Lista_Emprestimo.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_emprestimo_keypressed(KeyEvent keyEvent) {
        exibeDadosEmprestimoSelecionado();
    }

    @FXML
    public void Lista_emprestimo_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosEmprestimoSelecionado();
    }

    private Livro buscaLivroEmprestimo(Emprestimo emprestimo){
        for(Livro livro : ComboBox_Livros.getItems()){
            if(livro.getId() == emprestimo.getCopia().getLivro().getId()) return livro;
        }
        return null;
    }

    private void exibeDadosEmprestimoSelecionado() {
        Emprestimo emprestimo = Lista_Emprestimo.getSelectionModel().getSelectedItem();
        if (emprestimo == null) return;

        Txt_Id.setText(emprestimo.getId().toString());
        ComboBox_Copias.getSelectionModel().select(emprestimo.getCopia());
        DP_Data.setValue(emprestimo.getData());
        DP_DataPrevistaEntrega.setValue(emprestimo.getDataPrevistaEntrega());
        DP_DataEntrega.setValue(emprestimo.getDataEntrega());
        ComboBox_Livros.getSelectionModel().select(buscaLivroEmprestimo(emprestimo));
        ComboBox_Leitores.getSelectionModel().select(emprestimo.getLeitor());
    }

    @FXML
    public void Btn_Deletar(ActionEvent actionEvent) {
        Emprestimo emprestimo = Lista_Emprestimo.getSelectionModel().getSelectedItem();
        if (emprestimo == null) return;
        try {
            emprestimoDao.delete(emprestimo.getId());
            limparCampos();
            preencherListaEmprestimos();
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    @FXML
    public void Btn_Devolver(ActionEvent actionEvent) {
        Emprestimo emprestimo = Lista_Emprestimo.getSelectionModel().getSelectedItem();
        if (emprestimo == null) return;
        try {
            Copia copiaDevolvida = emprestimo.getCopia();
            copiaDevolvida.setDisponivel(true);
            emprestimo.setDataEntrega(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void criaEmprestimo(){
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setData(LocalDate.now());
        emprestimo.setDataPrevistaEntrega(DP_DataPrevistaEntrega.getValue());
        emprestimo.setLeitor(ComboBox_Leitores.getSelectionModel().getSelectedItem());
        Copia copiaSelecionada = ComboBox_Copias.getSelectionModel().getSelectedItem();
        copiaSelecionada.setDisponivel(false);
        copiaDao.update(copiaSelecionada);
        emprestimo.setCopia(copiaSelecionada);
        emprestimoDao.create(emprestimo);
    }

    private void atualizaEmprestimo(){
        Emprestimo emprestimo = Lista_Emprestimo.getSelectionModel().getSelectedItem();
        Copia copiaDevolvida = emprestimo.getCopia();
        copiaDevolvida.setDisponivel(true);
        Copia copiaEmprestada = ComboBox_Copias.getSelectionModel().getSelectedItem();
        copiaEmprestada.setDisponivel(false);
        emprestimo.setCopia(copiaEmprestada);
        emprestimoDao.update(emprestimo);
    }

    @FXML
    public void Btn_Gravar(ActionEvent actionEvent) {
        try {
            if(Txt_Id.getText().length() == 0){
                criaEmprestimo();
            }else{
                atualizaEmprestimo();
            }
            limparCampos();
            preencherListaEmprestimos();
            //DiversosJavaFX.exibirMensagem("Verifique se os campos foram preenchidos corretamente");
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    @FXML
    public void handleComboBox_LeitoresAction(ActionEvent actionEvent) {
        try {
            DP_DataPrevistaEntrega.setValue(LocalDate.now().plusDays(ComboBox_Leitores.getSelectionModel().getSelectedItem().getDiasDeEmprestimo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}