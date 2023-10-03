/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade2.backend.infra.error;

/**
 *
 * @author ma_fe
 */
public class ErrorHandling {
    public static boolean FormHasEmptyFields(String... fields){
        for (String field: fields){
            if (field.isBlank() || field.isEmpty()){
                return true;
            }
        }
        return false;
    }
}
