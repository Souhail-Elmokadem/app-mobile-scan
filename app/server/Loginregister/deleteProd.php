<?php
require "DataBase.php";

$db = new DataBase();

if (isset($_POST['idProd'])) {
    if ($db->dbConnect()) {
        if ($db->deleteProd("produit", "codebarre", $_POST['idProd'])) {
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