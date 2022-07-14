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

    private String previousBlockHash;
    private long timestamp;
    private String transactionsHash;
    private Transaction[] transactions;

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
    public boolean AddGenesisTransaction() {
        return false;
    }
    //TO IMPLEMENT
    public void HashTransactions(){

    }
}

