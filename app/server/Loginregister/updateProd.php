<?php
require "DataBase.php";
$db = new DataBase();

// if ($db->dbConnect()) {
//     if ($db->UpdateProduit("produit", $_POST['idProd'],$_POST['NomProd'], $_POST['PrixAchat'], $_POST['dateProd'], $_POST['MargeProd'],$_POST['idFour'])) {
//         echo "Updated Success";
//     } else echo " Failed";
// } else echo "Error: Database connection";
// if (isset($_POST['NomProd']) && isset($_POST['PrixAchat']) && isset($_POST['MargeProd']) && isset($_POST['idFour'])) {
//     if(empty($_POST['NomProd']) && empty($_POST['PrixAchat']) && empty($_POST['MargeProd']) && empty($_POST['idFour'])){
//         echo "all empty !";
//     }else{
//         echo "rempli";
        if ($db->dbConnect()) {
            if ($db->UpdateProduit("produit", $_POST['idProd'],$_POST['NomProd'], $_POST['PrixAchat'], $_POST['dateProd'], $_POST['MargeProd'],$_POST['idFour'])) {
                echo "Updated Success";
            } else echo " Failed";
        } else echo "Error: Database connection";
//     }
// }else{
//     echo "all variable empty !";
// }

?>