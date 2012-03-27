package project.dictionary.comparison.bitwisetrie;

import project.dictionary.comparison.tree.Node;
import project.dictionary.comparison.tree.Tree;

public class BitwiseTrie extends Tree 
{
	Node root;
	int numLevels; // number of levels in the trie
	
	public BitwiseTrie( int u )
	{
		root = new Node("-1", null, null, null);
		
		numLevels = (int)Math.ceil(Math.log(u)/Math.log(2));
	}

	public void print()
	{
		printRec(root);
	}
	
	public void printRec(Node currNode)
	{
		if(currNode.getChild(true) != null)
			printRec( currNode.getChild(true) );
		
		System.out.println(currNode.getKey());
		
		if(currNode.getChild(false) != null)
			printRec( currNode.getChild(false) );
		
	}
	
	public boolean search( int key )
	{
		String keyString = calcKeyBitString(key);
		
		boolean found = false;
		Node currNode = root;
		
		for(int i = 0; i < keyString.length() && currNode != null; i++)
		{
			currNode = currNode.getChild(keyString.charAt(i) == '0');
		}
		
		if( currNode != null )
		{
			found = true;
		}
		
		return found;
	}
	
	public void insert( int key )
	{
		String keyString = calcKeyBitString(key);
		
		Node currNode = root;
		
		for( int i = 0; i < keyString.length(); i++ )
		{
			if( currNode.getChild(keyString.charAt(i) == '0') == null )
			{	
				Node childNode = new Node(keyString.substring(0,i + 1), currNode, null, null);
				currNode.setChild(childNode, keyString.charAt(i) == '0');
			}
			currNode = currNode.getChild(keyString.charAt(i) == '0');
		}
	}
	
	public void delete( int key )
	{
		String keyString = calcKeyBitString(key);
		
		Node currNode = root;
		
		// Find the node to be deleted, if it exists
		for(int i = 0; i < keyString.length() && currNode != null; i++)
			currNode = currNode.getChild(keyString.charAt(i) == '0');
		
		if( currNode != null )
		{
			while(currNode.getParent().getNumChildren() == 1 && currNode.getParent() != root)
				currNode = currNode.getParent();

			currNode.getParent().setChild(null, currNode.getKey().charAt(currNode.getKey().length()-1) == '0');
		}
	}
	
	public String predecessor( int key )
	{
		String keyString = calcKeyBitString(key);
		String returnVal = "NULL";
		
		boolean found = false;
		Node currNode = root;
		
		// Search for the key
		for(int i = 0; (i < keyString.length()) && (currNode.getChild(keyString.charAt(i) == '0') != null); i++)
		{
			currNode = currNode.getChild(keyString.charAt(i) == '0');
		}

		if( currNode.getKey().equals(keyString))
			returnVal = keyString;
		else
		{
			Node prevNode = null;
			boolean finished = false;
			
			// determine common ancestor for the predecessor and the prevNode
			while( !finished )
			{
				if( currNode.getChild(true) != null && currNode.getChild(true) != prevNode )
				{
					currNode = currNode.getChild(true);
					finished = true;
					
					// find the max in this sub-tree
					boolean foundPred = false;
					
					while( !foundPred )
					{
						if( currNode.getChild(false) != null )
							currNode = currNode.getChild(false);
						else if( currNode.getChild(true) != null )
							currNode = currNode.getChild(true);
						else
						{
							foundPred = true;
							returnVal = currNode.getKey();
						}
					}
				}
				else
				{
					if( currNode != root )
					{
						prevNode = currNode;
						currNode = currNode.getParent();
					}
					else
						finished = true;
				}
			}
		}
		
		return returnVal;
	}
	
	private String calcKeyBitString( int key )
	{
		String keyString = Integer.toBinaryString(key);

		String temp = "";
		for(int i = 0; i < numLevels - keyString.length(); i++)
		{
			temp = temp + "0";
		}

		keyString = temp + keyString;
		
		return keyString;
	}
}
