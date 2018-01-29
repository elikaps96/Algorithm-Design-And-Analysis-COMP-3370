package edu.wit.cs.comp3370;

import java.util.ArrayList;

/* Implements a trie data structure 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 6 solution
 * Eli Kapetanopoulos
 * 6/21/17
 */

public class Trie extends Speller {

	private TrieNode root;

	// Constructor
	public Trie() {
		root = new TrieNode();
	}

	// Insert into Trie
	@Override
	public void insertWord(String s) {
		// TODO Implement this method

		TrieNode x = root;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int index = c - 'a';
			if (x.letters[index] == null) {
				TrieNode temp = new TrieNode();
				x.letters[index] = temp;
				x = temp;
			} else {
				x = x.letters[index];
			}
		}
		x.isEnd = true;

	}

	// Calls searchNode for String
	@Override
	public boolean contains(String s) {
		// TODO Implement this method

		TrieNode x = searchNode(s);
		if (x == null) {
			return false;
		} else {
			if (x.isEnd)
				return true;
		}

		return false;
	}

	// Searches Trie for each character in the passed String.
	public TrieNode searchNode(String s) {
		TrieNode x = root;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int index = c - 'a';
			if (x.letters[index] != null) {
				x = x.letters[index];
			} else {
				return null;
			}
		}

		if (x == root)
			return null;

		return x;
	}

	// Get Suggestions.
	@Override
	public String[] getSugg(String s) {
		// TODO Implement this method

		ArrayList<String> suggestions = new ArrayList<String>();
		int editDistance = 2;

		findletters(s, suggestions, 0, editDistance);

		return suggestions.toArray(new String[0]);
	}

	// Find letters.
	private void findletters(String s, ArrayList<String> suggestions, int strPos, int editDistance) {
		if (strPos == s.length()) {
			if (this.contains(s))
				if (!suggestions.contains(s))
					suggestions.add(s);
			return;
		}

		for (int i = 0; i < 26; i++) {
			
			if (s.charAt(strPos) == 'a' + i)
				findletters(s, suggestions, strPos + 1, editDistance);
			else if (editDistance > 0)
				findletters(s.substring(0, strPos) + (char) ('a' + i) + s.substring(strPos + 1, s.length()),
						suggestions, strPos + 1, editDistance - 1);
		}
	}

}
