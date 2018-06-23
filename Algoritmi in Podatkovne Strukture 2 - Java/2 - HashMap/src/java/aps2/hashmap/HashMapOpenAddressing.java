package aps2.hashmap;

/**
 * Hash map with open addressing.
 */
public class HashMapOpenAddressing {
	private Element table[]; // table content, if element is not present, use Integer.MIN_VALUE for Element's key
	private HashFunction.HashingMethod h;
	private CollisionProbeSequence c;
	
	public static enum CollisionProbeSequence {
		LinearProbing,    // new h(k) = (h(k) + i) mod m
		QuadraticProbing, // new h(k) = (h(k) + i^2) mod m
		DoubleHashing     // new h(k) = (h(k) + i*h(k)) mod m
	};
	
	public HashMapOpenAddressing(int m, HashFunction.HashingMethod h, CollisionProbeSequence c) {
		this.table = new Element[m];
		this.h = h;
		this.c = c;
		
		// init empty slot as MIN_VALUE
		for (int i=0; i<m; i++) {
			table[i] = new Element(Integer.MIN_VALUE, "");
		}
	}
	
	private int getSequencedHash(int k, int i) {
		int hash = this.getHash(k);
		if(this.c == HashMapOpenAddressing.CollisionProbeSequence.LinearProbing) {
			hash = hash + i;
		}else if(this.c == HashMapOpenAddressing.CollisionProbeSequence.QuadraticProbing) {
			hash = (int) (hash + (Math.pow(i, 2))) ;
		}else if(this.c == HashMapOpenAddressing.CollisionProbeSequence.DoubleHashing) {
			hash = hash + (i * this.getHash(k));
		}
		hash = hash % this.getTable().length;
		return hash; 
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

	public Element[] getTable() {
		return this.table;
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
		for(int i = 0; i < this.getTable().length; i++) {
			int hash = this.getSequencedHash(k, i);
			if (this.getTable()[hash].equals(new Element(Integer.MIN_VALUE, ""))) {
				this.table[hash] = new Element(k, v);
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the element from the set.
	 * 
	 * @param k Element key
	 * @return true, if the element was removed; otherwise false
	 */
	public boolean remove(int k) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		for(int i = 0; i < this.getTable().length; i++) {
			int hash = this.getSequencedHash(k, i);
			if(this.getTable()[hash].equals(new Element(k, ""))) {
				this.table[hash] = new Element(Integer.MIN_VALUE, "");
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
		for(int i = 0; i < this.getTable().length; i++) {
			int hash = this.getSequencedHash(k, i);
			if (this.getTable()[hash].equals(new Element(k, ""))) return true;
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
		for(int i = 0; i < this.getTable().length; i++) {
			int hash = this.getSequencedHash(k, i);
			if (this.getTable()[hash].equals(new Element(k, ""))) return this.table[hash].value;
		}
		return null;
	}
}

