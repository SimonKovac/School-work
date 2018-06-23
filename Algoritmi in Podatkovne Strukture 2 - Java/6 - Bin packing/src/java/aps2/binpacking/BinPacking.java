package aps2.binpacking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.PriorityQueue;

class Bin {
	int m;
	LinkedList<Integer> contents;
	
	public Bin(int m) {
		this.m = m;
		this.contents = new LinkedList<Integer>();
	}
	
	public int getCapacity() {
		int filled = 0;
		for(int i = 0; i < this.contents.size(); i++) {
			filled += this.contents.get(i);
		}
		return this.m - filled;
	}
	
	public boolean insert(int size) {
		if (size <= this.getCapacity()) {
			this.contents.add(size);
			return true;
		}
		return false;
	}
}

/**
 * Bin packing algorithm to compute the minimum number of bins to store all the
 * given items.
 */
public class BinPacking {
	int[] items; // sizes of items to store
	int m; // size of each bin

	public BinPacking(int[] items, int m) {
		this.items = items;
		this.m = m;
	}

	/**
	 * Computes the strictly minimal number of bins for the given items.
	 * 
	 * @return Array of size n which stores the index of bin, where each item is stored in.
	 */
	public int[] binPackingExact() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		boolean[] isAdded = new boolean[this.items.length];
		for(int i = 0; i < isAdded.length; i++) {
			isAdded[i] = false;
		}
		LinkedList<Bin> bins = new LinkedList<Bin>();
		PriorityQueue<Integer> items = new PriorityQueue<Integer>(this.items.length, Collections.reverseOrder());
		for(int i = 0; i < this.items.length; i++) {
			items.add(this.items[i]);
		}
		bins.add(new Bin(this.m));
		int[] output = new int[this.items.length];
		while(!items.isEmpty()) {
			int nextItem = items.poll();
			int count = 0;
			int itemNo = 0;
			while(!bins.get(count).insert(nextItem)) {
				count++;
				while(bins.size() <= count) {
					bins.add(new Bin(this.m));
				}
			}
			for(int i = 0; i < this.items.length; i++) {
				if((nextItem == this.items[i]) && !(isAdded[i])) {
					itemNo = i;
					isAdded[i] = true;
					break;
				}
			}
			output[itemNo] = count;
		}
		return output;
	}
	
	/**
	 * Uses heuristics (e.g. first-fit, best-fit, ordered first-fit etc.) to
	 * compute the minimal number of bins for the given items. The smaller the
	 * number, the more tests you will pass.
	 * 
	 * @return Array of size n which stores the index of bin, where each item is stored in.
	 */
	public int[] binPackingApproximate() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		return this.binPackingExact();
	}

}
