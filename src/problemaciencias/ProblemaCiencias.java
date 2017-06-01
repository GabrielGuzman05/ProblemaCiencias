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

    static HashMap<Integer, Integer> depths = new HashMap();
    static ArrayList<Nodo> nodos = new ArrayList<>();
    static HashMap<Integer, Integer> maxvalueinminordepth = new HashMap();
    static int[] profundidades = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9};
    static int chars;
    static int maxDepth;

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        chars = profundidades.length;
        agregarProfundidad(profundidades);
        crearArbol();
        //System.out.println(nodos.size());
        
        maxDepth = maxValue();
        rellenarZeros();
        
        //System.out.println(nodos.get(0).weigth);
        
        rellenar();
        System.out.println("La palabra mas corta tiene " + nodos.get(0).weigth + " caracteres");
        
        /*for(int i=0;i<nodos.size();i++){
            System.out.println(i+" - "+nodos.get(i).weigth);
        }*/
        
        /*
        for (Nodo n : nodos) {
            if (n.leaf) {
                System.out.println(n.weigth + "-hoja");
            }
            if (!n.leaf) {
                System.out.println(n.weigth + "-no hoja");
            }
        }
        */
        
        //System.out.println(maxvalueinminordepth.toString());
    }

    public static int maxValue() {
        int aux = 0;
        for (int i = 0; i < profundidades.length; i++) {
            if (profundidades[i] > aux) {
                aux = profundidades[i];
            }
        }
        return aux;
    }

    public static void rellenarZeros() {
        for (int i = 0; i <= maxDepth; i++) {
            maxvalueinminordepth.put(i, 0);
        }
    }

    public static void agregarProfundidad(int[] hola) {
        for (int i = 0; i < chars; i++) {
            if (depths.containsKey(hola[i])) {
                depths.put(hola[i], depths.get(hola[i]) + 1);
            } else {
                depths.put(hola[i], 1);
            }
        }
        for (int i = 0; i < chars; i++) {
            if (!depths.containsKey(i)) {
                depths.put(i, 0);
            }
        }
    }

    public static void crearArbol() {
        Nodo head = new Nodo(0, false);
        nodos.add(head);
        crearDivision(head, 0);
    }

    public static void crearDivision(Nodo father, int depth) {
        if (depths.get(depth + 1) == 1) {
            Nodo h1 = new Nodo(depth + 1, true);//es una hoja, no puede tener hijos
            Nodo h2 = new Nodo(depth + 1, false);//es un nodo puede tener hijos
            h1.father = nodos.indexOf(father);
            h2.father = nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            nodos.add(h1);
            nodos.add(h2);
            depths.put(depth + 1, depths.get(depth + 1) - 1);
            crearDivision(h2, depth + 1);
        } else if (depths.get(depth + 1) >= 2) {
            Nodo h1 = new Nodo(depth + 1, true);//es hoja
            Nodo h2 = new Nodo(depth + 1, true);//es hoja
            h1.father = nodos.indexOf(father);
            h2.father = nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            depths.put(depth + 1, depths.get(depth + 1) - 2);
            nodos.add(h1);
            nodos.add(h2);
        } else if (depths.get(depth + 1) == 0) {
            Nodo h1 = new Nodo(depth + 1, false);//es un nodo puede tener hijos
            Nodo h2 = new Nodo(depth + 1, false);//es un nodo puede tener hijos
            h1.father = nodos.indexOf(father);
            h2.father = nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            crearDivision(h1, depth + 1);
            crearDivision(h2, depth + 1);
            nodos.add(h1);
            nodos.add(h2);
        }
    }

    public static void rellenar() {
        int currentDepthCompleted = maxDepth;
        for (int i = 0; i < nodos.size(); i++) {
            if (nodos.get(i).leaf) {
                if (nodos.get(i).depth >= maxDepth - 1) {
                    nodos.get(i).weigth = 1;
                    maxvalueinminordepth.put(nodos.get(i).depth, 1);
                }
            }
        }
        for (int i = maxDepth - 1; i >= 0; i--) {
            for (Nodo n : nodos) {
                if (n.leaf) {
                    if (n.depth == i) {
                        n.weigth = maxvalueinminordepth.get(n.depth + 1);
                    }
                } else {
                    n.weigth = n.nodos.get(0).weigth + n.nodos.get(1).weigth;
                }
            }
            for (Nodo n : nodos) {
                if (n.leaf) {
                    if (n.depth == i) {
                        n.weigth = maxvalueinminordepth.get(n.depth + 1);
                    }
                } else {
                    n.weigth = n.nodos.get(0).weigth + n.nodos.get(1).weigth;
                    if (n.weigth > maxvalueinminordepth.get(n.depth)) {
                        maxvalueinminordepth.put(n.depth, n.weigth);
                    }
                }
            }
        }
    }

    /* metodo antigua de rellenar
    public static void rellenar(Nodo nod) {
        if (nod.weigth == 0) {
            if (nod.leaf) {
                if(nod.depth==maxDepth){
                    nod.weigth=1;
                }else if(nod.depth==maxDepth-1){
                    nod.weigth=1;
                }else{nod.weigth = maxDepth - nod.depth;}
            } else {
                if (nod.nodos.get(0).weigth == 0) {
                    rellenar(nod.nodos.get(0));
                }
                if (nod.nodos.get(1).weigth == 0) {
                    rellenar(nod.nodos.get(1));
                }
                nod.weigth = nod.nodos.get(0).weigth + nod.nodos.get(1).weigth;
            }
        }
    }
     */
}
