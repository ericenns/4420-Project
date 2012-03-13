package project.dictionary.comparison.tree;

public abstract class Tree {
	
	public abstract boolean search(int key);
	public abstract void insert(int key);
	public abstract void delete(int key);
	public abstract int predecessor(int key);
}
