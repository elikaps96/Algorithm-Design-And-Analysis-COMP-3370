package edu.wit.cs.comp3370.tests;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.Permission;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import edu.wit.cs.comp3370.HashTable;
import edu.wit.cs.comp3370.LAB3;
import edu.wit.cs.comp3370.RandTable;
import edu.wit.cs.comp3370.SimpleTable;

public class LAB3TestCase{
	

	@Rule
	public Timeout globalTimeout = Timeout.seconds(15);
	
	@SuppressWarnings("serial")
	private static class ExitException extends SecurityException {}
	
	private static class NoExitSecurityManager extends SecurityManager 
    {
        @Override
        public void checkPermission(Permission perm) {}
        
        @Override
        public void checkPermission(Permission perm, Object context) {}
        
        @Override
        public void checkExit(int status) { super.checkExit(status); throw new ExitException(); }
    }
	
	@Before
    public void setUp() throws Exception 
    {
        System.setSecurityManager(new NoExitSecurityManager());
    }
	
	@After
    public void tearDown() throws Exception 
    {
        System.setSecurityManager(null);
    }
	
	private void _testHash(HashTable h, int wordcount, String fileName) {
		PrintStream stdout = System.out;
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		try {
			LAB3.buildTable(h, fileName);
			LAB3.printBabble(h, wordcount);
		} catch (ExitException e) {}
		assertEquals(outContent.toString().split(" ").length, wordcount);
		
		System.setOut(stdout);
	}
	
	
	private void _testStDev(HashTable h, double max, String fileName) {
		double result = Double.MAX_VALUE;
		try {
			LAB3.buildTable(h, fileName);
			result = h.getStDev();
		} catch (ExitException e) {}
		assertTrue("Bucket distribution is not even enough.", max > result);
		
	}

	@Test
	public void testSimple() {
		
		HashTable h = new SimpleTable(256);
		_testHash(h, 5, "text/dickens.txt");
		
		h = new SimpleTable(256);
		_testHash(h, 100, "text/dickens.txt");
		
		h = new SimpleTable(256);
		_testHash(h, 4000, "text/dickens.txt");
	}

	@Test
	public void testRand() {
		
		HashTable h = new RandTable(256);
		_testHash(h, 5, "text/dickens.txt");
		
		h = new RandTable(256);
		_testHash(h, 100, "text/dickens.txt");
		
		h = new RandTable(256);
		_testHash(h, 4000, "text/dickens.txt");
	}
	
	@Test
	public void testSimpleStDevGood() {
		HashTable h = new SimpleTable(256);
		_testStDev(h, 50, "text/wallpaper.txt");
		
		h = new SimpleTable(256);
		_testStDev(h, 200, "text/dickens.txt");
	}	
	
	@Test
	public void testRandStDevGood() {
		HashTable h = new RandTable(256);
		_testStDev(h, 50, "text/wallpaper.txt");
		
		h = new RandTable(256);
		_testStDev(h, 200, "text/dickens.txt");
	}
	
	@Test
	public void testSimpleStDevOk() {
		HashTable h = new SimpleTable(256);
		_testStDev(h, 80, "text/wallpaper.txt");
		
		h = new SimpleTable(256);
		_testStDev(h, 250, "text/dickens.txt");
	}	
	
	@Test
	public void testRandStDevOk() {
		HashTable h = new RandTable(256);
		_testStDev(h, 80, "text/wallpaper.txt");
		
		h = new RandTable(256);
		_testStDev(h, 250, "text/dickens.txt");
	}


}
