<?php
$server   = "127.0.0.1";
$database = "icaredb";
$username = "longvh";
$password = "12345";

//$COUNTRY_ID = $_GET['id'];

// Create connection
$con=mysqli_connect($server,$username,$password,$database);
 
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$sqlSelect = "SELECT * FROM icaredb.tbl_cities";
echo "asd";
// Check if there are results
if ($result = mysqli_query($con, $sqlSelect))
{   echo "ahihi";
	// If so, then create a results array and a temporary one
	// to hold the data
	$resultArray = array();
	$tempArray = array();
 
	// Loop through each row in the result set
	while($row = $result->fetch_object())
	{
		// Add each row into our results array
		$tempArray = $row;
	    array_push($resultArray, $tempArray);
	}
 
	// Finally, encode the array to JSON and output the results
	echo json_encode(array("Select_Cities" => $resultArray));
}else{
    echo "fail";
}
 
// Close connections
mysqli_close($con);
?>