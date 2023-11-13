package uk.ac.rgu.cm2115;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TitledPane;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.util.Duration;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AccountManagerController<T> extends Controller<T> {

	protected T model;	
	
	@FXML
	private AnchorPane apAccountManager;
	
	@FXML
	private Label lblTitle;
	@FXML
	private Line lnHeader1;
	@FXML
	private Line lnHeader2;
	@FXML
	private Line lnHeader3;
	@FXML
	private Label lblAllCustomerDetails;
	
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
	private Button btnAddCustomer;
	@FXML
    private TitledPane tpAddCustomer;
	
	@FXML
	private Button btnSearch_Clicked;
	@FXML
	private Button btnReload_Clicked;
	@FXML
	private ListView<String> lvCustomerDetails;
	
	@FXML
	private TextField txtCurrentDate;
	@FXML
	private Label lblCurrentDate;
	@FXML
	private Line lnSettingsDivider;
	@FXML
	private Label lblTheme;
	@FXML
	private RadioButton rbDark;
	@FXML
	private RadioButton rbLight;
	@FXML
	private RadioButton rbRed;
	@FXML
	private RadioButton rbOrange;
	@FXML
	private RadioButton rbPink;
	
	private ToggleGroup rbCustomerType = new ToggleGroup();
	private ToggleGroup rbTheme = new ToggleGroup();
	private RadioButton selectedCustomerType;
	private ObservableList<String> obCustomerDetails = FXCollections.observableArrayList();
	private long mouseLastClickTime = 0;
	private Timeline objTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTime()));
	List<Customer> savedCustomerDetails;

    public void initialize() {
    	rbIndividual.setToggleGroup(rbCustomerType);
    	rbBusiness.setToggleGroup(rbCustomerType);
    	rbCharity.setToggleGroup(rbCustomerType);
    	
    	rbDark.setToggleGroup(rbTheme);
    	rbLight.setToggleGroup(rbTheme);
    	rbRed.setToggleGroup(rbTheme);
    	rbOrange.setToggleGroup(rbTheme);
    	rbPink.setToggleGroup(rbTheme);
    	
    	lvCustomerDetails.setItems(obCustomerDetails);
    	
    	objTimeline.setCycleCount(Timeline.INDEFINITE);
    	objTimeline.play();
    	changeSettingsVisibility(false);
    	
    	tpAddCustomer.expandedProperty().addListener((observable, oldValue, newValue) -> { changeSettingsVisibility(!newValue); });
    	txtName.textProperty().addListener((observable, oldValue, newValue) -> { if (newValue.length() > 25) { txtName.setText(oldValue); } });
    	txtAddress.textProperty().addListener((observable, oldValue, newValue) -> { if (newValue.length() > 150) { txtAddress.setText(oldValue); } });
    	rbTheme.selectedToggleProperty().addListener((observable, oldValue, newValue) -> { if(newValue != null) { themeChanged(); }  });
    }
    
    private void themeChanged() {
    	if (rbDark.isSelected()) { CommonMethods.theme = "#000000"; }
		else if (rbLight.isSelected()) { CommonMethods.theme = "#FFFFFF"; }
		else if (rbRed.isSelected()) { CommonMethods.theme = "#F5B7B1"; }
		else if (rbPink.isSelected()) { CommonMethods.theme = "#FFC6D1"; }
		else { CommonMethods.theme = "#FDEBD0"; }
		Background background = new Background(new BackgroundFill(Color.web(CommonMethods.theme), null, null));
        apAccountManager.setBackground(background);
        Paint contentColor = (rbDark.isSelected()) ? Color.WHITE : Color.BLACK;
        lblTitle.setTextFill(contentColor);
    	lnHeader1.setStroke(contentColor);
    	lnHeader2.setStroke(contentColor);
    	lnHeader3.setStroke(contentColor);
    	lblAllCustomerDetails.setTextFill(contentColor);
    	lblTheme.setTextFill(contentColor);
    	lblCurrentDate.setTextFill(contentColor);
    	rbDark.setTextFill(contentColor);
    	rbLight.setTextFill(contentColor);
    	rbRed.setTextFill(contentColor);
    	rbOrange.setTextFill(contentColor);
    	rbPink.setTextFill(contentColor);
    	CommonMethods.preferences.put("ThemeColor", CommonMethods.theme);
    }
    
    private void loadDummyData() {
    	CommonMethods.bankAccountManager.addCustomer(new Customer("BOB JOHNSON", "123 MAIN ST, APT 4B, CITY, STATE 12345", CustomerType.CHARITY));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("DAVID LEE", "654 BIRCH ST, ROOM 2A, CITY, STATE 98765", CustomerType.BUSINESS));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("JANE SMITH", "789 OAK ST, UNIT 5C, CITY, STATE 67890", CustomerType.INDIVIDUAL));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("JANE ALICE", "123 MAIN ST, APT 4B, CITY, STATE 12345", CustomerType.CHARITY));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("ALICE BROWN", "789 OAK ST, UNIT 5C, CITY, STATE 67890", CustomerType.CHARITY));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("JANE BOB", "456 ELM ST, SUITE 101, CITY, STATE 54321", CustomerType.CHARITY));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("BOB BROWN", "654 BIRCH ST, ROOM 2A, CITY, STATE 98765", CustomerType.BUSINESS));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("JANE JOHNSON", "123 MAIN ST, APT 4B, CITY, STATE 12345", CustomerType.INDIVIDUAL));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("ALICE JANE", "456 ELM ST, SUITE 101, CITY, STATE 54321", CustomerType.CHARITY));
    	CommonMethods.bankAccountManager.addCustomer(new Customer("BOB SMITH", "123 MAIN ST, APT 4B, CITY, STATE 12345", CustomerType.BUSINESS));
    	
    	BankAccount newBankAccount = new RewardAccount("JOHN CENA", 1297201, 123456);
    	newBankAccount.deposit(4500);
    	((RewardAccount)newBankAccount).addReward("DISCOUNT COUPONS");
    	((RewardAccount)newBankAccount).addReward("TRAVEL VOUCHER");
    	((RewardAccount)newBankAccount).addReward("SHOPPING DISCOUNTS");
    	CommonMethods.bankAccountManager.getCustomer(0).openAccount(newBankAccount);
    	newBankAccount = new ISA("RANDY ORTON", 1297202, 789012, 5600);
    	newBankAccount.deposit(2600);
    	CommonMethods.bankAccountManager.getCustomer(0).openAccount(newBankAccount);
    	newBankAccount = new BasicAccount("TRIPLE H", 1297203, 345678);
    	newBankAccount.deposit(3100);
    	CommonMethods.bankAccountManager.getCustomer(0).openAccount(newBankAccount);

    	loadCustomerNames("");
    }
    
    private void updateDateTime() { txtCurrentDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))); }
    
    private void changeSettingsVisibility(boolean is_visible) {
    	txtCurrentDate.setVisible(is_visible);
    	lblCurrentDate.setVisible(is_visible);
    	lnSettingsDivider.setVisible(is_visible);
    	lblTheme.setVisible(is_visible);
    	rbDark.setVisible(is_visible);
    	rbLight.setVisible(is_visible);
    	rbRed.setVisible(is_visible);
    	rbOrange.setVisible(is_visible);
    	rbPink.setVisible(is_visible);
    }
    
    private void setApplicationTheme() {
    	String themeColor = CommonMethods.preferences.get("ThemeColor", "#FDEBD0");
    	if (!List.of("#000000", "#FFFFFF", "#F5B7B1", "#FFC6D1", "#FDEBD0").contains(themeColor)) { themeColor = "#FDEBD0"; }
    	CommonMethods.preferences.put("ThemeColor", themeColor);
    	CommonMethods.theme = themeColor;
    	setRadioByTheme();
    }
    
    private void setRadioByTheme() {
    	if (CommonMethods.theme.equals("#000000")) { rbDark.setSelected(true); }
		else if (CommonMethods.theme.equals("#FFFFFF")) { rbLight.setSelected(true); }
		else if (CommonMethods.theme.equals("#F5B7B1")) { rbRed.setSelected(true); }
		else if (CommonMethods.theme.equals("#FDEBD0")) { rbOrange.setSelected(true); }
		else if (CommonMethods.theme.equals("#FFC6D1")) { rbPink.setSelected(true); }
    }

    public void setModel(T model) { 
    	this.model = model; 
    	if(model == null) { 
    		CommonMethods.showSuccessAlert("Welcome to the Sapphire Horizon Bank...!");
        	CommonMethods.bankAccountManager = new AccountManager();
        	CommonMethods.readDataFromCSV();
        	setApplicationTheme();
//    		loadDummyData();
//    		rbOrange.setSelected(true);
		} else { setRadioByTheme(); }
    	loadCustomerNames("");
	}
    
    @FXML
    private void btnSearch_Clicked(ActionEvent event) {
    	TextInputDialog userInput = new TextInputDialog();
    	userInput.setTitle("Search customer");
    	userInput.setHeaderText("Enter customer name or few characters of name can also work to search!");
    	userInput.setContentText("Search: ");
    	userInput.showAndWait().ifPresent(input -> { loadCustomerNames(input.strip().toLowerCase()); });
    }
    
    @FXML
    private void btnReload_Clicked(ActionEvent event) { loadCustomerNames(""); }

    @FXML
    private void btnAddCustomer_Clicked(ActionEvent event) {
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
        	boolean is_successfull = CommonMethods.bankAccountManager.addCustomer(new Customer(txtName.getText(), txtAddress.getText(), currentCustomerType));
        	if(is_successfull) {
        		CommonMethods.showSuccessAlert("New customer added successfully...!");
        		loadCustomerNames("");
        		resetForm();
        	}
        	else { CommonMethods.showErrorAlert("Please enter valid customer details with unique name...!!"); }
    	}
    }
    
    private void loadCustomerNames(String search) {
    	savedCustomerDetails = CommonMethods.bankAccountManager.getCustomers();
    	List<String> customerNamesAndBalances = savedCustomerDetails.stream().filter(curCustomer -> curCustomer.getName().toLowerCase().contains(search)).map(curCustomer -> curCustomer.getName() + " - " + curCustomer.getType() + " - BAL.: " + String.valueOf(curCustomer.totalBalance())).collect(Collectors.toList());
    	obCustomerDetails.clear();
    	obCustomerDetails.addAll(customerNamesAndBalances);
    	lblAllCustomerDetails.setText("All Customer Details (Bal: " + String.valueOf(CommonMethods.bankAccountManager.totalBalance()) + ")");
//    	savedCustomerDetails.forEach(curCustomer -> obCustomerDetails.add(curCustomer.getName() + " - " + curCustomer.getType() + " - BAL.: " + String.valueOf(curCustomer.totalBalance())));
    }
    
    @FXML
    private void btnReset_Clicked(ActionEvent event) { resetForm(); }
    
    private void resetForm() {
    	txtName.clear();
    	txtAddress.clear();
    	rbIndividual.setSelected(false);
    	rbBusiness.setSelected(false);
    	rbCharity.setSelected(false);
    }
    
    @FXML
    private void lvCustomerDetails_MouseDoubleClicked(MouseEvent event) {
    	long mouseCurrentClickTime = System.currentTimeMillis();
        if (mouseCurrentClickTime - mouseLastClickTime <= 500) { zoomCustomerDetails(); }
        mouseLastClickTime = mouseCurrentClickTime;
    }

    @FXML
    private void lvCustomerDetails_KeyPressed(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) { zoomCustomerDetails(); }
    }
    
    private void zoomCustomerDetails() {
    	CommonMethods.customerIndex = lvCustomerDetails.getSelectionModel().getSelectedIndex();
        if (CommonMethods.customerIndex >= 0) {
        	String searchCustomer = obCustomerDetails.get(CommonMethods.customerIndex);
        	loadCustomerNames("");
        	CommonMethods.customerIndex = obCustomerDetails.indexOf(searchCustomer);
        	try { MainApp.setScene("Customer", null); }
        	catch (IOException ex) { CommonMethods.showErrorAlert("Some problems while opening customer detail...!!"); }
        }
    }
}
