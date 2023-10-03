/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade2.backend.infra.error;

import javax.swing.JOptionPane;

/**
 *
 * @author ma_fe
 */
public class ErrorHandling {
    
    /**
     * Verify if any of the param fields are empty or blank.
     * @param fields The fields that need verification.
     * @return True if any field are empty or blank, otherwise returns False.
     */
    public static boolean FormHasEmptyFields(String... fields){
        for (String field: fields){
            if (field.isBlank() || field.isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos para prosseguir.");
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verify if any of the param fields are empty or blank.
     * @param msg The message that will be displayed.
     * @param fields The fields that need verification.
     * @return True if any field are empty or blank, otherwise returns False.
     */
    public static boolean FormHasEmptyFields(String msg, String... fields){
        for (String field: fields){
            if (field.isBlank() || field.isEmpty()){
                JOptionPane.showMessageDialog(null, msg);
                return true;
            }
        }
        return false;
    }
}
