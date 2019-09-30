
package clases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import Tdas.ArrayList;
import Tdas.DoublyCircularList;
import java.util.HashSet;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/*
    Clase que contendra los albums y sera llamada por el jframe menu
*/

public class PhotoViewer {
    
    private Usuario usuario;
    private ArrayList<Album> albums;
    private HashSet<Camara> camaras;
    private HashSet<Persona> personas;
    private DoublyCircularList<Foto> fotos;
    private ArrayList<Foto> fotos2;

    public HashSet<Camara> getCamaras() {
        return camaras;
    }

    public void setCamaras(HashSet<Camara> camaras) {
        this.camaras = camaras;
    }

    public HashSet<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(HashSet<Persona> personas) {
        this.personas = personas;
    }
     /**
     * Constructor de la clase PhotoViewer
     * @param usuario
     */ 
    public PhotoViewer(Usuario usuario) {
        
        this.usuario = usuario;
        this.albums = new ArrayList<>();
        this.personas = new HashSet<>();
        this.camaras = new HashSet<>();
        this.fotos = new DoublyCircularList<>();
        this.fotos2 = new ArrayList<>();

        generarContenidoAlbums();
        generarContenidoFotos();    
        
    }
    
     
    // Lee toda la informacion de Albumes de dicho usuario en archivos JSON
    public void generarContenidoAlbums(){
        
        JSONParser parser = new JSONParser();
	try{
            
            FileReader file = new FileReader("src/archivos/" + usuario.getNombre() +"_albums.txt");
            BufferedReader bufferReader = new BufferedReader(file);
            String linea;
            while(( linea = bufferReader.readLine() ) != null){
                
                Object obj = parser.parse(linea);
                JSONObject jsonObject = (JSONObject) obj;

                String nombreJS, descripcionJS;
                
                // Lee solo el campo usuario, si es el usuario entonces se guarda en un album
                nombreJS = (String) jsonObject.get("nombre");
                descripcionJS = (String) jsonObject.get("descripcion");   

                if (!existeAlbum(nombreJS)) {
                    Album album = new Album(nombreJS, descripcionJS);
                    albums.addLast(album);
                }
            }
            
	}catch(Exception ex){
            System.err.println("Error: "+ ex.toString());
	}
        
    }
    
    public void generarContenidoFotos(){
        
        JSONParser parser = new JSONParser();
	try{
            
            FileReader file = new FileReader("src/archivos/" + usuario.getNombre() +"_fotos.txt");
            BufferedReader bufferReader = new BufferedReader(file);
            String linea;
            while(( linea = bufferReader.readLine() ) != null){
                
                Object obj = parser.parse(linea);
                JSONObject jsonObject = (JSONObject) obj;

                //String usuarioJS = (String) jsonObject.get("usuario");
                String albumJS = (String) jsonObject.get("album");
                    
                String nombreJS = (String) jsonObject.get("nombre");
                String pathJS = (String) jsonObject.get("path");
                String camaraJS = (String) jsonObject.get("camara");
                String lugarJS = (String) jsonObject.get("lugar");
                String fechaJS = (String) jsonObject.get("fecha");
                JSONArray personasJS = (JSONArray) jsonObject.get("personas");

                HashSet<String> personas = new HashSet<>();
                for (Object personaSt: personasJS) {
                    personas.add((String) personaSt);
                    this.personas.add(new Persona((String) personaSt));
                }
                this.camaras.add(new Camara(camaraJS));

                Foto foto = new Foto(nombreJS, pathJS, personas, camaraJS, lugarJS, fechaJS, albumJS);

                for (Album album: this.albums) {
                    if (album.getNombre().equals(albumJS)) {
                        album.agregarFotos(foto);
                        break;
                    }
                }
                
                this.fotos.addLast(foto);
                this.fotos2.addLast(foto);
                
            }         

	}catch(Exception ex){
            System.out.println("Error: generar fotos");
	}
        
    }      

