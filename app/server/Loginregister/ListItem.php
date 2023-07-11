<?php
require "DataBase.php";
$db = new DataBase();

    if ($db->dbConnect()) {
        if ($db->getItems("produit")) {
            echo "End Of List";
        }
    } 

?>
