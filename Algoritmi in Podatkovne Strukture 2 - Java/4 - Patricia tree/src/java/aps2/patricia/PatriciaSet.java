package aps2.patricia;

import java.util.Stack;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author matevz
 *
 */
public class PatriciaSet {
	PatriciaSetNode root;
	
	PatriciaSet() {
		this.root = new PatriciaSetNode("", false);
	}
	
	public PatriciaSetNode getRoot() {
		return root;
	}
	
	/**
	 * Inserts the given key to PATRICIA tree. Returns false, if such a key
	 * already exists in the tree; otherwise true.
	 */
	public boolean insert(String key) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if (this.contains(key)) return false;
		PatriciaSetNode newKey = new PatriciaSetNode(key, true);
		PatriciaSetNode temp = root.getChild(key.toCharArray()[0]);
		if(temp == null) {
			root.addChild(newKey);
			return true;
		}
		else {
			int razlika = 0;
			if(key.length() > temp.getLabel().length()) {
				for(int i = 0; i < temp.getLabel().length(); i++) {
					if(key.toCharArray()[i] != temp.getLabel().toCharArray()[i]) break;
					razlika++;
				}
				if(razlika == temp.getLabel().length()) {
					if(temp.isTerminal()) {
						temp.setTerminal(false);;
						newKey.setLabel(key.substring(razlika-1));
						temp.addChild(newKey);
						newKey = new PatriciaSetNode(temp.getLabel().substring(razlika-1), true);
						temp.addChild(newKey);
						
						temp.setLabel(temp.getLabel().substring(0, razlika-1));
						return true;
					}
				}
				
			}else {
				for(int i = 0; i < key.length(); i++) {
					if(key.toCharArray()[i] != temp.getLabel().toCharArray()[i]) break;
					razlika++;
				}
				if (razlika == key.length()) {
					if(temp.isTerminal()) {
						temp.setTerminal(false);
						
					}
				}
			}
			return false;
		}
	}
	
	/**
	 * Inserts the given key from PATRICIA tree. Returns false, if a key didn't
	 * exist or wasn't a terminal node; otherwise true.
	 */
	public boolean remove(String key) {
		throw new UnsupportedOperationException("You need to implement this function!");
	}
	
	/**
	 * Returns true, if the given key exists in PATRICIA tree and is a terminal
	 * node; otherwise false.
	 */
	public boolean contains(String key) {
		//throw new UnsupportedOperationException("You need to implement this function!");
		if(root.firstChild == null) return false;
		if((root.firstChild.getLabel() == key ) && (root.firstChild.isTerminal())){
			return true;
		}else {
			PatriciaSetNode temp = root.getChild(key.toCharArray()[0]);
			if(temp == null) return false;
			else {
				int razlika = 0;
				if (temp.getLabel().length() < key.length()){
					for(int i = 0; i < temp.getLabel().length(); i++) {
						if (temp.getLabel().toCharArray()[i] != key.toCharArray()[i]) break;
						razlika++;
					}
					if ((razlika == key.length()) && (key.length() == temp.getLabel().length())) return (true && temp.isTerminal());
					return contains(key.substring(razlika));
				}
				return false;
			}
		}
	}
	
	/**
	 * Returns the longest prefix of the given string which still exists in
	 * PATRICIA tree.
	 */
	public String longestPrefixOf(String s) {
		throw new UnsupportedOperationException("You need to implement this function!");
	}
	
	/**
	 * Returns all stored strings with the given prefix.
	 */
	public Set<String> keysWithPrefix(String s) {
		throw new UnsupportedOperationException("You need to implement this function!");
	}
	
	/**
	 * Returns the node with the largest string-depth which is not a leaf.
	 * String-depth is the sum of label lengths on that root-to-node path.
	 * This node also corresponds to the longest common prefix of at least two
	 * inserted strings.
	 */
	public PatriciaSetNode getLongestPrefixNode() {
		throw new UnsupportedOperationException("You need to implement this function!");
	}

	/**
	 * Computes and returns the longest substring in the given text repeated at
	 * least 2 times by finding the deepest node.
	 */
	public static String longestRepeatedSubstring(String text) {
		throw new UnsupportedOperationException("You need to implement this function!");
	}
}
