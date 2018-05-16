<?php
session_start();
$nome_partida= $_POST["nomePartida"];
$descricao_partida = $_POST["descpartida"]; 
$endereco = $_POST["rua"];
$latitude= floatval($_POST["Latitude"]);
$bairro = $_POST["Bairro"];    
$longitude = floatval($_POST["Longitude"]);    

$_SESSION['valorEndereco'] = $endereco;

$conn = mysqli_connect("localhost","root","","naTrave");
$insertQuery = "insert into cadpartida(nomepartida,descPartida,
rua,latitude,bairro,longitude) values('$nome_partida','$descricao_partida','$endereco',$latitude,'$bairro',$longitude)";

mysqli_query($conn,$insertQuery);

header('location: index.php');
?>