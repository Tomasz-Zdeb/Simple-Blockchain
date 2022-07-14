package com.capgemini.tozdeb;

import org.junit.Assert;
import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void NoExceptionPass() {
        boolean result = true;
        try{
            new Main();
            Main.main(null);
        }
        catch(Exception e)
        {
            result = false;
        }
        assertTrue(result);
    }
}