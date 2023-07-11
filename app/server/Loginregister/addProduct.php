<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['NomProd']) && isset($_POST['PrixAchat']) && isset($_POST['MargeProd']) && isset($_POST['idFour'])) {
    if ($db->dbConnect()) {
        if ($db->addProduct("produit", $_POST['NomProd'], $_POST['PrixAchat'], date('Y-m-d'), $_POST['MargeProd'],$_POST['idFour'],1)) {
            echo "Add Success";
        } else echo " Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>