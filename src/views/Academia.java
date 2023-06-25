/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package views;

import controllers.AtualizarAlunoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Aluno;

/**
 *
 * @author Gisele
 */
public class Academia extends Application {
    
    private static Scene main;
    private static Scene telaCadastro;
    private static Scene listaAlunos;
    private static Stage stage;
    
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        
        Parent fxmlMain = FXMLLoader.load(getClass().getResource("Main.fxml"));
        main = new Scene(fxmlMain);
        Parent fxmlTelaCadastro = FXMLLoader.load(getClass().getResource("TelaCadastro.fxml"));
        telaCadastro = new Scene(fxmlTelaCadastro);
        Parent fxmlListaAlunos = FXMLLoader.load(getClass().getResource("ListaAlunos.fxml"));
        listaAlunos = new Scene(fxmlListaAlunos);
        
        primaryStage.setScene(main);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
