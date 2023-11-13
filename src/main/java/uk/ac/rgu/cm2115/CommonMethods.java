package uk.ac.rgu.cm2115;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CommonMethods {
	public static AccountManager bankAccountManager;
	public static int customerIndex = 0;
	public static String theme;
	public static Preferences preferences = Preferences.userNodeForPackage(MainApp.class);
	
	public static void showSuccessAlert(String message) {
        Alert objAlert = new Alert(AlertType.INFORMATION);
        objAlert.setTitle("Sapphire Horizon Bank");
        objAlert.setHeaderText(null);
        objAlert.setContentText(message);
        objAlert.showAndWait();
    }
	
	public static void showWarningAlert(String message) {
        Alert objAlert = new Alert(AlertType.WARNING);
        objAlert.setTitle("Sapphire Horizon Bank");
        objAlert.setHeaderText(null);
        objAlert.setContentText(message);
        objAlert.showAndWait();
    }
    
    public static void showErrorAlert(String message) {
        Alert objAlert = new Alert(AlertType.ERROR);
        objAlert.setTitle("Sapphire Horizon Bank");
        objAlert.setHeaderText(null);
        objAlert.setContentText(message);
        objAlert.showAndWait();
    }
    
    public static String filterInput(String input) { return input.replaceAll("-", "").replaceAll("\"", "").trim().toUpperCase(); }
    
    public static Customer getSelectedCustomer() { return bankAccountManager.getCustomer(customerIndex); }
    
    public static void saveDataToCSV() {
//    	if("1".equals(String.valueOf(1))) { return; }
    	try {
            CSVWriter csvCustomerWriter = new CSVWriter(new FileWriter("customer_master.csv"));
            CSVWriter csvBankWriter = new CSVWriter(new FileWriter("customer_bank_master.csv"));
            String[] row;
            
            File oldFile = new File("customer_master.csv");
    		if (oldFile.exists()) { oldFile.delete(); }
    		oldFile = new File("customer_bank_master.csv");
    		if (oldFile.exists()) { oldFile.delete(); }
            
            row = new String[]{"Name", "Address", "Type"};
            csvCustomerWriter.writeNext(row);
            row = new String[]{"Customer", "Account Number", "Account Name", "Sort Code", "Balance", "Interest Rate", "Allowance", "Rewards"};
            csvBankWriter.writeNext(row);
            
            for (Customer curCustomer : bankAccountManager.getCustomers()) {
            	row = new String[]{curCustomer.getName(), curCustomer.getAddress(), curCustomer.getType().toString()};
            	csvCustomerWriter.writeNext(row);
            	for (BankAccount curBankAccount : curCustomer.getAccounts()) {
            		if (curBankAccount instanceof BasicAccount) {
            			row = new String[]{curCustomer.getName(), String.valueOf(curBankAccount.getAccountNumber()), curBankAccount.getName(), String.valueOf(curBankAccount.getSortCode()), String.valueOf(curBankAccount.getBalance()), "", "", ""};
            		}
            		else if (curBankAccount instanceof RewardAccount) {
            			row = new String[]{curCustomer.getName(), String.valueOf(curBankAccount.getAccountNumber()), curBankAccount.getName(), String.valueOf(curBankAccount.getSortCode()), String.valueOf(curBankAccount.getBalance()), String.valueOf(((RewardAccount)curBankAccount).getInterestRate()), "", String.join(", ", ((RewardAccount)curBankAccount).getRewards())};
            		}
            		else if (curBankAccount instanceof ISA) {
            			row = new String[]{curCustomer.getName(), String.valueOf(curBankAccount.getAccountNumber()), curBankAccount.getName(), String.valueOf(curBankAccount.getSortCode()), String.valueOf(curBankAccount.getBalance()), String.valueOf(((ISA)curBankAccount).getInterestRate()), String.valueOf(((ISA)curBankAccount).getRemainingAllowance()), ""};
            		}
            		else { continue; }
            		csvBankWriter.writeNext(row);
            	}
			}

            csvCustomerWriter.close();
            csvBankWriter.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public static void readDataFromCSV() {
    	try {
    		CSVReader csvReader = new CSVReader(new FileReader("customer_master.csv"));
    		List<String[]> csvData = csvReader.readAll();
    		int recordsLength = csvData.size();
    		if(recordsLength > 1) {
    			csvData.remove(0);
    			for (String[] row : csvData) {
    				if(row.length == 3) {
    					CustomerType objCustomerType;
    					if(row[2].equals("BUSINESS")) { objCustomerType = CustomerType.BUSINESS; }
    					else if(row[2].equals("CHARITY")) { objCustomerType = CustomerType.CHARITY; }
    					else if(row[2].equals("INDIVIDUAL")) { objCustomerType = CustomerType.INDIVIDUAL; }
    					else { continue; }
    					bankAccountManager.addCustomer(new Customer(row[0], row[1], objCustomerType));
    				}
    			}
    		}
    		csvReader.close();
    		
    		int customersCount = bankAccountManager.getCustomerCount();
    		if(customersCount == 0) { return; }
    		
    		csvReader = new CSVReader(new FileReader("customer_bank_master.csv"));
    		csvData = csvReader.readAll();
    		recordsLength = csvData.size();
    		
    		if(recordsLength > 1) {
    			csvData.remove(0);
    			
    			int curNoCustomer = 0;
        		List<BankAccount> accounts = new ArrayList<>();
        		String curCustomer = csvData.get(0)[0];
        		BankAccount objNewBankAccount = null;

    			for (String[] row : csvData) {
    				if(row.length == 8) {
    					if(curCustomer.equals(row[0])) {
    						objNewBankAccount = addNewBankAccount(row);
    						if(objNewBankAccount != null) { accounts.add(objNewBankAccount); }
    						else { continue; }
    					}
    					else {
    						for(int z = curNoCustomer; z < customersCount; z++) {
    							if(curCustomer.equals(bankAccountManager.getCustomer(z).getName())) {
    								bankAccountManager.getCustomer(z).setAccounts(accounts);
    								curNoCustomer = z;
    								break;
    							}
    						}
    						curCustomer = row[0];
    						accounts = new ArrayList<>();
    						objNewBankAccount = addNewBankAccount(row);
    						if(objNewBankAccount != null) { accounts.add(objNewBankAccount); }
    						else { continue; }
    					}
    				}
    			}
    			for(int z = curNoCustomer; z < customersCount; z++) {
					if(curCustomer.equals(bankAccountManager.getCustomer(z).getName())) {
						bankAccountManager.getCustomer(z).setAccounts(accounts);
						break;
					}
				}
    		}
    		
    		csvReader.close();
    	} catch (Exception e) { e.printStackTrace(); }
    }
    
    private static BankAccount addNewBankAccount(String[] row) {
    	BankAccount objNewBankAccount = null;
    	if(row[5].isEmpty()) { objNewBankAccount = new BasicAccount(row[2], Integer.parseInt(row[1]), Integer.parseInt(row[3])); }
		else if(!row[6].isEmpty()) { 
			objNewBankAccount = new ISA(row[2], Integer.parseInt(row[1]), Integer.parseInt(row[3]), Integer.parseInt(row[6])); 
			((ISA)objNewBankAccount).setInterestRate(Integer.parseInt(row[5]));
		}
		else if(row[6].isEmpty()) { 
			objNewBankAccount = new RewardAccount(row[2], Integer.parseInt(row[1]), Integer.parseInt(row[3]));
			for (String curReward : row[7].split(", ")) { ((RewardAccount)objNewBankAccount).addReward(curReward); }
			((RewardAccount)objNewBankAccount).setInterestRate(Integer.parseInt(row[5]));
		}
		else { return null; }
    	objNewBankAccount.setBalance(Integer.parseInt(row[4]));
		return objNewBankAccount;
    }
}