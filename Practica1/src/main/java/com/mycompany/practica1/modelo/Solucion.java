package com.mycompany.practica1.modelo;

import java.util.Locale;

/**
 * ============================================================================
 * CLASE: Solucion
 * ============================================================================
 * Genera el resultado y sus derivados de cara a mostrarlo por pantalla
 */

public class Solucion extends ParPuntos {
    
    private long distanciasCalculadas;
    private double tiempoMs;
    private String estrategia;

    public Solucion() {
        super(null, null, Double.POSITIVE_INFINITY);
        this.distanciasCalculadas = 0;
        this.tiempoMs = 0.0;
        this.estrategia = "";
    }

    public Solucion(Punto p1, Punto p2, double distancia, long distanciasCalculadas, double tiempoMs, String estrategia) {
        super(p1, p2, distancia);
        this.distanciasCalculadas = distanciasCalculadas;
        this.tiempoMs = tiempoMs;
        this.estrategia = estrategia;
    }

    public Solucion(ParPuntos par, long distanciasCalculadas, double tiempoMs, String estrategia) {
        super(par != null ? par.getP1() : null,
              par != null ? par.getP2() : null,
              par != null ? par.getDistancia() : Double.POSITIVE_INFINITY);
        this.distanciasCalculadas = distanciasCalculadas;
        this.tiempoMs = tiempoMs;
        this.estrategia = estrategia;
    }

    public long getDistanciasCalculadas() {
        return distanciasCalculadas;
    }

    public void setDistanciasCalculadas(long distanciasCalculadas) {
        this.distanciasCalculadas = distanciasCalculadas;
    }

    public double getTiempoMs() {
        return tiempoMs;
    }

    public void setTiempoMs(double tiempoMs) {
        this.tiempoMs = tiempoMs;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public String getDistanciaFormateada() { //formateador de distancia (segun enunciado)
        return String.format(Locale.US, "%.8f", getDistancia());
    }

    public String getTiempoFormateado() { //formateador de tiempo (segun enunciado)
        return String.format(Locale.US, "%.4f", tiempoMs);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%-16s %-25s %-25s %-12s %-10d %-10s",
                estrategia,
                (getP1() != null ? getP1().toString() : "-"),
                (getP2() != null ? getP2().toString() : "-"),
                getDistanciaFormateada(),
                distanciasCalculadas,
                getTiempoFormateado());
    }
}
