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
 * Table Manegement object
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
    
    public void updateFilteredList(String kw) {        
        Movie[] movies = this.DB.getFilteredMovieArray(kw);
        update(movies);
    }
    
    public void updateFullList() {
        Movie[] movies = this.DB.getComleteMovieArray();
        update(movies);
    }
    
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
