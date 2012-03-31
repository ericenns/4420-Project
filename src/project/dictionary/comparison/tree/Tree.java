package project.dictionary.comparison.tree;

public abstract class Tree {
	
	public abstract int search(int key);
	public abstract int insert(int key);
	public abstract int delete(int key);
	public abstract int predecessor(int key);
}
