/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemaciencias;

import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class Nodo {
    int depth;
    int weigth;
    boolean leaf;
    int father;
    ArrayList<Nodo> nodos;
    
    Nodo(int d, boolean l){
        this.nodos = new ArrayList<>();
        depth=d;
        leaf=l;
    }
}
