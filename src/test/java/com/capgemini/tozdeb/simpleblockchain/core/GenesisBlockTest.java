package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Test;
import java.time.Instant;
import static org.junit.Assert.*;


public class GenesisBlockTest {
    Block block;
    @Test
    public void testGenesisBlockCreationDoesNotThrow() {
        try{
            block = new GenesisBlock("root_user",1000);
        }
        catch(Exception e)
        {
            assertTrue(false);
        }
    }

    @Test
    public void testBlockGetTimestampIsValid() {
        long timestamp = Instant.now().getEpochSecond();
        block = new GenesisBlock("root_user",1000);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + 5);
    }

    @Test
    public void testNewGenesisBlockNumberOfTransactionsIsOne() {
        block = new GenesisBlock("root_user",1000);
        assertEquals(1,block.getNumberOfTransactions());
    }


    @Test
    public void testIsTransactionHashValid() {
        block = new GenesisBlock("root_user",1000);
        assertEquals(64,block.getTransactionsHash().length());
    }

    @Test
    public void testPreviousTransactionHashIsNull() {
        block = new GenesisBlock("root_user",1000);
        assertEquals(null,block.getPreviousBlockHash());
    }


    @Test
    public void testNewBlocksTransactionListContainsOneGenesisTransaction() {
        block = new GenesisBlock("root_user",1000);
        assertFalse(block.transactions[0].getRecipient() == null);
        assertTrue(block.transactions[0].getSender() == null);
        for(int i = 1;i<block.transactions.length;i++){
            assertTrue(block.transactions[i] == null);
        }
    }

    @Test
    public void testTransactionIsAddedProperly(){
        block = new GenesisBlock("root_user",1000);
        assertTrue(block.addTransaction(new RegularTransaction("userA","userB",50)));
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
    public void testAddingThreeTransactionsResultInCounterIncrement(){
        block = new GenesisBlock("root_user",1000);
        block.addTransaction(new RegularTransaction("userA", "userB",100));
        block.addTransaction(new RegularTransaction("userB", "userC",50));
        block.addTransaction(new RegularTransaction("userA", "userD",25));
        assertEquals(4,block.getNumberOfTransactions());
    }
    @Test
    public void testAddingOneTransactionsResultInCounterIncrement() {
        block = new GenesisBlock("root_user",1000);
        block.addTransaction(new RegularTransaction("userA", "userB", 100));
        assertEquals(2,block.getNumberOfTransactions());
    }

    @Test
    public void testAddingATransactionToFullBlockReturnsFalse()
    {
        block = new GenesisBlock("root_user",1000);
        for (int i=0;i<block.transactions.length-1;i++)
        {
            block.addTransaction(new RegularTransaction("userA", "userB", 100));
        }
        assertFalse(block.addTransaction(new RegularTransaction("userA", "userB", 100)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullTransactionThrows()
    {
        block = new GenesisBlock("root_user",1000);
        block.addTransaction(null);
    }
}
