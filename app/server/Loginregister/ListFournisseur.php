<?php
require "DataBase.php";
$db = new DataBase();

    if ($db->dbConnect()) {
        if ($db->getFournisseur("fournisseur")) {
            echo "End Of List";
        }
    } 

?>