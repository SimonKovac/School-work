<?php

require_once "DBInit.php";

class ReviewDB {

    public static function getForRecipe($recipe) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT id, recipe, user, content, rating FROM review where recipe = :recipe");
        $statement->bindParam(":recipe", $recipe, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function getForUser($user) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT id, recipe, user, content, rating FROM review WHERE user = :user");
        $statement->bindParam(":user", $user, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function getAll($recipe) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT id, recipe, user, content, rating FROM review WHERE recipe = :recipe");
        $statement->bindParam(":recipe", $recipe, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function get($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT id, recipe, user, content, rating FROM review WHERE id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();

        $book = $statement->fetch();

        if ($book != null) {
            return $book;
        } else {
            throw new InvalidArgumentException("No record with id $id");
        }
    }

    public static function insert($recipe, $user, $content, $rating) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("INSERT INTO review (recipe, user, content, rating) 
            VALUES (:recipe, :user, :content, :rating)");
        $statement->bindParam(":recipe", $recipe);
        $statement->bindParam(":user", $user);
        $statement->bindParam(":content", $content);
        $statement->bindParam(":rating", $rating);
        $statement->execute();
    }

    public static function update($id, $content, $rating) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("UPDATE review SET content = :content, rating = :rating WHERE id = :id");
        $statement->bindParam(":content", $content);
        $statement->bindParam(":rating", $rating);
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }

    public static function delete($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("DELETE FROM review WHERE id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }        
}
