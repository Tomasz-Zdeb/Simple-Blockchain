package com.capgemini.tozdeb.simpleblockchain.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    final long timestamp;
    final String ID;
    List<Block> blocks = new ArrayList<>();

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
}
