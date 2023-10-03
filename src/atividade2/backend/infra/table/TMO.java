/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade2.backend.infra.table;

import atividade2.backend.classes.Movie;
import atividade2.backend.infra.db.DAO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Table Manegement object.
 * Will handle the updates for the table of the MainView
 *
 * @author ma_fe
 */
public class TMO {

    private final DAO DB;
    private final JTable TABLE;
    
    public TMO(JTable table, DAO d) {
        this.DB = d;
        this.TABLE = table;
    }
    
    /**
     * Calls update to display the filtered movies on the table
     * 
     * @param kw The keyword used to filter the results
     */
    public void updateFilteredList(String kw) {        
        Movie[] movies = this.DB.getFilteredMovieArray(kw);
        update(movies);
    }
    
    /**
     * Calls update to display all the movies available on the table
     */
    public void updateFullList() {
        Movie[] movies = this.DB.getCompleteMovieArray();
        update(movies);
    }
    
    /**
     * Display the the received array of movies on the table
     * 
     * @param movies The array of movies to be displayed
     */
    private void update(Movie[] movies) {
        String[] data;
        DefaultTableModel model = (DefaultTableModel) TABLE.getModel();
        model.setRowCount(0);
        for (Movie m : movies) {
            data = m.toArray();
            model.addRow(data);
        }
        TABLE.setModel(model);
    }
}
