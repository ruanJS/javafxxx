package com.example.data;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Nome;
import com.example.Treino;

public class TreinoDao {

    static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
    static final String USER = "pf1389";
    static final String PASS = "fiap23";

    public static void inserir(Treino Treino) throws SQLException {
        var conexao = DriverManager.getConnection(URL, USER, PASS);

        var sql = "INSERT INTO Treinos (nome, duração, grupoMuscular, frequencia, Treino_id) VALUES (?, ?, ?, ?, ?) ";
        var comando = conexao.prepareStatement(sql);
        comando.setString(1,Treino.getNome());
        comando.setString(2, Treino.getDuraçã0());
        comando.setInt(3, Treino.getGrupoMuscular());
        comando.setBigDecimal(4, Treino.getFrequencia());
        comando.setString(5, (String) Treino.getTreino());
        comando.executeUpdate();

        conexao.close();

    }

    public static ArrayList<Treino> buscarTodos() throws SQLException{
        var lista = new ArrayList<Treino>();

        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("SELECT Treinos.*, Treinos.nome, Treinos.duração, Treinos.grupomuscular, Treinos.frequencia, Treino.periodo FROM Treinos INNER JOIN Treinos ON Treinos.Treino_id=Treinos.id");
        var resultado = comando.executeQuery();

        while(resultado.next()){
            lista.add (new Treino(
                resultado.getLong("id"), 
                resultado.getString("nome"), 
                resultado.getString("duração"), 
                resultado.getInt("grupomuscular"), 
                resultado.getBigDecimal("frequencia"),
                new Treino(
                    resultado.getLong("Treino_id"),
                    resultado.getString("nome"),
                    resultado.getString("tptreino"),
                    resultado.getString("repetição")
                )
            ));
        }

        conexao.close();
        return lista;
    }

    public static void apagar(Long id) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("DELETE FROM Treinos WHERE id=?");
        comando.setLong(1, id);
        comando.executeUpdate();
        conexao.close();
    }

    public void atualizar(Nome nome) throws SQLException{
        var conexao = DriverManager.getConnection(URL, USER, PASS);
        var comando = conexao.prepareStatement("UPDATE Treinos SET marca=?, modelo=?, ano=?, valor=? WHERE id=?");
        comando.setString(1, nome.getNome());
        comando.setString(2, nome.getDuração());
        comando.setInt(3, nome.getGrupoMuscular());
        comando.setBigDecimal(4, nome.getFrequencia());
        comando.setLong(5, nome.getPeriodo());
        comando.executeUpdate();
        conexao.close();
    }

    public void atualizar(Nome nome) {
    }
}
