<?php
$server   = "127.0.0.1";
$database = "icaredb_test";
$username = "longvh";
$password = "12345";

//Credential Information
$LOGIN_ID = "TEST_USERNAME"; //GET FROM TEXTFIELD
$PASSWORD = "TEST_PASSWORD"; //GET FROM TEXTFIELD

// Create connection
$con=mysqli_connect($server,$username,$password,$database);

// Check connectio
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

// This SQL statement selects ALL from the table 'Locations'
$sqlInsert = "INSERT INTO test.tbl_customers (LOGIN_ID, PASSWORD) VALUES ('" . $LOGIN_ID . "', '" . $PASSWORD . "')";

// Check if there are results
if ($result = mysqli_query($con, $sqlInsert))
{
	echo "Inserted";
} else {
	echo "Failed";
}

// Close connections
mysqli_close($con);
?>
