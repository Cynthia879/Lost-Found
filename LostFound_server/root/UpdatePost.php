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

    if (isset($_POST['title'])) {
        $title = "'".$_POST['title']."'";
        $item = "'".$_POST['item']."'";
        $type = "'".$_POST['type']."'";
        $time="'".$_POST['time']."'";
        $location = "'".$_POST['location']."'";
        $describe = "'".$_POST['describe']."'";
// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
    $sql="update postinfo set item=".$item.", time=".$time.", location=".$location.", detail=".$describe.", type=".$type. 
    "where title=".$title;
    if ($conn->query($sql) === TRUE) {
        echo "modify successfully";
    } else {
      echo "modify failed";
    }
    
    }
?>