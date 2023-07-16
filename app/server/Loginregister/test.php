<?php

// $servername = 'localhost';
// $username = 'souhailelmokadem@savagegaming.ma';
// $password = 'jWi6YG;T&K;w';
// $databasename = 'savagi70_AppMohammedia';

// $con = mysqli_connect($servername,$username,$password,$databasename);

// if ($con){
//     echo "<h1>Yes !!</h1>";
// }else{
//     echo "<h1>Noo !</h1>";
// }
$host = 'http://ftapp.finesttechnology.ma'; // Replace with the MySQL server hostname or IP address
$database = 'savagi70_AppMohammedia'; // Replace with your database name
$username = 'souhailelmokadem@savagegaming.ma'; // Replace with your MySQL username
$password = 'jWi6YG;T&K;w'; // Replace with your MySQL password

// Create a connection
$connection = mysqli_connect($host, $username, $password, $database);

// Rest of your code...
if($connection){
    echo "OK";

}else{
    echo "so sorry";
}
echo ini_set('display_errors', 1);
echo ini_set('display_startup_errors', 1);
echo error_reporting(E_ALL);

?>
