/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import dao.AlunoDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import modelo.Aluno;

/**
 * FXML Controller class
 *
 * @author Gisele
 */
public class AtualizarAlunoController implements Initializable {

    @FXML
    private Button btnAlunos;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnSalvar;

    @FXML
    private DatePicker dpDataNascimento;

    @FXML
    private Label lbId;

    @FXML
    private TextField txtAltura;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPeso;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    private static Aluno aluno2;
    
    
    public void inicializarAluno(){
        
        lbId.setText(Integer.toString(aluno2.getId()));
        txtNome.setText(aluno2.getNome());
        txtCPF.setText(aluno2.getCpf());
        txtPeso.setText(String.valueOf(aluno2.getPeso()));
        txtAltura.setText(String.valueOf(aluno2.getAltura()));
        
    }

    @FXML
    void atualizarAluno(ActionEvent event) throws SQLException, IOException {
        
        if(txtNome.getText().isEmpty() || txtCPF.getText().isEmpty() || txtPeso.getText().isEmpty() || txtAltura.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Por favor, preencha todos os campos.");
            a.show();
        } else{
            int id = Integer.parseInt(lbId.getText());
            String nome = txtNome.getText();
            String cpf = txtCPF.getText();
            Date dataNascimento = java.sql.Date.valueOf(dpDataNascimento.getValue());
            Double peso = Double.valueOf(txtPeso.getText());
            Double altura = Double.valueOf(txtAltura.getText());
            
            TelaCadastroController calculoImc = new TelaCadastroController();
            Double imc = calculoImc.calcularIMC(peso, altura);

            Aluno aluno = new Aluno(id, nome, cpf, dataNascimento, peso, altura, imc);
            AlunoDAO dao = new AlunoDAO();
            dao.atualizarAluno(aluno);
            JOptionPane.showMessageDialog(null, "Aluno atualizado com sucesso!");
        }
        trocarParaListaDeAlunos(event);
    }

    @FXML
    void cancelar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListaAlunos.fxml"));
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
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void trocarParaTelaInicial(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarAluno();
    }    

    public static Aluno getAluno2() {
        return aluno2;
    }

    public static void setAluno2(Aluno aluno2) {
        AtualizarAlunoController.aluno2 = aluno2;
    }
 
}
