/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Gisele
 */
public class ConnectionFactory {
    public Connection getConnection(){
        String url = "jdbc:mysql://localhost/academia";
        try{
            return DriverManager.getConnection(url, "root","");
        } catch(SQLException excecao){
            JOptionPane.showMessageDialog(null, "Erro de conexao: " + excecao.getMessage());
        }
        return null;
    }
}
