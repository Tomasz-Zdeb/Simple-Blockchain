package com.capgemini.tozdeb.simpleblockchain.core;

public class RegularBlock extends Block{

    RegularBlock(String PreviousBlockHash, RegularTransaction transaction) {
        super(PreviousBlockHash);
        if(PreviousBlockHash == null)
        {
            throw new IllegalArgumentException("previous block hash can't be null");
        }
        AddTransaction(transaction);
    }

}
