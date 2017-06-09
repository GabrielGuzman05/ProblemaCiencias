package problemaciencias;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Arbol {

    //Método de resolucion::::rellenar hojas con mayor no hoja del nivel 
    //de abajo, los nodos en las ultimas dos profundidades (hojas) siempre se 
    //rellenan con unos
    static HashMap<Integer, Integer> depths = new HashMap();
    static ArrayList<Nodo> nodos = new ArrayList<>();
    static HashMap<Integer, Integer> maxvalueinminordepth = new HashMap();
    static int[] profundidades;
    static int chars;
    static int maxDepth;


    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);
        //Se piden los datos de entrada por consola
        System.out.println("Ingrese la cantidad de caracteres diferentes");
        try {
            chars = leer.nextInt();
            profundidades = new int[chars];

            for (int i = 0; i < chars; i++) {
                System.out.println("Caracter " + (i + 1) + "/" + chars);
                profundidades[i] = leer.nextInt();
            }
        } catch (InputMismatchException exc) {
            System.out.println("Error de entrada, ejecute nuevamente");
        } catch (NegativeArraySizeException exc) {
            System.out.println("Error de entrada ingresado un numero negativo");
        }

        boolean errorDeDatos = false;
        try {
            //1.-Cuantos "niveles" tiene el arbol
            agregarProfundidad(profundidades);
            //2.-Se crea el arbol, dentro de este método se encuentra el método
            //"crearDivision()"
            crearArbol();
            
        } catch (NullPointerException exc) {
            System.out.println("Los datos que se entregaron, no forman un set"
                    + " de datos correcto");
            errorDeDatos = true;
        }

        if (!errorDeDatos) {
            //3.-"maxValue()" Retorna la profundidad maxima y se le asigna a la
            //variable estática
            maxDepth = maxValue();

            //4.-Se rellena con ceros el hashmap de profundidades, hasta la 
            //profundidad mas grande, este hashmap es para ver el valor del nodo 
            //en una profundidad menor
            rellenarZeros();

            
            //5.-Se rellena considerando el mayor nodo (no hoja) de la profundidad 
            //anterior nota: las hojas en la mayor profundidad se rellenan con 1's
            rellenar();
            System.out.println("La palabra mas corta tiene " + nodos.get(0).weigth + " caracteres");
        }


    }

    //3.- retorna el valor max del array profundidades
    public static int maxValue() {
        int aux = 0;
        for (int i = 0; i < profundidades.length; i++) {
            if (profundidades[i] > aux) {
                aux = profundidades[i];
            }
        }
        return aux;
    }

    //4.-Se rellena el HashMap con ceros en los respectivos indices
    //hasta llegar a la profundidad maxima, con tal de trabajar con los niveles
    public static void rellenarZeros() {
        for (int i = 0; i <= maxDepth; i++) {
            //"valor maximo en la profundidad menor"
            maxvalueinminordepth.put(i, 0);
        }
    }

    //1.-Tiene como dato de entrada la matriz de las profundidades de los 
    //caracteres.
    public static void agregarProfundidad(int[] caracteres) {

        for (int i = 0; i < chars; i++) {
            //Se encuentra el caracter en la profundidad "i" en el hashmap?
            if (depths.containsKey(caracteres[i])) {
                //Se agrega lo que está contenido en el hashmap en la 
                //profundidad i
                depths.put(caracteres[i], depths.get(caracteres[i]) + 1);
            } else {
                //Sino, se agrega un 1 en la profundidad 1
                depths.put(caracteres[i], 1);
            }
        }
        for (int i = 0; i < chars; i++) {
            if (!depths.containsKey(i)) {
                //Mientras no haya nada en el indice i en el hashmap, se 
                //agrega en dicho indice un 0
                depths.put(i, 0);
            }
        }
    }

    //2.- Se crea el arbol agregando el nodo principal y despues
    //se hace la división
    public static void crearArbol() {
        Nodo head = new Nodo(0, false);
        nodos.add(head);
        crearDivision(head, 0);
    }

    //2.1-Se crea la division (Objeto de clase Nodo:father, profundidad).
    //Se trabaja considerando la profundidad+1, es decir, la profundidad de los 
    //poisbles hijos del nodo father , hay que denotar que por definicion del 
    //programa los valores de las hojas en estas profundidades solo pueden ser 
    //mayores o iguales a cero es que en la siguiente profundidad hay hojas.
    //No hay que preocuparse de que el arbol pase de largo, ya que el hashmap 
    //solo tiene datos hasta la profundidad mas alta que hay
    public static void crearDivision(Nodo father, int depth) {
        //Si es que la cantidad de hojas en la profundidad en la que se trabajará
        //es una sola, se crean dos nodos, y a estos se le asigna el marcador de
        //hoja y nodo según corresponde, luego se le asignan estos nodos al
        //padre, se eliminan 1 de la cantidad de hojas en la profundidad,
        //dejando esa profundidad en 0, finalmente se vuelve a aplicar el algoritmo 
        //para el nodo, que no es hoja
        if (depths.get(depth + 1) == 1) {
            //Se crea un nodo y una hoja
            Nodo h1 = new Nodo(depth + 1, true);//es una hoja, no puede tener hijos
            Nodo h2 = new Nodo(depth + 1, false);//es un nodo puede tener hijos

            //h1.father=posicion en el arraylist
            h1.father = nodos.indexOf(father);
            //h2.father=posicion en el arraylist
            h2.father = nodos.indexOf(father);
            father.nodos.add(h1);
            father.nodos.add(h2);
            nodos.add(h1);
            nodos.add(h2);
            depths.put(depth + 1, depths.get(depth + 1) - 1);
            crearDivision(h2, depth + 1);
            //Es hoja, por lo que no seguirá bajando en esta parte

            //Cuando la cantidad de hojas es mayor o igual a dos, simplemente se 
            //crean dos nodos hojas y se le asignan al padre, se eliminan dos 
            //hojas del contador y no se vuelve a aplicar el algoritmo, ya que 
            //las hojas no pueden tener hijos
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

            //El útlimo caso es cuando no hay hojas en la profundidad en la que se
            //trabajaraá en este caso, como existe el dato, significa que no hay 
            //hojas en este nivel, pero deben haber en niveles posteriores, por 
            //lo tanto se crean dos nodos no hojas, que pueden tener hijos y se 
            //vuelve a aplicar el algoritmo
        } else if (depths.get(depth + 1) == 0) {
            //Se crea un nodo y una hoja
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

    //5.-Luego de armar el arbol, finalmente se rellena
    public static void rellenar() {
        int currentDepthCompleted = maxDepth;

        for (int i = 0; i < nodos.size(); i++) {
            //Pregunta si es hoja
            if (nodos.get(i).leaf) {
                //Si la profundidad es max-1; se le asigna un 1 al nodo, notar
                //que se ya se había definido que las ultimas hojas serán 1
                if (nodos.get(i).depth >= maxDepth - 1) {
                    nodos.get(i).weigth = 1;
                    //Se agrega al hashMap
                    maxvalueinminordepth.put(nodos.get(i).depth, 1);
                }
            }
        }
        //for de atrás hacia adelante con inicio de profundidad máxima-1
        for (int i = maxDepth - 1; i >= 0; i--) {

            for (Nodo n : nodos) {
                //Si es hoja
                if (n.leaf) {
                    if (n.depth == i) {
                        //Se le asigna el valor del hashmap en el indice i+1 al peso
                        n.weigth = maxvalueinminordepth.get(n.depth + 1);
                    }
                    //Sino
                } else {//NODO
                    //Se le asigna al peso la suma de los pesos laterales
                    n.weigth = n.nodos.get(0).weigth + n.nodos.get(1).weigth;
                }
            }

            for (Nodo n : nodos) {
                //Si es hoja .
                if (n.leaf) {
                    if (n.depth == i) {
                        //Se le asigna el valor del hashmap en el indice i+1 al peso
                        n.weigth = maxvalueinminordepth.get(n.depth + 1);
                    }
                    //Sino
                } else {//HOJA
                    //Se le asigna al peso la suma de los pesos laterales
                    n.weigth = n.nodos.get(0).weigth + n.nodos.get(1).weigth;
                    if (n.weigth > maxvalueinminordepth.get(n.depth)) {
                        //Se agrega al hashMap
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

