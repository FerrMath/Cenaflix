/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade2.backend.infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import atividade2.backend.classes.Movie;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author ma_fe
 */
public class DAO {

    private Connection conn = null;
    private PreparedStatement pstt;
    private Statement stt;

    public DAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cenaflix", "", ""); // ALTERAR LOGIN E SENHA
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao BD");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver não encontrado");
        }
    }

    public boolean addMovie(Movie m) {
        try {
            pstt = conn.prepareStatement("INSERT INTO filmes (nome, datalancamento, categoria) VALUES (?, ?, ?)");
            pstt.setString(1, m.getName());
            pstt.setString(2, m.getReleaseDate().toString());
            pstt.setString(3, m.getCategory());
            pstt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar o filme");
        }
        return false;
    }

    public Movie getMovie(int movieId) {
        Movie m =  null;
        
        try {
            pstt = conn.prepareStatement("SELECT id, nome, datalancamento, categoria FROM filmes WHERE id = ?");
            pstt.setInt(1, movieId);
            ResultSet rslt = pstt.executeQuery();
            
            if(rslt.next()){
                int id = rslt.getInt("id");
                String name = rslt.getString("nome");
                LocalDate release = rslt.getDate("datalancamento").toLocalDate();
                String category = rslt.getString("categoria");
                m = new Movie(id, name, release, category);
                return m;
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro ao pegar filme em bd " + ex.getMessage());
        }
        return m;
    }
    
    public boolean editMovie(Movie m) {
        try {
            pstt = conn.prepareStatement(
                    "UPDATE filmes "+
                    "SET nome = ?, datalancamento = ?, categoria = ?" +
                    "WHERE id = ?");
            pstt.setString(1, m.getName());
            pstt.setDate(2, Date.valueOf(m.getReleaseDate()));
            pstt.setString(3, m.getCategory());
            pstt.setInt(4, m.getId());
            if (pstt.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            //Faço algo pro erro
        }
        return false;
    }

    public boolean delMovie(int id) {
        try {
            pstt = conn.prepareStatement("DELETE FROM filmes WHERE ID = (?)");
            pstt.setInt(1, id);
            if (pstt.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            //Faço algo pro erro
        }
        return false;
    }

    public Movie[] getComleteMovieArray() {

        ArrayList<Movie> al = new ArrayList<>();
        Movie m;

        int id;
        String name;
        LocalDate release;
        String category;

        try {
            stt = conn.createStatement();
            ResultSet moviesResult = this.stt.executeQuery("SELECT id, nome, datalancamento, categoria FROM filmes ORDER BY id ASC");
            while (moviesResult.next()) {
                id = moviesResult.getInt("id");
                name = moviesResult.getString("nome");
                release = moviesResult.getDate("datalancamento").toLocalDate();
                category = moviesResult.getString("categoria");
                m = new Movie(id, name, release, category);
                al.add(m);
            }
        } catch (SQLException e) {
        }
        return al.toArray(Movie[]::new);
    }

    public Movie[] getFilteredMovieArray(String kw) {

        ArrayList<Movie> al = new ArrayList<>();
        Movie m;
        kw = "%" + kw + "%";
        int id;
        String name;
        LocalDate release;
        String category;

        try {
            pstt = conn.prepareStatement("SELECT id, nome, datalancamento, categoria FROM filmes WHERE nome LIKE (?)");
            pstt.setString(1, kw);
            ResultSet moviesResult = this.pstt.executeQuery();
            while (moviesResult.next()) {
                id = moviesResult.getInt("id");
                name = moviesResult.getString("nome");
                release = moviesResult.getDate("datalancamento").toLocalDate();
                category = moviesResult.getString("categoria");
                m = new Movie(id, name, release, category);
                al.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao filtrar");
        }
        return al.toArray(Movie[]::new);
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexão ao BD");
        }
    }
}
