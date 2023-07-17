<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['idUser'])) {
    if ($db->dbConnect()) {
        if ($db->UpdateUser("user", $_POST['idUser'],
        $_POST['NomUser'], $_POST['PrenomUser'],
         $_POST['EmailUser'], $_POST['TelUser'])) {
            echo "Updated Success";
        } else echo " Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>