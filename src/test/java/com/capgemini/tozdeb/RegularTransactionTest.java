package com.capgemini.tozdeb;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class RegularTransactionTest {
    RegularTransaction transaction;

    @Test
    public void testGetTimestamp() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new RegularTransaction("A","B", 10);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + 5);
    }

    @Test
    public void testGetSenderNotNullValue() {
        transaction = new RegularTransaction("A","B", 1);
        assertEquals("A",transaction.getSender());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSenderNullValueExceptionWithAmount() {
        transaction = new RegularTransaction(null,"B", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSenderNullValueExceptionWithoutAmount() {
        transaction = new RegularTransaction(null,"B");
    }

    @Test
    public void testGetRecipientNotNullValue() {
        transaction = new RegularTransaction("A","B", 1);
        assertEquals("B",transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullRecipientException() {
        transaction = new RegularTransaction("A",null, 1);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoAmountConstructorNullRecipientException() {
        transaction = new RegularTransaction("A",null);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test
    public void testGetAmountWhenValuePassedToConstructor(){
        transaction = new RegularTransaction("A", "B", 1);
        assertEquals(1,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenValueNotPassedToConstructor(){
        transaction = new RegularTransaction("A", "B");
        assertEquals(0,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenZeroPassedToConstructor(){
        transaction = new RegularTransaction("A", "B", 0);
        assertEquals(0,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountNegativeValuePassedToConstructor(){

        transaction = new RegularTransaction("A", "B", -1);
    }
}