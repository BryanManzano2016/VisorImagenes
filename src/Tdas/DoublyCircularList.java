package Tdas;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

public class DoublyCircularList<E> implements Iterable<E>{

    private Node<E> tail = null; 
    private int size = 0; 
    public DoublyCircularList() {} 

    Node<E> nodeIterator;
    Node<E> nodeListIterator;
            
    public int size( ) { return size; }
    public boolean isEmpty( ) { return size == 0; }
    
    public Node first( ) {  
    if (isEmpty( )) return null;
        return tail;
    }
    public Node last( ) { 
        if (isEmpty( )) return null;
        return tail;
    }
    public void rotate( ) {
        if (tail != null) 
            tail = tail.getNext();
    }

    public void addFirst(E element) { 
        Node<E> nodo = new Node(element);
        if (size == 0) {
            tail = nodo;
            tail.setNext(tail);
            tail.setPrev(tail);
        } else {
            nodo.setNext(tail.getNext());
            tail.getNext().setPrev(nodo);
            
            nodo.setPrev(tail);
            tail.setNext(nodo);
        }
        size++;
    }

    public void addLast(E element) { 
        addFirst(element); 
        tail = tail.getNext();
    }
    
    public E removeFirst( ) {  
        if (isEmpty( )) return null; 
        Node<E> head = tail.getNext( );
        if (head == tail) 
            tail = null; 
        else
            tail.setNext(head.getNext( ));  
        size--;
        return head.getElement( );
    }
    
    public E remove(E element){
        E data = null;
        for (Node<E> node = this.tail.getNext(); node != null ;node = node.getNext()) {
            if (node.getElement().equals(element)) {
                data = node.getElement();
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
                break;
            }
        }
        return data;
    }
    
    public boolean contains(E e){
        int i = 0;
        ListIterator<E> listIterador = this.listIterator();
        while (listIterador.hasNext()) {
            E elemento = listIterador.next();
            if (elemento.equals(e)) {
                return true;
            }
            i++;
            if (i == this.size) {
                break;
            }
        }
        return false;
    }
    
    public Iterator<E> iterator() {

        nodeIterator = tail;

        Iterator<E> it = new Iterator<E>(){
            
            @Override
            public boolean hasNext() {
                return nodeIterator != null;
            }
            @Override
            public E next() {
                E data = null;
                if (nodeIterator != null) 
                    data = nodeIterator.getElement();                    
                changeNodeIterator(); // Metodo implementado abajo  
                return data;
            }
        };
        
        return it;
        
    }    
    
    private void changeNodeIterator(){
        nodeIterator = nodeIterator.getNext();
    }
    
    public ListIterator<E> listIterator(){
        
        nodeListIterator = this.tail;
        
        ListIterator<E> it = new ListIterator<E>(){
            @Override
            public boolean hasNext() {
                try {
                    return nodeListIterator != null;
                } catch (Exception e) {}
                return false;            
            }

            @Override
            public E next() {
                E data = null;
                try {
                    nextListIterator();
                    data = nodeListIterator.getElement();
                } catch (Exception e) {}
                return data;
            }

            @Override
            public boolean hasPrevious() {
                try {
                    return nodeListIterator != null;
                } catch (Exception e) {}
                return false;   
            }

            @Override
            public E previous() {
                E data = null;
                try {
                    previewListIterator();                    
                    data = nodeListIterator.getElement();
                } catch (Exception e) {}
                return data;            
            }
            
            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void set(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void add(E e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        return it;
    }
    
    private void nextListIterator(){
        nodeListIterator = nodeListIterator.getNext();
    }
    private void previewListIterator(){
        nodeListIterator = nodeListIterator.getPrev();
    }    
    
    public DoublyCircularList<E> findAll(Comparator comparador, E otroElemento) {
        
        DoublyCircularList<E> nuevos_elementos = new DoublyCircularList();
        
        Iterator<E> iterador = this.iterator();
        while(iterador.hasNext()){
            E elemento  = iterador.next();
            if( (comparador.compare(elemento, otroElemento)) == 0){
                nuevos_elementos.addLast(elemento);
            }
        }
        
        return nuevos_elementos;
    }

}