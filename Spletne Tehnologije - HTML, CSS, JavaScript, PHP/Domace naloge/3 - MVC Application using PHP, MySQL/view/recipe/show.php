<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">

<title>Recipe detail</title>

<h1>Details of: <?= $recipe["title"] ?></h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<p><?= $recipe["content"] ?></p><br/>

<p>Rating: <?= $recipe["avg"] ?>/5</p><br/>

        <?php 
        if(!empty($_SESSION["id"]) && $recipe["user"] == $_SESSION["id"]):?>
<form action="<?= BASE_URL . "recipe/delete" ?>" method="post">
    <input type="hidden" name="id" value="<?= $recipe["id"] ?>"  />
    <div class="form-group row">
        <label class="col-sm-1 col-form-label" for="delete_confirmation">Delete? </label>
        <div class="col-sm-1">
            <input class="form-control" type="checkbox" name="delete_confirmation" title="Are you sure you want to delete this recipe?" />
        </div>
        <button class="col-sm-1 btn btn-primary">Delete recipe</button>
    </div>
</form>
<?php endif;?>

<h2>Reviews:</h2>

<?php 
if (!empty($reviews)): 
    foreach($reviews as $review): ?>
        <p>Username: <?= $review["username"] ?> Rating: <?= $review["rating"] ?>/5<br/>
        <?= $review["content"] ?><br/>
    <?php 
        if((!empty($_SESSION["id"]) &&$review["user"] == $_SESSION["id"])):?>
            <form action="<?= BASE_URL . "review/delete" ?>" method="post">
                <input type="hidden" name="id" value="<?= $review["id"] ?>"  />
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label" for="delete_confirmation">Delete? </label>
                    <div class="col-sm-1">
                        <input class="form-control" type="checkbox" name="delete_confirmation" title="Are you sure you want to delete this review?" />
                    </div>
                    <button class="col-sm-1 btn btn-primary">Delete review</button>
                </div>
            </form>
    <?php
        endif;
    endforeach;
else:?>
    <p>No reviews.</p>
<?php
endif;

if (!empty($_SESSION["id"])):?>
<h2>Add review:</h2><br/>
<form action="<?= BASE_URL . "review/add" ?>" method="post">
    <input type="hidden" name="recipe" value="<?= $recipe["id"] ?>" />
    <input type="hidden" name="user" value="<?= $_SESSION["id"] ?>" />
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
        <label class="col-sm-2 col-form-label" for="content">Content: </label>
        <div class="col-sm-9">
            <textarea class="form-control" name="content" autocomplete="off" cols="40" rows="10" required autofocus></textarea>
        </div>
    </div>
        <?php
        if (!empty($errors["rating"])): ?>
            <ul>
            <?php
            foreach ($errors["rating"] as $error): ?>
                <li><p class="important"><?= $error ?></p><br/>
            <?php 
            endforeach; ?>
            </ul>
        <?php 
        endif; ?>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label" for="rating">Rating: </label>
        <div class="col-sm-9">
            <input type="number" name="rating" autocomplete="off" required />/5
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-2">
            <button class="btn btn-primary">Submit</button>
        </div>
    </div>
</form>

<?php endif; ?>
</div>