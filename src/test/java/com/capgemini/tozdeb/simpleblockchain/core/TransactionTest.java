package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;

public class TransactionTest {
    final int maxTimestampTimeout = 5,
            mockValueA = 1000;
    final String mockUserA = "userA",
            mockUserB = "userB";
    final long mockTimestampA = 12345678;
    Transaction transaction;

    @Test
    public void testGetTimestampNonGenesis() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + maxTimestampTimeout);
    }
    @Test
    public void testGetTimestampGenesis() {
        long timestamp = Instant.now().getEpochSecond();
        transaction = new Transaction(mockUserB, mockValueA);
        assertTrue(transaction.getTimestamp() >= timestamp);
        assertTrue(transaction.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testGetSenderNonGenesis() {
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserA,transaction.getSender());
    }
    @Test
    public void testGetSenderGenesis()
    {
        transaction = new Transaction(mockUserA,mockValueA);

        assertNull(transaction.getSender());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSenderNullValueThrows() {
        transaction = new Transaction(null,mockUserB, mockValueA);
    }

    @Test
    public void testGetRecipientNonGenesis() {
        transaction = new Transaction(mockUserA,mockUserB, mockValueA);
        assertEquals(mockUserB,transaction.getRecipient());
    }

    @Test
    public void testGetRecipientGenesis() {
        transaction = new Transaction(mockUserB, mockValueA);
        assertEquals(mockUserB,transaction.getRecipient());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecipientNullValueThrowsNonGenesis() {
        transaction = new Transaction(mockUserA,null, mockValueA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecipientNullValueThrowsGenesis() {
        transaction = new Transaction(null, mockValueA);
    }

    @Test
    public void testGetAmountNonGenesis(){
        transaction = new Transaction(mockUserA, mockUserB, mockValueA);
        assertEquals(mockValueA,transaction.getAmount());
    }

    @Test
    public void testGetAmountGenesis(){
        transaction = new Transaction(mockUserB, mockValueA);
        assertEquals(mockValueA,transaction.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAmountZeroValueThrowsNonGenesis(){
        final int mockIllegalAmount = 0;

        transaction = new Transaction(mockUserA, mockUserB, mockIllegalAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAmountZeroValueThrowsGenesis(){
        final int mockIllegalAmount = 0;

        transaction = new Transaction(mockUserB, mockIllegalAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAmountNegativeValueThrowsNonGenesis(){
        final int mockIllegalAmount = -1;

        transaction = new Transaction(mockUserA, mockUserB, mockIllegalAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAmountNegativeValueThrowsGenesis(){
        final int mockIllegalAmount = -1;

        transaction = new Transaction(mockUserB, mockIllegalAmount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSenderEqualToRecipientThrows(){
        transaction = new Transaction(mockUserA,mockUserA,mockValueA);
    }

    @Test
    public void testGenerateTransactionString(){
        transaction = new Transaction(mockUserA,mockUserB,mockValueA);
        assertEquals(transaction.getTimestamp()+transaction.getSender()+transaction.getRecipient()+transaction.getAmount(),transaction.generateTransactionString());
    }

    @Test
    public void testValueOfSameReferences(){
        transaction = new Transaction(mockUserA,mockUserB,mockValueA);
    }

    @Test
    public void testValueOfSameValueDifferentReferences(){
        transaction = new Transaction(mockTimestampA,mockUserA,mockUserB,mockValueA);

        assertTrue(transaction.valueOf(new Transaction(mockTimestampA,mockUserA,mockUserB,mockValueA)));
    }

    @Test
    public void testValueOfSameValueDifferentReferencesGenesis(){
        transaction = new Transaction(mockTimestampA,mockUserA,mockValueA);

        assertTrue(transaction.valueOf(new Transaction(mockTimestampA,mockUserA,mockValueA)));
    }

    @Test
    public void TestValueOfDifferentValues(){
        transaction = new Transaction(mockTimestampA,mockUserA,mockUserB,mockValueA);

        assertFalse(transaction.valueOf(new Transaction(mockTimestampA,mockUserB,mockUserA,mockValueA)));
    }
}