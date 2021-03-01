package me.shanodis.HomePlugin;

public class List<T1, T2> {
    // Node Class Objects
    private Node<T1, T2> first, last;

    // Constructor
    public List() { first = last = null; }

    // private Methods
    private void createList(T1 location, T2 name) {
        first = last = new Node<>(location, name);
        first.next = last.next = null;
    }

    private boolean deleteLastNode() {
        first = last = null;
        return true;
    }

    private Node<T1, T2> getPrevNode() {
        Node<T1, T2> current = first;
        while(current != last) {
            if(current.next == last)
                return current;
            current = current.next;
        }
        return null;
    }
    
    private boolean checkData(T2 name) {
		var current = first;
		while(current != null) {
			if(current.name.equals(name))
				return false;
			current = current.next;
		}
		return true;
	}

    // public Methods
    public boolean addFirst(T1 location, T2 name) {
    	if(!checkData(name)) 
    		return false;
    	
        if(first == null)
            createList(location, name);
        else {
            Node<T1, T2> newNode = new Node<T1, T2>(location, name);
            newNode.next = first;
            first = newNode;
        }
        return true;
    }

    public boolean addLast(T1 location, T2 name) {
    	if(!checkData(name)) 
    		return false;
    	
        if(first == null)
            createList(location, name);
        else {
            Node<T1, T2> newNode = new Node<T1, T2>(location, name);
            newNode.next = null;
            last.next = newNode;
            last = newNode;
        }
        return true;
    }

    public boolean deleteFirst() {
        if(first != null) {
            if(first == last)
                return deleteLastNode();

            Node<T1, T2> nextNode = first.next;
            first.next = null;
            first = nextNode;

            return true;
        }
        return false;
    }

    public boolean deleteLast() {
        if(last != null) {
            if(first == last)
                return deleteLastNode();
            
            Node<T1, T2> prevNode = getPrevNode();
            last = prevNode;
            last.next = null;

            return true;
        }
        return false;
    }
    
    public int size() {
    	int listSize = 0;
    	var current = first;
    	while(current != null) {
    		listSize++;
    		current = current.next;
    	}
    	return listSize;
    }
    
    public Node<T1, T2> searchNode(int index) {
    	var current = first;
    	int i = 1;
    	while(current != null) {
    		if(i == index) 
    			return current;
    		i++;
    		current = current.next;
    	}
    	return null;
    }
}
