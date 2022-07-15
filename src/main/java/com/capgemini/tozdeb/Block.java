package com.capgemini.tozdeb;

import java.time.Instant;
import org.apache.commons.codec.digest.DigestUtils;

class Block {
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

    Block(String PreviousBlockHash){
        previousBlockHash = PreviousBlockHash;
        timestamp = Instant.now().getEpochSecond();
        transactions = new Transaction[10];
    }

    public boolean AddTransaction(Transaction transaction){

        if(transaction == null)
        {
            throw new IllegalArgumentException("transaction to be added must be of non null value");
        }
        if(numberOfTransactions < transactions.length)
        {
            transactions[numberOfTransactions] = transaction;
            numberOfTransactions++;
            HashTransactions();
            return true;
        }
        else
        {
            return false;
        }
    }

    protected void HashTransactions(){
        transactionsHash = String.valueOf(DigestUtils.sha256(String.valueOf(transactions)));
    }
}

