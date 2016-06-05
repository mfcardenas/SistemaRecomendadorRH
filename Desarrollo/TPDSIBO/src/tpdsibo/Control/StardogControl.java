/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpdsibo.Control;

import com.complexible.stardog.StardogException;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.UpdateQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;
import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.api.Getter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openrdf.model.IRI;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import tpdsibo.Entidades.Persona;
import tpdsibo.Entidades.Puesto;


/**

 */
public class StardogControl {

    private static StardogControl instancia = null;

    // Variable de conexion a la base de datos
    private static ReasoningConnection conexionStardog;

    protected StardogControl(){}

    public static StardogControl getInstancia() throws StardogException {
        if(instancia == null){
            instancia = new StardogControl();
            instancia.conectarServidor("DSIBO2", "admin", "admin", "snarl://localhost:5820");
        }

        return instancia;
    }


    public List<Persona> procesarResultadosPosiblesPostulantes(TupleQueryResult resultado) throws QueryEvaluationException{
            //Este metodo parsea los resultados de un TupleQueryResult a una lista de personas

            List<Persona> listaResultado = new ArrayList<Persona>(); //Lista final de resultados
            Persona personaActual = null;

            while (resultado.hasNext()) {
                // Obtiene la siguiente fila
                BindingSet filaActual = resultado.next();

               //Una persona puede haber ocupado varios puestos, es decir, varias filas pueden
               //corresponder a la misma persona, variando unicamente en los puestos ocupados
               //de manera que se debe verificar esto a la hora de construir los resultados

               //Si se trata de la misma persona, simplemente se agrega el nuevo puesto ocupado
                if((personaActual != null) && (personaActual.getIriPersona().equals((IRI)filaActual.getValue("persona")))){
                    if(filaActual.getValue("antecedentes") != null){
                        Puesto p = new Puesto();
                        p.setIriPuesto((IRI)filaActual.getValue("antecedentes"));
                        p.setNombre(filaActual.getValue("nombrePuesto").stringValue());
                        personaActual.addAntecedente(p);
                    }
                }
                else{ //De lo contrario, al ser una nueva persona, se agrega la persona anterior
                      //(ya esta completa), y se construye el nuevo objeto persona

                    if(personaActual != null) //Si habia una persona actual creada, se agrega
                        listaResultado.add(personaActual);

                    personaActual = new Persona();
                    personaActual.setNombre(filaActual.getValue("nombrePersona").stringValue());
                    personaActual.setUriPersona((IRI)filaActual.getValue("persona"));
                    if(filaActual.getValue("desemp") != null){
                        personaActual.setDesempeño(filaActual.getValue("desemp").stringValue());
                    }
                    personaActual.setEdad(Integer.parseInt(filaActual.getValue("edad").stringValue()));
                    personaActual.setSexo(filaActual.getValue("sexo").stringValue());
                    personaActual.setHabilitado(Boolean.valueOf(filaActual.getValue("habilitado").stringValue()));
                    if(filaActual.getValue("liderazgo") != null){
                        personaActual.setLiderazgo(Boolean.valueOf(filaActual.getValue("liderazgo").stringValue()));
                    }

                    if(filaActual.getValue("antecedentes") != null){
                        Puesto p = new Puesto();
                        p.setIriPuesto((IRI)filaActual.getValue("antecedentes"));
                        p.setNombre(filaActual.getValue("nombrePuesto").stringValue());
                        personaActual.addAntecedente(p);
                    }
                }


            }

                    if(personaActual != null) //Si corresponde, se agrega la última persona, cuando se terminan los resultados de la consulta
                        listaResultado.add(personaActual);



            return listaResultado;
    }

    public static void conectarServidor(String nombreDB, String usuario, String contrasena, String servidor) throws StardogException{
        conexionStardog = ConnectionConfiguration.to(nombreDB).credentials(usuario, contrasena).server(servidor).reasoning(true).connect().as(ReasoningConnection.class);
    }

    public static void desconectarServidor() throws StardogException{
        conexionStardog.close();
    }

   public List<Persona> consultarPosiblesPostulantes(IRI puesto) throws StardogException, QueryEvaluationException{
       TupleQueryResult r = consultarPostulantes(puesto);

       List<Persona> resultado = procesarResultadosPosiblesPostulantes(r);

       return resultado;

   }

    public  TupleQueryResult consultarPostulantes(IRI posiblePuesto) throws StardogException {
        //String para la query base, se debe desagregar el OPTIONAL en distintas clausulas
        //ya que si falla alguno puede hacer que fallen todos por alguna razon (?)

        String queryGenerica = "SELECT ?persona ?nombrePersona ?edad ?sexo ?habilitado ?liderazgo ?desemp ?antecedentes ?nombrePuesto "
        + "WHERE { "
        + "?persona a ?posiblePuesto. "
        + "OPTIONAL { " //Elementos que si o si deberia tener cualquier persona
                + "?persona :tieneEdad ?edad. "
                + "?persona :tieneSexo ?sexo. "
                + "?persona :estaHabilitado ?habilitado. "
                + "?persona :tieneNombre ?nombrePersona. } "
        + "OPTIONAL { " //Elementos opcionales
                + "?persona :tieneLiderazgo ?liderazgo. } "
        + "OPTIONAL { "
                + "?persona :tieneDesempeño ?desemp. } "
        + "OPTIONAL { "
                + "?persona :ocupoPuesto ?antecedentes. "
                + "?antecedentes a ?puesto. "
                + "?puesto :nombre ?nombrePuesto. } "
                + " } ";


        SelectQuery q = conexionStardog.select(queryGenerica); //Se crea la query en funcion de la cadena


        q.parameter("posiblePuesto",posiblePuesto); //Se setea el parametro "puesto" de la consulta anterior
                                            //al puesto que se desea consultar


        TupleQueryResult resultado = q.execute(); //Se ejecuta la consulta y se devuelven los resultados

        return resultado;


    }


