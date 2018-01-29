package edu.wit.cs.comp3370;

class TrieNode {
	TrieNode[] letters;
	boolean isEnd;

	public TrieNode() {
		this.letters = new TrieNode[26];
	}
}