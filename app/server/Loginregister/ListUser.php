<?php
require "DataBase.php";
$db = new DataBase();

    if ($db->dbConnect()) {
        if ($db->getUsers("user")) {
            echo "End Of List";
        }
    } 

?>