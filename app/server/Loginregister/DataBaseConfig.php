<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {
        $this->servername = 'localhost';
        $this->username = 'root';
        $this->password = '';
        $this->databasename = 'savagi70_appmohammedia';
        // $this->servername = 'http://ftapp.finesttechnology.ma';
        // $this->username = 'stagiaires55@savagegaming.ma';
        // $this->password = 'NWbGkPGsw,=X';
        // $this->databasename = 'savagi70_AppMohammedia';

    }
}

?>
