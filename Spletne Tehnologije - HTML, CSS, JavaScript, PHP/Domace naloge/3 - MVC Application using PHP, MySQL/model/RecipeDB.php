<?php

require_once "DBInit.php";

class RecipeDB {

    public static function getByUser($user) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT id, user, title, content FROM recipe WHERE user = :user");
        $statement->bindParam(":user", $user);
        $statement->execute();
        return $statement->fetchAll();
    }

    public static function getAll() {
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT id, user, title, content FROM recipe");
        $statement->execute();
        return $statement->fetchAll();
    }

    public static function get($id) {
        $db = DBInit::getInstance();
        $statement = $db->prepare("SELECT id, user, title, content FROM recipe WHERE id = :id");
        $statement->bindParam(":id", $id);
        $statement->execute();

        return $statement->fetch();
    }

    public static function insert($user, $title, $content){
        $db = DBInit::getInstance();
        $statement = $db->prepare("INSERT INTO recipe (user, title, content) VALUES (:user, :title, :content)");
        $statement->bindParam(":user", $user);
        $statement->bindParam(":title", $title);
        $statement->bindParam(":content", $content);
        $statement->execute();
        return $db->lastInsertId();
    }

    public static function update($id, $title, $content){
        $db = DBInit::getInstance();
        $statement = $db->prepare("UPDATE recipe SET title = :title, content = :content WHERE id = :id");
        $statement->bindParam(":title", $title);
        $statement->bindParam(":content", $content);
        $statement->bindParam(":id", $id);
        $statement->execute();
    }

    public static function delete($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("DELETE FROM recipe WHERE id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }    

    public static function search($query) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT id, author, title, description, price, year, quantity  
            FROM book WHERE MATCH (author, title, description) AGAINST (:query IN BOOLEAN MODE)");
        $statement->bindValue(":query", $query);
        $statement->execute();

        return $statement->fetchAll();
    }    
}
