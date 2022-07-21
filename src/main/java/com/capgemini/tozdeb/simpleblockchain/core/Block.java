package com.capgemini.tozdeb.simpleblockchain.core;

import java.time.Instant;
import org.apache.commons.codec.digest.DigestUtils;

class Block {
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

    Block(long timestamp,String previousBlockHash,Transaction transaction) {
        if (previousBlockHash == null)
        {
            throw new IllegalArgumentException("previous block hash can't be null");
        }
        this.previousBlockHash = previousBlockHash;
        this.timestamp = timestamp;
        this.transactions = new Transaction[BLOCK_SIZE];
        this.transactions[0] = transaction;
        this.numberOfTransactions++;
        hashTransactions(this.transactions);
    }
    Block(String previousBlockHash,Transaction transaction)
    {
        this(Instant.now().getEpochSecond(),previousBlockHash,transaction);
    }

    Block(long timestamp, String recipient, int amount){
        this.timestamp = timestamp;
        this.previousBlockHash = null;
        this.transactions = new Transaction[BLOCK_SIZE];
        this.transactions[0] = new Transaction(recipient,amount);
        this.numberOfTransactions++;
        hashTransactions(this.transactions);
    }
    Block(String recipient, int amount)
    {
        this(Instant.now().getEpochSecond(),recipient,amount);
    }


    public boolean addTransaction(Transaction transaction){

        if(transaction == null)
        {
            throw new IllegalArgumentException("transaction to be added must be of non null value");
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

    public boolean valueOf(Block block) {
        if (this.timestamp == block.timestamp && this.transactionsHash.equals(block.transactionsHash)) {
            boolean transactionsAreEqual = true;
            for (int i = 0; i < BLOCK_SIZE; i++) {
                if(this.transactions[i] != null) {
                    if (!this.transactions[i].valueOf(block.transactions[i])) {
                        transactionsAreEqual = false;
                    }
                }
            }
            if (transactionsAreEqual && this.previousBlockHash == null && block.previousBlockHash == null) {
                return true;
            }
            return transactionsAreEqual && this.previousBlockHash != null && block.previousBlockHash != null;
        }
        return false;
    }
}

