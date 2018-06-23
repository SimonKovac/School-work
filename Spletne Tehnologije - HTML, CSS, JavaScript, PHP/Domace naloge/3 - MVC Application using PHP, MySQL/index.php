
<?php

session_start();

require_once("controller/UserController.php");
require_once("controller/RecipesController.php");
require_once("controller/ReviewController.php");

define("BASE_URL", $_SERVER["SCRIPT_NAME"] . "/");
define("IMAGES_URL", rtrim($_SERVER["SCRIPT_NAME"], "index.php") . "static/images/");
define("CSS_URL", rtrim($_SERVER["SCRIPT_NAME"], "index.php") . "static/css/");

$path = isset($_SERVER["PATH_INFO"]) ? trim($_SERVER["PATH_INFO"], "/") : "";
$urls = [
    "review/delete" => function() {
        if($_SERVER["REQUEST_METHOD"] == "POST") {
            ReviewController::delete();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "review/add" => function() {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            ReviewController::add();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "recipe/delete" => function() {
        if($_SERVER["REQUEST_METHOD"] == "POST"){
            RecipesController::delete();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "recipe/add" => function () {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            RecipesController::add();
        } else {
            RecipesController::showAddForm();
        }
    },
    "recipe/show" => function () {
        if (empty($_GET)) {
            RecipesController::showAll();
        } else {
            RecipesController::show($_GET["id"]);
        }
    },
    "user/all" => function() {
        UserController::showAllUsers();
    },
    "user/settings" => function () {
        if(!empty($_SESSION["id"])){
            UserController::showUserSettings();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "user/password" => function() {
        if ($_SERVER["REQUEST_METHOD"] == "POST"){
            UserController::changePassword();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "user/name" => function() {
        if ($_SERVER["REQUEST_METHOD"] == "POST"){
            UserController::changeFullName();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "user/delete" => function() {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            UserController::deleteUser();
        } else {
            ViewHelper::redirect(BASE_URL);
        }
    },
    "user/logout" => function () {
        UserController::logout();
    },
    "user/account" => function () {
        if (!empty($_GET["id"])){
            UserController::showUser($_GET["id"]);
        }
    },
    "user/register" => function () {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            UserController::register();
        } else {
            UserController::showRegisterForm();
        }
    },
    "user/login" => function () {
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
            UserController::login();
        } else {
            UserController::showLoginForm();
        }
    },
    "" => function () {
        ViewHelper::redirect(BASE_URL . "recipe/show");
    },
];

try {
    if (isset($urls[$path])) {
       $urls[$path]();
    } else {
        echo "No controller for '$path'";
    }
} catch (Exception $e) {
    //echo "An error occurred: <pre>$e</pre>";
    ViewHelper::error404();
} 
