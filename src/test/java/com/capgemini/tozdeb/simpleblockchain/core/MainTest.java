package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void testNoExceptionPass() {
        boolean result = true;
        try{
            Main.main(null);
        }
        catch(Exception e)
        {
            result = false;
        }
        assertTrue(result);
    }
}
//https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected