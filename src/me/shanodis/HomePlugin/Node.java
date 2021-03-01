package me.shanodis.HomePlugin;


public class Node<T1, T2> {
    // Data
    T1 location;
    T2 name;
    Node<T1, T2> next;

    // Constructor
    public Node(T1 location, T2 name) {
        this.name = name; 
        this.location = location;
    }
}
