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
        this.amount = amount;
    }
    Transaction(String recipient, int amount)
    {
        this.timestamp = Instant.now().getEpochSecond();
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
}
