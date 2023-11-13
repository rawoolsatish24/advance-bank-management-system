package uk.ac.rgu.cm2115;

public class BasicAccount extends BankAccount implements CurrentAccount {
	private int transactionFee = 1;
	
	public BasicAccount(String accountName, int accountNumber, int sortCode) {
        super(accountName, accountNumber, sortCode);
    }
	
	@Override
	public void processCardTransaction(int amount) {
		if (amount > 0) { deposit(amount); } else if (amount < 0) { withdraw(Math.abs(amount) + transactionFee); }
	}
	
	@Override
    public String toString() {
        return "Basic Account [Account Name: " + accountName + ", Account Number: " + accountNumber + ", Sort Code: " + sortCode + ", Balance: " + balance + "]";
    }
}
