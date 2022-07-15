package com.capgemini.tozdeb.simpleblockchain.core;

public class RegularTransaction extends Transaction{
    RegularTransaction(String sender, String recipient, int amount){
        super(sender,recipient,amount);
        if(sender == null || sender.length() == 0 || sender.isEmpty())
        {
            throw new IllegalArgumentException("sender can't be null or empty in a non-genesis transaction");
        }
    }
    RegularTransaction(String sender, String recipient){
        super(sender,recipient);
        if(sender == null || sender.length() == 0 || sender.isEmpty())
        {
            throw new IllegalArgumentException("sender  can't be null or empty in a non-genesis transaction");
        }
    }
}
