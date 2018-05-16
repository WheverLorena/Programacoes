<?php
require("conexao.php");

function parseToXML($htmlStr){
	$xmlStr=str_replace('<','&lt;',$htmlStr);
	$xmlStr=str_replace('>','&gt;',$xmlStr);
	$xmlStr=str_replace('"','&quot;',$xmlStr);
	$xmlStr=str_replace("'",'&#39;',$xmlStr);
	$xmlStr=str_replace("&",'&amp;',$xmlStr);
	return $xmlStr;
}

// Select all the rows in the markers table
$result_markers = "SELECT * FROM cadpartida";
$resultado_markers = mysqli_query($conn, $result_markers);

header("Content-type: text/xml");

// Start XML file, echo parent node
echo '<markers>';

// Iterate through the rows, printing XML nodes for each
while ($row_markers = mysqli_fetch_assoc($resultado_markers)){
  // Add to XML document node
  echo '<marker ';
  echo 'name="' . parseToXML($row_markers['nomePartida']) . '" ';
  echo 'address="' . parseToXML($row_markers['rua']) . '" ';
  echo 'lat="' . $row_markers['latitude'] . '" ';
  echo 'lng="' . $row_markers['longitude'] . '" ';
  echo 'type="' . $row_markers['descPartida'] . '" ';
  echo '/>';
}

// End XML file
echo '</markers>';



