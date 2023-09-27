package com.example.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Treino;
import com.example.model.Cliente;

public class ExerciciosDao {

    private Connection conexao;

    public ExerciciosDao() throws SQLException {
        conexao = ConnectionFactory.getConnection();
    }

    public void inserir(Cliente cliente) throws SQLException {
        var sql = "INSERT INTO Exercicioss (nome, TpExercicios, Repetição) VALUES ( ?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1, cliente.getNome());
        comando.setString(2, cliente.getTpExercicios());
        comando.setString(3, cliente.getRepetição());
        comando.executeUpdate();

    }

    public List<Exercicios> buscarTodos() throws SQLException{
        var lista = new ArrayList<Exercicios>();

        var comando = conexao.prepareStatement("SELECT * FROM Exercicioss");
        var resultado = comando.executeQuery();

        while(resultado.next()){
            lista.add (new Treino(
                resultado.getLong("id"), 
                resultado.getString("nome"), 
                resultado.getString("email"), 
                resultado.getString("telefone")
            ));
        }

        return lista;
    }

    public void inserir(ExerciciosDao exerciciosDao) {
    }
}
