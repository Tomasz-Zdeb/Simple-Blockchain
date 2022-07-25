package com.capgemini.tozdeb.simpleblockchain.utilities;

import com.capgemini.tozdeb.simpleblockchain.core.*;

public class ValidationService {
    public boolean validateTransaction(Transaction transaction, int senderBalance){
        return transaction.getAmount() <= senderBalance;
    }

    boolean isGenesisTransaction(Transaction transaction){
        return transaction.getSender() == null;
    }

    boolean isGenesisBlock(Block block){
        return isGenesisTransaction(block.getTransactions()[0]);
    }

    public boolean validateBlock(Block block){
        return blockHasTimestampContinuity(block);
    }

    private boolean blockHasTimestampContinuity(Block block){
        for(int i = block.getNumberOfTransactions()-1;i>0;i--){
                if(!(block.getTransactions()[i].getTimestamp() >= block.getTransactions()[i-1].getTimestamp())){
                    return false;
            }
        }
        return true;
    }

    public boolean validateBlockchain(Blockchain blockchain){
        return false;

        //Blockchain is valid when it's block points to previous block hash
        //blockchainHasHashContinuity(blockchain)

        //Blockchain is valid when there is exactly one genesis block

        //Blockchain is valid when latest block timestamp is equal or greater than previous one

        //Blockchain is valid if no transaction results in negative money amount for a user
    }

    boolean blockchainHasHashContinuity(Blockchain blockchain) {
        for (int i = blockchain.getBlocks().size() - 1; i > 0; i--) {
            if (!blockchain.getBlocks()
                    .get(i)
                    .getPreviousBlockHash()
                    .equals(blockchain.getBlocks().get(i - 1).hashBlock())) {
                return false;
            }
        }
        return true;
    }
    private boolean blockchainHasOneGenesisBlock(Blockchain blockchain)
    {
        return false;
    }
    private boolean blockchainHasTimestampContinuity(Blockchain blockchain){
        return false;
    }
    private boolean blockchainTransactionsDoNotResultInNegativeMoneyAmount(Blockchain blockchain){
        return false;
    }

}