    public static TupleQueryResult consultarSinReasoning(String consultaSPARQLString) throws StardogException {

        // Genera una consulta a la base de datos stardog
        SelectQuery consultaStardog = conexionStardog.select(consultaSPARQLString);
        consultaStardog.reasoning(false);


        // Setea el límite de tripletas de la consulta
        consultaStardog.limit(SelectQuery.NO_LIMIT);

        // Ejecuta la consulta y genera un resultado iterable
        TupleQueryResult resultado = consultaStardog.execute();

        return resultado;

    }

    public static void actualizarBD(String consultaSPARQLString) throws StardogException {
        // Genera una actualizacion a la base de datos stardog
        UpdateQuery consultaStardog = conexionStardog.update(consultaSPARQLString);

        // Ejecuta la consulta
        consultaStardog.execute();

    }

    private static IRI encontrarIndividuoPorPuesto(IRI iriPuesto) {
        //Busca el individuo asociado a un Puesto dado
        IRI iriResultado = null;
        String queryString = "SELECT ?p "
                + "WHERE { ?p a <" + iriPuesto.stringValue() + "> .}";

        SelectQuery q = conexionStardog.select(queryString);

        TupleQueryResult resultado = q.execute();

        //Parsea resultados
        while (resultado.hasNext()){
            BindingSet filaActual = resultado.next();
            iriResultado = (IRI)filaActual.getValue("p");


        }

        return iriResultado;
    }

    public List<Puesto> obtenerPuestosParaCombo() throws QueryEvaluationException, StardogException {
       String queryString =  "SELECT ?puesto ?postulante ?nombrePuesto " +
        "WHERE { " +
        "  ?postulante a owl:Class. " +
        "  ?puesto a owl:Class. " +
        "  ?puesto :nombre ?nombrePuesto. " +
        "  ?postulante :asociadoAPuesto ?puesto. }";
       TupleQueryResult resultadoConsulta = null;

       resultadoConsulta = consultarSinReasoning(queryString);
       List<Puesto> puestos = new ArrayList<Puesto>();

       Puesto puestoInicial = new Puesto(); //Representa "todos" los puestos
      puestoInicial.setPostulante(Values.iri("http://www.frsf.utn.edu.ar/DSIBO-2015/Sistema-Recomendador-Recursos-Humanos#Postulante"));
      puestoInicial.setNombre("Todos");
      puestoInicial.setIriPuesto(Values.iri("http://www.frsf.utn.edu.ar/DSIBO-2015/Sistema-Recomendador-Recursos-Humanos#Puesto"));

      puestos.add(puestoInicial);



        while (resultadoConsulta.hasNext()) {
            // Obtiene la siguiente fila
            BindingSet filaActual = resultadoConsulta.next();

            Puesto p = new Puesto();

            p.setIriPuesto((IRI)filaActual.getValue("puesto"));



            if(filaActual.getValue("postulante").stringValue().contains("PrioridadBaja"))
                p.setNombre(filaActual.getValue("nombrePuesto").stringValue() + " (Prioridad Baja)");

            else if(filaActual.getValue("postulante").stringValue().contains("PrioridadAlta"))
                p.setNombre(filaActual.getValue("nombrePuesto").stringValue() + " (Prioridad Alta)");
            else
                p.setNombre(filaActual.getValue("nombrePuesto").stringValue());

            p.setPostulante((IRI)filaActual.getValue("postulante"));

            puestos.add(p);

        }

        return puestos;
    }

    public static void guardar(Persona p) throws StardogException{

        String queryInsert = "INSERT DATA { <http://www.frsf.utn.edu.ar/DSIBO-2015/Sistema-Recomendador-Recursos-Humanos#"
                + p.getNombre().replace(" ", "_") +"> :tieneSexo \""
                + p.getSexo() + "\"; :tieneDesempeño \""
                + p.getDesempeño() + "\"; :estaHabilitado "
                + p.isHabilitado() + "; :tieneLiderazgo "
                + p.isLiderazgo() + "; :tieneEdad "
                + p.getEdad()+ "; :tieneNombre "
                + "\"" + p.getNombre() + "\"";

        for(Puesto puesto : p.getAntecedentes()){
            IRI iriPuesto = encontrarIndividuoPorPuesto(puesto.getIriPuesto());
            queryInsert = queryInsert + "; :ocupoPuesto " + "<" + iriPuesto.stringValue() + ">";

        }
        queryInsert = queryInsert + "; a :Persona. }";

        System.out.print(queryInsert);


        actualizarBD(queryInsert);

    }

}
