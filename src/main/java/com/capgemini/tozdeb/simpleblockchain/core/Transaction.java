package com.capgemini.tozdeb.simpleblockchain.core;

import java.time.Instant;

class Transaction {
    public long getTimestamp() {
        return timestamp;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public int getAmount() {
        return amount;
    }

    private final long timestamp;
    private final String sender;
    private final String recipient;
    private final int amount;

    Transaction(long timestamp, String sender, String recipient, int amount)
    {
        this.timestamp = timestamp;
        if(sender == null || sender.isEmpty())
        {
            throw new IllegalArgumentException("sender can't be null or empty");
        }
        this.sender = sender;
        if(recipient == null || recipient.isEmpty())
        {
            throw new IllegalArgumentException("recipient can't be null or empty");
        }
        this.recipient = recipient;
        if(amount <= 0)
        {
            throw new IllegalArgumentException("transaction amount can't be negative or equal to zero");
        }
        if(sender.equals(recipient))
        {
            throw new IllegalArgumentException("recipient must differ from sender");
        }
        this.amount = amount;
    }
    Transaction(String sender, String recipient, int amount)
    {
        this(Instant.now().getEpochSecond(),sender,recipient,amount);
    }
    Transaction(long timestamp, String recipient, int amount)
    {
        this.timestamp = timestamp;
        this.sender = null;
        if(recipient == null || recipient.isEmpty())
        {
            throw new IllegalArgumentException("genesis recipient can't be null or empty");
        }
        this.recipient = recipient;
        if(amount <= 0)
        {
            throw new IllegalArgumentException("genesis transaction amount can't be negative or equal to zero");
        }
        this.amount = amount;
    }
    Transaction(String recipient,int amount){
        this(Instant.now().getEpochSecond(),recipient,amount);
    }
    protected String generateTransactionString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(timestamp);
        if(sender != null)
        {
            builder.append(sender);
        }
        builder.append(recipient);
        builder.append(amount);
        return builder.toString();
    }

    public boolean valueOf(Transaction transaction) {
        if (this.timestamp == transaction.timestamp &&
                this.recipient.equals(transaction.recipient) &&
                this.amount == transaction.amount) {
            if(this.sender == null && transaction.sender == null){
                return true;
            }
            return this.sender != null && transaction.sender != null && this.sender.equals(transaction.sender);
        }
        return false;
    }
}
