package com.mycompany.practica1.algoritmos;

import com.mycompany.practica1.modelo.Punto;
import com.mycompany.practica1.modelo.Solucion;

/**
 * ============================================================================
 * ESTRATEGIA 2: Búsqueda Exhaustiva con Poda
 * ============================================================================
 * 
 * ¿Cómo funciona?
 * 1. Clona el array de puntos original para no alterar los datos de entrada.
 * 2. Ordena los puntos según su coordenada X usando nuestro algoritmo QuickSort.
 * 3. Para cada punto i, compara con los puntos posteriores j (j > i).
 * 4. PODA: Dado que los puntos están ordenados en el eje X, si la distancia horizontal
 *    (puntos[j].x - puntos[i].x) ya es mayor o igual que la distancia mínima actual (minDist),
 *    es imposible que ningún punto posterior (k > j) esté más cerca. Por tanto, se aborta
 *    el bucle interno con 'break'.
 * 
 * Aspecto clave para la evaluación:
 * - La condición de poda comprueba una resta de coordenadas X:
 *      (puntos[j].getX() - puntos[i].getX()) >= minDist
 *   Esta resta NO es una distancia euclídea, por lo que NO se suma al contador de distancias.
 * - Solo sumamos al contador cuando calculamos la distancia euclídea real con p1.distancia(p2).
 */
public class BusquedaPoda {

    /**
     * Ejecuta el algoritmo de búsqueda con poda sobre el array de puntos.
     * 
     * @param points Array original de puntos.
     * @return Objeto Solucion con los puntos encontrados y las métricas.
     */
    public static Solucion ejecutar(Punto[] points) {
        if (points == null || points.length < 2) {
            return new Solucion(null, null, Double.POSITIVE_INFINITY, 0, 0.0, "Exhaustivo con Poda");
        }

        int n = points.length;
        double minDist = Double.POSITIVE_INFINITY;
        Punto bestP1 = null;
        Punto bestP2 = null;
        long distanciasCalculadas = 0;

        // Iniciamos el cronómetro (incluye el tiempo de ordenación)
        long tiempoInicio = System.nanoTime();

        // 1. Clonamos y ordenamos por coordenada X mediante nuestro QuickSort
        Punto[] sortedX = points.clone();
        QuickSort.ordenarPorX(sortedX);

        // 2. Búsqueda con poda
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                
                // PODA: Si la diferencia en X ya supera o iguala la distancia mínima,
                // ningún punto siguiente en X podrá mejorarla -> rompemos el bucle
                if ((sortedX[j].getX() - sortedX[i].getX()) >= minDist) {
                    break;
                }

                // Si no se poda, calculamos la distancia euclídea y sumamos 1 a la métrica
                double dist = sortedX[i].distancia(sortedX[j]);
                distanciasCalculadas++;

                if (dist < minDist) {
                    minDist = dist;
                    bestP1 = sortedX[i];
                    bestP2 = sortedX[j];
                }
            }
        }

        double tiempoMs = (System.nanoTime() - tiempoInicio) / 1_000_000.0;

        return new Solucion(bestP1, bestP2, minDist, distanciasCalculadas, tiempoMs, "Exhaustivo con Poda");
    }
}
