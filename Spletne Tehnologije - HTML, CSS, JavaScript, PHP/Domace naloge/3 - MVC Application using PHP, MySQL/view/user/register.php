<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">

<title>Registration form</title>

<h1>Registration</h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<form action="<?= BASE_URL . "user/register" ?>" method="post">
    <div class="form-group row">
        <?php
        if (!empty($errors["full_name"])): ?>
            <ul>
            <?php
            foreach ($errors["full_name"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
        <label for="full_name" class="col-sm-2 col-form-label">Name: </label>
        <div class="col-sm-9">
            <input class="form-control" type="text" name="full_name" autocomplete="off" required autofocus />
        </div>
    </div>
        <?php
        if (!empty($errors["username"])): ?>
            <ul>
            <?php
            foreach ($errors["username"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label for="username" class="col-sm-2 col-form-label">Username: </label>
        <div class="col-sm-9">
            <input class="form-control" type="text" name="username" autocomplete="off" required />
        </div>
    </div>
        <?php
        if (!empty($errors["password"])): ?>
            <ul>
            <?php
            foreach ($errors["password"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label for="password" class="col-sm-2 col-form-label">Password: </label>
        <div class="col-sm-9">
            <input class="form-control" type="password" name="password" required />
        </div>
    </div>
        <?php
        if (!empty($errors["password_repeat"])): ?>
            <ul>
            <?php
            foreach ($errors["password_repeat"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label for="password_repeat" class="col-sm-2 col-form-label">Repeat password: </label>
        <div class="col-sm-9">
            <input class="form-control" type="password" name="password_repeat" required />
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-2">
            <button class="btn btn-primary">Register</button>
        </div>
    </div>
</form>
</div>