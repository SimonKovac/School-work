<!DOCTYPE html>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "style.css" ?>">
<link rel="stylesheet" type="text/css" href="<?= CSS_URL . "bootstrap.min.css" ?> ">

<title>Login form</title>

<h1>Please log in</h1>

<?php include("view/menu-links.php"); ?>

<div id="content" class="panel-body">

<?php if (!empty($errorMessage)): ?>
    <p class="important"><?= $errorMessage ?></p>
<?php endif; ?>

<form action="<?= BASE_URL . "user/login" ?>" method="post">
    <div class="form-group row">
        <label for="username" class="col-sm-2 col-form-label">Username: </label>
        <div class="col-sm-9">
            <input class="form-control" type="text" name="username" autocomplete="off" required autofocus />
        </div>
    </div>
    <div class="form-group row">
        <label for="password" class="col-sm-2 col-form-label">Password: </label>
        <div class="col-sm-9">
            <input class="form-control" type="password" name="password" required />
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-2">
            <button class="btn btn-primary">Log-in</button>
        </div>
    </div>
</form>
</div>