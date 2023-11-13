package uk.ac.rgu.cm2115;
import java.util.ArrayList;
import java.util.List;

public class RewardAccount extends BankAccount implements CurrentAccount, SavingsAccount {
    private List<String> rewards;
    private int interestRate = 3;
    private int depositBonus = 2;

    public RewardAccount(String accountName, int accountNumber, int sortCode) {
        super(accountName, accountNumber, sortCode);
        rewards = new ArrayList<>();
    }

    public List<String> getRewards() { return rewards; }
    public void addReward(String reward) { rewards.add(reward); }
    
    @Override
	public void processCardTransaction(int amount) {
    	if (amount > 0) { deposit(amount + depositBonus); } else if (amount < 0) { withdraw(Math.abs(amount)); }
	}
    
    @Override
    public void applyInterest() { balance += (balance * interestRate / 100); }
    
    @Override
	public int getInterestRate() { return interestRate; }
	
    @Override
	public void setInterestRate(int rate) { interestRate = rate; }

    @Override
    public String toString() {
        return "Reward Account [Account Name: " + accountName + ", Account Number: " + accountNumber + ", Sort Code: " + sortCode + ", Balance: " + balance + ", Rewards: " + rewards + "]";
    }
}
