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

    Transaction(String sender, String recipient, int amount)
    {
        this.timestamp = Instant.now().getEpochSecond();
        this.sender = sender;
        if(recipient == null || recipient.isEmpty())
        {
            throw new IllegalArgumentException("recipient can't be null or empty");
        }
        this.recipient = recipient;
        if(amount < 0)
        {
            throw new IllegalArgumentException("transaction amount can't be negative");
        }
        this.amount = amount;
    }
    Transaction(String sender, String recipient)
    {
        this(sender,recipient,0);
    }
}
