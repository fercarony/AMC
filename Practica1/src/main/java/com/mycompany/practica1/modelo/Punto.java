package com.mycompany.practica1.modelo;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Locale;

/**
 * ============================================================================
 * CLASE: Punto
 * ============================================================================
 * Representa un punto bidimensional (x, y) en el plano cartesiano.
 */

public class Punto {
    
    private double x;
    private double y;
    private int id;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
        this.id = 0;
    }
    
    public Punto(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Punto(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public double distancia(Punto p) {
        return sqrt(pow(getX() - p.getX(), 2) + pow(getY() - p.getY(), 2));
    }

    @Override
    public String toString() {
        if (id > 0) {
            return String.format(Locale.US, "%d (%.3f, %.3f)", id, x, y);
        }
        return String.format(Locale.US, "(%.3f, %.3f)", x, y);
    }
}
