<?php
require "DataBase.php";
$db = new DataBase();

    if ($db->dbConnect()) {
        if ($db->getItemDetail("produit")) {
            echo "End Of List";
        }
    } 

?>