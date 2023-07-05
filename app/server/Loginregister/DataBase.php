<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where EmailUser = '" . $username . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['EmailUser'];
            $dbpassword = $row['PasswordUser'];
            if ($dbusername == $username && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function signUp($table, $prenom, $nom, $username, $password, $telephone)
    {
        $fullname = $this->prepareData($prenom);
        $fullname = $this->prepareData($nom);
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $email = $this->prepareData($telephone);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (NomUser, PrenomUser, EmailUser, PasswordUser, TelUser) VALUES ('" . $nom . "','" . $prenom . "','" . $username . "','" . $password . "','" . $telephone . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
    function addProduct($table, $nomproduct, $priceproduct, $dateprod, $margeprod, $idfour ,$iduser)
    {
        $nomproduct = $this->prepareData($nomproduct);
        $priceproduct = $this->prepareData($priceproduct);
        $dateprod = $this->prepareData($dateprod);
        $margeprod = $this->prepareData($margeprod);
        $idfour = $this->prepareData($idfour);
        $iduser = $this->prepareData($iduser);
        $this->sql =
            "INSERT INTO " . $table . " (NomProd, PrixAchat, dateProd,MargeProd, idFour,idUser) VALUES ('" . $nomproduct . "','" . $priceproduct . "','" . $dateprod . "','" . $margeprod . "','" . $idfour . "','" . $iduser . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
    function getItems($table)
    {
    
        $this->sql = "SELECT NomProd, PrixAchat FROM $table";
        $result = mysqli_query($this->connect, $this->sql);
    
        if ($result) {
            $items = array();
 
            while ($row = mysqli_fetch_assoc($result)) {

                $item = array(
                    "NomProd" => $row["NomProd"],
                    "PrixAchat" => $row["PrixAchat"]
                );
    
                // Append the item to the items array
                $items[] = $item;
      
            }

            // Return the items array as JSON
             echo json_encode($items);

        } else {
            echo "Error executing the query: " . mysqli_error($this->connect);
        }
    
    }
    function getItemDetail($table)
    {
    
        $this->sql = "SELECT NomProd, PrixAchat, dateProd, MargeProd, idFour, idUser FROM $table";
        $result = mysqli_query($this->connect, $this->sql);
    
        if ($result) {
            $items = array();
 
            while ($row = mysqli_fetch_assoc($result)) {

                $item = array(
                    "NomProd" => $row["NomProd"],
                    "PrixAchat" => $row["PrixAchat"],
                    "dateProd" => $row["dateProd"],
                    "MargeProd" => $row["MargeProd"],
                    "idFour" => $row["idFour"]
                );
    
                // Append the item to the items array
                $items[] = $item;
      
            }

            // Return the items array as JSON
             echo json_encode($items);

        } else {
            echo "Error executing the query: " . mysqli_error($this->connect);
        }
    
    }
    function getFournisseur($table)
    {
    
        $this->sql = "SELECT NomFour, PrenomFour, TelFour FROM $table";
        $result = mysqli_query($this->connect, $this->sql);
    
        if ($result) {
            $items = array();
 
            while ($row = mysqli_fetch_assoc($result)) {

                $item = array(
                    "NomFour" => $row["NomFour"],
                    "PrenomFour" => $row["PrenomFour"],
                    "TelFour" => $row["TelFour"]
                );
    
                // Append the item to the items array
                $items[] = $item;
      
            }

            // Return the items array as JSON
             echo json_encode($items);

        } else {
            echo "Error executing the query: " . mysqli_error($this->connect);
        }
    
    }
 //   getItems("produit");
}

?>
