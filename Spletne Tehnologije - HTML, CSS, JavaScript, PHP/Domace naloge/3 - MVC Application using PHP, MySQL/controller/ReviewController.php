<?php


require_once("model/ReviewDB.php");
require_once("ViewHelper.php");

class ReviewController {

    public static function delete() {
        $errors = [];
        $errors["id"] = [];
        if (filter_var($_POST["id"], FILTER_VALIDATE_INT, array("options" => array("min_range" => 1))) == false) {
            array_push($errors["id"], "Wrong ID");
        }
        $errors["delete_confirmation"] = [];
        if (filter_var($_POST["delete_confirmation"], FILTER_VALIDATE_BOOLEAN) == false) {
            array_push($errors["delete_confirmation"], "Please confirm deletion.");
        }
        $data = [];
        $isDataValid = true;
        foreach ($errors as $key => $value){
            if (!empty($value)) {
                $isDataValid = false;
                $data[$key] = "";
            } else {
                $data[$key] = $_POST[$key];
            }
        }
        if ($isDataValid) {
            ReviewDB::delete($data["id"]);
        }
        RecipesController::show($data["id"], $errors);
    }

    public static function add() {
        $errors = [];
        $errors["user"] = [];
        $errors["recipe"] = [];
        $errors["rating"] = [];
        if (filter_var($_POST["rating"], FILTER_VALIDATE_INT, array("options" => array("min_range" => 1, "max_range" => 5))) == false){
            array_push($errors["rating"], "Rating should be between 1 and 5");
        }
        $errors["content"] = [];
        if (filter_var($_POST["content"], FILTER_CALLBACK, array("options" => function($value) { return (strlen($value) >= 20) ? $value : false; })) == false) {
            array_push($errors["content"], "Content should be at least 20 characters.");
        }

        $data = [];
        $isDataValid = true;
        foreach ($errors as $key => $value) {
            if (!empty($value)) {
                $isDataValid = false;
                $data[$key] = false;
            } else {
                $data[$key] = $_POST[$key];
            }
        }
        if (!$isDataValid) {
            RecipesController::show($data["recipe"], $errors);
        } else {
            $id = ReviewDB::insert($data["recipe"], $data["user"], $data["content"], $data["rating"]);
            RecipesController::show($data["recipe"]);
        }
    }

}
?>