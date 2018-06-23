<!DOCTYPE html>

<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<meta charset="UTF-8" />
<title>Add entry</title>

<h1>Add new book</h1>

<?php include("view/menu-links.php"); ?>

<form action="<?= BASE_URL . "book/add" ?>" method="post">
    <p><label>Author: <input type="text" name="author" value="<?= $book["author"] ?>" autofocus />
        <span class="important"><?= $errors["author"] ?></span>
    </label>
	</p>
    <p><label>Title: <input type="text" name="title" value="<?= $book["title"] ?>" />
        <span class="important"><?= $errors["title"] ?></span></label>
	</p>
    <p><label>Description: <br />
		<textarea name="description" rows="10" cols="40"><?= $book["description"] ?></textarea></label>
	</p>
    <p><label>Price: <input type="text" name="price" value="<?= $book["price"] ?>" />
        <span class="important"><?= $errors["price"] ?></span></label></p>
    <p><label>Year: <input type="text" name="year" value="<?= $book["year"] ?>" />
        <span class="important"><?= $errors["year"] ?></span></label></p>
    <p><label>Quantity: <input type="text" name="quantity" value="<?= $book["quantity"] ?>" />
        <span class="important"><?= $errors["quantity"] ?></span></label></p>
    <p><button>Insert</button></p>
</form>
