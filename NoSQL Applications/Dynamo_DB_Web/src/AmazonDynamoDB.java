import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;

// Basic code for performing CRUD operations and creating table were referred from Amazon Website

/**
 * This class is used to create tables in DynamoDB and 
 * update the values according to user interactions.
 * 
 */


public class AmazonDynamoDB {


	String newA, newB, newC;

	static String global_UserName = "";

	static AmazonDynamoDBClient dynamoDB;

	String tableName1 = "UserTable";

	String tableName2= "ProductsTable";

	String initialItems = "{568:0,876:0,356:0}";

	static int[] temp24 = new int[3];

	static int[] parameters = new int[3];
	
	
	/**
	 * This method is used to set the userid
	 * 
	 * @param   string userid            
	 * 
	 * @return   no return value
	 */
	
	public void setName(String uname){
		global_UserName = uname;
	}
	
	/**
	 * This method is used to set initial profile
	 * 
	 * @param   string userid            
	 * 
	 * @return   no return value
	 */

	public void init() throws Exception {

		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider("SOWGANDH").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException(
					"Cannot load the credentials from the credential profiles file. " +
							"Please make sure that your credentials file is at the correct " +
							"location (C:\\Users\\$HY@M\\.aws\\credentials), and is in valid format.",
							e);
		}
		dynamoDB = new AmazonDynamoDBClient(credentials);
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		dynamoDB.setRegion(usWest2);
	}

	
	/**
	 * This method is used to set the values 
	 * in userid table
	 * 
	 * @return   Map Value of User table stored in Map
	 */

	private static Map<String, AttributeValue> newItem(String userid, String password, String firstname, 
			String lastname, String emailID, String itemsPurchased) {

		Map<String, String> data = new HashMap<String, String>();
		data.put("John", "Taxi Driver");
		data.put("Mark", "Professional Killer");
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("userid", new AttributeValue(userid));
		item.put("password", new AttributeValue(password));
		//item.put("mobile", new AttributeValue().withN(Integer.toString(mobile)));
		item.put("firstname", new AttributeValue(firstname));
		item.put("lastname", new AttributeValue(lastname));
		item.put("emailID", new AttributeValue(emailID));
		item.put("itemsPurchased", new AttributeValue(itemsPurchased));

		return item;
	}

	/**
	 * This method is used to set the values 
	 * in product table
	 * 
	 * @return   Map Values of Product table stored in Map
	 */
	private static Map<String, AttributeValue> newItemProduct(String productid, String productName, String price, 
			String availableQuantity,String productDescription) {

		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("productid", new AttributeValue(productid));
		item.put("productName", new AttributeValue(productName));
		//item.put("mobile", new AttributeValue().withN(Integer.toString(mobile)));
		item.put("price", new AttributeValue(price));
		item.put("availableQuantity", new AttributeValue(availableQuantity));
		item.put("productDescription", new AttributeValue(productDescription));
		return item;
	}

	/**
	 * This method is used to create initial tables
	 * 
	 * @return   no return values
	 */
	public void createInitialTableUser(){

		if (Tables.doesTableExist(dynamoDB, tableName1)) {
			System.out.println("Table " + tableName1 + " is already ACTIVE");
		} else {
			// Create a table with a primary hash key named 'userid', which holds a string
			CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName1)
					.withKeySchema(new KeySchemaElement().withAttributeName

("userid").withKeyType(KeyType.HASH))
					.withAttributeDefinitions(new AttributeDefinition().withAttributeName

("userid").withAttributeType(ScalarAttributeType.S))
					.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits

(1L).withWriteCapacityUnits(1L));
			TableDescription createdTableDescription = dynamoDB.createTable

(createTableRequest).getTableDescription();
			System.out.println("Created Table: " + createdTableDescription);

			// Wait for it to become active
			System.out.println("Waiting for " + tableName1 + " to become ACTIVE...");
			Tables.waitForTableToBecomeActive(dynamoDB, tableName1);
		}


		// Describe our new table
		DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName1);
		TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
