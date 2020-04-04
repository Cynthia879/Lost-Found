<?php
 /* to save the results of the survey from survey app*/
    header('Content-Type: text/html; charset=utf-8');
    $servername = "localhost";
    $username = "root";
    $password = "usbw";
    $dbname = "myDB";

    $conn = new mysqli($servername, $username, $password);
    // 检测连接
    if ($conn->connect_error) {
    die("Connect success\n: " . $conn->connect_error);} 
    $sql = "CREATE DATABASE if not exists ".$dbname;
    if ($conn->query($sql) === FALSE) {
    echo "Error creating database : " . $conn->error;}


    $conn = new mysqli($servername, $username, $password, $dbname);

    if (isset($_POST['userid'])&&isset($_POST['title'])) {
        $userID = $_POST['userid'];
        $title ="'". $_POST['title']."'";
        $replycontent ="'". $_POST['replycontent']."'";
       


// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
    $sql = "INSERT INTO replyinfo
    VALUES ($title,$userID,$replycontent)";
     if ($conn->query($sql) === TRUE) {
        echo "publish successfully";
    } else {
      echo "publish failed";
    }
    
    }

?>