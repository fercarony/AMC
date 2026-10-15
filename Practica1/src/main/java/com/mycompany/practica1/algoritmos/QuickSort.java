package com.mycompany.practica1.algoritmos;

import com.mycompany.practica1.modelo.Punto;
import java.util.List;

/**
 * Implementación propia del algoritmo de ordenación QuickSort para objetos Punto.
 * 
 * ¿Por qué esta clase?
 * El enunciado de la práctica exige explícitamente implementar un algoritmo propio 
 * de ordenación (QuickSort o HeapSort) en lugar de utilizar los métodos estándar de Java (Arrays.sort).
 * 
 * Funcionamiento:
 * 1. Divide y vencerás: Selecciona un elemento pivote (en nuestro caso, el elemento central).
 * 2. Partición: Reorganiza los elementos de forma que los menores que el pivote queden a su izquierda
 *    y los mayores a su derecha.
 * 3. Recursión: Aplica el mismo procedimiento recursivamente a los subconjuntos izquierdo y derecho.
 * 
 * Complejidad:
 * - Caso mejor y caso medio: O(n * log n)
 * - Peor caso: O(n^2) (mitigado al elegir el elemento central como pivote).
 */
public class QuickSort {

    // =========================================================================
    // 1. ORDENACIÓN POR COORDENADA X (Usada por Poda y Divide y Vencerás)
    // =========================================================================

    /**
     * Ordena un array completo de puntos ascendentemente según su coordenada X.
     * Si dos puntos tienen la misma X, se desempata por su coordenada Y.
     * 
     * @param puntos Array de puntos a ordenar.
     */
    public static void ordenarPorX(Punto[] puntos) {
        if (puntos == null || puntos.length <= 1) {
            return;
        }
        quickSortX(puntos, 0, puntos.length - 1);
    }

    /**
     * Método recursivo auxiliar para ordenar por X en el intervalo [izq, der].
     */
    private static void quickSortX(Punto[] puntos, int izq, int der) {
        int i = izq;
        int j = der;
        
        // Elegimos el pivote como el elemento central del segmento
        Punto pivote = puntos[izq + (der - izq) / 2];

        // Fase de partición
        while (i <= j) {
            // Avanzar el puntero izquierdo mientras los puntos sean menores que el pivote
            while (compararX(puntos[i], pivote) < 0) {
                i++;
            }
            // Retroceder el puntero derecho mientras los puntos sean mayores que el pivote
            while (compararX(puntos[j], pivote) > 0) {
                j--;
            }
            // Si los punteros no se han cruzado, intercambiamos y avanzamos ambos
            if (i <= j) {
                intercambiar(puntos, i, j);
                i++;
                j--;
            }
        }

        // Llamadas recursivas para los sub-arrays izquierdo y derecho
        if (izq < j) {
            quickSortX(puntos, izq, j);
        }
        if (i < der) {
            quickSortX(puntos, i, der);
        }
    }

    /**
     * Compara dos puntos por X, desempatando por Y.
     * @return Valor negativo si p1 < p2, positivo si p1 > p2, o 0 si son idénticos.
     */
    private static int compararX(Punto p1, Punto p2) {
        if (Double.compare(p1.getX(), p2.getX()) != 0) {
            return Double.compare(p1.getX(), p2.getX());
        }
        return Double.compare(p1.getY(), p2.getY());
    }

    // =========================================================================
    // 2. ORDENACIÓN POR COORDENADA Y (Usada por Divide y Vencerás Mejorado)
    // =========================================================================

    /**
     * Ordena un array completo de puntos ascendentemente según su coordenada Y.
     * Si dos puntos tienen la misma Y, se desempata por su coordenada X.
     * 
     * @param puntos Array de puntos a ordenar.
     */
    public static void ordenarPorY(Punto[] puntos) {
        if (puntos == null || puntos.length <= 1) {
            return;
        }
        quickSortY(puntos, 0, puntos.length - 1);
    }

    /**
     * Método recursivo auxiliar para ordenar por Y en el intervalo [izq, der].
     */
    private static void quickSortY(Punto[] puntos, int izq, int der) {
        int i = izq;
        int j = der;
        Punto pivote = puntos[izq + (der - izq) / 2];

        while (i <= j) {
            while (compararY(puntos[i], pivote) < 0) {
                i++;
            }
            while (compararY(puntos[j], pivote) > 0) {
                j--;
            }
            if (i <= j) {
                intercambiar(puntos, i, j);
                i++;
                j--;
            }
        }

        if (izq < j) {
            quickSortY(puntos, izq, j);
        }
        if (i < der) {
            quickSortY(puntos, i, der);
        }
    }

    /**
     * Sobrecarga de conveniencia para ordenar una lista (List<Punto>) por coordenada Y.
     * Especialmente útil para ordenar la franja central ("strip") en DyV Mejorado.
     * 
     * @param puntos Lista de puntos a ordenar in-place.
     */
    public static void ordenarPorY(List<Punto> puntos) {
        if (puntos == null || puntos.size() <= 1) {
            return;
        }
        Punto[] array = puntos.toArray(new Punto[0]);
        ordenarPorY(array);
        puntos.clear();
        for (Punto p : array) {
            puntos.add(p);
        }
    }

    /**
     * Compara dos puntos por Y, desempatando por X.
     * @return Valor negativo si p1 < p2, positivo si p1 > p2, o 0 si son idénticos.
     */
    private static int compararY(Punto p1, Punto p2) {
        if (Double.compare(p1.getY(), p2.getY()) != 0) {
            return Double.compare(p1.getY(), p2.getY());
        }
        return Double.compare(p1.getX(), p2.getX());
    }

    // =========================================================================
    // 3. MÉTODOS AUXILIARES
    // =========================================================================

    /**
     * Intercambia las posiciones de dos elementos dentro del array.
     */
    private static void intercambiar(Punto[] puntos, int i, int j) {
        Punto temp = puntos[i];
        puntos[i] = puntos[j];
        puntos[j] = temp;
    }
}
