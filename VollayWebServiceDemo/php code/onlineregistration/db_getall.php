<?php
 
// array for JSON response
	$response = array();
 
// include db connect class
	require_once __DIR__ . '/db_connect.php';
 
// connecting to db
	$db = new DB_CONNECT();
 

 // get all all data from students table

	$result = mysql_query("SELECT *FROM students") or die(mysql_error());
 
// check for empty result
	if (mysql_num_rows($result) > 0) {
    // looping through all results
    // contacts node
    $response["student"] = array();
 
		while ($row = mysql_fetch_array($result)) {
        // temp user array
			$contactsData = array();
			$contactsData["ID"] = $row["id"];
			$contactsData["NAME"] = $row["name"];
			$contactsData["EMAIL"] = $row["email"];
			$contactsData["PHONE"] = $row["phone"];
			$contactsData["ADDRESS"] = $row["address"];
			
 
			// push single contactsData into final response array
			array_push($response["student"], $contactsData);
		}
    // success
		$response["success"] = 1;
 
    // echoing JSON response
		echo json_encode($response);
	} else {
    // no products found
		$response["success"] = 0;
		$response["message"] = "No products found";
 
    // echo no users JSON
		echo json_encode($response);
	}
?>