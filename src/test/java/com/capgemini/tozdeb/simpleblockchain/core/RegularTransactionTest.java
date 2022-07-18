package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class RegularTransactionTest {
    final int maxTimestampTimeout = 5,
            expectedDefaultAmount = 0,
            mockValueA = 1000,
            mockValueB = 0;
    final String mockUserA = "userA",
            mockUserB = "userB";
    RegularTransaction transaction;

    @Test
    public void testGetTimestamp() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new RegularTransaction(mockUserA,mockUserB, mockValueA);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testGetSenderNotNullValue() {
        transaction = new RegularTransaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserA,transaction.getSender());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSenderNullValueExceptionWithAmount() {

        transaction = new RegularTransaction(null,mockUserB, mockValueA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSenderNullValueExceptionWithoutAmount() {

        transaction = new RegularTransaction(null,mockUserB);
    }

    @Test
    public void testGetRecipientNotNullValue() {
        transaction = new RegularTransaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserB,transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullRecipientException() {
        transaction = new RegularTransaction(mockUserA,null, mockValueA);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoAmountConstructorNullRecipientException() {
        transaction = new RegularTransaction(mockUserA,null);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test
    public void testGetAmountWhenValuePassedToConstructor(){
        transaction = new RegularTransaction(mockUserA, mockUserB, mockValueA);
        assertEquals(mockValueA,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenValueNotPassedToConstructor(){
        transaction = new RegularTransaction(mockUserA, mockUserB);
        assertEquals(expectedDefaultAmount,transaction.getAmount());
    }

    @Test
    public void testGetAmountWhenZeroPassedToConstructor(){
        transaction = new RegularTransaction(mockUserA, mockUserB, mockValueB);
        assertEquals(mockValueB,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountNegativeValuePassedToConstructor(){
        final int mockIllegalAmount = -1;

        transaction = new RegularTransaction(mockUserA, mockUserB, mockIllegalAmount);
    }
}