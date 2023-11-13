package uk.ac.rgu.cm2115;

public abstract class BankAccount {
	protected int accountNumber;
    protected int sortCode;
    protected int balance;
    protected final String accountName;

    public BankAccount(String accountName, int accountNumber, int sortCode) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.sortCode = sortCode;
        this.balance = 0;
    }

    public int getAccountNumber() { return accountNumber; }
    public int getSortCode() { return sortCode; }
    public int getBalance() { return balance; }
    public String getName() { return accountName; }

    public boolean deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        else { return false; }
    }

    public boolean withdraw(int amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        else { return false; }
    }
    
    public void setBalance(int balance) { this.balance = balance; }
}
