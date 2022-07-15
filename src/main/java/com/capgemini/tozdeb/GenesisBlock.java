package com.capgemini.tozdeb;

import java.time.Instant;

public class GenesisBlock extends Block{

    GenesisBlock(){
        super(null);
        transactions[0] = new GenesisTransaction(null,"root_user",1000);
        numberOfTransactions++;
        HashTransactions();
    }
}
