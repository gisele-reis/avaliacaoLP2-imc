/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Aluno;
/**
 *
 * @author Gisele
 */
public class AlunoDAO {
    private Connection connection;
    
    public AlunoDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adicionarAluno(Aluno aluno) throws SQLException{
        String sql = "INSERT INTO aluno_academia(nome, cpf, dataNascimento, peso, altura, imc) VALUES (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setDate(3, (Date) aluno.getDataNascimento());
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.setDouble(6, aluno.getImc());
            stmt.execute();
            stmt.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao adicionar aluno: "+ e.getMessage());
        }
    }
    
    public ArrayList<Aluno> listarAluno(){
        String sql = "SELECT * FROM academia.aluno_academia";
        ArrayList<Aluno> lista = new ArrayList<Aluno>();
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id_aluno");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Date dataNascimento = rs.getDate("dataNascimento");
                Double peso = rs.getDouble("peso");
                Double altura = rs.getDouble("altura");
                Double imc = rs.getDouble("imc");
                
                Aluno aluno = new Aluno(id, nome, cpf, dataNascimento, peso, altura, imc);
                lista.add(aluno);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao listar alunos: " + e.getMessage());
        }
        return lista;
    }
    
    public void excluirAluno(String id){
        String sql = "DELETE FROM academia.aluno_academia WHERE cpf = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.execute();
            stmt.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + e.getMessage());
        }
    }
    
    public void atualizarAluno(Aluno aluno) throws SQLException{
        String sql = "UPDATE aluno_academia SET nome = ?, cpf = ?, dataNascimento = ?, peso = ?, altura = ?, imc = ? WHERE id_aluno = ?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getCpf());
            stmt.setDate(3, (Date) aluno.getDataNascimento());
            stmt.setDouble(4, aluno.getPeso());
            stmt.setDouble(5, aluno.getAltura());
            stmt.setDouble(6, aluno.getImc());
            stmt.setInt(7, aluno.getId());
            stmt.execute();
            stmt.close();
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar aluno" + e.getMessage());
        }
    }
    
}
