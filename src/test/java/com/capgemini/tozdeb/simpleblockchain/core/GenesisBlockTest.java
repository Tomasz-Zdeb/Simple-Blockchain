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
            fail();
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
        assertNull(block.getPreviousBlockHash());
    }


    @Test
    public void testNewBlocksTransactionListContainsOneGenesisTransaction() {
        block = new GenesisBlock("root_user",1000);
        assertNotNull(block.transactions[0].getRecipient());
        assertNull(block.transactions[0].getSender());
        for(int i = 1;i<block.transactions.length;i++){
            assertNull(block.transactions[i]);
        }
    }

    @Test
    public void testTransactionIsAddedProperly(){
        block = new GenesisBlock("root_user",1000);
        assertTrue(block.addTransaction(new RegularTransaction("userA","userB",50)));
        Transaction[] transactions = block.getTransactions();

        assertNull(transactions[0].getSender());
        assertSame("root_user", transactions[0].getRecipient());
        assertEquals(1000, transactions[0].getAmount());

        assertSame("userA", transactions[1].getSender());
        assertSame("userB", transactions[1].getRecipient());
        assertEquals(50, transactions[1].getAmount());

        for(int i = 2; i<transactions.length;i++)
        {
            assertNull(transactions[i]);
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
