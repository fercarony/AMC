package com.mycompany.practica1.modelo;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Locale;

/**
 * ============================================================================
 * CLASE: ParPuntos
 * ============================================================================
 * Representa una pareja de dos puntos y la distancia euclídea existente entre ellos.
**/

public class ParPuntos {
    
    private Punto P1;
    private Punto P2;
    private double distancia;

    
    public ParPuntos(Punto P1, Punto P2, double distancia) {
        this.P1 = P1;
        this.P2 = P2;
        this.distancia = distancia;
    }

    public ParPuntos(Punto P1, Punto P2) {
        this.P1 = P1;
        this.P2 = P2;
        if (P1 != null && P2 != null) {
            this.distancia = P1.distancia(P2);
        } else {
            this.distancia = Double.POSITIVE_INFINITY;
        }
    }

    public Punto getP1() {
        return P1;
    }

    public void setP1(Punto P1) {
        this.P1 = P1;
    }

    public Punto getP2() {
        return P2;
    }

    public void setP2(Punto P2) {
        this.P2 = P2;
    }

    public Punto getPunto1() {
        return P1;
    }

    public Punto getPunto2() {
        return P2;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    public void distancia() {
        if (P1 != null && P2 != null) {
            distancia = sqrt(pow(P1.getX() - P2.getX(), 2) + pow(P1.getY() - P2.getY(), 2));
        }
    }

    @Override
    public String toString() {  // de cara a mostrar por pantalla los resultados
        return (P1 != null ? P1.toString() : "-") + " <-> " + 
               (P2 != null ? P2.toString() : "-") + 
               " (distancia: " + String.format(Locale.US, "%.8f", distancia) + ")";
    }
}
