package com.capgemini.tozdeb.simpleblockchain.utilities;

import com.capgemini.tozdeb.simpleblockchain.core.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ValidationServiceTest {

    final int mockValueA = 1000,
            mockValueB = 100;
    final String mockUserA = "userA",
            mockUserB = "userB",
            mockHash = "ABC-DEF-GHI",
            mockID = "myBlockchain";
    final long mockTimestampA = 12345678,
    mockTimestampB = 1000000000;
    ValidationService validationService;

    Block block;
    List<Block> blocks;

    Blockchain blockchain;
    @Before
    public void initializeValidationService() {
        validationService = new ValidationService();
    }

    @Test
    public void testValidateTransactionValidBalanceGreater() {
        assertTrue(validationService.validateTransaction(new Transaction(mockTimestampA,
                                                                         mockUserA,
                                                                         mockUserB,
                                                                         mockValueB),
                                                         mockValueA));
    }

    @Test
    public void testValidateTransactionValidBalanceEqual() {
        assertTrue(validationService.validateTransaction(new Transaction(mockTimestampA,
                                                                         mockUserA,
                                                                         mockUserB,
                                                                         mockValueB),
                                                         mockValueB));
    }

    @Test
    public void testValidateTransactionNonValid() {
        assertFalse(validationService.validateTransaction(new Transaction(mockTimestampA,
                                                                          mockUserA,
                                                                          mockUserB,
                                                                          mockValueA),
                                                          mockValueB));
    }

    @Test
    public void testIsGenesisTransactionGenesisPassed(){
        assertTrue(validationService.isGenesisTransaction(new Transaction(mockUserA,mockValueA)));
    }

    @Test
    public void testIsGenesisTransactionNonGenesisPassed(){
        assertFalse(validationService.isGenesisTransaction(new Transaction(mockTimestampA,mockUserA,mockUserB,mockValueA)));
    }

    @Test
    public void testIsGenesisBlockGenesisPassed(){
        assertTrue(validationService.isGenesisBlock(new Block(mockTimestampA,mockUserA,mockValueA)));
    }

    @Test
    public void testIsGenesisBlockNonGenesisPassed(){
        assertFalse(validationService.isGenesisBlock(new Block(mockTimestampA,
                                                              mockHash,
                                                              new Transaction(mockTimestampA,
                                                                              mockUserA,
                                                                              mockUserB,
                                                                              mockValueA))));
    }
    @Test
    public void testIsBlockValidNoTimeContinuity(){
        block = new Block(mockTimestampA,mockHash,new Transaction(mockTimestampB,mockUserA,mockUserB,mockValueA));
        block.addTransaction(new Transaction(mockTimestampA,mockUserA,mockUserB,mockValueA));

        assertFalse(validationService.validateBlock(block));
    }

    @Test
    public void testIsBlockValidTimeContinuity(){
        block = new Block(mockTimestampA,mockHash,new Transaction(mockTimestampA,mockUserA,mockUserB,mockValueA));
        block.addTransaction(new Transaction(mockTimestampB,mockUserA,mockUserB,mockValueA));

        assertTrue(validationService.validateBlock(block));
    }

    @Test
    public void testBlockchainHasHashContinuityTrueTwoBlocks() {
        block = new Block(mockTimestampA,
                          mockUserA,
                          mockValueA);
        Block nextBlock = new Block(mockTimestampB,
                                    block.hashBlock(),
                                    new Transaction(mockTimestampA,
                                                    mockUserA,
                                                    mockUserB,
                                                    mockValueA));
        blocks = new ArrayList<>();
        blocks.add(block);
        blocks.add(nextBlock);

        blockchain = new Blockchain(mockTimestampA,mockID,blocks) ;

        assertTrue(validationService.blockchainHasHashContinuity(blockchain));
    }

    @Test
    public void testBlockchainHasHashContinuityTrueThreeBlocks() {
        block = new Block(mockTimestampA,
                          mockUserA,
                          mockValueA);
        Block secondBlock = new Block(mockTimestampB,
                                    block.hashBlock(),
                                    new Transaction(mockTimestampA,
                                                    mockUserA,
                                                    mockUserB,
                                                    mockValueA));
        Block thirdBlock = new Block(mockTimestampB,
                                    secondBlock.hashBlock(),
                                    new Transaction(mockTimestampA,
                                                    mockUserA,
                                                    mockUserB,
                                                    mockValueA));
        blocks = new ArrayList<>();
        blocks.add(block);
        blocks.add(secondBlock);
        blocks.add(thirdBlock);

        blockchain = new Blockchain(mockTimestampA,mockID,blocks) ;

        assertTrue(validationService.blockchainHasHashContinuity(blockchain));
    }

    //IMPLEMENT - testBlockchainHasHashContinuityTrueThreeBlocks

    @Test
    public void testBlockchainHasHashContinuityFalse() {
        block = new Block(mockTimestampA,
                          mockUserA,
                          mockValueA);
        Block nextBlock = new Block(mockTimestampB,
                                    mockHash,
                                    new Transaction(mockTimestampA,
                                                    mockUserA,
                                                    mockUserB,
                                                    mockValueA));
        blocks = new ArrayList<>();
        blocks.add(block);
        blocks.add(nextBlock);

        blockchain = new Blockchain(mockTimestampA,mockID,blocks) ;

        assertFalse(validationService.blockchainHasHashContinuity(blockchain));
    }
}