//		System.out.println("Table Description: " + tableDescription);


	}

	/**
	 * This method is used to insert values into 
	 * product table
	 * 
	 * @return   no return values
	 */

	public void createInitialTableProduct(){

		if (Tables.doesTableExist(dynamoDB, tableName2)) {
			System.out.println("Table " + tableName2 + " is already ACTIVE");
		} else {
			// Create a table with a primary hash key named 'userid', which holds a string
			CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName2)
					.withKeySchema(new KeySchemaElement().withAttributeName

("productid").withKeyType(KeyType.HASH))
					.withAttributeDefinitions(new AttributeDefinition().withAttributeName

("productid").withAttributeType(ScalarAttributeType.S))
					.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits

(1L).withWriteCapacityUnits(1L));
			TableDescription createdTableDescription = dynamoDB.createTable

(createTableRequest).getTableDescription();
			System.out.println("Created Table: " + createdTableDescription);

			// Wait for it to become active
			System.out.println("Waiting for " + tableName2 + " to become ACTIVE...");
			Tables.waitForTableToBecomeActive(dynamoDB, tableName2);
		}


		// Describe our new table
		DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName2);
		TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
//		System.out.println("Table Description: " + tableDescription);


	}
	
	/**
	 * This method is used to insert values into 
	 * User table
	 * 
	 * @return   no return values
	 */

	public void insertItemsUser(){


		// Add an item
		Map<String, AttributeValue> item = 
				newItem("ss3168", "abcd","Shyam", 

"Sangaraju","ss3168@g.rit.edu","{568:1,876:2,356:3}");
		PutItemRequest putItemRequest = new PutItemRequest(tableName1, item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);

		// Add another item
		Map<String, AttributeValue> item2 = 
				newItem("sn2638", "wxyz","Sowgandh", 

"Krishna","sn2638@g.rit.edu","{568:0,876:4,356:2}");

		PutItemRequest putItemRequest2 = new PutItemRequest(tableName1, item2);
		PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);


		Map<String, AttributeValue> item3 = 
				newItem("pd3698", "pqrs","pradeep", 

"Duvvur","pd3698@g.rit.edu","{568:2,876:1,356:0}");
		PutItemRequest putItemRequest3 = new PutItemRequest(tableName1, item3);
		PutItemResult putItemResult3 = dynamoDB.putItem(putItemRequest3);
	}


	/**
	 * This method is used to insert values into 
	 * product table
	 * 
	 * @return   no return values
	 */
	
	public void insertItemsProduct(){


		// Add an item
		Map<String, AttributeValue> item = 
				newItemProduct("568", "MacBookPro","1699", 

"5","{Size:15inches,Brand:Apple,Colour:Silver,Weight:500gms}");
		PutItemRequest putItemRequest = new PutItemRequest(tableName2, item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);

		// Add another item
		Map<String, AttributeValue> item2 = 
				newItemProduct("876", "TitanWatch","450", 

"5","{Type:Digital,Brand:Titan,Colour:Silver,Weight:50gms}");

		PutItemRequest putItemRequest2 = new PutItemRequest(tableName2, item2);
		PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);


		Map<String, AttributeValue> item3 = 
				newItemProduct("356", "FootBall","120", 

"5","{Radius:20inches,Type:Chequred,Brand:Adidas,Colour:White}");
		PutItemRequest putItemRequest3 = new PutItemRequest(tableName2, item3);
		PutItemResult putItemResult3 = dynamoDB.putItem(putItemRequest3);
	}

	/**
	 * This method is used to return product
	 * description to front end 
	 * 
	 * 
	 * @return string productDescription
	 */

	public String itemDescription(String productId){
		HashMap<String, Condition> scanFilter1 = new HashMap<String, Condition>();
		Condition condition1 = new Condition()
		//   .withComparisonOperator(ComparisonOperator.GT.toString())
		.withComparisonOperator(ComparisonOperator.EQ.toString())
		.withAttributeValueList(new AttributeValue().withS(productId));
		scanFilter1.put("productid", condition1);
		ScanRequest scanRequest1 = new ScanRequest(tableName2).withScanFilter(scanFilter1);
		ScanResult scanResult1 = dynamoDB.scan(scanRequest1);
		String item = scanResult1.getItems().toString();
		String[] splits = item.split("productDescription");
		String firstSplit = splits[0]; 
		String secondSplit = splits[1];
		String[]temp = secondSplit.split("productName");
		String temp2 = temp[0];
		String finalString = temp2.substring(6, temp2.length()-5);
//		System.out.println(finalString);
		return item;

	}

	
	/**
	 * This method is used to update the
	 * user data base and product data base
	 * 
	 * @return   no return values
	 */

	public void  updateDatabase(){

		newA = Integer.toString(temp24[0]-parameters[0]);

		newB = Integer.toString(temp24[1]-parameters[1]);

		newC = Integer.toString(temp24[2]-parameters[2]);

		Map<String, AttributeValue> item = 
				newItemProduct("568", "MacBookPro","1699", 

newB,"{Size:15inches,Brand:Apple,Colour:Silver,Weight:500gms}");
		PutItemRequest putItemRequest = new PutItemRequest(tableName2, item);
		PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);

		// Add another item
		Map<String, AttributeValue> item2 = 
				newItemProduct("876", "TitanWatch","450", 

newA,"{Type:Digital,Brand:Titan,Colour:Silver,Weight:50gms}");

		PutItemRequest putItemRequest2 = new PutItemRequest(tableName2, item2);
		PutItemResult putItemResult2 = dynamoDB.putItem(putItemRequest2);

		Map<String, AttributeValue> item3 = 
				newItemProduct("356", "FootBall","120", 

newC,"{Radius:20inches,Type:Chequred,Brand:Adidas,Colour:White}");
		PutItemRequest putItemRequest3 = new PutItemRequest(tableName2, item3);
		PutItemResult putItemResult3 = dynamoDB.putItem(putItemRequest3);
		
