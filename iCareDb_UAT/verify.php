<?php
$server = "127.0.0.1";
$username = "longvh";
$password = "12345";
$dbname = "icaredb";

$EMAIL = $_GET['email'];
$VERIFY_CODE = $_GET['code'];

// Create connection
$con=mysqli_connect($server,$username,$password,$dbname);

// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

//Specifies the character set when sending data to and from database
mysqli_set_charset($con,"utf8");

$sql = "SELECT * FROM icaredb.tbl_Customers WHERE EMAIL = '" . $EMAIL . "' AND VERIFY_CODE = '" . $VERIFY_CODE . "' AND ACTIVE = 0";
$result = mysqli_query($con, $sql);

if($row = mysqli_num_rows($result) > 0){
    $sql = "UPDATE icaredb.tbl_Customers SET ACTIVE = 1 WHERE EMAIL = '" . $EMAIL . "' AND VERIFY_CODE = '" . $VERIFY_CODE . "' AND ACTIVE = 0";
    mysqli_query($con, $sql);
    echo "activate success";
}else {
    echo "activate failed";
}

// Close connections
mysqli_close($con);
?>