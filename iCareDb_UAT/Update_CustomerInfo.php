<?php
$server = "127.0.0.1";
$username = "longvh";
$password = "12345";
$dbname = "icaredb";

//Data to insert

$CUS_ID = $_POST['cus_id'];//$_GET['cus_id'];//urldecode($_POST['cus_id']); //GET CUSTOMER ID
$CUS_NAME = $_POST['name'];//$_GET['name'];//urldecode($_POST['name']); //GET FROM TEXT FIELD
$DOB = $_POST['dob'];//$_GET['dob'];//urldecode($_POST['dob']); //GET FROM TEXT FIELD
$GENDER = $_POST['gender'];//$_GET['gender'];//urldecode($_POST['gender']); //GET FROM TEXT FIELD
$PHONE = $_POST['phone'];//$_GET['phone'];//urldecode($_POST['phone']); //GET FROM TEXT FIELD
$ADDRESS = $_POST['address'];//['address'];//urldecode($_POST['address']); //GET FROM TEXT FIELD
$EMAIL = $_POST['email'];//$_GET['email'];//urldecode($_POST['email']); //GET FROM TEXT FIELD
$UPDATEDATE = $_POST['update_date'];//$_GET['update_date'];//urldecode($_POST['update_date']); //GET CURRENT TIMESTAMP
$VERIFY_CODE = md5(rand(0,1000));

// Create connection
$con=mysqli_connect($server,$username,$password,$dbname);

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

//Specifies the character set when sending data to and from database
mysqli_set_charset($con,"utf8");

// This SQL statement selects ALL from the table 'Locations'
$sqlInsert = "UPDATE icaredb.tbl_Customers SET CUSTOMER_NAME = '" . $CUS_NAME . "', DOB = '" . $DOB . "', GENDER = '" . $GENDER . "', PHONE = '" . $PHONE . "', ADDRESS = '" . $ADDRESS . "', EMAIL = '" . $EMAIL . "', UPDATEDAT = '" . $UPDATEDATE . "', VERIFY_CODE = '" . $VERIFY_CODE . "' WHERE CUSTOMER_ID = '" . $CUS_ID . "'";

// Check if there are results
if ($result = mysqli_query($con, $sqlInsert))
{
	$json = json_encode(array("Update_CustomerInfo" => "Updated"));
    echo $json;
} else {
	$json = json_encode(array("Update_CustomerInfo" => "Failed"));
    echo $json;
}

// Close connections
mysqli_close($con);
?>
