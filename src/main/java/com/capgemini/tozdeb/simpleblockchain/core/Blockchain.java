package com.capgemini.tozdeb.simpleblockchain.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    final private long timestamp;
    final private String ID;
    private List<Block> blocks = new ArrayList<>();

    public long getTimestamp() {
        return timestamp;
    }

    public String getID() {
        return ID;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public Blockchain(Long timestamp, String ID, List<Block> blocks){
        long numberOfGenesisTransactions=0;
        for(Block block : blocks)
        {
            Transaction[] transactions = block.getTransactions();
            for(Transaction transaction : transactions)
            {
                if(transaction != null && transaction.getSender() == null)
                {
                    numberOfGenesisTransactions++;
                }
            }
        }
        if(numberOfGenesisTransactions != 1)
        {
            throw new IllegalArgumentException("only one genesis block with one genesis transaction is allowed in a blockchain");
        }
        this.timestamp = timestamp;
        this.ID = ID;
        this.blocks = blocks;
    }

    public Blockchain(String ID, String recipient, int amount){
        this.timestamp = Instant.now().getEpochSecond();
        this.ID = ID;
        blocks.add(new Block(recipient,amount));
    }

    static Blockchain initializeNewBlockchain(String ID, String recipient, int amount)
    {
        return new Blockchain(ID,recipient,amount);
    }

    public void addBlock(Block block){
        if(block == null)
        {
            throw new IllegalArgumentException("Block to be added can't be null");
        }
        long numberOfGenesisTransactions=0;
        Transaction[] transactions = block.getTransactions();
        for (Transaction transaction : transactions) {
            if (transaction != null && transaction.getSender() == null) {
                numberOfGenesisTransactions++;
            }
        }
        if(numberOfGenesisTransactions>0)
        {
            throw new IllegalArgumentException("Cannot add block containing genesis transactions");
        }
    }
}