//		System.out.println(global_UserName);
		HashMap<String, Condition> scanFilter1 = new HashMap<String, Condition>();
		Condition condition1 = new Condition()
		//   .withComparisonOperator(ComparisonOperator.GT.toString())
		.withComparisonOperator(ComparisonOperator.EQ.toString())
		.withAttributeValueList(new AttributeValue().withS(global_UserName));
		scanFilter1.put("userid", condition1);
		ScanRequest scanRequest1 = new ScanRequest(tableName1).withScanFilter(scanFilter1);
		ScanResult scanResult1 = dynamoDB.scan(scanRequest1);
		String fullRecord = scanResult1.getItems().toString();

		String temp25 = fullRecord.substring(2, fullRecord.length()-1);

		String[] splits = temp25.split("password");
		String firstSplit = splits[0]; 
		String secondSplit = splits[1];

		String[] splits2 = secondSplit.split("S: ");

		String []temp26 = splits2[1].split(",}}");
		String parameter_password = temp26[0];
		
		String[] splits27 = firstSplit.split("firstname");
		String[] splits28 = splits27[1].split(",}");
		String[] splits29 = splits28[0].split("S: ");
//        System.out.println(splits27[0]);
        
        String parameter_firstname = splits29[1];
        
        
        splits27 = splits27[0].split("lastname");
        splits28 = splits27[1].split(",}");
		splits29 = splits28[0].split("S: ");
        
