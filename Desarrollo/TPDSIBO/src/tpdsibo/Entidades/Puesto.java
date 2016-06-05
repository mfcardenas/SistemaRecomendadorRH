/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpdsibo.Entidades;

import org.openrdf.model.IRI;
import org.openrdf.model.URI;

/**
 *
 * @author Usuario
 */
public class Puesto {
    private String nombre;
    private IRI iriPuesto;
    private IRI postulante; //Este campo se usa para los items del combo en PantallaRecomendar

    
    public Puesto(){}
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the uriPuesto
     */
    public IRI getIriPuesto() {
        return iriPuesto;
    }

    /**
     * @param uriPuesto the uriPuesto to set
     */
    public void setIriPuesto(IRI uriPuesto) {
        this.iriPuesto = uriPuesto;
    }

    /**
     * @return the postulante
     */
    public IRI getPostulante() {
        return postulante;
    }

    /**
     * @param postulante the postulante to set
     */
    public void setPostulante(IRI postulante) {
        this.postulante = postulante;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
    
}
