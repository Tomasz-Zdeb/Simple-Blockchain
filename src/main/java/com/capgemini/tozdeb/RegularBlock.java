package com.capgemini.tozdeb;

public class RegularBlock extends Block{

    RegularBlock(String PreviousBlockHash) {
        super(PreviousBlockHash);
        if(PreviousBlockHash == null)
        {
            throw new IllegalArgumentException("previous block hash can't be null");
        }
    }

}
