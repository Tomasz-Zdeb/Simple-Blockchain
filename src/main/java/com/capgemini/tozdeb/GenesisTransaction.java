package com.capgemini.tozdeb;

public class GenesisTransaction extends Transaction{
    GenesisTransaction(String sender, String recipient, int amount){
        super(sender,recipient,amount);
        if(sender != null)
        {
            throw new IllegalArgumentException("genesis transaction sender must be null");
        }
        if(amount == 0)
        {
            throw new IllegalArgumentException("genesis transaction amount can't be equal to zero");
        }
    }
}
