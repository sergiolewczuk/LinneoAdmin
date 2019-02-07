package Utilidades;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Jason
 */
public class Class_makeobj {
    
    static public Object main (final String item) {
            return new Object() { public String toString() { return item; }};
        }
    
}
