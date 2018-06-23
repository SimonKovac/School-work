<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">

<title>New recipe</title>
<body>
<h1>Add a new recipe</h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<form action="<?= BASE_URL . "recipe/add" ?>" method="post">

    <div class="form-group row">
        <?php
        if (!empty($errors["title"])): ?>
            <ul>
            <?php
            foreach ($errors["title"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
        <label for="title" class="col-sm-2 col-form-label">Title: </label>
            <div class="col-sm-9">
            <input class="form-control" type="text" name="title" autocomplete="off" required autofocus />    
            </div><br/></div>
        <?php
        if (!empty($errors["content"])): ?>
            <ul>
            <?php
            foreach ($errors["content"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
        <div class="form-group row">
        <label for="content" class="col-sm-2 col-form-label">Content: </label>
        <div class="col-sm-9">
        <textarea class="form-control" name="content" autocomplete="off" cols="40" rows="10" required></textarea>
        </div></div>
    <div class="form-group row">
    <div class="col-sm-2"><button class="btn btn-primary">Submit</button>
    </div>
    </div>
</form>

</div>
</body>
