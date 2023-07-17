<?php
require "DataBase.php";
$db = new DataBase();

    if ($db->dbConnect()) {
        if ($db->getCodebarre("codebarre")) {
            echo "End Of List";
        }
    } 
?>