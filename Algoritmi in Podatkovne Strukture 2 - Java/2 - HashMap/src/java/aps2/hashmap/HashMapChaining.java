package aps2.hashmap;

import java.util.LinkedList;

/**
 * Hash map employing chaining on collisions.
 */
public class HashMapChaining {
	private LinkedList<Element> table[];
	private HashFunction.HashingMethod h;
	
	public HashMapChaining(int m, HashFunction.HashingMethod h) {
		this.h = h;
		this.table = new LinkedList[m];
		for (int i=0; i<table.length; i++) {
			table[i] = new LinkedList<Element>();
		}
	}
	
	public LinkedList<Element>[] getTable() {
		return this.table;
	}
	
	private int getHash(int k) {
		int hash = 0;
		if (this.h == HashFunction.HashingMethod.DivisionMethod) {
			hash = HashFunction.DivisionMethod(k, this.getTable().length);
		}else if (this.h == HashFunction.HashingMethod.KnuthMethod) {
			hash = HashFunction.KnuthMethod(k, this.getTable().length);
		}
		return hash;
	}
	
	/**
	 * If the element doesn't exist yet, inserts it into the set.
	 * 
	 * @param k Element key
	 * @param v Element value
	 * @return true, if element was added; false otherwise.
	 */
	public boolean add(int k, String v) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if (this.contains(k)) return false;
		int hash = this.getHash(k);
		this.getTable()[hash].add(new Element(k, v));
		return true;
	}

	/**
	 * Removes the element from the set.
	 * 
	 * @param k Element key
	 * @return true, if the element was removed; otherwise false
	 */
	public boolean remove(int k) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if (!this.contains(k)) return false;
		int hash = this.getHash(k);
		for(int i = 0; i < this.getTable()[hash].size(); i++) {
			if(this.getTable()[hash].get(i).equals(new Element(k, ""))) {
				this.getTable()[hash].remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds the element.
	 * 
	 * @param k Element key
	 * @return true, if the element was found; false otherwise.
	 */
	public boolean contains(int k) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int hash = this.getHash(k);
		for(int i = 0; i < this.getTable()[hash].size(); i++) {
			if (this.getTable()[hash].get(i).equals(new Element(k, ""))) return true;
		}
		return false;
	}
	
	/**
	 * Maps the given key to its value, if the key exists in the hash map.
	 * 
	 * @param k Element key
	 * @return The value for the given key or null, if such a key does not exist.
	 */
	public String get(int k) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int hash = this.getHash(k);
		for(int i = 0; i < this.getTable()[hash].size(); i++) {
			if(this.getTable()[hash].get(i).equals(new Element(k, ""))) return this.getTable()[hash].get(i).getValue();
		}
		return null;
	}
}

