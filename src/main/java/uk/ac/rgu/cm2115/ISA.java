package uk.ac.rgu.cm2115;

public class ISA extends BankAccount implements SavingsAccount {
	private int remainingAllowance;
	private int interestRate = 6;

    public ISA(String accountName, int accountNumber, int sortCode, int remainingAllowance) {
        super(accountName, accountNumber, sortCode);
        this.remainingAllowance = remainingAllowance;
    }

    public int getRemainingAllowance() { return remainingAllowance; }
    public void depositAllowance(int allowanceAmount) { this.remainingAllowance += allowanceAmount; }
    
    @Override
    public void applyInterest() { balance += (balance * interestRate / 100); }
    
    @Override
	public int getInterestRate() { return interestRate; }
	
    @Override
	public void setInterestRate(int rate) { interestRate = rate; }
    
    @Override
    public boolean deposit(int amount) {
        if (amount > 0 && amount <= remainingAllowance) {
            balance += amount;
            remainingAllowance -= amount;
            return true;
        } else { return false; }
    }
    
    @Override
    public String toString() {
        return "ISA Account [Account Name: " + accountName + ", Account Number: " + accountNumber + ", Sort Code: " + sortCode + ", Balance: " + balance + ", Remaining Allowance: " + remainingAllowance + "]";
    }
}