    public Album albumPorNombre(String nombreAlbum){
        for (Album album: this.albums) {
            if (album.getNombre().equals(nombreAlbum)) {
                return album;
            }
        }
        return null;
    }    
    public Foto fotoPorNombre(String nombreAlbum, String nombreFoto){
        for (Album album: this.albums) {
            if (album.getNombre().equals(nombreAlbum)) {
                int i = 0;
                Iterator<Foto> it = album.getFotos().iterator();        
                while ( it.hasNext() ) {
                    Foto foto = it.next();
                    if (foto.getNombre().equals(nombreFoto)) {
                        return foto;
                    }
                    i++;
                    if (i == fotos.size()) {
                        break;
                    }            
                }
            }
        }
        return null;
    }
    public Foto fotoPorNombre2(String nombreFoto){
        for (Album album: this.albums) {
            int i = 0;
            Iterator<Foto> it = album.getFotos().iterator();        
            while ( it.hasNext() ) {
                Foto foto = it.next();
                if (foto.getNombre().equals(nombreFoto)) {
                    return foto;
                }
                i++;
                if (i == fotos.size()) {
                    break;
                }            
            }
        }
        return null;
    }
    
    public DoublyCircularList<Foto> fotosPorPersona(String nombrePersona){
        DoublyCircularList<Foto> fotos = albumTotal();
        DoublyCircularList<Foto> fotosMetodo = new DoublyCircularList<>();
        int i = 0;
        Iterator<Foto> it = fotos.iterator();
        while ( it.hasNext() ) {
            Foto foto = it.next();
            for (String persona: foto.getPersonas()) {
                if (nombrePersona.equals(persona)) {
                    fotosMetodo.addLast(foto);
                }
            }
            i++;
            if (i == fotos.size()) {
                break;
            }            
        }
        return fotosMetodo;
    }
    
    public DoublyCircularList<Foto> fotosPorCamara(String nombreCamara){
        DoublyCircularList<Foto> fotos = albumTotal();
        DoublyCircularList<Foto> fotosMetodo = new DoublyCircularList<>();
        int i = 0;
        Iterator<Foto> it = fotos.iterator();
        while ( it.hasNext() ) {
            Foto foto = it.next();
            if (foto.getCamara().equals(nombreCamara)) {
                fotosMetodo.addLast(foto);
            }
            i++;
            if (i == fotos.size()) {
                break;
            }            
        }
        return fotosMetodo;
    }
    public DoublyCircularList<Foto> albumTotal(){
        DoublyCircularList<Foto> fotosMetodo = new DoublyCircularList<>();
        for (Album album: this.albums) {
            Iterator<Foto> it = album.getFotos().iterator();
            int i = 0;
            while ( it.hasNext() ) {
                Foto foto = it.next();
                fotosMetodo.addLast(foto);
                i++;
                if (i == album.getFotos().size()) {
                    break;
                }  
            }            
        }
        return fotosMetodo;
    }
    
    public DoublyCircularList fotosPorAlbum(String nombreAlbum){
        if (nombreAlbum.equals("TODO")) {
            return albumTotal();
        } else {
            for (Album album: this.albums) {
                if (album.getNombre().equals(nombreAlbum)) {
                   return album.getFotos();
                }
            }
        }
        return null;
    }

    public boolean agregarFotoAlbum(Foto foto, String nombreAlbum){
        for (Album album: this.albums) {
            if (album.getNombre().equals(nombreAlbum)) {
                album.agregarFotos(foto);
                return true;
            }
        }    
        return false;
    }
    
    // Guarda toda la informacion de Albumes de dicho usuario en archivos JSON
    public void guardarContenidoAlbums(){
	try{
            FileWriter file = new FileWriter("src/archivos/" + usuario.getNombre() + "_albums.txt", false);
            
            for (Album album: albums) {
                JSONObject obj = new JSONObject();
                obj.put("nombre", album.getNombre());
                obj.put("descripcion", album.getDescripcion());
                file.write(obj.toJSONString()+"\n");
                file.flush();
            }          
            
            file.close();
	}catch(Exception ex){
            System.out.println("Error: "+ex.toString());
	}
	finally{
            System.out.println("JSON de Albunes escrito correctamente!!");
	}
    }
    
