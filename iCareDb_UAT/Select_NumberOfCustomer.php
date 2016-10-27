<?php
$server = "127.0.0.1";
$username = "longvh";
$password = "12345";
$database = "icaredb";

// Create connection
$con = mysqli_connect($server,$username,$password,$database);
 
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$sqlSelect = "SELECT COUNT(CUSTOMER_ID) as 'Number Of Customer' FROM icaredb.tbl_customers";

// Check if there are results
/*if ($result = mysqli_query($con, $sqlSelect))
{
	// If so, then create a results array and a temporary one
	// to hold the data
	//$resultArray = array();
	//$tempArray = array();
 
	// Loop through each row in the result set
	//while($row = $result->fetch_object())
	//{
		// Add each row into our results array
		//$tempArray = $row;
	    //array_push($resultArray, $tempArray);
	//}
 
	// Finally, encode the array to JSON and output the results
    
	echo json_encode($resultArray);
}*/
$result = mysqli_query($con, $sqlSelect);
if ($data=mysqli_fetch_assoc($result)) {
    //$num_rows = mysqli_num_rows($result);
    echo json_encode(array("Select_NumberOfCustomers" => $data['Number Of Customer']));
} else {
    echo json_encode(array("Select_NumberOfCustomers" => -1));
}
 
// Close connections
mysqli_close($con);
?>