<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">

<title>Recipes</title>
<h1>All recipes</h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<ul>

    <?php foreach ($recipes as $recipe): ?>
        <div class="row"><li><?= $recipe["username"] ?> - <b><a href="<?= BASE_URL . "recipe/show?id=" . $recipe["id"] ?>"><?php echo($recipe["title"]); ?></a></b> - <?= $recipe["avg"] ?>/5</li></div>
    <?php endforeach; ?>

</ul>

</div>