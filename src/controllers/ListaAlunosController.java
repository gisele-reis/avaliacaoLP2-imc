package controllers;

import dao.AlunoDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Aluno;

public class ListaAlunosController implements Initializable{
    
    @FXML
    private TableView<Aluno> tabelaAlunos;
    
    @FXML
    private TableColumn<Aluno, String> cpfCol;

    @FXML
    private TableColumn<Aluno, Date> dataNascimentoCol;

    @FXML
    private TableColumn<Aluno, Double> imcCol;

    @FXML
    private TableColumn<Aluno, String> nomeCol;

    @FXML
    private TableColumn<Aluno, Double> pesoCol;
    
    @FXML
    private TableColumn<Aluno, Double> alturaCol;
    
    @FXML
    private TableColumn<Aluno, Integer> idCol;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnPesquisar;
    
    @FXML
    private Button btnExcluir;
    
    @FXML
    private Button btnRelatorio;
    
    @FXML
    private TextField txtBusca;
    
    private Parent root;
    private Stage stage;
    private Scene scene;
    
    private Aluno selecionado;
    
    ObservableList<Aluno> listaAlunos;
    
    public void listarTabela(){
        AlunoDAO dao = new AlunoDAO();
        this.listaAlunos = FXCollections.observableArrayList(dao.listarAluno());
        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        nomeCol.setCellValueFactory(new PropertyValueFactory("nome"));
        cpfCol.setCellValueFactory(new PropertyValueFactory("cpf"));
        dataNascimentoCol.setCellValueFactory(new PropertyValueFactory("dataNascimento"));
        pesoCol.setCellValueFactory(new PropertyValueFactory("peso"));
        alturaCol.setCellValueFactory(new PropertyValueFactory("altura"));
        imcCol.setCellValueFactory(new PropertyValueFactory("imc"));
        
        tabelaAlunos.setItems(listaAlunos);
    }
    
    public ObservableList<Aluno> atualizarTabela(){
        AlunoDAO dao = new AlunoDAO();
        listaAlunos = FXCollections.observableArrayList(dao.listarAluno());
        return listaAlunos;
    }

    @FXML
    void atualizarAluno(ActionEvent event) throws IOException {
            if(selecionado != null){
                AtualizarAluno alt = new AtualizarAluno(selecionado);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AtualizarAluno.fxml"));
                root = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else{
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setHeaderText("Selecione um Aluno");
                a.show();
            }
    }

    @FXML
    void cadastrarAluno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/TelaCadastro.fxml"));
        root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void excluirAluno(ActionEvent event) throws IOException {
        if(selecionado != null){
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Deseja excluir o aluno " + selecionado.getNome() + "?");
            a.setTitle("Deletar aluno?");
            Optional<ButtonType> result = a.showAndWait();
            if(result.get() == ButtonType.OK ){
                AlunoDAO dao = new AlunoDAO();
                dao.excluirAluno(selecionado.getCpf());
                Alert confirmacao = new Alert(Alert.AlertType.INFORMATION);
                confirmacao.setHeaderText("Aluno deletado com sucesso!");
                confirmacao.show();
                tabelaAlunos.setItems(atualizarTabela());
            }
            
        } else{
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Selecione um Aluno");
            a.show();
        }
    }
    
    public ObservableList<Aluno> pesquisa(){
        ObservableList<Aluno> alunoPesquisa = FXCollections.observableArrayList();
        for(int i = 0; i < listaAlunos.size(); i++){
            if(listaAlunos.get(i).getNome().toLowerCase().contains(txtBusca.getText().toLowerCase())){
                alunoPesquisa.add(listaAlunos.get(i));
            } else if(listaAlunos.get(i).getCpf().contains(txtBusca.getText())){
                alunoPesquisa.add(listaAlunos.get(i));
            }
        }
        return alunoPesquisa;
    }

    @FXML
    void pesquisarAluno(ActionEvent event) {
        tabelaAlunos.setItems(pesquisa());   
    }
    
    @FXML
    void buscar(ActionEvent event) {
        tabelaAlunos.setItems(pesquisa());
    }

    @FXML
    void trocarParaTelaInicial(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    void gerarRelatorio(ActionEvent event){
        if(selecionado != null){       
            gerarRelatorioAluno(selecionado);
        } else{
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Selecione um aluno");
            a.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listarTabela();
        
        tabelaAlunos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue){
                selecionado = (Aluno) newValue;
            }
        });
    }

    
    
    private static class AtualizarAluno {

        public AtualizarAluno(Aluno a1uno){
            AtualizarAlunoController.setAluno2(a1uno);
        }
    }
    
    private void gerarRelatorioAluno(Aluno aluno){
        try{
            File arq = new File("src\\relatorio\\relatorio.txt");
            FileWriter fw = new FileWriter(arq, true);
            BufferedWriter relatorio = new BufferedWriter(fw);
            
            LocalDate dataAtual = LocalDate.now();
            DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String status = "";

            relatorio.write("Relatorio do(a) Aluno(a) " + aluno.getNome());
            relatorio.newLine();
            relatorio.write("-----------------------------");
            relatorio.newLine();
            relatorio.write("Data do Relatório: " + dataAtual.format(formatar));
            relatorio.newLine();
            relatorio.write("Nome: " + aluno.getNome());
            relatorio.newLine();
            relatorio.write("CPF: " + aluno.getCpf());
            relatorio.newLine();
            relatorio.write("IMC: " + aluno.getImc());
            relatorio.newLine();
            
            if(aluno.getImc() < 18.5){
                status = "Abaixo do peso";
            } else if(aluno.getImc() < 25){
                status = "Peso Adequado";
            } else if(aluno.getImc() < 30){
                status = "Acima do peso";
            } else if(aluno.getImc() < 35){
                status = "Obesidade Grau I";
            } else if(aluno.getImc() < 40){
                status = "Obesidade Grau II";
            } else if(aluno.getImc() > 40){
                status = "Obesidade Grau III";
            }
            
            relatorio.write("Status: " + status);
            relatorio.newLine();
            relatorio.write("=============================");
            relatorio.newLine();
            relatorio.newLine();
            
            relatorio.close();
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Relatório gerado com sucesso!");
            a.show();
            
                
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Problema ao abrir o arquivo " + ex.getMessage());
        }   
    }

}