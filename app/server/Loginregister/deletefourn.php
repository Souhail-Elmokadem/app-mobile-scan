<?php
require "DataBase.php";

$db = new DataBase();

if (isset($_POST['idFour'])) {
    if ($db->dbConnect()) {
        if ($db->deleteFournisseur("fournisseur", "produit", $_POST['idFour'])) {
            echo "Product deleted successfully.";
        } else {
            echo "Failed to delete product.";
        }
    } else {
        echo "Error: Database connection.";
    }
} else {
    echo "Product ID is required.";
}
?>