
package clases;

import Tdas.DoublyCircularList;
import java.util.Objects;

public class Album {
    private String nombre;
    private String descripcion;
    private DoublyCircularList<Foto> fotos;

    public Album() {}

    public Album(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotos = new DoublyCircularList<>();
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DoublyCircularList<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(DoublyCircularList<Foto> fotos) {
        this.fotos = fotos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    
    public void agregarFotos(Foto foto){
        this.fotos.addLast(foto);
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
        final Album other = (Album) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }
  
}
