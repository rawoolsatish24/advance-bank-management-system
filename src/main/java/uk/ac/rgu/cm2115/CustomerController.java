package uk.ac.rgu.cm2115;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class CustomerController<T> extends Controller<T> {

	protected T model;
	
	@FXML
	private AnchorPane apCustomer;
	
	@FXML
	private Label lblTitle;
	@FXML
	private Line lnHeader1;
	@FXML
	private Line lnHeader2;
	@FXML
	private Line lnHeader3;
	
	@FXML
	private TextField txtName;
	@FXML
	private TextArea txtAddress;
	@FXML
	private RadioButton rbIndividual;
	@FXML
	private RadioButton rbBusiness;
	@FXML
	private RadioButton rbCharity;
	@FXML
	private Button btnReset;
	@FXML
	private Button btnEditCustomer;
	@FXML
	private Button btnAccountManager;
	@FXML
    private TitledPane tpEditCustomer;
	@FXML
	private Button btnReloadCustomer;
	
	@FXML
	private RadioButton rbSavings;
	@FXML
	private RadioButton rbCurrent;
	@FXML
	private RadioButton rbReward;
	@FXML
	private RadioButton rbBasic;
	@FXML
	private RadioButton rbISA;
	@FXML
	private TextField txtAccountNo;
	@FXML
	private TextField txtAccountName;
	@FXML
	private TextField txtSortCode;
	@FXML
	private TextField txtBalance;
	@FXML
	private Label lblInterestRate;
	@FXML
	private TextField txtInterestRate;
	@FXML
	private Label lblAllowance;
	@FXML
	private TextField txtAllowance;
	@FXML
	private Button btnAReset;
	@FXML
	private Button btnAddBankAccount;
	@FXML
	private ListView<String> lvAccRewards;
	@FXML
	private Button btnRemoveReward;
	@FXML
	private Button btnAddReward;
	@FXML
	private Button btnRemoveAllRewards;
	@FXML
	private TitledPane tpAddBankAccount;
	
	@FXML
	private ListView<String> lvBankAccounts;
	@FXML
	private Label lblBankAccounts;
	
	private long mouseLastClickTime = 0;
	private ToggleGroup rbCustomerType = new ToggleGroup();
	private ToggleGroup rbAccountType = new ToggleGroup();
	private ToggleGroup rbAccountFeatures = new ToggleGroup();
	private RadioButton selectedCustomerType;
	private RadioButton selectedAccountType;
	private RadioButton selectedAccountFeatures;
	private ObservableList<String> obAccRewards = FXCollections.observableArrayList();
	private ObservableList<String> obBankAccounts = FXCollections.observableArrayList();
	private ObservableList<String> remainingAccountRewards = FXCollections.observableArrayList();
	private ObservableList<String> lsAccountRewards = FXCollections.observableArrayList("FREE COFFEE", "MOVIE TICKETS", "DISCOUNT COUPONS", "SPA DAY", "CASHBACK", "TRAVEL VOUCHER", "AMAZON GIFT CARD", "SHOPPING DISCOUNTS", "RESTAURANT GIFT CARD", "FITNESS CLASSES");
	
	private Customer selectedCustomer;
	
	private RewardAccount tmpRewardAccount = new RewardAccount("", 0, 0);
	private ISA tmpISA = new ISA("", 0, 0, 0);
	
	public void setModel(T model) {
		this.model = model;
		loadCustomerDetails();
	}
	
	private void loadCustomerDetails() {
		selectedCustomer = CommonMethods.getSelectedCustomer();
		txtName.setText(selectedCustomer.getName());
		txtAddress.setText(selectedCustomer.getAddress());
		if(selectedCustomer.getType().equals(CustomerType.INDIVIDUAL)) { rbIndividual.setSelected(true); }
		else if(selectedCustomer.getType().equals(CustomerType.BUSINESS)) { rbBusiness.setSelected(true); }
		else { rbCharity.setSelected(true); }
		resetBankAccountForm();
		tpAddBankAccount.setExpanded(false);
		obAccRewards.clear();
		remainingAccountRewards.clear();
		remainingAccountRewards.addAll(lsAccountRewards);
		loadBankAccountDetails();
	}
	
	private void loadBankAccountDetails() {
		obBankAccounts.clear();
		lblBankAccounts.setText("All bank account Details || " + String.valueOf(selectedCustomer.getAccounts().size()) + " accounts || Total Balance: " + String.valueOf(selectedCustomer.totalBalance()));
		for (BankAccount curBankAccount : selectedCustomer.getAccounts()) {
			if (curBankAccount instanceof BasicAccount) { obBankAccounts.add("Account Type: Basic Account"); }
			else if (curBankAccount instanceof RewardAccount) { obBankAccounts.add("Account Type: Reward Account"); }
			else if (curBankAccount instanceof ISA) { obBankAccounts.add("Account Type: Individual Savings Account"); }
			obBankAccounts.add("Account Number: " + curBankAccount.getAccountNumber());
			obBankAccounts.add("Account Name: " + curBankAccount.getName());
			obBankAccounts.add("Sort Code: " + curBankAccount.getSortCode());
			obBankAccounts.add("Balance: " + curBankAccount.getBalance());
			if (curBankAccount instanceof BasicAccount) { obBankAccounts.add(""); }
			else if (curBankAccount instanceof RewardAccount) { obBankAccounts.add("Interest Rate: " + ((RewardAccount)curBankAccount).getInterestRate() + " || Rewards: " + String.join(", ", ((RewardAccount)curBankAccount).getRewards())); }
			else if (curBankAccount instanceof ISA) { obBankAccounts.add("Interest Rate: " + ((ISA)curBankAccount).getInterestRate() + " || Allowance: " + ((ISA)curBankAccount).getRemainingAllowance()); }
			obBankAccounts.add("======================================================");
		}
	}
	
	@FXML
    private void lvBankAccounts_MouseDoubleClicked(MouseEvent event) {
    	long mouseCurrentClickTime = System.currentTimeMillis();
        if (mouseCurrentClickTime - mouseLastClickTime <= 500) { zoomBankAccountOptions(); }
        mouseLastClickTime = mouseCurrentClickTime;
    }

    @FXML
    private void lvBankAccounts_KeyPressed(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) { zoomBankAccountOptions(); }
    }
    
    private void zoomBankAccountOptions() {
    	int selectedRowIndex = lvBankAccounts.getSelectionModel().getSelectedIndex();
        if (selectedRowIndex >= 0 && (selectedRowIndex % 7 == 4) && obBankAccounts.get(selectedRowIndex).contains("Balance: ")) {
        	BankAccount selectedBankAccount = selectedCustomer.getAccounts().get(selectedRowIndex / 7);
        	int accountType = (selectedBankAccount instanceof BasicAccount) ? 1 : (selectedBankAccount instanceof RewardAccount) ? 2 : 3;
        	Dialog<ButtonType> objAmountOperation = new Dialog<>();
        	objAmountOperation.setTitle("Update balance");
        	objAmountOperation.setHeaderText("Select whether to deposit/withdraw money!");
        	objAmountOperation.setContentText("Choose an option:");
        	ButtonType btDeposit = new ButtonType("Deposit");
            ButtonType btWithdraw = new ButtonType("Withdraw");
            ButtonType btCardTransaction = new ButtonType("Card Transaction");
            ButtonType btAllowance = new ButtonType("Allowance");
            ButtonType btUpdateInterestRate = new ButtonType("Update Interest Rate");
            ButtonType btApplyInterestRate = new ButtonType("Apply Interest Rate");
            if(accountType == 1) { objAmountOperation.getDialogPane().getButtonTypes().addAll(btDeposit, btWithdraw, btCardTransaction, ButtonType.CANCEL); }
            else if(accountType == 2) { objAmountOperation.getDialogPane().getButtonTypes().addAll(btDeposit, btWithdraw, btCardTransaction, btUpdateInterestRate, btApplyInterestRate, ButtonType.CANCEL); }
            else { objAmountOperation.getDialogPane().getButtonTypes().addAll(btDeposit, btWithdraw, btAllowance, btUpdateInterestRate, btApplyInterestRate, ButtonType.CANCEL); }
            
        	objAmountOperation.showAndWait().ifPresent(result -> {
        		if (result == btDeposit || result == btWithdraw) { 
                	TextInputDialog userInput = new TextInputDialog();
                	String type = (result == btDeposit) ? "deposit" : "withdraw";
                	userInput.setTitle("Bank account " + type + " money");
                	userInput.setHeaderText("Please enter amount!");
                	userInput.setContentText("Amount: ");
                	userInput.showAndWait().ifPresent(input -> { 
                		try {
                            int amount = Integer.parseInt(input);
                            boolean response = false;
                            if (accountType == 3) { 
                            	if(result == btDeposit) { response = ((ISA)selectedBankAccount).deposit(amount); }
                            	else { response = ((ISA)selectedBankAccount).withdraw(amount); }
                        	}
                			else if (accountType == 2) { 
                				if(result == btDeposit) { response = ((RewardAccount)selectedBankAccount).deposit(amount); }
                            	else { response = ((RewardAccount)selectedBankAccount).withdraw(amount); }
                			}
                			else { 
                				if(result == btDeposit) { response = ((BasicAccount)selectedBankAccount).deposit(amount); }
                            	else { response = ((BasicAccount)selectedBankAccount).withdraw(amount); }
                			}
                            if(response) { CommonMethods.showSuccessAlert("Operation performed successfully...!"); }
                            else { CommonMethods.showErrorAlert("Operation failed, try again later...!!"); }
                            loadBankAccountDetails();
                        } catch (Exception ex) { CommonMethods.showErrorAlert("Invalid amount, please enter valid integer amount...!!"); }
            		});
            	}
        		else if(accountType < 3 && result == btCardTransaction) {
        			TextInputDialog userInput = new TextInputDialog();
                	userInput.setTitle("Card Transaction");
                	userInput.setHeaderText("Please enter amount, positive amount to deposit and negative amount to withdraw!");
                	userInput.setContentText("Amount: ");
                	userInput.showAndWait().ifPresent(input -> { 
                		try {
                            int amount = Integer.parseInt(input);
                            if (accountType == 2) { ((RewardAccount)selectedBankAccount).processCardTransaction(amount); }
                            else { ((BasicAccount)selectedBankAccount).processCardTransaction(amount); }
                            CommonMethods.showSuccessAlert("Operation performed successfully...!");
                            loadBankAccountDetails();
                        } catch (Exception ex) { CommonMethods.showErrorAlert("Invalid amount, please enter valid integer amount...!!"); }
            		});
        		}
        		else if(accountType > 1 && result == btUpdateInterestRate) {
        			int currentInterestRate = (accountType == 2) ? ((RewardAccount)selectedBankAccount).getInterestRate() : ((ISA)selectedBankAccount).getInterestRate();
        			TextInputDialog userInput = new TextInputDialog(String.valueOf(currentInterestRate));
                	userInput.setTitle("Interest Rate");
                	userInput.setHeaderText("Please enter integer interest rate between [0-100]!");
                	userInput.setContentText("Rate (%): ");
                	userInput.showAndWait().ifPresent(input -> { 
                		try {
                            int interestRate = Integer.parseInt(input);
                            if(interestRate >= 0 && interestRate <= 100) {
                            	if (accountType == 2) { ((RewardAccount)selectedBankAccount).setInterestRate(interestRate); }
                                else { ((ISA)selectedBankAccount).setInterestRate(interestRate); }
                            	CommonMethods.showSuccessAlert("Interest rate updated successfully...!");
                            } else { CommonMethods.showErrorAlert("Interest rate must be positive integer between [0-100]...!!"); }
                            loadBankAccountDetails();
                        } catch (Exception ex) { CommonMethods.showErrorAlert("Invalid amount, please enter valid integer amount...!!"); }
            		});
        		}
        		else if(accountType > 1 && result == btApplyInterestRate) {
        			Dialog<ButtonType> objConfirmation = new Dialog<>();
        			objConfirmation.setTitle("Confirmation");
        			objConfirmation.setHeaderText("Are you sure want to apply interest rate to balance?");
        			objConfirmation.setContentText("Choose an option:");
        	        objConfirmation.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        	        objConfirmation.showAndWait().ifPresent(response -> {
        	            if (response == ButtonType.OK) { 
        	            	if (accountType == 2) { ((RewardAccount)selectedBankAccount).applyInterest(); }
                            else { ((ISA)selectedBankAccount).applyInterest(); }
        	            	CommonMethods.showSuccessAlert("Interest rate applied successfully...!");
                        	loadBankAccountDetails();
        	        	}
        	        });
        		}
        		else if(accountType == 3 && result == btAllowance) {
        			TextInputDialog userInput = new TextInputDialog();
                	userInput.setTitle("Allowance");
                	userInput.setHeaderText("Please enter amount to deposit in allowance!");
                	userInput.setContentText("Amount: ");
                	userInput.showAndWait().ifPresent(input -> { 
                		try {
                            int amount = Integer.parseInt(input);
                            if(amount > 0) {
                            	((ISA)selectedBankAccount).depositAllowance(amount);
                            	CommonMethods.showSuccessAlert("Allowance deposited successfully...!");
                            } else { CommonMethods.showErrorAlert("Amount must be positive integer more than 0...!!"); }
                            loadBankAccountDetails();
                        } catch (Exception ex) { CommonMethods.showErrorAlert("Invalid amount, please enter valid integer amount...!!"); }
            		});
        		}
            });
        }
    }
	
	public void initialize() {
		Background background = new Background(new BackgroundFill(Color.web(CommonMethods.theme), null, null));
        apCustomer.setBackground(background);
        Paint contentColor = (CommonMethods.theme == "#000000") ? Color.WHITE : Color.BLACK;
        lblTitle.setTextFill(contentColor);
    	lnHeader1.setStroke(contentColor);
    	lnHeader2.setStroke(contentColor);
    	lnHeader3.setStroke(contentColor);
    	lblBankAccounts.setTextFill(contentColor);
		
		lvAccRewards.setItems(obAccRewards);
		lvBankAccounts.setItems(obBankAccounts);
		
		rbIndividual.setToggleGroup(rbCustomerType);
    	rbBusiness.setToggleGroup(rbCustomerType);
    	rbCharity.setToggleGroup(rbCustomerType);
    	
    	rbSavings.setToggleGroup(rbAccountType);
    	rbCurrent.setToggleGroup(rbAccountType);
    	
    	rbReward.setToggleGroup(rbAccountFeatures);
    	rbBasic.setToggleGroup(rbAccountFeatures);
    	rbISA.setToggleGroup(rbAccountFeatures);
    	
    	tpEditCustomer.setCollapsible(false);
    	lvBankAccounts.setVisible(true);
    	tpAddBankAccount.expandedProperty().addListener((observable, oldValue, newValue) -> { 
    		lvBankAccounts.setVisible(!newValue); 
    		lblBankAccounts.setVisible(!newValue); 
		});
    	txtName.textProperty().addListener((observable, oldValue, newValue) -> { if (newValue.length() > 25) { txtName.setText(oldValue); } });
    	txtAddress.textProperty().addListener((observable, oldValue, newValue) -> { if (newValue.length() > 150) { txtAddress.setText(oldValue); } });
    	txtAccountNo.textProperty().addListener((observable, oldValue, newValue) -> { 
    		if (newValue.length() > 9) { txtAccountNo.setText(oldValue); } 
    		if (!newValue.matches("\\d*")) { txtAccountNo.setText(newValue.replaceAll("[^\\d]", "")); }
		});
    	txtAccountName.textProperty().addListener((observable, oldValue, newValue) -> { if (newValue.length() > 35) { txtAccountName.setText(oldValue); } });
    	txtSortCode.textProperty().addListener((observable, oldValue, newValue) -> { 
    		if (newValue.length() > 6) { txtSortCode.setText(oldValue); }
    		if (!newValue.matches("\\d*")) { txtSortCode.setText(newValue.replaceAll("[^\\d]", "")); }
    	});
    	txtBalance.textProperty().addListener((observable, oldValue, newValue) -> { 
    		if (newValue.length() > 6) { txtBalance.setText(oldValue); }
    		if (!newValue.matches("\\d*")) { txtBalance.setText(newValue.replaceAll("[^\\d]", "")); }
    	});
    	txtAllowance.textProperty().addListener((observable, oldValue, newValue) -> { 
    		if (newValue.length() > 6) { txtAllowance.setText(oldValue); }
    		if (!newValue.matches("\\d*")) { txtAllowance.setText(newValue.replaceAll("[^\\d]", "")); }
    	});
    	txtInterestRate.textProperty().addListener((observable, oldValue, newValue) -> { 
    		if (newValue.length() > 3) { txtInterestRate.setText(oldValue); }
    		if (!newValue.matches("\\d*")) { txtInterestRate.setText(newValue.replaceAll("[^\\d]", "")); }
    	});
    	rbAccountType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
    		rbReward.setSelected(false);
    		rbBasic.setSelected(false);
    		rbISA.setSelected(false);
    		
    		lblInterestRate.setVisible(false);
    		txtInterestRate.setVisible(false);
    		txtInterestRate.setText("0");
    		
    		if(newValue == null) {
    			lblInterestRate.setVisible(false);
        		txtInterestRate.setVisible(false);
    		}
    		else if (newValue.equals(rbSavings)) {
            	rbBasic.setVisible(false);
        		rbISA.setVisible(true);
        		lblInterestRate.setVisible(true);
        		txtInterestRate.setVisible(true);
            }
    		else if (newValue.equals(rbCurrent)) {
            	rbBasic.setVisible(true);
        		rbISA.setVisible(false);
            }
        });
    	rbAccountFeatures.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
    		lblAllowance.setVisible(false);
    		txtAllowance.setVisible(false);
    		txtAllowance.setText("0");
    		
    		lvAccRewards.setVisible(false);
    		btnAddReward.setVisible(false);
    		btnRemoveReward.setVisible(false);
    		btnRemoveAllRewards.setVisible(false);
    		obAccRewards.clear();
    		
    		if(newValue == null) { txtInterestRate.setText("0"); }
    		else if (newValue.equals(rbReward)) {
    			txtInterestRate.setText(String.valueOf(tmpRewardAccount.getInterestRate()));
    			lvAccRewards.setVisible(true);
        		btnAddReward.setVisible(true);
        		btnRemoveReward.setVisible(true);
        		btnRemoveAllRewards.setVisible(true);
    		}
    		else if (newValue.equals(rbISA)) { 
    			txtInterestRate.setText(String.valueOf(tmpISA.getInterestRate())); 
    			lblAllowance.setVisible(true);
        		txtAllowance.setVisible(true);
			}
    		else { txtInterestRate.setText("0"); }
    	});
	}
	
	@FXML
	private void btnAccountManager_Clicked(ActionEvent event) {
		try { MainApp.setScene("AccountManager", ""); }
    	catch (IOException ex) { CommonMethods.showErrorAlert("Some problems while opening account manager...!!"); }
	}
	
	@FXML
	private void btnReloadCustomer_Clicked(ActionEvent event) {
		loadCustomerDetails();
	}
	
	@FXML
	private void btnRemoveReward_Clicked(ActionEvent event) {
		int selectedRewardIndex = lvAccRewards.getSelectionModel().getSelectedIndex();
        if (selectedRewardIndex >= 0) {
        	remainingAccountRewards.add(obAccRewards.get(selectedRewardIndex));
        	obAccRewards.remove(selectedRewardIndex);
        } else { CommonMethods.showWarningAlert("Please select reward to remove!"); }
	}
	
	@FXML
	private void btnAddReward_Clicked(ActionEvent event) {
		if(remainingAccountRewards.size() == 0) {
			CommonMethods.showWarningAlert("All available rewards have already been added!");
			return;
		}
		ChoiceDialog<String> objChoiceDialog = new ChoiceDialog<>(remainingAccountRewards.get(0), remainingAccountRewards);
		objChoiceDialog.setTitle("Add rewards");
		objChoiceDialog.setHeaderText("Select a reward from the list to add.");
		objChoiceDialog.setContentText("Choose an option:");

		objChoiceDialog.showAndWait().ifPresent(choice -> {
        	remainingAccountRewards.remove(choice);
        	obAccRewards.add(choice);
        });
	}
	
	@FXML
	private void btnRemoveAllRewards_Clicked(ActionEvent event) {
		Dialog<ButtonType> objConfirmation = new Dialog<>();
		objConfirmation.setTitle("Confirmation");
		objConfirmation.setHeaderText("Are you sure to remove all the added rewards?");
		objConfirmation.setContentText("Choose an option:");
        objConfirmation.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        objConfirmation.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) { 
            	obAccRewards.clear(); 
            	remainingAccountRewards.clear();
            	remainingAccountRewards.addAll(lsAccountRewards);
        	}
        });
	}
	
	@FXML
	private void btnEditCustomer_Clicked(ActionEvent event) {
		txtName.setText(CommonMethods.filterInput(txtName.getText()));
    	txtAddress.setText(CommonMethods.filterInput(txtAddress.getText()));
    	selectedCustomerType = (RadioButton) rbCustomerType.getSelectedToggle();
    	if (txtName.getText().isEmpty()) { 
    		CommonMethods.showWarningAlert("Customer name cannot be blank!");
    		txtName.requestFocus();
		}
    	else if (txtAddress.getText().isEmpty()) {
    		CommonMethods.showWarningAlert("Customer address cannot be blank!");
			txtAddress.requestFocus();
    	}
    	else if (selectedCustomerType == null) { CommonMethods.showWarningAlert("Please choose customer type!"); }
    	else {
    		CustomerType currentCustomerType = 
				(selectedCustomerType.getText().equals("Individual")) 
				? CustomerType.INDIVIDUAL
				: (selectedCustomerType.getText().equals("Business"))
				? CustomerType.BUSINESS
				: CustomerType.CHARITY;
    		Customer updatedCustomerDetails = new Customer(txtName.getText(), txtAddress.getText(), currentCustomerType);
    		updatedCustomerDetails.setAccounts(selectedCustomer.getAccounts());
        	boolean is_successfull = CommonMethods.bankAccountManager.editCustomer(updatedCustomerDetails, CommonMethods.customerIndex);
        	if(is_successfull) {
        		CommonMethods.showSuccessAlert("Customer details edited successfully...!");
        		loadCustomerDetails();
        	}
        	else { CommonMethods.showErrorAlert("Please enter valid customer details with unique name...!!"); }
    	}
	}
	
	@FXML
	private void btnReset_Clicked(ActionEvent event) { resetCustomerForm(); }
	
	@FXML
	private void btnAddBankAccount_Clicked(ActionEvent event) { 
		selectedAccountType = (RadioButton) rbAccountType.getSelectedToggle();
		selectedAccountFeatures = (RadioButton) rbAccountFeatures.getSelectedToggle();
		txtAccountName.setText(CommonMethods.filterInput(txtAccountName.getText()));
		if (selectedAccountType == null) { CommonMethods.showWarningAlert("Please choose bank account type!"); }
		else if (selectedAccountFeatures == null) { CommonMethods.showWarningAlert("Please choose account features!"); }
		else if (txtAccountNo.getText().isEmpty()) { 
			CommonMethods.showWarningAlert("Bank account number cannot be blank!");
			txtAccountNo.requestFocus();
		}
		else if (txtAccountName.getText().isEmpty()) { 
			CommonMethods.showWarningAlert("Bank account name cannot be blank!");
			txtAccountName.requestFocus();
		}
		else if (txtSortCode.getText().isEmpty() || txtSortCode.getText().length() != 6) { 
			CommonMethods.showWarningAlert("Bank sort code cannot be blank and must be 6 digits!");
			txtSortCode.requestFocus();
		}
		else if (txtBalance.getText().isEmpty()) { 
			CommonMethods.showWarningAlert("Bank balance cannot be blank!");
			txtBalance.requestFocus();
		}
		else if (selectedAccountType.equals(rbSavings) && txtInterestRate.getText().isEmpty()) { 
			CommonMethods.showWarningAlert("Bank interest rate cannot be blank!");
			txtInterestRate.requestFocus();
		}
		else if (selectedAccountFeatures.equals(rbISA) && txtAllowance.getText().isEmpty()) { 
			CommonMethods.showWarningAlert("Account allowance cannot be blank!");
			txtInterestRate.requestFocus();
		}
		else {
			int accountNo = Integer.parseInt(txtAccountNo.getText());
			int sortCode = Integer.parseInt(txtSortCode.getText());
			int openingBalance = Integer.parseInt(txtBalance.getText());
			int interestRate = Integer.parseInt(txtInterestRate.getText());
			int allowance = Integer.parseInt(txtAllowance.getText());
			if(accountNo == 0 || !CommonMethods.bankAccountManager.isAccountNoAvailable(accountNo)) {
				CommonMethods.showWarningAlert("Please enter valid and unique account number!");
				txtAccountNo.requestFocus();
			}
			else if(selectedAccountType.equals(rbSavings) && (interestRate < 0 || interestRate > 100)) {
				CommonMethods.showWarningAlert("Bank interest rate must be between [0-100]!");
				txtInterestRate.requestFocus();
			}
			else {
				BankAccount objNewBankAccount = null;

				if(selectedAccountFeatures.equals(rbBasic)) { 
					objNewBankAccount = new BasicAccount(txtAccountName.getText(), accountNo, sortCode);
			    } else if(selectedAccountFeatures.equals(rbReward)) { 
			    	objNewBankAccount = new RewardAccount(txtAccountName.getText(), accountNo, sortCode);
			    	((RewardAccount)objNewBankAccount).setInterestRate(interestRate);
			    	for (String curReward : obAccRewards) { ((RewardAccount)objNewBankAccount).addReward(curReward); }
			    } else if(selectedAccountFeatures.equals(rbISA)) { 
			    	objNewBankAccount = new ISA(txtAccountName.getText(), accountNo, sortCode, allowance);
			    	((ISA)objNewBankAccount).setInterestRate(interestRate);
			    }

				if(openingBalance > 0) { objNewBankAccount.deposit(openingBalance); }

			    if (selectedCustomer.openAccount(objNewBankAccount)) {
			    	CommonMethods.showSuccessAlert("New bank account added successfully...!");
			        loadCustomerDetails();
			    } else { CommonMethods.showErrorAlert("Some issues while adding a new bank account...!!"); }
			}
		}
	}
	
	@FXML
	private void btnAReset_Clicked(ActionEvent event) { resetBankAccountForm(); }
	
	private void resetCustomerForm() {
    	txtName.clear();
    	txtAddress.clear();
    	rbIndividual.setSelected(false);
    	rbBusiness.setSelected(false);
    	rbCharity.setSelected(false);
    }
	
	private void resetBankAccountForm() {
    	txtAccountNo.clear();
    	txtAccountName.clear();
    	txtBalance.clear();
    	txtSortCode.clear();
    	txtInterestRate.setText("0");
    	rbSavings.setSelected(false);
    	rbCurrent.setSelected(false);
    	rbBasic.setSelected(false);
    	rbReward.setSelected(false);
    	rbISA.setSelected(false);
    }
}