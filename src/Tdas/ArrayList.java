package Tdas;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>, Iterable<E>{
    private E[] array;
    private int capacity = 10;
    private int size;
    
    public ArrayList(){ //Todas las TDA van a inicializar vacias
        //No se puede hacer new de E directamente, por ello tienes que hacer el casting 
        array = (E[]) new Object[capacity];    
        size = 0;
    }

    public ArrayList(E[] array) {
        this.array = array;
        size= array.length;
    }
    
    
    @Override
    public boolean addFirst(E element) {
        if(element == null){
            return false;
        }else if(isEmpty()){
            array[size ++] = element;
            return true;
        }else if (capacity == size){
            addCapacity();
        }
        
        for(int i = size -1 ; i >= 0; i-- ){
            array[i+1] = array[i];
        }
        
        
        array [0] = element;
        size++;
        return true;   
    }
    
    @Override
    public boolean insert (int  index, E element){
        if(element == null || index >= size)
            return false;
        else if (size == capacity)
            addCapacity();
        
        for(int i = size; i > index; i--){
            array[i] = array[i-1];
        }
        
        array[index] = element;
        size++;
        return true;
    }
    
    @Override
    public boolean addLast(E element) {
        if(element == null){
            return false;
        }else if(size == capacity){
            addCapacity();
        }
        
        array[size++] = element;
        return true;
    }
    
    private void addCapacity(){
        E[] tmp = (E[]) new Object [capacity*2];
        for(int i = 0; i < capacity; i++){
            tmp[i] = array[i];
        }
        array = tmp;
        capacity = capacity*2;
    }

    @Override
    public E getFirst() {
        return array[0];
    }

    @Override
    public E getLast() {
        return array[size -1];
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty())
            return false;
        size--;
        for(int i = 0; i < size; i++){
            array[i] = array[i+1];
        }
        
        array[size] = null;
        return true;
    }

    @Override
    public boolean removeLast() {
        if(isEmpty()){
            return false;
        }
        
        array[--size] = null;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(E element) {
        if(element == null || isEmpty()){
            return false;
        }
        
        for(int i = 0; i < size ; i++){
            if(array[i].equals(element)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("[");
        
        for(int i = 0; i < size; i++){
            if(i != size-1){
                s.append(array[i] + ", ");
            }else
               s.append(array[i]);
        }
        
        s.append("]");
        return s.toString();
    }
    
    public List<E> slicing(int start, int end){
        List<E> lista = new ArrayList<>();
        if(start >= end || end > size) {
            return lista;
        }
        
        for(int i = start; i < end; i++ ){
            lista.addLast(array[i]);
        }
        
        return lista;
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
        final ArrayList<?> other = (ArrayList<?>) obj;
        if (this.size != other.size) {
            return false;
        }
        if (!Arrays.deepEquals(this.array, other.array)) {
            return false;
        }
        return true;
    }
    
   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.deepHashCode(this.array);
        hash = 23 * hash + this.size;
        return hash;
    }

    @Override
    public E get(int index) {
        if(size == 0 || index < 0 || index >= size){
            return null;
        }
        return array[index];
    }

    @Override
    public int indexOf(E element) {
        if (element == null){
            return -1;
        }
        
        for(int i = 0; i < size; i++){
            if(array[i].equals(element)){
                return i;
            }
        }
        
        return -1;
    }

    @Override
    public E remove(int index) {
        if(size == 0 || index < 0 || index >= size){
            return null;
        }
        E element = array[index];
        for(int i = index; i < size-1; i++){
            array[index] = array[index +1];
        }
        array[size-1] = null;
        size --;
        return element;
    }

    @Override
    public boolean remove(E element) {
        if(element == null) {
            return false;
        }
        for(int i = 0; i < size; i++){
            if(array[i].equals(element)){
                remove(i);
                return true;
            }
        }
        
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (element == null || index < 0 || index >= size){
            return null;
        }
        
        E i = array[index];
        array[index] = element;
        return i;
    }

    @Override
    public int size() {
        return size;
    }
    
    public ListIterator<E> listIterator(int index){
        ListIterator<E> it = new ListIterator<E>(){
            
            private int i = index;
            
            @Override
            public boolean hasNext() {
                return i < size && i >= 0;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E tmp = get(i);
                i++;
                return tmp;
            }

            @Override
            public boolean hasPrevious() {
                return i > 0 && i < size;
            }

            @Override
            public E previous() {
                E tmp = get(i);
                i--;
                return tmp;
            }

            @Override
            public int nextIndex() {
                return i + 1;
            }

            @Override
            public int previousIndex() {
                return i - 1;
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
    
    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>(){
            
            private int index = 0;
            
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E tmp = get(index);
                index++;
                return tmp;
            }
            
        };
        
        return it;
    }

    public ArrayList<E> findAll(Comparator cmp, E object) {
        
        ArrayList<E> arrayListFindAll = new ArrayList<>();
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            E data = iterator.next();
            if ( cmp.compare(data, object) == 0 ) {
                arrayListFindAll.addLast(data);
            }
        }
        return arrayListFindAll;
    }


}