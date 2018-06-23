package aps2.bstmap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the (unbalanced) Binary Search Tree set node.
 */
public class BSTMapNode {
	private static int counter;
	private BSTMapNode left, right, parent;
	private int key;
	private String value;

	public BSTMapNode(BSTMapNode l, BSTMapNode r, BSTMapNode p,
			int key, String value) {
		super();
		this.left = l;
		this.right = r;
		this.parent = p;
		this.key = key;
		this.value = value;
	}

	public BSTMapNode getLeft() {
		return left;
	}

	public void setLeft(BSTMapNode left) {
		this.left = left;
	}

	public BSTMapNode getRight() {
		return right;
	}

	public void setRight(BSTMapNode right) {
		this.right = right;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	public int compare(BSTMapNode node) {
		counter++;
		return node.key-this.key;
	}

	public int getCounter() {
		return counter;
	}

	public void resetCounter() {
		counter = 0;
	}

	/**
	 * If the element doesn't exist yet, adds the given element to the subtree.
	 * 
	 * @param element Given key/value wrapped inside an empty BSTNode instance
	 * @return true, if the element was added; false otherwise.
	 */
	public boolean add(BSTMapNode element) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if(this.contains(element)) return false;
		if(this.compare(element) < 0){
			if(this.getLeft() == null) this.setLeft(element);
			else this.getLeft().add(element);
		}else{
			if(this.getRight() == null) this.setRight(element);
			else this.getRight().add(element);
		}
		return true;
	}
	
	/**
	 * Finds and removes the element with the given key from the subtree.
	 * 
	 * @param element Given key wrapped inside an empty BSTNode instance
	 * @return true, if the element was found and removed; false otherwise.
	 */
	public boolean remove(BSTMapNode element) {
		throw new UnsupportedOperationException("You need to implement this function!");
	}

	/**
	 * Checks whether the element with the given key exists in the subtree.
	 * 
	 * @param element Query key wrapped inside an empty BSTNode instance
	 * @return true, if an element with the given key is contained in the subtree; false otherwise.
	 */
	public boolean contains(BSTMapNode element) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int razlika = this.compare(element);
		if(razlika == 0) return true;
		else if(razlika < 0){
			if(this.getLeft() == null) return false;
			else return this.getLeft().contains(element);
		}
		else{
			if(this.getRight() == null) return false;
			else return this.getRight().contains(element);
		}
	}
	
	/**
	 * Maps the given key to its value.
	 * 
	 * @param element Query key wrapped inside an empty BSTNode instance
	 * @return String value of the given key; null, if an element with the given key does not exist in the subtree.
	 */
	public String get(BSTMapNode element) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int razlika = this.compare(element);
		if(razlika == 0) return this.getValue();
		else if(razlika < 0){
			if(this.getLeft() == null) return null;
			else return this.getLeft().get(element);
		}
		else{
			if(this.getRight() == null) return null;
			else return this.getRight().get(element);
		}
	}

	/**
	 * Finds the smallest element in the subtree.
	 * 
	 * @return Smallest element in the subtree
	 */
	public BSTMapNode findMin() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if(this.getLeft() == null) return this;
		else{
			return this.getLeft().findMin();
		}
	}
	
	/**
	 * Depth-first pre-order traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by pre-order traversing the tree.
	 */
	List<Integer> traversePreOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		List<Integer> output = new ArrayList<Integer>();
		output.add(this.getKey());
		if(this.getLeft() != null) output.addAll(this.getLeft().traversePreOrder());
		if(this.getRight() != null) output.addAll(this.getRight().traversePreOrder());
		return output;
	}

	/**
	 * Depth-first in-order traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by in-order traversing the tree.
	 */
	List<Integer> traverseInOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		List<Integer> output = new ArrayList<Integer>();
		if(this.getLeft() != null) output.addAll(this.getLeft().traverseInOrder());
		output.add(this.getKey());
		if(this.getRight() != null) output.addAll(this.getRight().traverseInOrder());
		return output;
	}

	/**
	 * Depth-first post-order traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by post-order traversing the tree.
	 */
	List<Integer> traversePostOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		List<Integer> output = new ArrayList<Integer>();
		if(this.getLeft() != null) output.addAll(this.getLeft().traversePostOrder());
		if(this.getRight() != null) output.addAll(this.getRight().traversePostOrder());
		output.add(this.getKey());
		return output;
	}

	/**
	 * Breadth-first (or level-order) traversal of the BST.
	 * 
	 * @return List of keys stored in BST obtained by breadth-first traversal of the tree.
	 */
	List<Integer> traverseLevelOrder() {
		throw new UnsupportedOperationException("You need to implement this function!");
	}
}
