<?php
$server   = "127.0.0.1";
$database = "iCareDb";
$username = "longvh";
$password = "12345";
$DAY_ID = "1";

// Create connection
$con=mysqli_connect($server,$username,$password,$database);
 
// Check connection
if (mysqli_connect_errno())
{
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
 
$sqlSelect = "SELECT tbl_time.TIME as SelectedTime FROM iCareDb.tbl_time";

// Check if there are results
if ($result = mysqli_query($con, $sqlSelect))
{
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
	echo json_encode(array("Select_AllTime" => $resultArray));
}
 
// Close connections
mysqli_close($con);
?>