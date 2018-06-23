<?php

require_once("model/UserDB.php");
require_once("model/RecipeDB.php");
require_once("ViewHelper.php");

class UserController {

    public static function showAllUsers(){
        $users = UserDB::getAll();
        ViewHelper::render("view/user/all.php", ["users" => $users]);
    }

    public static function logout() {
        session_unset();
        ViewHelper::redirect(BASE_URL);
    }

    public static function showUser($user, $isNew = false) {
        $usr = UserDB::get($user);
        $usr["recipes"] = RecipeDB::getByUser($user);
        ViewHelper::render("view/user/account.php", ["user" => $usr, "isNew" => $isNew]);
    }

    public static function showUserSettings($data = [], $errors = []){
        if (empty($data["username"]) || empty($data["full_name"] || empty($data["id"]))){
            $user = UserDB::get($_SESSION["id"]);
            $data["id"] = $user["id"];
            $data["username"] = $user["username"];
            $data["full_name"] = $user["full_name"];
        }
        $vars = ["user" => $data, "errors" => $errors];
        ViewHelper::render("view/user/settings.php", $vars);
    }

    public static function deleteUser(){
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
            UserController::showUserSettings($data, $errors);
        } else {
            UserDB::delete($data["id"]);
            session_unset();
            ViewHelper::redirect(BASE_URL);
        }
    }

    function matchesNewPassword($value) {
        return ($value == $_POST["new"]) ? $value : false;
    }

    public static function changeFullName() {
        $errors = [];
        $errors["id"] = [];
        if (filter_var($_POST["id"], FILTER_VALIDATE_INT, array("options" => array("min_range" => 1))) == false) {
            array_push($errors["id"], "Wrong ID");
        }
        $errors["full_name"] = [];
        if (filter_var($_POST["full_name"], FILTER_VALIDATE_REGEXP, array("options" => array("regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\-]+$/"))) == false) {
            array_push($errors["full_name"], "Name contains invalid characters.");
        }
        if (filter_var($_POST["full_name"], FILTER_CALLBACK, array("options" => function($value) {return UserController::checkLength($value);})) == false) {
            array_push($errors["full_name"], "Name length should be between 6 and 45.");
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
            UserController::showUserSettings($data, $errors);
        } else {
            UserDB::changeFullName($data["id"], $data["full_name"]);
            UserController::showUser($data["id"]);
        }
    }

    public static function changePassword() {
        $errors = [];
        $errors["id"] = [];
        if (filter_var($_POST["id"], FILTER_VALIDATE_INT, array("options" => array("min_range" => 1))) == false) {
            array_push($errors["id"], "Wrong ID");
        }
        $errors["old"] = [];
        if (filter_var($_POST["old"], FILTER_CALLBACK, array("options" => function($value) {return UserDB::validatePassword($_POST["id"], $value) ? $value : false;}))== false) {
            array_push($errors["old"], "Password is incorrect");
        }
        $errors["new"] = [];
        if (filter_var($_POST["new"], FILTER_VALIDATE_REGEXP, array("options" => array("regexp" => "/^[ a-zA-Z0-9\.\-]+$/"))) == false) {
            array_push($errors["new"], "Password contains invalid characters.");
        }
        if (filter_var($_POST["new"], FILTER_CALLBACK, array("options" => function($value) { return UserController::checkLength($value); })) == false) {
            array_push($errors["new"], "Password length should be between 6 and 45.");
        }
        $errors["repeat"] = [];
        if (filter_var($_POST["repeat"], FILTER_CALLBACK, array("options" => function($value) { return UserController::matchesNewPassword($value); })) == false) {
            array_push($errors["repeat"], "Passwords do not match.");
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

        if(!$isDataValid) {
            $data["old"] = "";
            $data["new"] = "";
            $data["repeat"] = "";
            UserController::showUserSettings($data, $errors);
        } else {
            UserDB::changePassword($data["id"], $data["new"]);
            UserController::showUser($data["id"]);
        }
    }

    public static function showLoginForm() {
       ViewHelper::render("view/user/login.php");
    }

    public static function login() {
       if (UserDB::validLoginAttempt($_POST["username"], $_POST["password"])) {
            $usr = UserDB::getByUsername($_POST["username"]);
            $_SESSION["id"] = $usr["id"];
            UserController::showUser($usr["id"]);
       } else {
            ViewHelper::render("view/user/login.php", 
                ["errorMessage" => "Invalid username or password."]);
       }
    }

    public static function showRegisterForm($data = [], $errors = []) {
        if (empty($data)) {
            $data = ["username" => "", "password" => "", "password_repeat" => "", "fullname" => ""];
        }
        if (empty($errors)) {
            foreach ($data as $key => $value) {
                $errors[$key] = [];
            }
        }
        $vars = ["data" => $data, "errors" => $errors];
        ViewHelper::render("view/user/register.php", $vars);
    }

    function checkLength($value) {
        return (strlen($value) >= 6 && strlen($value) <= 45) ? $value : false;
    }

    function doesExist($user){
        return (empty(UserDB::getByUsername($user))) ? $user : false;
    }

    function matchesPassword($value) {
        return ($value == $_POST["password"]) ? $value : false;
    }

    public static function register() {
        $errors = [];
        $errors["full_name"] = [];
        if (filter_var($_POST["full_name"], FILTER_VALIDATE_REGEXP, array("options" => array("regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\-]+$/"))) == false) {
            array_push($errors["full_name"], "Name contains invalid characters.");
        }
        if (filter_var($_POST["full_name"], FILTER_CALLBACK, array("options" => function($value) {return UserController::checkLength($value);})) == false) {
            array_push($errors["full_name"], "Name length should be between 6 and 45.");
        }
        $errors["username"] = [];
        if (filter_var($_POST["username"], FILTER_VALIDATE_REGEXP,array("options" => array("regexp" => "/^[a-zA-Z0-9\-]+$/"))) == false) {
            array_push($errors["username"], "Username contains invalid characters.");
        }
        if (filter_var($_POST["username"], FILTER_CALLBACK, array("options" => function($value) { return UserController::checkLength($value); })) == false) {
            array_push($errors["username"], "Username length should be between 6 and 45.");
        }
        if (filter_var($_POST["username"], FILTER_CALLBACK, array("options" => function($value) { return UserController::doesExist($value); })) == false) {
            array_push($errors["username"], "Username already exists.");
        }
        $errors["password"] = [];
        if (filter_var($_POST["password"], FILTER_VALIDATE_REGEXP, array("options" => array("regexp" => "/^[ a-zA-Z0-9\.\-]+$/"))) == false) {
            array_push($errors["password"], "Password contains invalid characters.");
        }
        if (filter_var($_POST["password"], FILTER_CALLBACK, array("options" => function($value) { return UserController::checkLength($value); })) == false) {
            array_push($errors["password"], "Password length should be between 6 and 45.");
        }
        $errors["password_repeat"] = [];
        if (filter_var($_POST["password_repeat"], FILTER_CALLBACK, array("options" => function($value) { return UserController::matchesPassword($value); })) == false) {
            array_push($errors["password_repeat"], "Passwords do not match.");
        }
        /*
        $filters = [
            "username" => [
                [
                    "filter" => FILTER_VALIDATE_REGEXP,
                    "options" => array("regexp" => "/^[ a-zA-Z\-]+$/"),
                    "error" => "Username contains invalid characters"
                ],
                [
                    "filter" => FILTER_CALLBACK,
                    "options" => checkLength,
                    "error" => "Username length should be between 6 and 45."
                ],
                [
                    "filter" => FILTER_CALLBACK,
                    "options" => "doesExist",
                    "error" => "Username already exists"
                ]
            ],
            "password" => [
                [
                    "filter" => FILTER_VALIDATE_REGEXP,
                    "options" => ["regexp" => "/^[ a-zA-Z0-9\.\-]+$/"],
                    "error" => "Password contains invalid characters"
                ],
                [
                    "filter" => FILTER_CALLBACK,
                    "options" => function($value) { return (strlen($value) >= 6 && strlen($value) <= 45) ? $value : false; },
                    "error" => "Password length should be between 6 and 45."
                ]
            ],
            "password_repeat" => [
                [
                    "filter" => FILTER_CALLBACK,
                    "options" => function($value) { return ($value == $_POST["password"]) ? $value : false; },
                    "error" => "Passwords do not match."
                ]
            ],
            "fullname" => [
                [
                    "filter" => FILTER_VALIDATE_REGEXP,
                    "options" => array("regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\-]+$/"),
                    "error" => "Name contains invalid characters."
                ],
                [
                    "filter" => FILTER_CALLBACK,
                    "options" => array(function($value) { return (strlen($value) >= 6 and strlen($value) <= 45) ? $value : false; }),
                    "error" => "Name length should be between 6 and 45."
                ]
            ]
        ];
        foreach ($filters as $key => $value) {
            var_dump($value);
            $errors[$key] = [];
            foreach ($value as $filter) {
                var_dump($filter);
                if (filter_var($_POST[$key], $filter["filter"], $filter["options"]) == false) {
                    array_push($errors[$key], $filter["error"]);
                }
            }
            $data[$key] = empty($errors[$key]) ? $value : false;
        } */
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
            UserController::showRegisterForm($data, $errors);
        } else {
            $id = UserDB::registerUser($data["username"], $data["password"], $data["full_name"]);
            $_SESSION["id"] = $id;
            UserController::showUser($id, true);
        }
    }
}