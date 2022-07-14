package com.capgemini.tozdeb;

import java.time.Instant;

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

    //TO IMPLEMENT
    public boolean AddTransaction(){
        return false;
    }
    //TO IMPLEMENT

    public void HashTransactions(){

    }
}

