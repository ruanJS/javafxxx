package com.example;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.data.Treino;
import com.example.data.TreinoDao;
import com.example.model.Cliente;
import com.example.data.Exercicios;
import com.example.data.ExerciciosDao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PrimaryController implements Initializable {

    private static final com.example.Nome Nome = null;
    private static final Cliente Exercicios = null;
    private static final com.example.data.TreinoDao NomeDao = null;
    @FXML TextField txtNome;
    @FXML TextField txtDuração;
    @FXML TextField txtGrupoMuscular;
    @FXML TextField txtFrequencia;

    @FXML TableView<Nome> tabelaNome;

    @FXML TableColumn<Nome, String> colNome;
    @FXML TableColumn<Nome, String> colDuração;
    @FXML TableColumn<Nome, Integer> colGrupoMuscular;
    @FXML TableColumn<Nome, BigDecimal> colValor;
    @FXML TableColumn<Nome, Exercicios> colExercicios;

    @FXML TextField txtNome;
    @FXML TextField txtTpTreino;
    @FXML TextField txtRepetição;

    @FXML TableView<Exercicios> tabelaExercicios;

    @FXML TableColumn<Exercicios, String> colNome;
    @FXML TableColumn<Exercicios, String> colTpTreino;
    @FXML TableColumn<Exercicios, String> colRepetição;

    @FXML ComboBox<Exercicios> cbExercicios;

    ExerciciosDao ExerciciosDao;
    ExerciciosDao TreinoDao;
    private TableView<com.example.Nome> tabelaExercicios;

    public void adicionarNome(){
        var Treino = new Nome(
            null, 
            txtNome.getText(), 
            txtDuração.getText(), 
            Integer.valueOf( txtGrupoMuscular.getText() ), 
            new BigDecimal( txtFrequencia.getText() ),
            cbExercicios.getSelectionModel().getSelectedItem()
        );

        try{
            ExerciciosDao.inserir(ExerciciosDao);
            tabelaNome.getItems().add(Nome);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void carregarNomes(){
        tabelaNome.getItems().clear();
        try {
            var Nomes = TreinoDao.buscarTodos();
            Iterable<Throwable> treino;
            treino.forEach(Treino -> tabelaNome.getItems().add(treino));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarTreino(){
        var Treino = new Treino(
            null, 
            txtNome.getText(), 
            txtTpTreino.getText(), 
            txtRepetição.getText()
        );

        try{
            ExerciciosDao.inserir(Exercicios);
            tabelaExercicios.getItems().add(Exercicios);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void carregarExercicioss(){
        tabelaExercicios.getItems().clear();
        try {
            var Exercicioss = ExerciciosDao.buscarTodos();
            Exercicioss.forEach(Exercicios -> tabelaExercicios.getItems().add(Exercicios));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void apagarNome(){
        var Nome = tabelaNome.getSelectionModel().getSelectedItem();
        if (Nome == null) {
            mostrarMensagem("Erro", "Você deve selecionar um Exercicio/Treino para apagar");
            return;
        }
        try {
            NomeDao.apagar(Nome.getId());
            tabelaNome.getItems().remove(Nome);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro o apagar");
        }
    }

    public void atualizarNome(Nome Nome){
        try {
            NomeDao.atualizar(Nome);
        } catch (SQLException e) {
            mostrarMensagem("Erro", "Erro ao atualizar dados");
            e.printStackTrace();
        }
    }

   private void mostrarMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }

 @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        colNome.setCellFactory(TextFieldTableCell.forTableColumn());
        colNome.setOnEditCommit(e -> atualizarNome(e.getRowValue().Nome(e.getNewValue())));
        
        colDuração.setCellValueFactory(new PropertyValueFactory<>("duração"));
        colDuração.setCellFactory(TextFieldTableCell.forTableColumn());
        colDuração.setOnEditCommit(e -> atualizarNome(e.getRowValue().duração(e.getNewValue())));
        
        colGrupoMuscular.setCellValueFactory(new PropertyValueFactory<>("grupoMuscular"));
        colGrupoMuscular.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colGrupoMuscular.setOnEditCommit(e -> atualizarNome(e.getRowValue().grupoMuscular(e.getNewValue())));
        
        colFrequencia.setCellValueFactory(new PropertyValueFactory<>("frequencia"));
        colFrequencia.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        colFrequencia.setOnEditCommit(e -> atualizarNome(e.getRowValue().valor(e.getNewValue())));

        colExercicios.setCellValueFactory(new PropertyValueFactory<>("Exercicios"));

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        
        try {
            ExerciciosDao = new ExerciciosDao();
            NomeDao = new NomeDao();
            cbExercicios.getItems().addAll(ExerciciosDao.buscarTodos());
        } catch (SQLException e1) {
            mostrarMensagem("Erro", "Erro ao buscar Exercicioss");
            e1.printStackTrace();
        }

        carregarNomes();
        carregarExercicioss();

        tabelaNome.setEditable(true);
        tabelaExercicios.setEditable(true);
    }

   
}
