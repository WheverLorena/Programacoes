<?php

$nome = $_POST['nome'];
$email = $_POST['email'];;


$conn = mysqli_connect("localhost","root","","naTrave");
$insertQuery = "insert into pessoa(nome,email) values('$nome','$email')";

mysqli_query($conn,$insertQuery);

header('location: index.php');
?>