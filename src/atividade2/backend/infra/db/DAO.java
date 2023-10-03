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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Class that manages the connection to the database and performs operations
 * related to movies. This class includes methods to add, retrieve, update, and
 * delete movies in the database. It also provides functionalities to retrieve
 * filtered lists of movies.
 *
 * @author ma_fe
 */
public class DAO {

    private Connection conn = null;
    private PreparedStatement pstt;
    private Statement stt;

    /**
     * Starts the database connection.
     */
    public DAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cenaflix", "root", ""); // ALTERAR LOGIN E SENHA
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao BD");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver não encontrado");
        }
    }

    /**
     * Add new movie entry to database.
     *
     * @param m The movie object to be added.
     * @return true if movie is sucessfully added to database.
     */
    public boolean addMovie(Movie m) {
        try {
            // Create and run the SQL query
            pstt = conn.prepareStatement("INSERT INTO filmes (nome, datalancamento, categoria) VALUES (?, ?, ?)");
            pstt.setString(1, m.getName());
            pstt.setString(2, m.getReleaseDate().toString());
            pstt.setString(3, m.getCategory());
            pstt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o filme");
        } finally {
            try {
                pstt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Get the movie data from the database with the informed Id.
     *
     * @param movieId The Id of the movie to select from the database.
     * @return Movie object from the database or Null if no movie is found.
     */
    public Movie getMovie(int movieId) {
        Movie m = null;

        try {
            // Starts the SQL query.
            pstt = conn.prepareStatement("SELECT id, nome, datalancamento, categoria FROM filmes WHERE id = ?");
            pstt.setInt(1, movieId);
            ResultSet rslt = pstt.executeQuery();

            // Returns the movie with the retrieved data.
            if (rslt.next()) {
                int id = rslt.getInt("id");
                String name = rslt.getString("nome");
                LocalDate release = rslt.getDate("datalancamento").toLocalDate();
                String category = rslt.getString("categoria");
                m = new Movie(id, name, release, category);
                return m;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pegar filme em bd ");
        } finally {
            try {
                pstt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return m;
    }

    /**
     * Updates the data of selected movie on the database.
     *
     * @param m The Movie object with the updated data to be added.
     * @return True if the data is successfuly edited on the database, otherwise
     * it returns False.
     */
    public boolean editMovie(Movie m) {
        try {
            pstt = conn.prepareStatement(
                    "UPDATE filmes "
                    + "SET nome = ?, datalancamento = ?, categoria = ?"
                    + "WHERE id = ?");
            pstt.setString(1, m.getName());
            pstt.setDate(2, Date.valueOf(m.getReleaseDate()));
            pstt.setString(3, m.getCategory());
            pstt.setInt(4, m.getId());
            return pstt.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar o filme.\nVerifique os dados e tente novamente.");
        } finally {
            try {
                pstt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Removes the movie entry from database with the selected Id.
     *
     * @param id The selected movie Id.
     * @return True if the movie data is successfuly deletes, otherwise it
     * returns False.
     */
    public boolean delMovie(int id) {
        try {
            pstt = conn.prepareStatement("DELETE FROM filmes WHERE ID = (?)");
            pstt.setInt(1, id);
            return pstt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o filme");
        } finally {
            try {
                pstt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    /**
     * Get the complete movie list from the database.
     *
     * @return Array of all the movies from the database.
     */
    public Movie[] getCompleteMovieArray() {

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
            JOptionPane.showMessageDialog(null, "Erro ao localizar os dados dos filmes.");
        } finally {
            try {
                stt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return al.toArray(Movie[]::new);
    }

    /**
     * Get the array of movies where the keyword is present on their titles.
     *
     * @param kw The key word used to filter the movie results.
     * @return Filtered Array of Movies.
     */
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
            JOptionPane.showMessageDialog(null, "Erro ao filtrar os filmes.\nVerifique o termo de pesquisa e tente novamente");
        } finally {
            try {
                pstt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return al.toArray(Movie[]::new);
    }

    /**
     * Ends the database connection
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar conexão ao BD");
        }
    }
}
