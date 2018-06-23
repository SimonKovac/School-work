<?php

require_once "DBInit.php";

class UserDB {

    public static function registerUser($username, $password, $fn){
        $db = DBInit::getInstance();
        $statement = $db->prepare("INSERT INTO user (username, password, full_name) VALUES (:username, :password, :fn)");
        $statement->bindParam(":username", $username);
        $statement->bindParam(":password", $password);
        $statement->bindParam(":fn", $fn);
        $statement->execute();
        return $db->lastInsertId();
    }

    public static function changePassword($id, $password){
        $db = DBInit::getInstance();
        $statement = $db->prepare("UPDATE user SET password = :password WHERE id = :id");
        $statement->bindParam(":password", $password);
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }

    public static function changeFullName($id, $fn) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("UPDATE user SET full_name = :fn WHERE id = :id");
        $statement->bindParam(":fn", $fn);
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }

    public static function getAll(){
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT id, username, full_name FROM user");
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function delete($id) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("DELETE FROM user WHERE id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }

    public static function getByUsername($username) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT id, username, full_name FROM user WHERE username = :username");
        $statement->bindParam(":username", $username);
        $statement->execute();
        
        return $statement->fetch();
    }

    public static function get($id) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT id, username, full_name FROM user WHERE id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetch();
    }

    public static function validatePassword($id, $password) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT COUNT(username) FROM user WHERE id = :id AND password = :password");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->bindParam(":password", $password);
        $statement->execute();
        return $statement->fetchColumn(0) == 1;
    }

    // Returns true if a valid combination of a username and a password are provided.
    public static function validLoginAttempt($username, $password) {
        $dbh = DBInit::getInstance();

        // !!! NEVER CONSTRUCT SQL QUERIES THIS WAY !!!
        // INSTEAD, ALWAYS USE PREPARED STATEMENTS AND BIND PARAMETERS!
        $query = "SELECT COUNT(id) FROM user WHERE username = :username AND password = :password";
        $stmt = $dbh->prepare($query);
        $stmt->execute(array('username' => $username, 'password' => $password));

        return $stmt->fetchColumn(0) == 1;
    }
}
