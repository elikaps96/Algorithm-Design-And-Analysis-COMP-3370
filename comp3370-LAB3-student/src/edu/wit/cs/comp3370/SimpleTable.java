package edu.wit.cs.comp3370;

public class SimpleTable extends HashTable {

	public SimpleTable(int size) {
		super(size);
	}

	@Override
	//Uses a multiplier to smear bits when hashing.
	public int calculateHash(String word) {
		// TODO: implement hash from slides that uses multiplier
		int hash = 0;
		int multiplier = 31;
		for (int i = 0; i < word.length(); i++) {

			hash = ((hash * multiplier) + word.charAt(i)) % tableSize;
		}
		return hash;
	}

}
