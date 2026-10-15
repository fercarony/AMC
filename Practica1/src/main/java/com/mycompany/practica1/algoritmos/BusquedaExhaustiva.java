package com.mycompany.practica1.algoritmos;

import com.mycompany.practica1.modelo.Punto;
import com.mycompany.practica1.modelo.Solucion;

/**
 * ============================================================================
 * ESTRATEGIA 1: Búsqueda Exhaustiva (Fuerza Bruta)
 * ============================================================================
 * 
 * ¿Cómo funciona?
 * 1. Compara todos los pares posibles de puntos en el conjunto mediante dos bucles anidados.
 * 2. Mantiene una variable con la distancia mínima encontrada hasta el momento.
 * 3. Si encuentra un par con una distancia menor, actualiza la distancia mínima y los puntos.
 * 
 * Métricas:
 * - distanciasCalculadas: Incrementa en 1 cada vez que se calcula la distancia euclídea entre 2 puntos.
 * - tiempoMs: Mide el tiempo total en milisegundos mediante System.nanoTime().
 * 
 * Complejidad:
 * - O(n^2) en el caso mejor, caso medio y peor caso.
 * - Número exacto de distancias calculadas: n * (n - 1) / 2.
 */
public class BusquedaExhaustiva {

    /**
     * Ejecuta el algoritmo de búsqueda exhaustiva sobre el array de puntos.
     * 
     * @param points Array con los puntos a analizar.
     * @return Objeto Solucion con el par más cercano, la distancia mínima,
     *         el número de distancias calculadas y el tiempo en milisegundos.
     */
    public static Solucion ejecutar(Punto[] points) {
        if (points == null || points.length < 2) {
            return new Solucion(null, null, Double.POSITIVE_INFINITY, 0, 0.0, "Exhaustivo");
        }

        int n = points.length;
        double minDist = Double.POSITIVE_INFINITY;
        Punto bestP1 = null;
        Punto bestP2 = null;
        long distanciasCalculadas = 0;

        // Iniciamos el cronómetro de alta precisión
        long tiempoInicio = System.nanoTime();

        // Doble bucle para comparar todos los pares posibles (i, j) con i < j
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                
                // Calculamos la distancia euclídea y sumamos 1 a las métricas
                double dist = points[i].distancia(points[j]);
                distanciasCalculadas++;

                // Si encontramos un par más cercano, lo guardamos
                if (dist < minDist) {
                    minDist = dist;
                    bestP1 = points[i];
                    bestP2 = points[j];
                }
            }
        }

        // Calculamos el tiempo total transcurrido en milisegundos
        double tiempoMs = (System.nanoTime() - tiempoInicio) / 1_000_000.0;

        return new Solucion(bestP1, bestP2, minDist, distanciasCalculadas, tiempoMs, "Exhaustivo");
    }
}
