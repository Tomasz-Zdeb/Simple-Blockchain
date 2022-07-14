package com.capgemini;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class TransactionTest {
    Transaction transaction;

    @Test
    public void testGetTimestamp() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new Transaction("A","B", 10);
        transaction.getTimestamp();
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + 5);
    }

    @Test
    public void testGetSenderNotNullValues() {
        transaction = new Transaction("A","B", 1);
        assertEquals("A",transaction.getSender());
    }

    @Test
    public void testGetSenderNullValues() {
        transaction = new Transaction(null,"B", 1);
        assertEquals(null,transaction.getSender());
    }

    @Test
    public void testGetRecipientNotNullValues() {
        transaction = new Transaction("A","B", 1);
        assertEquals("B",transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullRecipient() {
        transaction = new Transaction("A",null, 1);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test
    public void testGetAmountWhenValuePassedToConstructor(){
        transaction = new Transaction("A", "B", 1);
        assertEquals(1,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenValueNotPassedToConstructor(){
        transaction = new Transaction("A", "B");
        assertEquals(0,transaction.getAmount());
    }
}