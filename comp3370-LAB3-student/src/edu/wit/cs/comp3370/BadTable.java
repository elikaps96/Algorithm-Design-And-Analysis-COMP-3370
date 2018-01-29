package edu.wit.cs.comp3370;

public class BadTable extends HashTable {

	public BadTable(int size) {
		super(size);
	}

	@Override
	public int calculateHash(String word) {
		return word.charAt(0) % tableSize;
	}

}
