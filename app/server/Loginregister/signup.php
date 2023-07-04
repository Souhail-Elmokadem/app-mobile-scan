<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['NomUser']) && isset($_POST['PrenomUser']) && isset($_POST['EmailUser']) && isset($_POST['PasswordUser']) && isset($_POST['TelUser'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("user", $_POST['NomUser'], $_POST['PrenomUser'], $_POST['EmailUser'], $_POST['PasswordUser'], $_POST['TelUser'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>