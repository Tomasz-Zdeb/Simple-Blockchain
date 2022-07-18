package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class TransactionTest {
    final int expectedDefaultValue = 0,
            maxTimestampTimeout = 5,
            mockValueA = 1000,
            mockValueB = 0;
    final String mockUserA = "userA",
            mockUserB = "userB";
    Transaction transaction;

    @Test
    public void testGetTimestamp() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testGetSenderNotNullValue() {
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserA,transaction.getSender());
    }

    @Test
    public void testGetSenderNullValue() {
        transaction = new Transaction(null,mockUserB, mockValueA);
        assertNull(transaction.getSender());
    }

    @Test
    public void testGetRecipientNotNullValue() {
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserB,transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullRecipientException() {
        transaction = new Transaction(mockUserA,null, mockValueA);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoAmountConstructorNullRecipientException() {
        transaction = new Transaction(mockUserA,null);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test
    public void testGetAmountWhenValuePassedToConstructor(){
        transaction = new Transaction(mockUserA, mockUserB, mockValueA);
        assertEquals(mockValueA,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenValueNotPassedToConstructor(){
        transaction = new Transaction(mockUserA, mockUserB);
        assertEquals(expectedDefaultValue,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenZeroPassedToConstructor(){
        transaction = new Transaction(mockUserA, mockUserB, mockValueB);
        assertEquals(mockValueB,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountNegativeValuePassedToConstructor(){
        final int mockIllegalAmount = -1;

        transaction = new Transaction(mockUserA, mockUserB, mockIllegalAmount);
    }
}