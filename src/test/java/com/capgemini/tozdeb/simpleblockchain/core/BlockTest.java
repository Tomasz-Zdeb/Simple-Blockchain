package com.capgemini.tozdeb.simpleblockchain.core;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.Instant;

public class BlockTest {
    Block block;
    @Test
    public void BlockCreationWithHashProvidedDoesNotThrow() {
        try{
            block = new Block("ABC-DEF-GHIJK-LM");
        }
        catch(Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void BlockCreationWithNullPreviousBlockHashProvidedDoesNotThrow() {
        try{
            block = new Block(null);
        }
        catch(Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void BlockGetTimestampWithPreviousBlockHash() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block("ABC-DEF-GHIJK-LM");
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + 5);
    }

    @Test
    public void BlockGetTimestampWithNullPreviousBlockHash() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block(null);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + 5);
    }

    @Test
    public void NewBlockNumberOfTransactionsIsZeroWithPreviousBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        assertEquals(0,block.getNumberOfTransactions());
    }

    @Test
    public void NewBlockNumberOfTransactionsIsZeroWithNullBlockHash() {
        block = new Block(null);
        assertEquals(0,block.getNumberOfTransactions());
    }

    @Test
    public void NewBlockTransactionHashIsNullWithBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        assertEquals(null,block.getTransactionsHash());
    }

    @Test
    public void NewBlockTransactionHashIsNullWithNullBlockHash() {
        block = new Block(null);
        assertEquals(null,block.getTransactionsHash());
    }

    @Test
    public void PreviousBlockHashIsNullWithBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        assertEquals("ABC-DEF-GHIJK-LM",block.getPreviousBlockHash());
    }

    @Test
    public void PreviousBlockHashIsNullWithNullBlockHash() {
        block = new Block(null);
        assertEquals(null,block.getPreviousBlockHash());
    }

    @Test
    public void NewBlocksTransactionListIsNullWithBlockHash() {
        block = new Block("ABC-DEF-GHIJK-LM");
        for (Transaction transaction : block.transactions)
        {
            assertEquals(null,transaction);
        }
    }

    @Test
    public void NewBlocksTransactionListIsNullWithNullBlockHash() {
        block = new Block(null);
        for (Transaction transaction : block.transactions)
        {
            assertEquals(null,transaction);
        }
    }

    @Test
    public void TransactionIsAddedProperlyWithPreviousBlockHash(){
        block = new Block("ABC-DEF-GHIJK-LM");
        block.AddTransaction(new RegularTransaction("userA","user",0));
        Transaction[] transactions = block.getTransactions();

        assertTrue(transactions[0].getSender() == "userA");
        assertTrue(transactions[0].getRecipient() == "user");
        assertTrue(transactions[0].getAmount()==0);

        for(int i = 1; i<transactions.length;i++)
        {
            assertTrue(transactions[i] == null);
        }
    }

    @Test
    public void TransactionIsAddedProperlyWithNullPreviousBlockHash(){
        block = new Block(null);
        block.AddTransaction(new RegularTransaction("userA","user",0));
        Transaction[] transactions = block.getTransactions();

        assertTrue(transactions[0].getSender() == "userA");
        assertTrue(transactions[0].getRecipient() == "user");
        assertTrue(transactions[0].getAmount()==0);

        for(int i = 1; i<transactions.length;i++)
        {
            assertTrue(transactions[i] == null);
        }
    }

    @Test
    public void AddingThreeTransactionsResultInCounterIncrement(){
        block = new Block("ABC-DEF-GHIJK-LM");
        block.AddTransaction(new RegularTransaction("userA", "userB",100));
        block.AddTransaction(new RegularTransaction("userB", "userC",50));
        block.AddTransaction(new RegularTransaction("userA", "userD",25));
        assertEquals(3,block.getNumberOfTransactions());
    }
    @Test
    public void AddingOneTransactionsResultInCounterIncrement() {
        block = new Block("ABC-DEF-GHIJK-LM");
        block.AddTransaction(new RegularTransaction("userA", "userB", 100));
        assertEquals(1,block.getNumberOfTransactions());
    }

    @Test
    public void AddingATransactionToFullBlockReturnsFalse()
    {
        block = new Block("ABC-DEF-GHIJK-LM");
        for (int i=0;i<block.transactions.length;i++)
        {
            block.AddTransaction(new RegularTransaction("userA", "userB", 100));
        }
        assertFalse(block.AddTransaction(new RegularTransaction("userA", "userB", 100)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddingNullTransactionThrows()
    {
        block = new Block("ABC-DEF-GHIJK-LM");
        block.AddTransaction(null);
    }

    @Test
    public void IsTransactionsHashValid() {
        block = new Block("ABC-DEF-GHIJK-LM");
        block.AddTransaction(new RegularTransaction("userA", "userB", 100));
        assertEquals(64,block.getTransactionsHash().length());
    }
}