package controllers;

import dao.AlunoDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Aluno;

public class TelaCadastroController {

    @FXML
    private Button btnAlunos;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnSalvar;
    
    @FXML
    private TextField txtAltura;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPeso;
    
    @FXML
    private DatePicker dpDataNascimento;
    
    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    void cadastrar(ActionEvent event) throws SQLException {
        
        if(txtNome.getText().isEmpty() || txtCPF.getText().isEmpty() || txtPeso.getText().isEmpty() || txtAltura.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Por favor, preencha todos os campos.");
            a.show();
        } else{
            String nome = txtNome.getText();
            String cpf = txtCPF.getText();
            Date dataNascimento = java.sql.Date.valueOf(dpDataNascimento.getValue());
            Double peso = Double.valueOf(txtPeso.getText());
            Double altura = Double.valueOf(txtAltura.getText());
            Double imc = calcularIMC(peso, altura);
            
            Aluno aluno = new Aluno(nome, cpf, dataNascimento, peso, altura, imc);
            AlunoDAO dao = new AlunoDAO();
            dao.adicionarAluno(aluno);
            JOptionPane.showMessageDialog(null, "Aluno adicionado com sucesso!");
        }
        
        txtNome.setText("");
        txtCPF.setText("");
        dpDataNascimento.setValue(null);
        txtPeso.setText("");
        txtAltura.setText("");
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        txtNome.setText("");
        txtCPF.setText("");
        dpDataNascimento.setValue(null);
        txtPeso.setText("");
        txtAltura.setText("");
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
        root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void trocarParaListaDeAlunos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListaAlunos.fxml"));
        root = loader.load();
        ListaAlunosController controller = loader.getController();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void trocarParaTelaInicial(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    public Double calcularIMC(Double peso, Double altura){
        Double imc = peso / (altura * altura);
        return imc;
    }

}