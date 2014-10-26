/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.excepciones;

/**
 * Clase de excepción en caso de existir un error de autenticació
 */
public class AutenticacionException extends Exception
{

    // -----------------------------------------------
    // Constructor
    // -----------------------------------------------

    /**
     * Constructor de la clase.
     * @param mensaje Mensaje de la excepción
     */
    public AutenticacionException( String mensaje ){
        super( mensaje );
    }
}

