<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">

<title>Account settings</title>

<h1>Details of: <?= $user["full_name"] ?></h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<ul>
    <li>Username: <b><?= $user["username"] ?></b></li>
</ul>

<h3>Change password:</h3>
<form action="<?= BASE_URL . "user/password" ?>" method="post">
    <input type="hidden" name="id" value="<?= $user["id"] ?>"  />
        <?php
        if (!empty($errors["old"])): ?>
            <ul>
            <?php
            foreach ($errors["old"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="old">Old password: </label>
        <div class="col-sm-9">
            <input class="form-control" type="password" name="old" value="" autofocus required/>
        </div>
    </div>
        <?php
        if (!empty($errors["new"])): ?>
            <ul>
            <?php
            foreach ($errors["new"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="new">New password: </label>
        <div class="col-sm-9">
            <input class="form-control" type="password" name="new" value="" required />
        </div>
    </div>
        <?php
        if (!empty($errors["repeat"])): ?>
            <ul>
            <?php
            foreach ($errors["repeat"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="repeat">Repeat password: </label>
        <div class="col-sm-9">
            <input class="form-control" type="password" name="repeat" value="" required />
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-2">
            <button class="btn btn-primary">Change password</button>
        </div>
    </div>
</form>

<h3>Change name:</h3>
<form action="<?= BASE_URL . "user/name" ?>" method="post">
    <input type="hidden" name="id" value="<?= $user["id"] ?>"  />
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="full_name">New name: </label>
        <div class="col-sm-9">
            <input class="form-control" type="text" name="full_name" value="<?= $user["full_name"] ?>" autofocus required/>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-2">
            <button class="btn btn-primary">Change name</button>
        </div>
    </div>
</form>

<form action="<?= BASE_URL . "user/delete" ?>" method="post">
    <input type="hidden" name="id" value="<?= $user["id"] ?>"  />
    <div class="form-group row">
        <label class="col-sm-1 col-form-label" for="delete_confirmation">Delete? </label>
        <input class="form-control col-sm-1" type="checkbox" name="delete_confirmation" title="Are you sure you want to delete your account?" />
        <button class="btn btn-primary col-sm-1">Delete account</button>
    </div>
</form>

</div>