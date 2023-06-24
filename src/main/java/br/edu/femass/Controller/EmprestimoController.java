package br.edu.femass.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import br.edu.femass.Dao.CopiaDao;
import br.edu.femass.Dao.Dao;
import br.edu.femass.Diversos.DiversosJavaFX;
import br.edu.femass.model.Leitor.Emprestimo;
import br.edu.femass.model.Leitor.Leitor;
import br.edu.femass.model.Livro.Copia;
import br.edu.femass.model.Livro.Livro;
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
    public TextField Txt_Copias;
    @FXML
    public DatePicker DP_Data;
    @FXML
    public DatePicker DP_DataPrevistaEntrega;
    @FXML
    public DatePicker DP_DataEntrega;
    @FXML
    public ComboBox<Livro> ComboBox_Livros;
    @FXML
    public ComboBox<Leitor> ComoboBox_Leitores;
    @FXML
    public ListView<Emprestimo> Lista_Emprestimo;
    
    private Dao<Emprestimo> emprestimoDao = new Dao<>(Emprestimo.class);
    private Dao<Leitor> leitorDao = new Dao<>(Leitor.class);
    private Dao<Livro> livroDao = new Dao<>(Livro.class);
    private CopiaDao copiaDao = new CopiaDao(Copia.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherListaEmprestimos();
        preencherComoboBox_Leitores();
        preencherComboBox_Livros();
        limparCampos();
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

    private void preencherComoboBox_Leitores() {
        try {
            ObservableList<Leitor> list = FXCollections.observableArrayList(
                    leitorDao.findAll()
            );
            ComoboBox_Leitores.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void limparCampos() {
        Txt_Id.setText("");
        atualizaTxt_Copias(ComboBox_Livros.getSelectionModel().getSelectedItem());
        DP_Data.setValue(LocalDate.now());
        DP_DataPrevistaEntrega.setValue(LocalDate.now().plusDays(5L));
        DP_DataEntrega.setValue(null);
        Lista_Emprestimo
.getSelectionModel().clearSelection();
    }

    @FXML
    public void Lista_Emprestimos_keypressd(KeyEvent keyEvent) {
        exibeDadosEmprestimoSelecionado();
    }

    @FXML
    public void Lista_Emprestimos_mouseclicked(MouseEvent mouseEvent) {
        exibeDadosEmprestimoSelecionado();
    }

    private Livro buscaLivroEmprestimo(Emprestimo emprestimo){
        for(Livro livro : ComboBox_Livros.getItems()){
            if(livro.getId() == emprestimo.getCopia().getLivro().getId()) return livro;
        }
        return null;
    }

    private void exibeDadosEmprestimoSelecionado() {
        Emprestimo emprestimo = Lista_Emprestimo
.getSelectionModel().getSelectedItem();
        if (emprestimo == null) return;

        Txt_Id.setText(emprestimo.getId().toString());
        atualizaTxt_Copias(emprestimo.getCopia().getLivro());
        DP_Data.setValue(emprestimo.getData());
        DP_DataPrevistaEntrega.setValue(emprestimo.getDataPrevistaEntrega());
        DP_DataEntrega.setValue(emprestimo.getDataEntrega());
        ComboBox_Livros.getSelectionModel().select(buscaLivroEmprestimo(emprestimo));
        ComoboBox_Leitores.getSelectionModel().select(emprestimo.getLeitor());
    }

    @FXML
    public void ExcluirButton(ActionEvent actionEvent) {
    }

    @FXML
    public void handleLimparButtonAction(ActionEvent actionEvent) {
        limparCampos();
    }

    private boolean validaCampos(boolean ehAtualizacao){
        boolean datasPreenchidas = 
        DP_Data.getValue() != null && 
        DP_DataPrevistaEntrega.getValue()!= null;
        if(ehAtualizacao) datasPreenchidas = datasPreenchidas &&  DP_DataEntrega.getValue()!=null;
        boolean leitorELivroSelecionados = ComoboBox_Leitores.getSelectionModel().getSelectedItem()!=null && ComboBox_Livros.getSelectionModel().getSelectedItem()!=null;
        boolean haCopiaParaEmprestimo = Integer.parseInt(Txt_Copias.getText()) > 1 || ehAtualizacao;
        return datasPreenchidas && leitorELivroSelecionados && haCopiaParaEmprestimo;
    }

    private void criaEmprestimo(){
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setData(LocalDate.now());
        emprestimo.setDataPrevistaEntrega(DP_DataPrevistaEntrega.getValue());
        emprestimo.setLeitor(ComoboBox_Leitores.getSelectionModel().getSelectedItem());
        List<Copia> copiasDisponiveis = copiaDao.buscaCopiasDisponiveisPorLivro(ComboBox_Livros.getSelectionModel().getSelectedItem());
        Copia copiaSelecionada = copiasDisponiveis.get(0);
        copiaSelecionada.setDisponivel(false);
        copiaDao.update(copiaSelecionada);
        emprestimo.setCopia(copiaSelecionada);
        
        emprestimoDao.create(emprestimo);
    }

    private void atualizaEmprestimo(){
        Emprestimo emprestimo = Lista_Emprestimo
.getSelectionModel().getSelectedItem();
        Copia copiaEmprestada = emprestimo.getCopia();
        copiaEmprestada.setDisponivel(true);
        emprestimo.setCopia(copiaEmprestada);
        emprestimo.setDataEntrega(DP_DataEntrega.getValue());
        emprestimoDao.update(emprestimo);
    }

    @FXML
    public void handleGravarButtonAction(ActionEvent actionEvent) {
        try {
            if(validaCampos(Txt_Id.getText().length() > 0)){
                if(Txt_Id.getText().length() == 0){
                    criaEmprestimo();
                }else{
                    atualizaEmprestimo();
                }
                limparCampos();
                preencherListaEmprestimos();
            }else{
                DiversosJavaFX.exibirMensagem("Verifique se os campos foram preenchidos corretamente");
            }
        } catch (Exception e) {
            e.printStackTrace();
            DiversosJavaFX.exibirMensagem(e.getMessage());
        }
    }

    private void atualizaTxt_Copias(Livro livro) {
        Txt_Copias.setText(String.valueOf(copiaDao.buscaCopiasDisponiveisPorLivro(livro).size()));
    }

    @FXML
    public void handleComboBox_LivrosAction(ActionEvent actionEvent) {
        try {
            atualizaTxt_Copias(ComboBox_Livros.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleComoboBox_LeitoresAction(ActionEvent actionEvent) {
        try {
            DP_DataPrevistaEntrega.setValue(LocalDate.now().plusDays(ComoboBox_Leitores.getSelectionModel().getSelectedItem().getDiasDeEmprestimo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}