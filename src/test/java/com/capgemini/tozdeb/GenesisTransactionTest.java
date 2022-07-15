package com.capgemini.tozdeb;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class GenesisTransactionTest {
    GenesisTransaction transaction;

    @Test
    public void testGetTimestamp() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new GenesisTransaction(null,"B", 10);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + 5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetSenderNotNullValueException() {
        transaction = new GenesisTransaction("A","B", 1);

    }

    @Test
    public void testGetSenderNullValue() {
        transaction = new GenesisTransaction(null,"B", 1);
        assertEquals(null,transaction.getSender());
    }

    @Test
    public void testGetRecipient() {
        transaction = new GenesisTransaction(null,"B", 1);
        assertEquals("B",transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullRecipientException() {
        transaction = new GenesisTransaction(null,null, 1);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test
    public void testGetAmount(){
        transaction = new GenesisTransaction(null, "B", 1);
        assertEquals(1,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountWhenZeroPassedToConstructor(){
        transaction = new GenesisTransaction(null, "B", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountNegativeValuePassedToConstructor(){

        transaction = new GenesisTransaction(null, "B", -1);
    }
}