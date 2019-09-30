/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galeria;

import Tdas.DoublyCircularList;
import clases.Foto;
import clases.PhotoViewer;
import clases.Usuario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import jframes.JFPrincipal;
import jframes.VisualizadorFoto;

public class Galeria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Usuario usuario = new Usuario("general", "");
        
        //Llama a la ventana/JFrame
        JFPrincipal menuPrincipal = new JFPrincipal();
        // Inicia la clase PhotoViewer en la clase JFPrincipal
        menuPrincipal.inicializarPhotoViewer(usuario);
        menuPrincipal.setVisible(true);
        
    }
}