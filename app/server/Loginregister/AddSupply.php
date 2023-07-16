<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['NomFour']) && isset($_POST['PrenomFour']) && isset($_POST['TelFour'])) {
    if ($db->dbConnect()) {
        if ($db->addSupply("fournisseur", $_POST['NomFour'], $_POST['PrenomFour'],$_POST['TelFour'])) {
            echo "Add Success";
        } else echo " Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>