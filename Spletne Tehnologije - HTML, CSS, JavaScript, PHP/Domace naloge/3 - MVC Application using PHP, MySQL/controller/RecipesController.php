<?php

require_once("model/UserDB.php");
require_once("model/RecipeDB.php");
require_once("model/ReviewDB.php");
require_once("ViewHelper.php");

class RecipesController {

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
        if (!$isDataValid) {
            RecipesController::show($data["id"], $errors);
        } else {
            RecipeDB::delete($data["id"]);
            RecipesController::showAll();
        }
    }

    public static function showAll() {
        $recipes = RecipeDB::getAll();
        foreach ($recipes as $key => $recipe){
            $usr = UserDB::get($recipe["user"]);
            $recipes[$key]["username"] = $usr["username"];
            $reviews = ReviewDB::getForRecipe($recipe["id"]);
            if (!empty($reviews)){
                $avg = 0;
                foreach ($reviews as $review){
                    $avg = $avg + $review["rating"];
                }
                $avg = $avg / count($reviews);
            } else {
                $avg = "(N/A)";
            }
            $recipes[$key]["avg"] = $avg;
        }
        ViewHelper::render("view/recipe/all.php", ["recipes" => $recipes]);
    }

    public static function showAddForm($data = [], $errors = []) {
        $vars = ["data" => $data, "errors" => $errors];
        ViewHelper::render("view/recipe/add.php", $vars);
    }

    public static function add() {
        $errors = [];
        $errors["title"] = [];
        if (filter_var($_POST["title"], FILTER_VALIDATE_REGEXP, array("options" => array("regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\-]+$/"))) == false){
            array_push($errors["title"], "Title contains invalid characters.");
        }
        if (filter_var($_POST["title"], FILTER_CALLBACK, array("options" => function($value) { return (strlen($value) >= 6 && strlen($value) <= 100) ? $value : false; })) == false) {
            array_push($errors["title"], "Title length should be between 6 and 100.");
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
            RecipesController::showAddForm($data, $errors);
        } else {
            $id = RecipeDB::insert($_SESSION["id"], $data["title"], $data["content"]);
            RecipesController::show($id);
        }
    }

    public static function show($id, $errors =[]){
        $recipe = RecipeDB::get($id);
        $reviews = ReviewDB::getForRecipe($id);
        if(!empty($reviews)){
            $avg = 0;
            foreach ($reviews as $key => $review){
                $usr = UserDB::get($review["user"]);
                $reviews[$key]["username"] = $usr["username"];
                $avg = $avg + $review["rating"];
            }
            $avg = $avg / count($reviews);
            $recipe["avg"] = $avg;
        } else {
            $recipe["avg"] = "(N/A)";
        }
        ViewHelper::render("view/recipe/show.php", ["recipe" => $recipe, "reviews" => $reviews, "errors" => $errors]);
    }
}
?>