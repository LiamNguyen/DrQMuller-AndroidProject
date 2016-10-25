<?php
$server = "127.0.0.1";
$username = "longvh";
$password = "12345";
$dbname = "icaredb";

//Data to insert

$CUS_ID = $_GET['cus_id']; //GET CUSTOMER ID
$CUS_NAME = $_GET['name']; //GET FROM TEXT FIELD
$DOB = $_GET['dob']; //GET FROM TEXT FIELD
$GENDER = $_GET['gender']; //GET FROM TEXT FIELD
$PHONE = $_GET['phone']; //GET FROM TEXT FIELD
$ADDRESS = $_GET['address']; //GET FROM TEXT FIELD
$EMAIL = $_GET['email']; //GET FROM TEXT FIELD
$UPDATEDAT = $_GET['update_date']; //GET CURRENT TIMESTAMP

// Create connection
$con=mysqli_connect($server,$username,$password,$dbname);

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// This SQL statement selects ALL from the table 'Locations'
$sqlInsert = "UPDATE icaredb.tbl_Customers SET CUSTOMER_NAME = '" . $CUS_NAME . "', DOB = '" . $DOB . "', GENDER = '" . $GENDER . "', PHONE = '" . $PHONE . "', ADDRESS = '" . $ADDRESS . "', EMAIL = '" . $EMAIL . "', UPDATEDAT = '" . $UPDATEDAT . "' WHERE CUSTOMER_ID = '" . $CUS_ID . "'";

// Check if there are results
if ($result = mysqli_query($con, $sqlInsert))
{
	echo "Updated";
} else {
	echo "Failed";
}

// Close connections
mysqli_close($con);
?>
