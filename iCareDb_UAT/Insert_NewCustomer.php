<?php
$server   = "127.0.0.1";
$database = "icaredb";
$username = "longvh";
$password = "12345";

//Credential Information
$LOGIN_ID = $_POST['login_id']; //GET FROM TEXTFIELD
$PASSWORD = $_POST['password']; //GET FROM TEXTFIELD

// Create connection
$con=mysqli_connect($server,$username,$password,$database);
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

//Specifies the character set when sending data to and from database
mysqli_set_charset($con,"utf8");

//Create random salt
$SALT = bin2hex(random_bytes(32));

//Prepend salt to password
$PASSWORD = $SALT . $PASSWORD;

//Hash password
$PASSWORD = hash('sha256', $PASSWORD);

// This SQL statement selects ALL from the table 'Locations'
$sqlInsert = "INSERT INTO icaredb.tbl_customers (LOGIN_ID, PASSWORD, SALT) VALUES ('" . $LOGIN_ID . "', '" . $PASSWORD . "', '" . $SALT . "')";

// Check if there are results
if ($result = mysqli_query($con, $sqlInsert))
{
	echo json_encode(array("Insert_NewCustomer" => "Inserted"));
} else {
	echo json_encode(array("Insert_NewCustomer" => "Failed"));
}

// Close connections
mysqli_close($con);
?>
