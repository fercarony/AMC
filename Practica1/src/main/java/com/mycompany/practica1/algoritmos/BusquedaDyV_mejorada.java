package com.mycompany.practica1.algoritmos;

import com.mycompany.practica1.modelo.ParPuntos;
import com.mycompany.practica1.modelo.Punto;
import com.mycompany.practica1.modelo.Solucion;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * ESTRATEGIA 4: Divide y Vencerás Mejorado (Óptimo con Poda en Y)
 * ============================================================================
 * 
 * ¿Cómo funciona?
 * 1. Ordena inicialmente los puntos por la coordenada X con QuickSort.
 * 2. Aplica la misma recursión de Divide y Vencerás (dividir por el corte medio en X).
 * 3. En el paso de combinación (la franja central "strip"):
 *    - Filtra los puntos a distancia horizontal < dMin del corte.
 *    - Ordena los puntos de la franja según su coordenada Y utilizando QuickSort.
 *    - Propiedad de los 11 vecinos: Para cada punto pi de la franja, sólo se comprueban
 *      como máximo los 11 puntos siguientes pj en la lista ordenada por Y.
 *    - Además, si la diferencia vertical (pj.y - pi.y) >= dMin, se aborta la búsqueda (break)
 *      porque ningún punto posterior tendrá una Y menor.
 * 
 * Ventaja fundamental:
 * - Garantiza una complejidad estrictamente O(n * log n) en TODOS los casos,
 *   incluso en el peor caso donde todos los puntos tienen la misma coordenada X (misma vertical).
 */
public class BusquedaDyV_mejorada {

    // Contador de distancias euclídeas computadas
    private long distanciasCalculadas;

    /**
     * Método público estático que ejecuta la versión mejorada sobre el array de puntos.
     */
    public static Solucion ejecutar(Punto[] points) {
        if (points == null || points.length < 2) {
            return new Solucion(null, null, Double.POSITIVE_INFINITY, 0, 0.0, "DyV Mejorado");
        }

        BusquedaDyV_mejorada dyvMej = new BusquedaDyV_mejorada();
        dyvMej.distanciasCalculadas = 0;

        long tiempoInicio = System.nanoTime();

        // 1. Clonar y ordenar por X usando QuickSort
        Punto[] sortedX = points.clone();
        QuickSort.ordenarPorX(sortedX);

        // 2. Llamada recursiva principal
        ParPuntos mejorPar = dyvMej.resolverRec(sortedX, 0, sortedX.length - 1);

        double tiempoMs = (System.nanoTime() - tiempoInicio) / 1_000_000.0;

        return new Solucion(mejorPar, dyvMej.distanciasCalculadas, tiempoMs, "DyV Mejorado");
    }

    /**
     * Método recursivo óptimo con ordenación de la franja en Y y poda de 11 vecinos.
     */
    private ParPuntos resolverRec(Punto[] sortedX, int left, int right) {
        int count = right - left + 1;

        // Caso base: 3 puntos o menos
        if (count <= 3) {
            return casoBaseExhaustivo(sortedX, left, right);
        }

        int mid = left + (right - left) / 2;
        Punto midPunto = sortedX[mid];

        // Llamadas recursivas
        ParPuntos leftRes = resolverRec(sortedX, left, mid);
        ParPuntos rightRes = resolverRec(sortedX, mid + 1, right);

        // Mejor par entre ambas mitades sin recalcular distancias
        ParPuntos minRes = (leftRes.getDistancia() < rightRes.getDistancia()) ? leftRes : rightRes;
        double dMin = minRes.getDistancia();

        // Construir la franja central
        List<Punto> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(sortedX[i].getX() - midPunto.getX()) < dMin) {
                strip.add(sortedX[i]);
            }
        }

        // Ordenamos la franja por la coordenada Y usando nuestro QuickSort
        QuickSort.ordenarPorY(strip);

        // Poda de los 11 vecinos: sólo comparamos cada punto con un máximo de 11 puntos siguientes
        for (int i = 0; i < strip.size(); i++) {
            Punto p1 = strip.get(i);
            
            for (int j = i + 1; j < strip.size() && (j - i) <= 11; j++) {
                Punto p2 = strip.get(j);

                // Si la diferencia vertical ya supera o iguala dMin, no hace falta seguir mirando
                if ((p2.getY() - p1.getY()) >= dMin) {
                    break;
                }

                // Calculamos la distancia euclídea y sumamos al contador
                double dist = p1.distancia(p2);
                distanciasCalculadas++;

                if (dist < minRes.getDistancia()) {
                    minRes = new ParPuntos(p1, p2, dist);
                    dMin = dist;
                }
            }
        }

        return minRes;
    }

    /**
     * Resuelve el caso base (3 puntos o menos) por comparación exhaustiva.
     */
    private ParPuntos casoBaseExhaustivo(Punto[] pts, int left, int right) {
        double minDist = Double.POSITIVE_INFINITY;
        Punto bestP1 = null;
        Punto bestP2 = null;

        for (int i = left; i < right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double d = pts[i].distancia(pts[j]);
                distanciasCalculadas++;

                if (d < minDist) {
                    minDist = d;
                    bestP1 = pts[i];
                    bestP2 = pts[j];
                }
            }
        }
        return new ParPuntos(bestP1, bestP2, minDist);
    }
}
