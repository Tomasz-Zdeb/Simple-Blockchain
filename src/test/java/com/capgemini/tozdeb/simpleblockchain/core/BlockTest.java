package com.capgemini.tozdeb.simpleblockchain.core;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.Instant;

public class BlockTest {
    Block block;
    @Test
    public void testBlockCreationWithHashProvidedDoesNotThrow() {
        try{
            block = new Block("ABC-DEF-GHIJK-LM");
        }
        catch(Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testBlockCreationWithNullPreviousBlockHashProvidedDoesNotThrow() {
        try{
            block = new Block(null);
        }
        catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void testBlockGetTimestampWithPreviousBlockHash() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block("ABC-DEF-GHIJK-LM");
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + 5);
    }

    @Test
    public void testBlockGetTimestampWithNullPreviousBlockHash() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block(null);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + 5);
    }

    @Test
    public void testNewBlockNumberOfTransactionsIsZeroWithPreviousBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        assertEquals(0,block.getNumberOfTransactions());
    }

    @Test
    public void testNewBlockNumberOfTransactionsIsZeroWithNullBlockHash() {
        block = new Block(null);
        assertEquals(0,block.getNumberOfTransactions());
    }

    @Test
    public void testNewBlockTransactionHashIsNullWithBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        assertEquals(null,block.getTransactionsHash());
    }

    @Test
    public void testNewBlockTransactionHashIsNullWithNullBlockHash() {
        block = new Block(null);
        assertEquals(null,block.getTransactionsHash());
    }

    @Test
    public void testPreviousBlockHashIsNullWithBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        assertEquals("ABC-DEF-GHIJK-LM",block.getPreviousBlockHash());
    }

    @Test
    public void testPreviousBlockHashIsNullWithNullBlockHash() {
        block = new Block(null);
        assertEquals(null,block.getPreviousBlockHash());
    }

    @Test
    public void testNewBlocksTransactionListIsNullWithBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        for (Transaction transaction : block.transactions)
        {
            assertEquals(null,transaction);
        }
    }

    @Test
    public void testNewBlocksTransactionListIsNullWithNullBlockHash() {
        block = new Block(null);
        for (Transaction transaction : block.transactions)
        {
            assertEquals(null,transaction);
        }
    }

    @Test
    public void testTransactionIsAddedProperlyWithPreviousBlockHash(){
        block = new Block("ABC-DEF-GHIJK-LM");
        block.addTransaction(new RegularTransaction("userA","user",0));
        Transaction[] transactions = block.getTransactions();

        assertTrue(transactions[0].getSender().equals("userA"));
        assertTrue(transactions[0].getRecipient().equals("user"));
        assertTrue(transactions[0].getAmount()==0);

        for(int i = 1; i<transactions.length;i++)
        {
            assertTrue(transactions[i] == null);
        }
    }

    @Test
    public void testTransactionIsAddedProperlyWithNullPreviousBlockHash(){
        block = new Block(null);
        block.addTransaction(new RegularTransaction("userA","user",0));
        Transaction[] transactions = block.getTransactions();

        assertTrue(transactions[0].getSender().equals("userA"));
        assertTrue(transactions[0].getRecipient().equals("user"));
        assertTrue(transactions[0].getAmount()==0);

        for(int i = 1; i<transactions.length;i++)
        {
            assertTrue(transactions[i] == null);
        }
    }

    @Test
    public void testAddingThreeTransactionsResultInCounterIncrement(){
        block = new Block("ABC-DEF-GHIJK-LM");
        block.addTransaction(new RegularTransaction("userA", "userB",100));
        block.addTransaction(new RegularTransaction("userB", "userC",50));
        block.addTransaction(new RegularTransaction("userA", "userD",25));
        assertEquals(3,block.getNumberOfTransactions());
    }
    @Test
    public void testAddingOneTransactionsResultInCounterIncrement() {
        block = new Block("ABC-DEF-GHIJK-LM");
        block.addTransaction(new RegularTransaction("userA", "userB", 100));
        assertEquals(1,block.getNumberOfTransactions());
    }

    @Test
    public void testAddingATransactionToFullBlockReturnsFalse()
    {
        block = new Block("ABC-DEF-GHIJK-LM");
        for (int i=0;i<block.transactions.length;i++)
        {
            block.addTransaction(new RegularTransaction("userA", "userB", 100));
        }
        assertFalse(block.addTransaction(new RegularTransaction("userA", "userB", 100)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullTransactionThrows()
    {
        block = new Block("ABC-DEF-GHIJK-LM");
        block.addTransaction(null);
    }

    @Test
    public void testIsTransactionsHashValid() {
        block = new Block("ABC-DEF-GHIJK-LM");
        block.addTransaction(new RegularTransaction("userA", "userB", 100));
        assertEquals(64,block.getTransactionsHash().length());
    }
}