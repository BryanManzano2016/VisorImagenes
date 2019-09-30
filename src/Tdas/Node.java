package Tdas;

public class Node<E> {
    
    private E element; 
    private Node<E> prev;  
    private Node<E> next;    
    
    public Node(E e, Node<E> n, Node<E> p) {
        element = e;
        next = n;
        prev = p;
    }

    public Node(E element, Node<E> next) {
        this.element = element;
        this.next = next;
        this.prev = null;
    }
    
    public Node(E e) {
        element = e;
        this.prev = null;
        this.next = null;
    }

    public E getElement( ) { 
        return element; 
    }
    public Node<E> getNext( ) { 
        return next; 
    }
    public void setNext(Node<E> n) { 
        next = n; 
    }
    public Node<E> getPrev( ) { 
        return prev; 
    }
    public void setPrev(Node<E> p) {
        prev = p; 
    }
    
}
