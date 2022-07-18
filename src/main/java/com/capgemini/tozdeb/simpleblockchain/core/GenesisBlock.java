package com.capgemini.tozdeb.simpleblockchain.core;

public class GenesisBlock extends Block{

    GenesisBlock(String recipient, int amount){
        super(null);
        transactions[0] = new GenesisTransaction(null,recipient,amount);
        numberOfTransactions++;
        hashTransactions(transactions);
    }
}
