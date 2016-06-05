/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpdsibo;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.StardogException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.URI;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import tpdsibo.Control.StardogControl;
import tpdsibo.Entidades.Persona;
import tpdsibo.Entidades.Puesto;
import tpdsibo.Interfaces.PantallaAltaPostulantes;
import tpdsibo.Interfaces.PantallaRecomendar;

/**
 *
 * @author Usuario
 */
public class TPDSIBO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws QueryEvaluationException, StardogException {
        
        /*Persona p = new Persona();
        List<Puesto> antecedentes = new ArrayList<Puesto>();
        
        p.setNombre("Pepe");
        p.setSexo("Masculino");
        p.setEdad(32);
        p.setDesempeño("Muy Bueno");
        p.setLiderazgo(true);
        p.setHabilitado(true);
        p.setAntecedentes(antecedentes);
       
        
        StardogControl.getInstancia().guardar(p); // Habia que inicializar! getInstancia inicializa la conexion, sin conexion no se puede guardar

      /* PantallaAltaPostulantes alt = new PantallaAltaPostulantes();
       PantallaRecomendar rcm = new PantallaRecomendar();
       
       alt.setVisible(true);
       rcm.setVisible(true);*/
    }
    
}
