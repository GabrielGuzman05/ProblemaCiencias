/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemaciencias;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Gabriel
 */
public class ProblemaCiencias {
    
    static HashMap<Integer,Integer> depths = new HashMap();
    static ArrayList<Nodo> nodos = new ArrayList<>();
    static int[] profundidades = {2,2,2,2};
    static int chars;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        chars=profundidades.length;
        agregarProfundidad(profundidades);
        crearArbol();
        System.out.println(nodos.size());
    }
    
    public static void maxValue(){
        
    }
    
    public static void agregarProfundidad(int[] hola){
        for(int i = 0;i<chars;i++){
            if(depths.containsKey(hola[i])){
                depths.put(hola[i], depths.get(hola[i])+1);
            }else{
                depths.put(hola[i], 1);
            }
        }
        for(int i = 0;i<chars;i++){
            if(!depths.containsKey(i)){
                depths.put(i, 0);
            }
        }
    }
    
    public static void crearArbol(){
        Nodo head = new Nodo(0, false);
        nodos.add(head);
        crearDivision(head,0);
    }
    
    public static void crearDivision(Nodo father, int depth){
        if(depths.get(depth+1)==1){
            Nodo h1 = new Nodo(depth+1, true);//es una hoja, no puede tener hijos
            Nodo h2 = new Nodo(depth+1, false);//es un nodo puede tener hijos
            h1.father=nodos.indexOf(father);
            h2.father=nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            nodos.add(h1);
            nodos.add(h2);
            depths.put(depth+1,depths.get(depth+1)-1);
            crearDivision(h2,depth+1);
        }else if(depths.get(depth+1)>=2){
            Nodo h1 = new Nodo(depth+1, true);//es hoja
            Nodo h2 = new Nodo(depth+1, true);//es hoja
            h1.father=nodos.indexOf(father);
            h2.father=nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            depths.put(depth+1,depths.get(depth+1)-2);
            nodos.add(h1);
            nodos.add(h2);
        }else if(depths.get(depth+1)==0){
            Nodo h1 = new Nodo(depth+1, false);//es un nodo puede tener hijos
            Nodo h2 = new Nodo(depth+1, false);//es un nodo puede tener hijos
            h1.father=nodos.indexOf(father);
            h2.father=nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            crearDivision(h1,depth+1);
            crearDivision(h2,depth+1);
            nodos.add(h1);
            nodos.add(h2);
        }
    }
    
}
