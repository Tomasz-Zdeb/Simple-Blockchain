package com.capgemini.tozdeb.simpleblockchain.core;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlockchainTest {

    final int maxTimestampTimeout = 5,
            mockValueA = 1000,
            mockValueB = 100;

    final long mockTimestampA = 12345678;
    final String mockHash = "ABC-DEF-GHI",
            mockUserA = "userA",
            mockUserB = "userB",
            mockID = "myBlockchain",
            mockIDB = "someBlockchain";
    Blockchain blockchain;
    List<Block> blocks;

    @Before
    public void initializeBlock()
    {
        blocks = new ArrayList<>();
        blocks.add(new Block(mockTimestampA,mockUserA,mockValueA));
        blocks.add(new Block(mockTimestampA,
                mockHash,
                new Transaction(mockTimestampA,
                        mockUserA,
                        mockUserB,
                        mockValueA)));
    }

    @Test
    public void testGetTimestampPassed(){
        blockchain = new Blockchain(mockTimestampA,mockID,blocks);

        assertEquals(mockTimestampA,blockchain.getTimestamp());
    }

    @Test
    public void testGetTimestampGenerated(){
        final long timestamp = Instant.now().getEpochSecond();
        blockchain = new Blockchain(mockID,mockUserA,mockValueA);
        assertTrue(blockchain.getTimestamp() >= timestamp);
        assertTrue(blockchain.getTimestamp() < timestamp + maxTimestampTimeout);
    }

    @Test
    public void testGetIdNonGenesis(){
        blockchain = new Blockchain(mockTimestampA,mockID,blocks);
        assertEquals(mockID,blockchain.getID());
    }

    @Test
    public void testGetIdGenesis(){
        blockchain = new Blockchain(mockID,mockUserA,mockValueA);
        assertEquals(mockID,blockchain.getID());
    }

    @Test
    public void testGetBlocksGenesis(){
        blockchain =  new Blockchain(mockID,mockUserA,mockValueA);
        blocks = blockchain.getBlocks();
        assertEquals(1,blocks.size());
        assertNull(blocks.get(0).getPreviousBlockHash());
        assertEquals(1,blocks.get(0).getNumberOfTransactions());
        assertNull(blocks.get(0).getTransactions()[0].getSender());
        assertNull(blocks.get(0).getTransactions()[1]);
    }

    @Test
    public void testGetBlocksNonGenesis(){
        final int expectedSize = 2;

        blockchain = new Blockchain(mockTimestampA,mockID,blocks);
        List<Block> blocksReturned = blockchain.getBlocks();
        assertTrue(blocksReturned.size() == blocks.size() &&
                blocksReturned.size() == expectedSize);
        assertTrue(blocksReturned.get(0).valueOf(blocks.get(0)));
    }

    @Test
    public void testInitializeNewBlockchain(){
        final long expectedTimestamp = Instant.now().getEpochSecond();
        blockchain = Blockchain.initializeNewBlockchain(mockID,mockUserA,mockValueA);
        assertTrue(blockchain.getTimestamp() >= expectedTimestamp);
        assertTrue(blockchain.getTimestamp() < expectedTimestamp + maxTimestampTimeout);
        assertEquals(mockID,blockchain.getID());
        assertEquals(1,blockchain.getBlocks().size());
    }

    @Test
    public void testInitializeNewBlockchainDifferentValues(){
        final long expectedTimestamp = Instant.now().getEpochSecond();
        blockchain = Blockchain.initializeNewBlockchain(mockIDB,mockUserB,mockValueB);
        assertTrue(blockchain.getTimestamp() >= expectedTimestamp);
        assertTrue(blockchain.getTimestamp() < expectedTimestamp + maxTimestampTimeout);
        assertEquals(mockIDB,blockchain.getID());
        assertEquals(1,blockchain.getBlocks().size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInitializationWithBlocksThatContainMoreThanOneGenesisTransactionThrows(){
        blocks = new ArrayList<>();
        blocks.add(new Block(mockTimestampA,mockUserA,mockValueA));
        blocks.add(new Block(mockTimestampA,mockUserA,mockValueA));

        blockchain = new Blockchain(mockTimestampA,mockID,blocks);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddBlockNullBlockThrows(){
        blockchain = Blockchain.initializeNewBlockchain(mockID,mockUserA,mockValueA);
        blockchain.addBlock(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddBlockGenesisBlockThrows(){
        Block block = new Block(mockTimestampA,mockUserA,mockValueA);
        blockchain = Blockchain.initializeNewBlockchain(mockID,mockUserA,mockValueA);

        blockchain.addBlock(block);
    }

    @Test
    public void testAddBlockDoesNotThrow() {
        Block block = new Block(mockTimestampA,
                                mockHash,
                                new Transaction(mockTimestampA,
                                                mockUserA,
                                                mockUserB,
                                                mockValueA));
        blockchain = Blockchain.initializeNewBlockchain(mockID,
                                                        mockUserA,
                                                        mockValueA);

        blockchain.addBlock(block);
    }
}