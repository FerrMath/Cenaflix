/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade2.backend.classes;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author ma_fe
 */
public class Movie {

    private int id;
    private String name;
    private LocalDate releaseDate;
    private String category;

    public Movie() {

    }

    /**
     *
     * @param id The id of the Movie.
     * @param name The name/title of the Movie.
     * @param releaseDate The release date of the Movie.
     * @param category The category of the movie.
     */
    public Movie(int id, String name, LocalDate releaseDate, String category) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.category = category;
    }

    public Movie(String name, LocalDate releaseDate, String category) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.category = category;
    }

    
    public Movie(String name, String releaseDate, String category) {
        this.name = name;
        setReleaseDate(releaseDate);
        this.category = category;
    }
    
    /**
     * Makes an array using the movie atributes.
     * 
     * @return String[id, name, date(dd/MM/yyyy), category]
     */
    public String[] toArray(){
        
        return new String[]{
            Integer.toString(this.id),
            this.name,
            getFormatedDate(),
            this.category};
    }
    
    /**
     *
     * @return The Movie id.
     */
    public int getId() {
        return id;
    }

    /**
     * The id setter
     * 
     * @param id new int Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return The Movie title/name
     */
    public String getName() {
        return name;
    }

    /**
     * The name/title setter
     * 
     * @param name new string name/title
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The Movie release date
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    /**
     *
     * @return the Movies current release date as a string with format("dd/MM/yyyy")
     */
    public String getFormatedDate(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return format.format(releaseDate);
    }

    /**
     * The release date setter
     * 
     * @param releaseDate
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    
    /**
     * Sets the releaseDate with a new LocalDate based of a formated date String
     * 
     * @param releaseDate The string formated date (dd/MM/yyyy).
     */
    public void setReleaseDate(String releaseDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(releaseDate, formatter);
            this.releaseDate = localDate;

        } catch (DateTimeParseException e) {
            System.out.println("A string não está em um formato válido de data.");
        }
    }

    /**
     *
     * @return The movie category
     */
    public String getCategory() {
        return category;
    }

    /**
     * The category setter
     * 
     * @param category new category string.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
