package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Assert;
import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;


public class GenesisBlockTest {
    Block block;
    @Test
    public void GenesisBlockCreationDoesNotThrow() {
        try{
            block = new GenesisBlock("root_user",1000);
        }
        catch(Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void BlockGetTimestampIsValid() {
        long timestamp = Instant.now().getEpochSecond();
        block = new GenesisBlock("root_user",1000);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + 5);
    }

    @Test
    public void NewGenesisBlockNumberOfTransactionsIsOne() {
        block = new GenesisBlock("root_user",1000);
        assertEquals(1,block.getNumberOfTransactions());
    }


    @Test
    public void IsTransactionHashValid() {
        block = new GenesisBlock("root_user",1000);
        assertEquals(64,block.getTransactionsHash().length());
    }

    @Test
    public void PreviousTransactionHashIsNull() {
        block = new GenesisBlock("root_user",1000);
        assertEquals(null,block.getPreviousBlockHash());
    }


    @Test
    public void NewBlocksTransactionListContainsOneGenesisTransaction() {
        block = new GenesisBlock("root_user",1000);
        assertFalse(block.transactions[0].getRecipient() == null);
        assertTrue(block.transactions[0].getSender() == null);
        for(int i = 1;i<block.transactions.length;i++){
            assertTrue(block.transactions[i] == null);
        }
    }

    @Test
    public void TransactionIsAddedProperly(){
        block = new GenesisBlock("root_user",1000);
        assertTrue(block.AddTransaction(new RegularTransaction("userA","userB",50)));
        Transaction[] transactions = block.getTransactions();

        assertTrue(transactions[0].getSender() == null);
        assertTrue(transactions[0].getRecipient() == "root_user");
        assertTrue(transactions[0].getAmount()==1000);

        assertTrue(transactions[1].getSender() == "userA");
        assertTrue(transactions[1].getRecipient() == "userB");
        assertTrue(transactions[1].getAmount()==50);

        for(int i = 2; i<transactions.length;i++)
        {
            assertTrue(transactions[i] == null);
        }
    }

    @Test
    public void AddingThreeTransactionsResultInCounterIncrement(){
        block = new GenesisBlock("root_user",1000);
        block.AddTransaction(new RegularTransaction("userA", "userB",100));
        block.AddTransaction(new RegularTransaction("userB", "userC",50));
        block.AddTransaction(new RegularTransaction("userA", "userD",25));
        assertEquals(4,block.getNumberOfTransactions());
    }
    @Test
    public void AddingOneTransactionsResultInCounterIncrement() {
        block = new GenesisBlock("root_user",1000);
        block.AddTransaction(new RegularTransaction("userA", "userB", 100));
        assertEquals(2,block.getNumberOfTransactions());
    }

    @Test
    public void AddingATransactionToFullBlockReturnsFalse()
    {
        block = new GenesisBlock("root_user",1000);
        for (int i=0;i<block.transactions.length-1;i++)
        {
            block.AddTransaction(new RegularTransaction("userA", "userB", 100));
        }
        assertFalse(block.AddTransaction(new RegularTransaction("userA", "userB", 100)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void AddingNullTransactionThrows()
    {
        block = new GenesisBlock("root_user",1000);
        block.AddTransaction(null);
    }
}
