package com.capgemini.tozdeb.simpleblockchain.core;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.Instant;

public class BlockTest {
    final int sha256HashLength = 64,
            maxTimestampTimeout = 5,
            mockValueA = 1000,
            mockValueB=100,
            mockValueC = 10,
            mockValueD= 8;
    final long mockTimestampA = 12345678;
    final String mockHash = "ABC-DEF-GHI",
    mockUserA = "userA",
    mockUserB = "userB",
    mockUserC = "userC",
    mockUserD = "userD";
    Block block;
    @Test
    public void testConstructorNonGenesisDoesNotThrow() {
        try{
            block = new Block(mockTimestampA,mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test
    public void testConstructorGenesisDoesNotThrow() {
        try{
            block = new Block(mockTimestampA,mockUserB,mockValueA);
        }
        catch(Exception e)
        {
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNullPreviousHashTrows() {
            block = new Block(null,new Transaction(mockUserA,mockUserB,mockValueA));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithGenesisTransactionPassedThrows(){
        block = new Block(mockTimestampA,mockHash,new Transaction(mockTimestampA,mockUserA,mockValueA));
    }

    @Test
    public void testGetPreviousHash(){
        block = new Block(mockTimestampA,mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        assertEquals(mockHash,block.getPreviousBlockHash());
    }

    @Test
    public void testGetTimestampNonGenesisGenerated() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testGetTimestampNonGenesisPassedByConstructor() {
        block = new Block(mockTimestampA,mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        assertEquals(mockTimestampA,block.getTimestamp());
    }

    @Test
    public void testGetTimestampGenesis() {
        long timestamp = Instant.now().getEpochSecond();
        block = new Block(mockUserA,mockValueA);
        assertTrue(block.getTimestamp() >= timestamp);
        assertTrue(block.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testNewBlocksNumberOfTransactionsIsOneNonGenesis() {
        final int expectedNumberOfTransactions = 1;

        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test
    public void testNewBlocksNumberOfTransactionsIsOneGenesis() {
        final int expectedNumberOfTransactions = 1;

        block = new Block(mockUserA,mockValueA);
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test
    public void testTransactionIsAddedProperlyWithPreviousBlockHash(){
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
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
    public void testAddingThreeTransactionsIncrementsCounter(){
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        final int expectedNumberOfTransactions = 3;

        block.addTransaction(new Transaction(mockUserB, mockUserC,mockValueC));
        block.addTransaction(new Transaction(mockUserA, mockUserD,mockValueD));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }
    @Test
    public void testAddingOneTransactionsIncrementsCounter() {
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));

        final int expectedNumberOfTransactions = 2;

        block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB));
        assertEquals(expectedNumberOfTransactions,block.getNumberOfTransactions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingGenesisTransactionThrows(){
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        block.addTransaction(new Transaction(mockTimestampA,mockUserA,mockValueA));
    }

    @Test
    public void testAddingATransactionToFullBlockReturnsFalse()
    {
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));

        for (int i=0;i<block.transactions.length-1;i++)
        {
            block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB));
        }
        assertFalse(block.addTransaction(new Transaction(mockUserA, mockUserB, mockValueB)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullTransactionThrows()
    {
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        block.addTransaction(null);
    }

    @Test
    public void testIsTransactionsHashValid() {
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));

        assertEquals(sha256HashLength,block.getTransactionsHash().length());
    }

    @Test
    public void testGenerateBlockString(){
        block = new Block(mockHash,new Transaction(mockUserA,mockUserB,mockValueA));
        assertEquals(block.getTimestamp()+block.previousBlockHash+block.transactionsHash,
                block.generateBlockString());
    }

    @Test
    public void testValueOfSameReferences(){
        block = new Block(mockTimestampA,mockHash,new Transaction(mockUserA,mockUserB,mockValueA));

        assertTrue(block.valueOf(block));
    }

    @Test
    public void testValueOfSameValueDifferentReferences(){
        block = new Block(mockTimestampA,mockHash,new Transaction(mockUserA,mockUserB,mockValueA));

        assertTrue(block.valueOf(new Block(mockTimestampA,mockHash,new Transaction(mockUserA,mockUserB,mockValueA))));
    }

    @Test //TO DO
    public void testValueOfSameValueDifferentReferencesGenesis(){
        block = new Block(mockTimestampA,mockUserA,mockValueA);

        assertTrue(block.valueOf(new Block(mockTimestampA,mockUserA,mockValueA)));
    }

    @Test
    public void testValueOfDifferentValues() {
        block = new Block(mockTimestampA, mockUserA, mockValueA);

        assertFalse(block.valueOf(new Block(mockTimestampA, mockHash, new Transaction(mockUserB, mockUserA, mockValueA))));
    }

    @Test
    public void testIsBlockHashValid(){
        block = new Block(mockTimestampA, mockUserA, mockValueA);
        assertEquals(sha256HashLength,block.hashBlock().length());
    }
}