<?php
$server   = "127.0.0.1";
$database = "icaredb";
$username = "longvh";
$password = "12345";

// Data to get from Android
$LOGIN_ID = $_GET['login_id']; //GET FROM TEXTFIELD
$PASSWORD = $_GET['password']; //GET FROM TEXTFIELD

// Create connection
$con = mysqli_connect($server,$username,$password,$database);
$JSONdata = array();

// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT CUSTOMER_ID FROM icaredb.tbl_Customers WHERE LOGIN_ID = '" . $LOGIN_ID . "' AND PASSWORD = BINARY '" . $PASSWORD . "'";
$result = mysqli_query($con, $sql);

if (mysqli_num_rows($result) > 0) {
//if ($result){
    // output data of each row
    //$row = mysqli_fetch_assoc($result);
    echo "1";
} else {
    echo "0";
}

mysqli_close($con);
?>