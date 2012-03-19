package project.dictionary.comparison.tree;

public class LeafNode extends Node {

	private int leafKey;
	
	public LeafNode(int key, int leafKey) {
		super(key);
		this.leafKey = leafKey;
	}
	
	public LeafNode(int key, int leafKey, Node parent, Node leftChild, Node rightChild) {
		super(key, parent, leftChild, rightChild);
		this.leafKey = leafKey;
	}
	
	public int getLeafKey()
	{
		return leafKey;
	}

}
