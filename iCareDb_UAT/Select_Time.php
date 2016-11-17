<?php
$servername = "127.0.0.1";
$username = "longvh";
$password = "12345";
$dbname = "iCareDb";
$JSONdata = array();
// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT * FROM icaredb_uat.tbl_time;";
$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {

    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
        array_push($JSONdata, $row["TIME"]);

        //Print directly variables
    }
} else {
    echo "0 results";
}

$json_encode = json_encode($JSONdata);
echo $json_encode;
mysqli_close($conn);
?>