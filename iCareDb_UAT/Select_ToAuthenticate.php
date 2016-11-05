<?php
$server   = "127.0.0.1";
$database = "icaredb";
$username = "longvh";
$password = "12345";

// Data to get from Android
$LOGIN_ID = $_POST['login_id']; //GET FROM TEXTFIELD
$PASSWORD = $_POST['password']; //GET FROM TEXTFIELD

// Create connection
$con = mysqli_connect($server,$username,$password,$database);
$JSONdata = array();

// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}

//Get user's salt
$sql = "SELECT SALT FROM FROM icaredb.tbl_Customers WHERE LOGIN_ID = BINARY '" . $LOGIN_ID . "'";
$result = mysqli_query($con, $sql);
//If user exists -> salt exist -> check username and use salt to check password
if  ($result){
    $row = mysqli_fetch_assoc($result);
    $SALT = $row['SALT'];
    $PASSWORD = $SALT . $PASSWORD;
    $PASSWORD = hash('sha256', $PASSWORD);
    
    $sql = "SELECT CUSTOMER_ID FROM icaredb.tbl_Customers WHERE LOGIN_ID = '" . $LOGIN_ID . "' AND PASSWORD = '" . $PASSWORD . "' AND ACTIVE = 1";
    $result = mysqli_query($con, $sql);

    if (mysqli_num_rows($result) > 0) {
    //if ($result){
        // output data of each row
        //$row = mysqli_fetch_assoc($result);
        echo json_encode(array("Select_ToAuthenticate" => "Success"));
    } else {
        echo json_encode(array("Select_ToAuthenticate" => "Fail"));
    }
}else {
    echo json_encode(array("Select_ToAuthenticate" => "Fail"));
}


mysqli_close($con);
?>