    public void guardarContenidoFotos(){
	try{
            FileWriter file = new FileWriter("src/archivos/" + usuario.getNombre() + "_fotos.txt", false);
            
            int a = 0;
            Iterator<Foto> listI = fotos2.iterator();
            while (listI.hasNext()) {
                Foto foto = listI.next();

                JSONObject obj = new JSONObject();
                obj.put("nombre", foto.getNombre());
                obj.put("path", foto.getPath());
                obj.put("lugar", foto.getLugar());
                obj.put("fecha", foto.getFecha());
                obj.put("album", foto.getAlbum());                
                obj.put("camara", foto.getCamara());

                JSONArray array = new JSONArray();
                for (String persona :foto.getPersonas()) {
                    array.add(persona);
                }
                obj.put("personas", array);

                file.write(obj.toJSONString()+"\n");
                file.flush();    
                
                a++;
                if (a == fotos.size()) {
                    break;
                }
            }
            file.close();
	}catch(Exception ex){
            System.out.println("Error: guardar fotos");
	}
	finally{
            //fotos = new ArrayList<>();
            System.out.println("JSON de Fotos escrito correctamente!!\n");
	}
    }    
    
    public void guardarFoto(){
	try{
            FileWriter file = new FileWriter("src/archivos/albums.txt");
            
            for (Album album: albums) {
                JSONObject obj = new JSONObject();
                obj.put("nombre", album.getNombre());
                obj.put("descripcion", album.getDescripcion());
                //obj.put("usuario", album.getUsuario());
                file.write(obj.toJSONString()+"\n");
                file.flush();
            }          
            
            file.close();
	}catch(Exception ex){
            System.out.println("Error: "+ex.toString());
	}
	finally{
            albums = new ArrayList<>();
            System.out.print("JSON de Albunes escrito correctamente!!");
	}
    }
    
    // Agrega un nuevo album al ArrayList<Album> del programa
    public void agregarAlbum(Album nuevoAlbum){
        if(noExisteAlbum(nuevoAlbum)){
            albums.addLast(nuevoAlbum);
        }else{
            System.out.println("Album ya existe");
        }
    }
    
    //Verifica si el nombre del album recien creado ya existe (evita repetidos)
    public boolean noExisteAlbum(Album b){
        boolean respuesta = true;
        for(Album a: albums){
            if(a.getNombre().toLowerCase().equals(b.getNombre().toLowerCase())){
                respuesta = false;
                break;
            }
        }
        return respuesta;
    }
    
    public boolean existeAlbum(String nombre){
        for(Album alb: albums){
            if(alb.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }    
    
    public void removerFoto(String nombre){
        int i = 0;
        Iterator<Foto> it = this.fotos2.iterator();
        while ( it.hasNext() ) {
            Foto foto = it.next();
            if (foto.equals(new Foto(nombre))) {
                this.fotos2.remove(foto);
                break;
            }
            i++;
            if (i == this.fotos.size()) {
                break;
            }            
        }        
    }
    
    public void removerAlbum(String nombre){
        for ( Album album: this.getAlbums() ) {
            if (album.getNombre().equals(nombre)) {
                this.getAlbums().remove(album);
                break;
            }
        }
    }
    public void removerPersona(String nombre){
        
    }
    
    public void removerCamara(String nombre){
        
    }
 
    //Devuelve el ArrayList de Albunes 
    
    public ArrayList<Album> getAlbums() {
        return albums;
    }
    
    //Actualiza el ArrayList de Albunes 
    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }
/* 
    public ArrayList<Foto> getFotos() {
        return fotos;
    }
*/

    public ArrayList<Foto> getFotos2() {
        return fotos2;
    }

    public void setFotos2(ArrayList<Foto> fotos2) {
        this.fotos2 = fotos2;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getCantidadAlbums() {
        return albums.size();
    }

    public int getCantidadFotos() {
        return fotos.size();
    }

    public DoublyCircularList<Foto> getFotos() {
        return fotos;
    }

}