//		System.out.println(splits27[0]);
        
        String parameter_lastname = splits29[1];
        
        String [] splits40 = splits27[0].split("itemsPurchased");
        
        
        
        String [] splits41 = splits40[1].split("S: ");
        
              
        String temp_itemsPurchased = splits41[1].substring(0,splits41[1].length()-4);
        
        
        String []splits50 = temp_itemsPurchased.split("568:");
        String []splits51 = splits50[1].split(",876:");
        
        int z = parameters[0]+Integer.parseInt(splits51[0]);
       
        String a1 = Integer.toString(z);
        String []splits52 = splits51[1].split(",356:");
      
        int y = parameters[1]+Integer.parseInt(splits52[0]);
        String a2 = Integer.toString(y);
        String []splits53 = splits52[1].split("}");
      
        int x = parameters[2]+Integer.parseInt(splits53[0]);
        String a3 = Integer.toString(x);
        
        String parameter_itemsPurchased = temp_itemsPurchased.substring(0,5)+a1+
        		temp_itemsPurchased.substring(6,11)+a2+temp_itemsPurchased.substring(12,17)+a3+"}";
        
        String [] splits70 = splits40[0].split("userid");
        String [] splits71 = splits70[0].split("emailID");
        
        String [] splits72 = splits71[1].split("S: ");
        
        String [] splits73 = splits72[1].split(",}");
        
        
        String parameter_emailID = splits73[0]; 
        
        Map<String, AttributeValue> item6 = 
				newItem(global_UserName, parameter_password,parameter_firstname, 

parameter_lastname,parameter_emailID,parameter_itemsPurchased);
		PutItemRequest putItemRequest6 = new PutItemRequest(tableName1, item6);
		PutItemResult putItemResult6 = dynamoDB.putItem(putItemRequest6);
		
	}

	/**
	 * This method is to check the availability 
	 * of the ordered items. 
	 * 
	 * @return  int[] boolean value of each item's status
	 */ 

	public int[] checkItems ( int a, int b, int c ) {

		String [] items_ID = {"876","568","356"};
		int [] array = {0,0,0};
		parameters[0] = a;
		parameters[1] = b;
		parameters[2] = c;

		for (int i = 0; i <3; i++){
			HashMap<String, Condition> scanFilter1 = new HashMap<String, Condition>();
			Condition condition1 = new Condition()
			//   .withComparisonOperator(ComparisonOperator.GT.toString())
			.withComparisonOperator(ComparisonOperator.EQ.toString())
			.withAttributeValueList(new AttributeValue().withS(items_ID[i]));
			scanFilter1.put("productid", condition1);
			ScanRequest scanRequest1 = new ScanRequest(tableName2).withScanFilter(scanFilter1);
			ScanResult scanResult1 = dynamoDB.scan(scanRequest1);
			String item = scanResult1.getItems().toString();
			String[] splits = item.split("availableQuantity");
			String firstSplit = splits[0]; 
			String secondSplit = splits[1];
			String available = "";
			for (int j = 5; j < Integer.MAX_VALUE; j++ ){
				if (secondSplit.charAt(j)==','){
					break;
				}
				available = available + secondSplit.charAt(j);
			}
			temp24[i] = Integer.parseInt(available);
			if (temp24[i] >= parameters[i] ){
				array[i] = 1;
			}
		}


		return array;

	}
	
	/**
	 * This method is used to validate 
	 * the userid and password 
	 * 
	 * @return  boolean  true - for valid user
	 *                   false - for invalid user
	 */

	public boolean validateLogin(String userName, String userPassWord){


		global_UserName = userName;
		int countUser = 0, countPassWord = 0;
		boolean flag , passWordFlag ;
		HashMap<String, Condition> scanFilter1 = new HashMap<String, Condition>();
		Condition condition1 = new Condition()
		//   .withComparisonOperator(ComparisonOperator.GT.toString())
		.withComparisonOperator(ComparisonOperator.EQ.toString())
		.withAttributeValueList(new AttributeValue().withS(userName));
		scanFilter1.put("userid", condition1);
		ScanRequest scanRequest1 = new ScanRequest(tableName1).withScanFilter(scanFilter1);
		ScanResult scanResult1 = dynamoDB.scan(scanRequest1);
		countUser = scanResult1.getCount();
		if (countUser < 1){
			System.out.println("Invalid user");
			return false;
		}
		String fullRecord = scanResult1.getItems().toString();



		String checkPassword = "";
		String[] splits = fullRecord.split("password");
		String firstSplit = splits[0]; 
		String secondSplit = splits[1];
		//	System.out.println(secondSplit);

		for ( int i = 5 ; i < secondSplit.length() ; i++ ){

			if((secondSplit.charAt(i)) == ',')
				break;

			checkPassword += secondSplit.charAt(i);
		}

		if (checkPassword.equals(userPassWord)){

			passWordFlag = true;
		} else {
			passWordFlag = false;
		}

		if ( ( countUser >= 1 ) && (passWordFlag)) {
			//System.out.println("Welcome to Periya e-commerce site");
			flag = true; 
		} else {
//			System.out.println("Invalid User");
			flag = false;
		}
		return flag;
	}

	/**
	 * This method is to register new user
	 * details into the User data base. 
	 * 
	 * @return  boolean  true - for valid user
	 *                   false - for invalid user
	 */
   

	public boolean registerUser(String userid, String password, String firstname, 
			String lastname, String emailID, String initialItems) {

		boolean flagRegister = true;

		try{

			Map<String, AttributeValue> item = 
					newItem(userid, password,firstname, lastname,emailID,initialItems);
			PutItemRequest putItemRequest = new PutItemRequest(tableName1, item);
			PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
		}
		catch(Exception e ){

			flagRegister = false;
		}
//		System.out.println("register : " + flagRegister);
		return flagRegister;

	}
}