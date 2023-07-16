<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idFour'])) {
    if ($db->dbConnect()) {
        if ($db->UpdateFournisseur("fournisseur", $_POST['idFour'],$_POST['NomFour'], $_POST['PrenomFour'], $_POST['TelFour'])) {
            echo "Updated Success";
        } else echo " Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>