package com.capgemini.tozdeb.simpleblockchain.core;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.Instant;

public class BlockTest {
    final int sha256HashLength = 64,
            maxTimestampTimeout = 5,
            mockValueA = 0,
            mockValueB=100,
            mockValueC = 10,
            mockValueD= 8;
    final String mockHash = "ABC-DEF-GHI",
    mockUserA = "userA",
    mockUserB = "userB",
    mockUserC = "userC",
    mockUserD = "userD";
    Block block;
    @Test
    public void testBlockCreationWithHashProvidedDoesNotThrow() {
        try{
            block = new Block(mockHash);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void testBlockCreationWithNullPreviousBlockHashProvidedDoesNotThrow() {
        try{
            block = new Block(null);
        }
        catch(Exception e) {
            fail();
        }
    }

    @Test
    public void testBlockGetTimestampWithPreviousBlockHash() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block(mockHash);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testBlockGetTimestampWithNullPreviousBlockHash() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block(null);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testNewBlockNumberOfTransactionsIsZeroWithPreviousBlockHash() {
        final int expectedNumberOfTransactions = 0;

        block = new Block(mockHash);
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test
    public void testNewBlockNumberOfTransactionsIsZeroWithNullBlockHash() {
        final int expectedNumberOfTransactions = 0;

        block = new Block(null);
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test
    public void testNewBlockTransactionHashIsNullWithBlockHash() {
        block = new Block(mockHash);
        assertNull(block.getTransactionsHash());
    }

    @Test
    public void testNewBlockTransactionHashIsNullWithNullBlockHash() {
        block = new Block(null);
        assertNull(block.getTransactionsHash());
    }

    @Test
    public void testPreviousBlockHashIsNullWithBlockHash() {
        block = new Block(mockHash);
        assertEquals(mockHash,block.getPreviousBlockHash());
    }

    @Test
    public void testPreviousBlockHashIsNullWithNullBlockHash() {
        block = new Block(null);
        assertNull(block.getPreviousBlockHash());
    }

    @Test
    public void testNewBlocksTransactionListIsNullWithBlockHash() {
        block = new Block(mockHash);
        for (Transaction transaction : block.transactions)
        {
            assertNull(transaction);
        }
    }

    @Test
    public void testNewBlocksTransactionListIsNullWithNullBlockHash() {
        block = new Block(null);
        for (Transaction transaction : block.transactions)
        {
            assertNull(transaction);
        }
    }

    @Test
    public void testTransactionIsAddedProperlyWithPreviousBlockHash(){
        block = new Block(mockHash);
        block.addTransaction(new Transaction(mockUserA,mockUserB,mockValueA));
        Transaction[] transactions = block.getTransactions();

        assertEquals(mockUserA, transactions[0].getSender());
        assertEquals(mockUserB, transactions[0].getRecipient());
        assertEquals(mockValueA, transactions[0].getAmount());

        for(int i = 1; i<transactions.length;i++)
        {
            assertNull(transactions[i]);
        }
    }

    @Test
    public void testTransactionIsAddedProperlyWithNullPreviousBlockHash(){
        block = new Block(null);
        block.addTransaction(new Transaction(mockUserA,mockUserB,mockValueA));
        Transaction[] transactions = block.getTransactions();

        assertEquals(mockUserA, transactions[0].getSender());
        assertEquals(mockUserB, transactions[0].getRecipient());
        assertEquals(mockValueA, transactions[0].getAmount());

        for(int i = 1; i<transactions.length;i++)
        {
            assertNull(transactions[i]);
        }
    }

    @Test
    public void testAddingThreeTransactionsResultInCounterIncrement(){
        block = new Block(mockHash);

        final int expectedNumberOfTransactions = 3;

        block.addTransaction(new Transaction(mockUserA, mockUserB,mockValueB));
        block.addTransaction(new Transaction(mockUserB, mockUserC,mockValueC));
        block.addTransaction(new Transaction(mockUserA, mockUserD,mockValueD));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }
    @Test
    public void testAddingOneTransactionsResultInCounterIncrement() {
        block = new Block(mockHash);

        final int expectedNumberOfTransactions = 1;

        block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test
    public void testAddingATransactionToFullBlockReturnsFalse()
    {
        block = new Block(mockHash);

        for (int i=0;i<block.transactions.length;i++)
        {
            block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB));
        }
        assertFalse(block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullTransactionThrows()
    {
        block = new Block(mockHash);
        block.addTransaction(null);
    }

    @Test
    public void testIsTransactionsHashValid() {
        block = new Block(mockHash);

        block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB));
        assertEquals(sha256HashLength,block.getTransactionsHash().length());
    }
}