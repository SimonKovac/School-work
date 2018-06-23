	package psa.naloga1;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the (unbalanced) Binary Search Tree set node.
 */
public class BSTSetNode {
	private static int counter;
	private BSTSetNode left, right, parent;
	private int key;

	public BSTSetNode(BSTSetNode l, BSTSetNode r, BSTSetNode p,
			int key) {
		super();
		this.left = l;
		this.right = r;
		this.parent = p;
		this.key = key;
	}

	public BSTSetNode getLeft() {
		return left;
	}

	public void setLeft(BSTSetNode left) {
		this.left = left;
	}

	public BSTSetNode getRight() {
		return right;
	}

	public void setRight(BSTSetNode right) {
		this.right = right;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int compare(BSTSetNode node) {
		counter++;
		return node.key-this.key;
	}

	public int getCounter() {
		return counter;
	}

	public void resetCounter() {
		counter = 0;
	}
	
	public int getHeight() {
		int lh = this.getLeft() != null ? this.getLeft().getHeight() : 0;
		int rh = this.getRight() != null ? this.getRight().getHeight() : 0;
		if (lh > rh) return lh+1;
		else return rh + 1;
	}

	/**
	 * If the element doesn't exist yet, adds the given element to the subtree.
	 * 
	 * @param element Given key wrapped inside an empty BSTNode instance
	 * @return true, if the element was added; false otherwise.
	 */
	public boolean add(BSTSetNode element) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int razlika = this.compare(element);
		if(razlika > 0) {
			if(this.getRight() == null) {
				this.setRight(element);
				this.getRight().parent = this;
			} else {
				this.getRight().add(element);
			}
		} else if(razlika < 0) {
			if(this.getLeft() == null) {
				this.setLeft(element);
				this.getLeft().parent = this;
			} else {
				this.getLeft().add(element);
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Finds and removes the element with the given key from the subtree.
	 * 
	 * @param element Given key wrapped inside an empty BSTNode instance
	 * @return true, if the element was found and removed; false otherwise.
	 */
	public boolean remove(BSTSetNode element) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int razlika = this.compare(element);
		if(razlika < 0) {
			if(this.getLeft() == null) {
				return false;
			}
			return this.getLeft().remove(element);
		} else if(razlika > 0){
			if(this.getRight() == null) {
				return false;
			}
			return this.getRight().remove(element);
		} else {
			if ((this.getLeft() == null) && (this.getRight() == null)) {
				if (this.parent.getLeft() == this) {
					this.parent.setLeft(null);
				} else {
					this.parent.setRight(null);
				}
			} else if ((this.getLeft() == null) || (this.getRight() == null)) {
				if (this.parent.getLeft() == this) {
					this.parent.setLeft((this.getLeft() == null ? this.getRight() : this.getLeft()));
					this.parent.getLeft().parent = this.parent;
				} else {
					this.parent.setRight((this.getLeft() == null ? this.getRight() : this.getLeft()));
					this.parent.getRight().parent = this.parent;
				}
			} else {
				BSTSetNode temp = this.getRight().findMin();
				int tempKey = temp.getKey();
				this.getRight().remove(temp);
				this.setKey(tempKey);
			}
			return true;
		}
	}

	/**
	 * Checks whether the element with the given key exists in the subtree.
	 * 
	 * @param element Query key wrapped inside an empty BSTNode instance
	 * @return true, if the element is contained in the subtree; false otherwise.
	 */
	public boolean contains(BSTSetNode element) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int razlika = this.compare(element);
		if(razlika < 0) {
			if(this.getLeft() == null) {
				return false;
			}
			return this.getLeft().contains(element);
		} else if(razlika > 0){
			if(this.getRight() == null) {
				return false;
			}
			return this.getRight().contains(element);
		} else {
			return true;
		}
	}

	/**
	 * Finds the smallest element in the subtree.
	 * This function is in some cases used during
	 * the removal of the element.
	 * 
	 * @return Smallest element in the subtree
	 */
	public BSTSetNode findMin() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if(this.getLeft() == null) {
			return this;
		} else {
			return this.getLeft().findMin();
		}
	}
	
	/**
	 * Depth-first pre-order traversal of the BST.
	 * 
	 * @return List of elements stored in BST obtained by pre-order traversing the tree.
	 */
	List<Integer> traversePreOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		List<Integer> order = new LinkedList<Integer>();
		order.add(this.getKey());
		if(this.getLeft() != null) order.addAll(this.getLeft().traversePreOrder());
		if(this.getRight() != null) order.addAll(this.getRight().traversePreOrder());
		return order;
	}

	/**
	 * Depth-first in-order traversal of the BST.
	 * 
	 * @return List of elements stored in BST obtained by in-order traversing the tree.
	 */
	List<Integer> traverseInOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		List<Integer> order = new LinkedList<Integer>();
		if(this.getLeft() != null) order.addAll(this.getLeft().traverseInOrder());
		order.add(this.getKey());
		if(this.getRight() != null) order.addAll(this.getRight().traverseInOrder());
		return order;
	}

	/**
	 * Depth-first post-order traversal of the BST.
	 * 
	 * @return List of elements stored in BST obtained by post-order traversing the tree.
	 */
	List<Integer> traversePostOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		List<Integer> order = new LinkedList<Integer>();
		if(this.getLeft() != null) order.addAll(this.getLeft().traversePostOrder());
		if(this.getRight() != null) order.addAll(this.getRight().traversePostOrder());
		order.add(this.getKey());
		return order;
	}
	
	/**
	 * Breadth-first (or level-order) traversal of the BST.
	 * 
	 * @return List of elements stored in BST obtained by breadth-first traversal of the tree.
	 */
	List<Integer> traverseLevelOrder() {
		//throw new UnsupportedOperationException("You need to implement this function!");
		int h = this.getHeight();
		int i;
		List<Integer> order = new LinkedList<Integer>();
		for(i = 1; i<=h; i++) {
			order.addAll(this.getGivenLevel(i));
		}
		return order;
	}
	
	List<Integer> getGivenLevel(int level){
		List<Integer> order = new LinkedList<Integer>();
		if (level == 1) order.add(this.getKey());
		else if (level > 1) {
			if (this.getLeft() != null) order.addAll(this.getLeft().getGivenLevel(level-1));
			if (this.getRight() != null) order.addAll(this.getRight().getGivenLevel(level -1));
		}
		return order;
	}
}
