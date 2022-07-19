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

    protected String previousBlockHash;
    protected final long timestamp;
    protected String transactionsHash;
    protected byte numberOfTransactions = 0;
    protected Transaction[] transactions;

    Block(String previousBlockHash,String sender, String recipient, int amount) {
        if (previousBlockHash == null)
        {
            throw new IllegalArgumentException("previous block hash can't be null");
        }
        this.previousBlockHash = previousBlockHash;
        timestamp = Instant.now().getEpochSecond();
        transactions = new Transaction[BLOCK_SIZE];
        transactions[0] = new Transaction(sender,recipient,amount);
        numberOfTransactions++;
        hashTransactions(transactions);
    }

    Block(String recipient, int amount){
        timestamp = Instant.now().getEpochSecond();
        transactions = new Transaction[BLOCK_SIZE];
        transactions[0] = new Transaction(recipient,amount);
        numberOfTransactions++;
        hashTransactions(transactions);
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
                builder.append(generateTransactionString(transaction));
            }
        }
        transactionsHash = DigestUtils.sha256Hex(builder.toString());
    }
    protected String generateTransactionString(Transaction transaction)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(transaction.getTimestamp());
        if(transaction.getSender() != null)
        {
            builder.append(transaction.getSender());
        }
        builder.append(transaction.getRecipient());
        builder.append(transaction.getAmount());
        return builder.toString();
    }
}

