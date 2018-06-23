<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">
<title>Account detail</title>

<h1>Details of: <?= $user["full_name"] ?></h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<?php if (isset($user["new"])): ?>
    <b>Welcome, <?php $user["full_name"] ?>!</b>
<?php endif; ?>

<ul>
    <li>Username: <b><?= $user["username"] ?></b></li>
</ul>

<h3>Recipes:</h3>
<?php 
if (!empty($user["recipes"])):?>
<ul>
<?php   
    foreach ($user["recipes"] as $recipe):?>
        <li><a href="<?= BASE_URL . "recipe/show?id=" . $recipe["id"] ?>"><?= $recipe["title"] ?></a></li>
<?php
    endforeach; ?>
</ul>
<?php
else:?>
<p>No recipes to show</p>
<?php
endif;?>

<?php if(!empty($_SESSION["id"]) && $_SESSION["id"] == $user["id"]): ?>
    <a href="<?= BASE_URL . "user/settings" ?>">Settings</a>
<?php endif; ?>
</div>