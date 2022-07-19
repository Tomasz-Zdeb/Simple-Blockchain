package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class TransactionTest {
    final int maxTimestampTimeout = 5,
            mockValueA = 1000;
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
    public void testGetSender() {
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserA,transaction.getSender());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSenderNullValueThrows() {
        transaction = new Transaction(null,mockUserB, mockValueA);
    }

    @Test
    public void testGetRecipient() {
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserB,transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecipientNullValueThrows() {
        transaction = new Transaction(mockUserA,null, mockValueA);
    }

    @Test
    public void testGetAmount(){
        transaction = new Transaction(mockUserA, mockUserB, mockValueA);
        assertEquals(mockValueA,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAmountZeroValueThrows(){
        final int mockIllegalAmount = 0;

        transaction = new Transaction(mockUserA, mockUserB, mockIllegalAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAmountNegativeValueThrows(){
        final int mockIllegalAmount = -1;

        transaction = new Transaction(mockUserA, mockUserB, mockIllegalAmount);
    }

    @Test
    public void testGetGenesisSender()
    {
        transaction = new Transaction(mockUserA,mockValueA);

        assertNull(transaction.getSender());
    }
}