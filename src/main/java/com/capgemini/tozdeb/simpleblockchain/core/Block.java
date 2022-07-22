package com.capgemini.tozdeb.simpleblockchain.core;

import java.time.Instant;
import org.apache.commons.codec.digest.DigestUtils;

public class Block {
    final int BLOCK_SIZE = 10;
    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTransactionsHash() {
        return transactionsHash;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public byte getNumberOfTransactions() { return numberOfTransactions;}

    protected final long timestamp;
    protected final String previousBlockHash;
    protected String transactionsHash;
    protected byte numberOfTransactions = 0;
    protected Transaction[] transactions;

    public Block(long timestamp,String previousBlockHash,Transaction transaction) {
        if (previousBlockHash == null)
        {
            throw new IllegalArgumentException("previous block hash can't be null");
        }
        if(transaction == null)
        {
            throw new IllegalArgumentException("Transaction cannot be null");
        } else {
            if (transaction.getSender() == null) {
                throw new IllegalArgumentException("Non genesis block cannot be initialized with genesis transaction");
            }
        }
        this.previousBlockHash = previousBlockHash;
        this.timestamp = timestamp;
        this.transactions = new Transaction[BLOCK_SIZE];
        this.transactions[0] = transaction;
        this.numberOfTransactions++;
        hashTransactions(this.transactions);
    }
    public Block(String previousBlockHash,Transaction transaction)
    {
        this(Instant.now().getEpochSecond(),previousBlockHash,transaction);
    }

    public Block(long timestamp, String recipient, int amount){
        this.timestamp = timestamp;
        this.previousBlockHash = null;
        this.transactions = new Transaction[BLOCK_SIZE];
        this.transactions[0] = new Transaction(recipient,amount);
        this.numberOfTransactions++;
        hashTransactions(this.transactions);
    }
    public Block(String recipient, int amount)
    {
        this(Instant.now().getEpochSecond(),recipient,amount);
    }


    public boolean addTransaction(Transaction transaction){

        if(transaction == null)
        {
            throw new IllegalArgumentException("transaction to be added must be of non null value");
        }
        if(transaction.getSender() == null)
        {
            throw new IllegalArgumentException("Genesis transaction can't be passed added to regular block");
        }
        if(numberOfTransactions < transactions.length)
        {
            transactions[numberOfTransactions] = transaction;
            numberOfTransactions++;
            hashTransactions(transactions);
            return true;
        }
        else
        {
            return false;
        }
    }

    protected void hashTransactions(Transaction[] transactions){
        StringBuilder builder = new StringBuilder();
        for (Transaction transaction : transactions)
        {
            if(transaction != null)
            {
                builder.append(transaction.generateTransactionString());
            }
        }
        transactionsHash = DigestUtils.sha256Hex(builder.toString());
    }

    protected String generateBlockString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(timestamp);
        if(previousBlockHash != null)
        {
            builder.append(previousBlockHash);
        }
        builder.append(transactionsHash);
        return builder.toString();
    }

    public String hashBlock()
    {
        return DigestUtils.sha256Hex(generateBlockString());
    }

    public boolean valueOf(Block block) {
        if (this.timestamp == block.timestamp && this.transactionsHash.equals(block.transactionsHash)) {
            if (this.previousBlockHash == null && block.previousBlockHash == null) {
                return true;
            }
            return this.previousBlockHash != null && block.previousBlockHash != null;
        }
        return false;
    }
}

