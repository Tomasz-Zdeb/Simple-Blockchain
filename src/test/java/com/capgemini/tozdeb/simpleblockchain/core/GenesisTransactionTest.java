package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class GenesisTransactionTest {
    final int maxTimestampTimeout = 5,
            mockValueA = 1000,
            mockValueB = 0;
    final String mockUserA = "userA",
            mockUserB = "userB";
    GenesisTransaction transaction;

    @Test
    public void testGetTimestamp() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new GenesisTransaction(null,mockUserB, mockValueA);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetSenderNotNullValueException() {
        transaction = new GenesisTransaction(mockUserA,mockUserB, mockValueA);

    }

    @Test
    public void testGetSenderNullValue() {
        transaction = new GenesisTransaction(null,mockUserA, mockValueA);
        assertNull(transaction.getSender());
    }

    @Test
    public void testGetRecipient() {
        transaction = new GenesisTransaction(null,mockUserA, mockValueA);
        assertEquals(mockUserA,transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullRecipientException() {
        transaction = new GenesisTransaction(null,null, mockValueA);
        //https://stackoverflow.com/questions/1151237/junit-expected-tag-not-working-as-expected
    }

    @Test
    public void testGetAmount(){
        transaction = new GenesisTransaction(null, mockUserB, mockValueA);
        assertEquals(mockValueA,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountWhenZeroPassedToConstructor(){

        transaction = new GenesisTransaction(null, mockUserB,  mockValueB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAmountNegativeValuePassedToConstructor(){
        final int mockIllegalAmount = -1;

        transaction = new GenesisTransaction(null, mockUserB, mockIllegalAmount);
    }
}