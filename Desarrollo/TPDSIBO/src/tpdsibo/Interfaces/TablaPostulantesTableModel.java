/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpdsibo.Interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import tpdsibo.Entidades.Persona;

/**
 *
 * Esta clase se crea para poder mantener una referencia del objeto que provee los valores para cada fila
 * así cuando el usuario seleccione una fila, se pueda mostrar sus antecedentes en el recuadro del costado
 * (Falta agregar toda esa funcionalidad)
 */
public class TablaPostulantesTableModel extends AbstractTableModel{
    
            private String[] nombresColumnas = null;
            private List<Persona> filas = null;
            
            public TablaPostulantesTableModel(List<Persona> postulantes, String[] columnas){
                nombresColumnas = columnas;
                
                this.
                filas = new ArrayList<Persona>(postulantes);
            }
            
 
            
            @Override //Las celdas no se pueden editar
            public boolean isCellEditable(int row, int column){
                return false;
            }
            
            @Override
            public String getColumnName(int index){
                return nombresColumnas[index];
            }
            
            @Override
            public int getRowCount(){
                if (filas == null)
                    return 0;
                else
                    return filas.size();
            }
            
            @Override
            public int getColumnCount(){
                if (nombresColumnas == null)
                    return 0;
                else return nombresColumnas.length;           
            }
            
            @Override
            public Object getValueAt(int row, int column){
                Object valor = null;
                Persona p = filas.get(row);
                
                switch(column){
                    case 0:
                        valor = p.getNombre();
                        break;
                    case 1:
                        valor = p.getEdad();
                        break;
                    case 2:
                        valor = p.getSexo();
                        break;
                    case 3:
                        valor = p.getDesempeño();
                        break;
                    case 4:
                        valor = p.isLiderazgo();
                        break;
                    case 5:
                        valor = p.isHabilitado();
                        break;
                }
                
                return valor;
            }
            
            public Persona getPostulantePorFila(int fila){
                if(fila < 0)
                    return null;
                else
                    return filas.get(fila);
                

            }
    
}
