package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;


public class GenesisBlockTest {
    final int sha256HashLength = 64,
            maxTimestampTimeout = 5,
            mockValueA = 1000,
            mockValueB = 100,
            mockValueC = 10,
            mockValueD = 8;
    final String mockInitialUser = "root",
    mockUserA = "userA",
    mockUserB = "userB",
    mockUserC = "userC",
    mockUserD = "userD";
    Block block;
    @Test
    public void testGenesisBlockCreationDoesNotThrow() {
        try{
            block = new GenesisBlock(mockInitialUser,mockValueA);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void testBlockGetTimestampIsValid() {
        long timestamp = Instant.now().getEpochSecond();
        block = new GenesisBlock(mockInitialUser,mockValueA);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testNewGenesisBlockNumberOfTransactionsIsOne() {
        final int expectedNumberOfTransactions = 1;
        block = new GenesisBlock(mockInitialUser,mockValueA);
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }


    @Test
    public void testIsTransactionHashValid() {
        block = new GenesisBlock(mockInitialUser,mockValueA);
        assertEquals(sha256HashLength,block.getTransactionsHash().length());
    }

    @Test
    public void testPreviousTransactionHashIsNull() {
        block = new GenesisBlock(mockInitialUser,mockValueA);
        assertNull(block.getPreviousBlockHash());
    }


    @Test
    public void testNewBlocksTransactionListContainsOneGenesisTransaction() {
        block = new GenesisBlock(mockInitialUser,mockValueA);
        assertNotNull(block.transactions[0].getRecipient());
        assertNull(block.transactions[0].getSender());
        for(int i = 1;i<block.transactions.length;i++){
            assertNull(block.transactions[i]);
        }
    }

    @Test
    public void testTransactionIsAddedProperly(){
        block = new GenesisBlock(mockInitialUser,mockValueA);
        assertTrue(block.addTransaction(new RegularTransaction(mockUserA,mockUserB,mockValueB)));
        Transaction[] transactions = block.getTransactions();

        assertNull(transactions[0].getSender());
        assertSame(mockInitialUser, transactions[0].getRecipient());
        assertEquals(mockValueA, transactions[0].getAmount());

        assertSame(mockUserA, transactions[1].getSender());
        assertSame(mockUserB, transactions[1].getRecipient());
        assertEquals(mockValueB, transactions[1].getAmount());

        for(int i = 2; i<transactions.length;i++)
        {
            assertNull(transactions[i]);
        }
    }

    @Test
    public void testAddingThreeTransactionsResultInCounterIncrement(){
        final int expectedNumberOfTransactions = 4;

        block = new GenesisBlock(mockInitialUser,mockValueA);
        block.addTransaction(new RegularTransaction(mockUserA, mockUserB,mockValueB));
        block.addTransaction(new RegularTransaction(mockUserB, mockUserC,mockValueC));
        block.addTransaction(new RegularTransaction(mockUserA, mockUserD,mockValueD));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }
    @Test
    public void testAddingOneTransactionsResultInCounterIncrement() {
        final int expectedNumberOfTransactions = 2;

        block = new GenesisBlock(mockInitialUser,mockValueA);
        block.addTransaction(new RegularTransaction(mockUserA, mockUserB, mockValueB));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test
    public void testAddingATransactionToFullBlockReturnsFalse()
    {
        block = new GenesisBlock(mockInitialUser,mockValueA);
        for (int i=0;i<block.transactions.length-1;i++)
        {
            block.addTransaction(new RegularTransaction(mockUserA, mockUserB, mockValueB));
        }
        assertFalse(block.addTransaction(new RegularTransaction(mockUserA, mockUserB, mockValueB)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullTransactionThrows()
    {
        block = new GenesisBlock(mockInitialUser,mockValueA);
        block.addTransaction(null);
    }
}
