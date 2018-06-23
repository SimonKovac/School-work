
<nav class="navbar inline row navbar-toggleable-md">
<div class="">
<div class="container fluid">
<div class="navbar-header">
<a class="navbar-brand" href="<?= BASE_URL . "recipe/show" ?>">Home</a>
</div>
<ul class="nav">
<li><a href="<?= BASE_URL . "recipe/show" ?>">All recipes</a></li>
<li><a href="<?= BASE_URL . "user/all" ?>">All users</a></li>
<?php if (empty($_SESSION["id"])): ?>
    <li><a href="<?= BASE_URL . "user/register" ?>">Register</a></li>
    <li><a href="<?= BASE_URL . "user/login" ?>">Log-in</a></li>
<?php else: ?>
    <li><a href="<?= BASE_URL . "recipe/add" ?>">Add recipe</a></li>
    <li><a href="<?= BASE_URL . "user/account?id=" . $_SESSION["id"] ?>">Account</a></li>
    <li><a href="<?= BASE_URL . "user/logout" ?>">Log-out</a></li>
<?php endif; ?>
</ul>
</div>
</nav>
