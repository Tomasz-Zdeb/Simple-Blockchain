package com.capgemini;

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

    private String previousBlockHash;
    private long timestamp;
    private String transactionsHash;
    private Transaction[] transactions;

    Block(String PreviousBlockHash){
        previousBlockHash = PreviousBlockHash;
        timestamp = Instant.now().getEpochSecond();
        transactions = new Transaction[10];
    }

    public boolean AddTransaction(){
        return true; //TO IMPLEMENT
    }
    public void HashTransactions(){

    }
}

