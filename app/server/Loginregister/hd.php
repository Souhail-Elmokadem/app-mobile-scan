<?php
$host = 'localhost'; // Replace with the MySQL server hostname or IP address
$database = 'savagi70_appmohammedia'; // Replace with your database name
$username = 'root'; // Replace with your MySQL username
$password = ''; // Replace with your MySQL password

// Create a connection
$connection = mysqli_connect($host, $username, $password, $database);

// Check connection
if (!$connection) {
    die("Connection failed: " . mysqli_connect_error());
}

// SQL query to select data from the "product" table
$query = "SELECT NomProd, PrixAchat, dateProd, MargeProd, idFour FROM produit";

// Execute the query
$result = mysqli_query($connection, $query);

// Check if the query executed successfully
if ($result) {
    // Fetch the rows from the result set
    while ($row = mysqli_fetch_assoc($result)) {
        // Append the row to the products array
        $products[] = $row;
    }

    // Process the array of products
    foreach ($products as $product) {
        // Access the column values by their names
        $nomProd = $product['NomProd'];
        $prixAchat = $product['PrixAchat'];
        $dateProd = $product['dateProd'];
        $margeProd = $product['MargeProd'];
        $idFour = $product['idFour'];

        // Do something with the values
        echo "Product: $nomProd, Price: $prixAchat, Date: $dateProd, Margin: $margeProd, Supplier ID: $idFour<br>";
    }
} else {
    echo "Error executing the query: " . mysqli_error($connection);
}

// Close the connection when you're done
mysqli_close($connection);
?>
