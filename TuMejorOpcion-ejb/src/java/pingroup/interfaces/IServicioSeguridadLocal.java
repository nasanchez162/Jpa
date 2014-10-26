/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pingroup.interfaces;

import pingroup.excepciones.AutenticacionException;
import pingroup.vos.Usuario;

/**
 *
 * @author estudiante
 */
public interface IServicioSeguridadLocal {
    /**
     * Registra el ingreso de un usuario al sistema.
     * @param token Token del usuario que está registrado
     * @return usuario Retorna el objeto que contiene la información del usuario que ingreso al sistema.
     * @throws pingroup.excepciones.AutenticacionException Lanza excepción en caso de que haya problemas con la autenticacion
     */
    public Usuario ingresar(String token)throws AutenticacionException;
}
