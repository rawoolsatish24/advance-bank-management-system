package uk.ac.rgu.cm2115;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
    private String address;
    private List<BankAccount> accounts;
    private CustomerType type;

    public Customer(String name, String address, CustomerType type) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.accounts = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public CustomerType getType() { return type; }
    public void setType(CustomerType type) { this.type = type; }

    public List<BankAccount> getAccounts() { return accounts; }
    public void setAccounts(List<BankAccount> accounts) { this.accounts = accounts; }

    public boolean openAccount(BankAccount account) {
        if (account != null) {
            accounts.add(account);
            return true;
        }
        return false;
    }
    
    public int totalBalance() {
    	int totalBalance = 0;
    	for (BankAccount curAccount : accounts) { totalBalance += curAccount.getBalance(); }
    	return totalBalance;
    }

    @Override
    public String toString() {
    	StringBuilder sbCustomerDetails = new StringBuilder();
        sbCustomerDetails.append("Customer Information\n");
        sbCustomerDetails.append("------------------------------------------------\n");
        sbCustomerDetails.append("Name: ").append(name).append("\n");
        sbCustomerDetails.append("Address: ").append(address).append("\n");
        sbCustomerDetails.append("Customer Type: ").append(type).append("\n");
        sbCustomerDetails.append("Accounts:\n");
        sbCustomerDetails.append("------------------------------------------------\n");

        for (BankAccount curAccount : accounts) {
        	sbCustomerDetails.append(curAccount.toString()).append("\n");
        }

        sbCustomerDetails.append("------------------------------------------------\n");
        return sbCustomerDetails.toString();
    }
}
