/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpdsibo.Entidades;

import java.util.ArrayList;
import java.util.List;
import org.openrdf.model.IRI;
import org.openrdf.model.URI;

/**
 *
 * @author Usuario
 */
public class Persona {
    private IRI iriPersona;
    private String nombre;
    private int edad;
    private String sexo;
    private boolean habilitado;
    private boolean liderazgo;
    private String desempeño;
    private List<Puesto> antecedentes;
    
    public Persona(){
        antecedentes = new ArrayList<Puesto>();
    }

    /**
     * @return the uriPersona
     */
    public IRI getIriPersona() {
        return iriPersona;
    }

    /**
     * @param iriPersona the uriPersona to set
     */
    public void setUriPersona(IRI iriPersona) {
        this.iriPersona = iriPersona;
    }

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
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the habilitado
     */
    public boolean isHabilitado() {
        return habilitado;
    }

    /**
     * @param habilitado the habilitado to set
     */
    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    /**
     * @return the liderazgo
     */
    public boolean isLiderazgo() {
        return liderazgo;
    }

    /**
     * @param liderazgo the liderazgo to set
     */
    public void setLiderazgo(boolean liderazgo) {
        this.liderazgo = liderazgo;
    }

    /**
     * @return the desempeño
     */
    public String getDesempeño() {
        return desempeño;
    }

    /**
     * @param desempeño the desempeño to set
     */
    public void setDesempeño(String desempeño) {
        this.desempeño = desempeño;
    }

    /**
     * @return the antecedentes
     */
    public List<Puesto> getAntecedentes() {
        return antecedentes;
    }

    /**
     * @param antecedentes the antecedentes to set
     */
    public void setAntecedentes(List<Puesto> antecedentes) {
        this.antecedentes = antecedentes;
    }
    
    public void addAntecedente(Puesto p){
        antecedentes.add(p);
    }
    
    
    
}
