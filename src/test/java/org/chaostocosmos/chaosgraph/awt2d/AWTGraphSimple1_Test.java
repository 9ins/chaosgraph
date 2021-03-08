package org.chaostocosmos.chaosgraph.awt2d;

import static org.junit.Assert.*;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AWTGraphSimple1_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAWTGraphSimple1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        new AWTGraphSimple1();
	}
}
