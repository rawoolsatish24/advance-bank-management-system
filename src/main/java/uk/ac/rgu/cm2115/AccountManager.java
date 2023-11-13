package uk.ac.rgu.cm2115;
import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private List<Customer> customers;

    public AccountManager() { customers = new ArrayList<>(); }

    public List<Customer> getCustomers() { return customers; }
    public void setCustomers(List<Customer> customers) { this.customers = customers; }

    public boolean addCustomer(Customer customer) {
        if (customer != null) {
        	for (Customer curCustomer : customers) {
        		if(curCustomer.getName().equals(customer.getName())) { return false; }
        	}
            customers.add(customer);
            return true;
        }
        return false;
    }
    
    public Customer getCustomer(int index) {
    	if(!(index >= 0 && index < customers.size())) { return null; }
    	return customers.get(index);
    }
    
    public int getCustomerCount() { return customers.size(); }
    
    public boolean editCustomer(Customer customer, int index) {
        if (customer != null && index >= 0 && index < customers.size()) {
        	for(int z = 0; z < customers.size(); z++) {
        		if(z != index && customers.get(z).getName().equals(customer.getName())) { return false; }
        	}
            customers.set(index, customer);
            return true;
        }
        return false;
    }
    
    public int totalBalance() {
    	int totalBalance = 0;
    	for (Customer curCustomer : customers) { totalBalance += curCustomer.totalBalance(); }
    	return totalBalance;
    }
    
    public boolean isAccountNoAvailable(int accountNo) {
    	for (Customer curCustomer : customers) {
    		for (BankAccount curBankAccount : curCustomer.getAccounts()) {
        		if(curBankAccount.getAccountNumber() == accountNo) { return false; }
        	}
    	}
    	return true;
    }
}
