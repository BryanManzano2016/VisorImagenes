
package clases;

import java.util.HashSet;
import java.util.Objects;

public class Foto {
    
    private String nombre;
    private String path;
    private HashSet<String> personas;
    private String camara;
    private String lugar;
    private String fecha;
    private String album;

    public Foto(String nombre) {
        this.nombre = nombre;
    }
    
    public Foto(String nombre, String path, HashSet<String> personas, String camara, String lugar, String fecha, String album) {

        this.nombre = nombre;
        this.path = path;
        this.personas = personas;
        this.camara = camara;
        this.lugar = lugar;
        this.fecha = fecha;
        this.album = album;
        
    }

    public String getCamara() {
        return camara;
    }

    public void setCamara(String camara) {
        this.camara = camara;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    
    public String getNombre() {
        return nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashSet<String> getPersonas() {
        return personas;
    }

    public void setPersonas(HashSet<String> personas) {
        this.personas = personas;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public String toString() {
        return "Foto{" + "nombre=" + nombre + '}';
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
        final Foto other = (Foto) obj;
        return this.nombre.equals(other.nombre);
    }
    
    
}
