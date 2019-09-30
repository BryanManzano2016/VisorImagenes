/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jframes;

import Tdas.ArrayList;
import clases.Album;
import clases.PhotoViewer;
import clases.Usuario;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Tdas.DoublyCircularList;
import clases.Camara;
import clases.Foto;
import clases.Persona;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.table.DefaultTableModel;

public final class JFPrincipal extends javax.swing.JFrame {
    private Album album;
    private Usuario usuario;
    public PhotoViewer photoViewer;
    
    private String albumActual;
    private String fotoActual;
    private String camaraActual;
    private String personaActual;
    
    String albumSeleccion = "";
    String camaraSeleccion = "";
    String personaSeleccion = "";
    String seleccion = "";
    boolean seleccionBusqueda= false;
    
    DoublyCircularList<Foto> resultadoBusqueda;
    //
    /*
        estadoTabla: 1 para ver fotos
    */
    private int estadoTabla= 1;
    /**
     * Creates new form NewJFrame
     */
    public JFPrincipal() {
        initComponents();
        
        activarClickCelda();
        
        /*Cuando sale del programa se guardan las fotos y los albums
        (sobreescribiendo el archivo anterior)*/
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                photoViewer.guardarContenidoAlbums();
                photoViewer.guardarContenidoFotos();
            }
        });        
    }

    // Metodo que inicializa la galeria
    public void activarClickCelda(){
        tablaContenido.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaContenido.getSelectedRow();
                int col = tablaContenido.getSelectedColumn();
                if ( col == 0 ) {
                    // Aqui tomo la informacion de que quiero mostrar
                    seleccion = (String) tablaContenido.getValueAt(row, col);                     
                    
                    switch (estadoTabla) {
                        case 2:
                            {
                                albumSeleccion = seleccion;
                                DoublyCircularList<Foto> fotos1 = photoViewer.fotosPorAlbum(seleccion);
                                if (fotos1.size() > 0) {
                                    String columnas[] = {"Nombre", "Ubicacion", "Fecha"};
                                    reiniciarTabla(columnas, fotos1);
                                }       
                                break;
                            }
                        case 4:
                            {
                                personaSeleccion = seleccion;
                                DoublyCircularList<Foto> fotos2 = photoViewer.fotosPorPersona(seleccion);
                                if (fotos2.size() > 0) {
                                    String columnas[] = {"Nombre", "Ubicacion", "Fecha"};
                                    reiniciarTabla(columnas, fotos2);
                                }       
                                break;
                            }
                        case 3:
                            {
                                camaraSeleccion = seleccion;
                                DoublyCircularList<Foto> fotos3 = photoViewer.fotosPorCamara(seleccion);
                                if (fotos3.size() > 0) {
                                    String columnas[] = {"Nombre", "Ubicacion", "Fecha"};
                                    reiniciarTabla(columnas, fotos3);
                                }       
                                break;
                            }
                        default:
                            break;
                    }

                    estadoTabla = -1;
                    System.out.println("Selecciom: " + seleccion + ", estadoTabla: " + 
                        estadoTabla + ", album seleccion: " + albumSeleccion + ", camaraEleccion: " +
                        camaraSeleccion + ", personaEleccion: " + personaSeleccion);
                }
            }
        });         
    }

    public void EliminarAlbum(){
        if (albumActual == null)
            System.out.println("No hay album seleccionado");
        else 
            photoViewer.getAlbums().remove(photoViewer.albumPorNombre(albumActual));
        albumActual= null;
    }
   
    
    // Metodo generico para llenar tablas, el cual recibe ARRAYLIST
    public void reiniciarTabla(String comando, String[] campos, ArrayList listaElementos){
        // datos[fila][columna] de tabla
        String[][] datos = null;
        switch(comando){           
            case "verAlbums": 
                datos = new String[ listaElementos.size() ][3];
                ArrayList<Album> albums = listaElementos;

                for ( int i = 0; i < albums.size(); i++ ) {
                    datos[i][0] = albums.get(i).getNombre();
                    datos[i][1] = albums.get(i).getDescripcion();
                }
                
                break;
            default: 
                break;
        }

        DefaultTableModel model = new DefaultTableModel(datos, campos);
        tablaContenido.setModel(model);
        // No permite modificar la tabla
        tablaContenido.setDefaultEditor(Object.class, null);
        
    }
    // Metodo generico para llenar tablas, el cual recibe HASHSET
    public void reiniciarTabla(String comando, String[] campos, HashSet listaElementos){
        // datos[fila][columna] de tabla
        String[][] datos = null;
        switch(comando){           
            case "verCamaras":
                datos = new String[ listaElementos.size() ][3];
                HashSet<Camara> camaras = listaElementos;

                int i = 0;
                for (Camara camara: camaras) {
                    datos[i][0] = camara.getNombre();
                    i++;
                }                
                break;
            case "verPersonas": 
                datos = new String[ listaElementos.size() ][3];
                HashSet<Persona> personas = listaElementos;

                int i2 = 0;
                for (Persona persona: personas) {
                    datos[i2][0] = persona.getNombre();
                    i2++;
                }
                break;                
            default: 
                break;
        }

        DefaultTableModel model = new DefaultTableModel(datos, campos);
        tablaContenido.setModel(model);
        // No permite modificar la tabla
        tablaContenido.setDefaultEditor(Object.class, null);
        
    }
    
    // Metodo generico para llenar tablas, el cual recibe lista circular
    public void reiniciarTabla(String[] campos, DoublyCircularList lista){
        
        String[][] datos = null;
        datos = new String[ lista.size() ][3];

        int i = 0;
        Iterator<Foto> it = lista.iterator();        
        while ( it.hasNext() ) {
            Foto foto = it.next();
            datos[i][0] = foto.getNombre();
            datos[i][1] = foto.getPath();
            datos[i][2] = foto.getFecha();              
            i++;
            if (i == lista.size()) {
                break;
            }            
        }     
        DefaultTableModel model = new DefaultTableModel(datos, campos);
        tablaContenido.setModel(model);
        // No permite modificar la tabla
        tablaContenido.setDefaultEditor(Object.class, null);         
        
    }    
 
    // Otorga el nombre de usuario e iniciar PhotoViewer con dicho usuario
    public void inicializarPhotoViewer(Usuario u){
        this.usuario = u;
        this.photoViewer = new PhotoViewer( this.usuario );        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        labelContenido = new javax.swing.JLabel();
        labelFotos = new javax.swing.JLabel();
        labelAlbums = new javax.swing.JLabel();
        labelCamaras = new javax.swing.JLabel();
        labelPersonas = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        TextFieldLugar = new javax.swing.JTextField();
        BotonBuscar = new javax.swing.JButton();
        TextFieldFecha = new javax.swing.JTextField();
        TextFieldPersonas = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaContenido = new javax.swing.JTable();
        botonVisualizar = new javax.swing.JButton();
        butonEliminar = new javax.swing.JButton();
        botonAgregar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        labelInformacion = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        labelUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(java.awt.Color.lightGray);

        labelContenido.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelContenido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelContenido.setText("PHOTOVIEWER");

        labelFotos.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelFotos.setText("Fotos");
        labelFotos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelFotosMouseClicked(evt);
            }
        });

        labelAlbums.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelAlbums.setText("Albums");
        labelAlbums.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelAlbumsMouseClicked(evt);
            }
        });

        labelCamaras.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelCamaras.setText("Camaras");
        labelCamaras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelCamarasMouseClicked(evt);
            }
        });

        labelPersonas.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        labelPersonas.setText("Personas");
        labelPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelPersonasMouseClicked(evt);
            }
        });

        jLabel1.setText("Buscar por:");

        TextFieldLugar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldLugarActionPerformed(evt);
            }
        });

        BotonBuscar.setText("Buscar fotos");
        BotonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonBuscarActionPerformed(evt);
            }
        });

        TextFieldFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldFechaActionPerformed(evt);
            }
        });

        TextFieldPersonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TextFieldPersonasActionPerformed(evt);
            }
        });

        jButton1.setText("Buscar albumes");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel2.setText("Fecha");

        jLabel3.setText("Lugar");

        jLabel4.setText("Personas");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelContenido, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(labelFotos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelAlbums, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelCamaras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelPersonas, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addComponent(jLabel4))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(TextFieldPersonas, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(TextFieldFecha, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(TextFieldLugar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(BotonBuscar)))
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelContenido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelFotos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelAlbums)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelCamaras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelPersonas)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(53, 53, 53)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextFieldLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(TextFieldFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFieldPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(BotonBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelContenido.setBackground(java.awt.Color.lightGray);
        panelContenido.setAutoscrolls(true);

        tablaContenido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaContenido.setShowVerticalLines(false);
        jScrollPane2.setViewportView(tablaContenido);

        botonVisualizar.setText("VISUALIZAR");
        botonVisualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonVisualizarMouseClicked(evt);
            }
        });

        butonEliminar.setText("ELIMINAR");
        butonEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                butonEliminarMouseClicked(evt);
            }
        });

        botonAgregar.setText("AGREGAR");
        botonAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAgregarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelContenidoLayout = new javax.swing.GroupLayout(panelContenido);
        panelContenido.setLayout(panelContenidoLayout);
        panelContenidoLayout.setHorizontalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelContenidoLayout.createSequentialGroup()
                        .addComponent(butonEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonAgregar))
                    .addComponent(botonVisualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelContenidoLayout.setVerticalGroup(
            panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContenidoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonVisualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(butonEliminar)
                    .addComponent(botonAgregar))
                .addContainerGap())
        );

        jPanel3.setBackground(java.awt.Color.lightGray);
        jPanel3.setAutoscrolls(true);

        labelInformacion.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelInformacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInformacion.setText("INFORMACION");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInformacion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInformacion)
                .addContainerGap(251, Short.MAX_VALUE))
        );

        labelUsuario.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelUsuario.setText("USUARIO");

        jLabel8.setText("no implementar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(24, 24, 24))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelUsuario)
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Cuando presiona label fotos
    private void labelFotosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelFotosMouseClicked

        DoublyCircularList<Foto> fotos = this.photoViewer.albumTotal();
        String columnas[] = {"Nombre", "Ubicacion", "Fecha"};       
        reiniciarTabla(columnas, fotos);
        
        this.estadoTabla = 1;
        
    }//GEN-LAST:event_labelFotosMouseClicked
    
    // Reinicia la tabla para mostrar informacion de los albums
    private void labelAlbumsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelAlbumsMouseClicked

        String columnas[] = {"Nombre", "Descripcion"};
        reiniciarTabla("verAlbums", columnas, this.photoViewer.getAlbums()); 

        this.estadoTabla = 2;
    }//GEN-LAST:event_labelAlbumsMouseClicked

    private void labelCamarasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelCamarasMouseClicked

        String columnas[] = {"Nombre"};
        reiniciarTabla("verCamaras", columnas, this.photoViewer.getCamaras());         
        
        this.estadoTabla = 3;
    }//GEN-LAST:event_labelCamarasMouseClicked

    private void labelPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelPersonasMouseClicked
        
        String columnas[] = {"Nombre"};
        reiniciarTabla("verPersonas", columnas, this.photoViewer.getPersonas());         
        
        this.estadoTabla = 4;        
    }//GEN-LAST:event_labelPersonasMouseClicked
   


    private void BotonBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBorrarActionPerformed
      
        
                
    }//GEN-LAST:event_BotonBorrarActionPerformed

    private void botonVisualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonVisualizarMouseClicked

        VisualizadorFoto menuPrincipal = null;
        Foto foto = null;
        DoublyCircularList<Foto> fotos = null;
        boolean validar = false;
        
        
         if(seleccionBusqueda) {
            foto = this.photoViewer.fotoPorNombre2(seleccion);
            fotos = this.resultadoBusqueda;
            if (fotos.size() > 0) {
                validar = true;            
            }  
            seleccionBusqueda=false;
        }
         else if (!albumSeleccion.equals("")) {
            foto = this.photoViewer.fotoPorNombre2(seleccion);
            fotos = this.photoViewer.fotosPorAlbum(albumSeleccion);
            if (fotos.size() > 0) {
                validar = true;
            }     
            albumSeleccion = "";
        } else if (!camaraSeleccion.equals("")) {
            foto = this.photoViewer.fotoPorNombre2(seleccion);
            fotos = this.photoViewer.fotosPorCamara(camaraSeleccion);
            if (fotos.size() > 0) {
                validar = true;
            }               
            camaraSeleccion = "";
        } else if (!personaSeleccion.equals("")) {
            foto = this.photoViewer.fotoPorNombre2(seleccion);
            fotos = this.photoViewer.fotosPorPersona(personaSeleccion);
            if (fotos.size() > 0) {
                validar = true;
            }  
            personaSeleccion = "";
        } else if(!seleccion.equals("")) {
            foto = this.photoViewer.fotoPorNombre2(seleccion);
            fotos = this.photoViewer.albumTotal();
            if (fotos.size() > 0) {
                validar = true;            
            }                 
        }
        
        if (validar) {
            menuPrincipal = new VisualizadorFoto(fotos, foto);
            menuPrincipal.setVentanaAtras(this);
            menuPrincipal.setVisible(true);
            this.setVisible(false);                
        }
        
        seleccion = "";
    }//GEN-LAST:event_botonVisualizarMouseClicked

    private void butonEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_butonEliminarMouseClicked
        
        boolean validar = false;
        if (!albumSeleccion.equals("")) {
            this.photoViewer.removerAlbum(albumSeleccion);
            validar = true;
            albumSeleccion = "";
        } else if (!camaraSeleccion.equals("")) {
            validar = true;            
            camaraSeleccion = "";
        } else if (!personaSeleccion.equals("")) {
            validar = true;
            personaSeleccion = "";
        } else if(!seleccion.equals("")) { 
            validar = true;            
            System.out.println(this.photoViewer.getFotos2().size());
            this.photoViewer.removerFoto(seleccion);
            System.out.println(this.photoViewer.getFotos2().size());            
        }
        
        if (validar) {
            
            this.photoViewer.guardarContenidoAlbums();
            this.photoViewer.guardarContenidoFotos();
            
            JFPrincipal nuevo = new JFPrincipal();
            nuevo.inicializarPhotoViewer(usuario);
            nuevo.setVisible(true);
            
            this.setVisible(false);
            this.dispose();            
        }
        seleccion = "";        
    }//GEN-LAST:event_butonEliminarMouseClicked

    private void botonAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAgregarMouseClicked
        
        EleccionAgregar eleccion = new EleccionAgregar();
        eleccion.setUsuario(this.usuario.getNombre());
        eleccion.setPhotoViewer(photoViewer);
        eleccion.setVisible(true);
        eleccion.setVentanaAtras(this);
        this.setVisible(false);
        //this.dispose();
        
    }//GEN-LAST:event_botonAgregarMouseClicked

    private void TextFieldLugarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldLugarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldLugarActionPerformed

    private void TextFieldPersonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldPersonasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldPersonasActionPerformed

    private void TextFieldFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TextFieldFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TextFieldFechaActionPerformed

    private void BotonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonBuscarActionPerformed
        String lugar = TextFieldLugar.getText();
        String[] arPersonas = TextFieldPersonas.getText().split(",");
        String fecha = TextFieldFecha.getText();
        ArrayList<String> personas = new ArrayList<String>(arPersonas);
        
        DoublyCircularList<Foto> resultado= new DoublyCircularList<Foto>();
        String columnas[] = {"Nombre", "Ubicacion", "Fecha"};     
        int i = 0;
        int j = 0;
        Iterator<Foto> it = photoViewer.albumTotal().iterator();        
        while ( it.hasNext() ) {
            Foto foto = it.next();
            for(String s : personas){
                if(foto.getPersonas().contains(s)) 
                    j++;
                if(j==personas.size()||foto.getLugar().equals(lugar)|| foto.getFecha().equals(fecha)){
                    resultado.addLast(foto);
                    break;  
                }
            }
             i++;
            if (i == photoViewer.albumTotal().size()) 
                break;

            reiniciarTabla(columnas, resultado);
            resultadoBusqueda=resultado;

            seleccionBusqueda=true;
        }  
        
    }//GEN-LAST:event_BotonBuscarActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        String lugar = TextFieldLugar.getText();
        String[] arPersonas = TextFieldPersonas.getText().split(",");
        String fecha = TextFieldFecha.getText();
        ArrayList<String> personas = new ArrayList<String>(arPersonas);
       
        DoublyCircularList<Foto> resultado= new DoublyCircularList<Foto>();
        ArrayList<Album> resultado2= new ArrayList<Album>();
        String columnas[] = {"Nombre", "Ubicacion", "Fecha"};     
        int i = 0;
        int j=0;

        Iterator<Foto> it = photoViewer.albumTotal().iterator();        
        while ( it.hasNext() ) {
            Foto foto = it.next();
            for(String s : personas){
                    if(foto.getPersonas().contains(s)) j++;
                if(j==personas.size()||foto.getLugar().equals(lugar)|| foto.getFecha().equals(fecha)){
                      resultado.addLast(foto);
                    break;
                }
            }
            i++;

            if (i == photoViewer.albumTotal().size()) {
                break;
            }
            Iterator<Foto> it2 = resultado.iterator();
            int ka=0;
            while ( it2.hasNext() ) {
                Foto f = it2.next();
                for(Album a: photoViewer.getAlbums())
                if (a.getFotos().contains(f)) resultado2.addLast(a);
                ka++;
                if (ka==resultado.size()) break;
            }
        
            String campos[] = {"Nombre", "Descripcion"};
            String[][] datos = null;
            
            datos = new String[ resultado2.size() ][3];

            for ( int k = 0; k < resultado2.size(); k++ ) {
                datos[k][0] = resultado2.get(k).getNombre();
                datos[k][1] = resultado2.get(k).getDescripcion();
            }

            DefaultTableModel model = new DefaultTableModel(datos, campos);
            tablaContenido.setModel(model);
            // No permite modificar la tabla
            tablaContenido.setDefaultEditor(Object.class, null);
                
            estadoTabla=2;
            seleccionBusqueda=true;
        
        }
          
    }//GEN-LAST:event_jButton1MouseClicked
  
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonBuscar;
    private javax.swing.JTextField TextFieldFecha;
    private javax.swing.JTextField TextFieldLugar;
    private javax.swing.JTextField TextFieldPersonas;
    private javax.swing.JButton botonAgregar;
    private javax.swing.JButton botonVisualizar;
    private javax.swing.JButton butonEliminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelAlbums;
    private javax.swing.JLabel labelCamaras;
    private javax.swing.JLabel labelContenido;
    private javax.swing.JLabel labelFotos;
    private javax.swing.JLabel labelInformacion;
    private javax.swing.JLabel labelPersonas;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JTable tablaContenido;
    // End of variables declaration//GEN-END:variables

}
