/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import galeria.*;
import java.util.Objects;

/**
 *
 * @author Alan Coello
 */
public class Camara {
    private String nombre;
    private String marca;
    private String modelo;

    public Camara() {
    }

    public Camara(String nombre) {
        this.nombre = nombre;
    }

    public Camara(String nombre, String marca) {
        this.nombre = nombre;
        this.marca = marca;
    }

    public Camara(String nombre, String marca, String modelo) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Camara other = (Camara) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

}
