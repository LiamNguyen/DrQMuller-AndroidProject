<?php
$server   = "127.0.0.1";
$database = "icaredb_test";
$username = "longvh";
$password = "12345";

//Data to insert

$CUS_ID = "10"; //GET CUSTOMER ID
$CUS_NAME = "Nguyen Quang Minh"; //GET FROM TEXT FIELD
$DOB = "1999-11-18"; //GET FROM TEXT FIELD
$GENDER = "Male"; //GET FROM TEXT FIELD
$PHONE = "0468452599"; //GET FROM TEXT FIELD
$ADDRESS = "HO CHI MINH"; //GET FROM TEXT FIELD
$EMAIL = ""; //GET FROM TEXT FIELD
$UPDATEDAT = "2016-10-18 12:45:00"; //GET CURRENT TIMESTAMP

// Create connection
$con=mysqli_connect($server,$username,$password,$dbname);

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// This SQL statement selects ALL from the table 'Locations'
$sqlInsert = "UPDATE test.tbl_Customers SET CUSTOMER_NAME = '" . $CUS_NAME . "', DOB = '" . $DOB . "', GENDER = '" . $GENDER . "', PHONE = '" . $PHONE . "', ADDRESS = '" . $ADDRESS . "', EMAIL = '" . $EMAIL . "', UPDATEDAT = '" . $UPDATEDAT . "' WHERE CUSTOMER_ID = '" . $CUS_ID . "'";

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
