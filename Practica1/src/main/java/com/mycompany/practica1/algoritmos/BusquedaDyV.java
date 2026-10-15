package com.mycompany.practica1.algoritmos;

import com.mycompany.practica1.modelo.ParPuntos;
import com.mycompany.practica1.modelo.Punto;
import com.mycompany.practica1.modelo.Solucion;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * ESTRATEGIA 3: Divide y Vencerás Clásico (Básico)
 * ============================================================================
 * 
 * ¿Cómo funciona?
 * 1. Ordena los puntos por la coordenada X con QuickSort.
 * 2. Caso Base: Si el subproblema tiene 3 puntos o menos, se resuelve directamente
 *    por búsqueda exhaustiva.
 * 3. División: Se divide el intervalo por el punto medio en dos subconjuntos:
 *    mitad izquierda [left, mid] y mitad derecha [mid + 1, right].
 * 4. Vencer: Se resuelven recursivamente ambas mitades obteniendo los pares óptimos
 *    de cada lado.
 * 5. Combinar (Franja central):
 *    - Se toma la menor distancia de ambas ramas: dMin = min(distIzq, distDer).
 *    - Se filtran los puntos que caen dentro de la franja vertical [mid.x - dMin, mid.x + dMin].
 *    - Se comparan de forma exhaustiva los puntos dentro de la franja.
 *    - Si en la franja hay un par más cercano que dMin, esa es la solución global;
 *      en caso contrario, la solución es la mejor de las dos mitades.
 * 
 * Optimización aplicada (según la Nota Aclaratoria del profesor):
 * - Cada llamada recursiva devuelve un ParPuntos con la distancia ya almacenada.
 *   Así, elegir el mínimo de las dos ramas no requiere recalcular distancias euclídeas.
 */
public class BusquedaDyV {

    // Contador de distancias euclídeas calculadas durante la ejecución
    private long distanciasCalculadas;

    /**
     * Método público estático que ejecuta el algoritmo sobre el array de puntos.
     */
    public static Solucion ejecutar(Punto[] points) {
        if (points == null || points.length < 2) {
            return new Solucion(null, null, Double.POSITIVE_INFINITY, 0, 0.0, "Divide y Vencerás");
        }

        BusquedaDyV dyv = new BusquedaDyV();
        dyv.distanciasCalculadas = 0;

        long tiempoInicio = System.nanoTime();

        // 1. Clonar y ordenar por X mediante QuickSort
        Punto[] sortedX = points.clone();
        QuickSort.ordenarPorX(sortedX);

        // 2. Llamada recursiva principal
        ParPuntos mejorPar = dyv.resolverRec(sortedX, 0, sortedX.length - 1);

        double tiempoMs = (System.nanoTime() - tiempoInicio) / 1_000_000.0;

        return new Solucion(mejorPar, dyv.distanciasCalculadas, tiempoMs, "Divide y Vencerás");
    }

    /**
     * Método recursivo de Divide y Vencerás.
     */
    private ParPuntos resolverRec(Punto[] sortedX, int left, int right) {
        int count = right - left + 1;
        
        // Caso base: 3 puntos o menos
        if (count <= 3) {
            return casoBaseExhaustivo(sortedX, left, right);
        }

        // Punto de corte central
        int mid = left + (right - left) / 2;
        Punto midPunto = sortedX[mid];

        // Llamadas recursivas para las dos mitades
        ParPuntos leftRes = resolverRec(sortedX, left, mid);
        ParPuntos rightRes = resolverRec(sortedX, mid + 1, right);

        // Determinamos el mejor par de ambas mitades mediante comparación de double (sin recalcular distancias)
        ParPuntos minRes = (leftRes.getDistancia() < rightRes.getDistancia()) ? leftRes : rightRes;
        double dMin = minRes.getDistancia();

        // Construir la franja central ("strip") con los puntos a distancia horizontal < dMin del corte
        List<Punto> strip = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (Math.abs(sortedX[i].getX() - midPunto.getX()) < dMin) {
                strip.add(sortedX[i]);
            }
        }

        // Búsqueda exhaustiva entre los puntos que cayeron dentro de la franja
        ParPuntos stripRes = busquedaExhaustivaFranja(strip, dMin);

        // Si la franja encontró un par mejor, lo retornamos; si no, retornamos el mejor de las mitades
        if (stripRes != null && stripRes.getDistancia() < dMin) {
            return stripRes;
        }
        return minRes;
    }

    /**
     * Resuelve el caso base (hasta 3 puntos) comparando todos los pares del sub-intervalo.
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

    /**
     * Compara exhaustivamente todos los puntos dentro de la franja central.
     */
    private ParPuntos busquedaExhaustivaFranja(List<Punto> strip, double currentMin) {
        ParPuntos best = null;
        double minDist = currentMin;

        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size(); j++) {
                double dist = strip.get(i).distancia(strip.get(j));
                distanciasCalculadas++;

                if (dist < minDist) {
                    minDist = dist;
                    best = new ParPuntos(strip.get(i), strip.get(j), dist);
                }
            }
        }
        return best;
    }
}
