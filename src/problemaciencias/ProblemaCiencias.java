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
    static int[] profundidades = {1,1};
    static int chars;
    static int maxDepth;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        chars=profundidades.length;
        agregarProfundidad(profundidades);
        crearArbol();
        //System.out.println(nodos.size());
        maxDepth=maxValue();
        rellenar(nodos.get(0));
        System.out.println("La palabra mas corta tiene "+nodos.get(0).weigth+" caracteres");
        /*for(int i=0;i<nodos.size();i++){
            System.out.println(i+" - "+nodos.get(i).weigth);
        }*/
    }
    
    public static int maxValue(){
        int aux=0;
        for(int i =0;i<profundidades.length;i++){
            if(profundidades[i]>aux){
                aux=profundidades[i];
            }
        }
        return aux+1;
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
    
    public static void rellenar(Nodo nod){
        if(nod.weigth==0){
            if(nod.leaf){
               // System.out.println(nod.depth);
                nod.weigth=maxDepth-nod.depth;
            }else{
                if(nod.nodos.get(0).weigth==0){
                    rellenar(nod.nodos.get(0));
                }
                if(nod.nodos.get(1).weigth==0){
                    rellenar(nod.nodos.get(1));
                }
                nod.weigth=nod.nodos.get(0).weigth+nod.nodos.get(1).weigth;
            }
        }
    }
    
}
