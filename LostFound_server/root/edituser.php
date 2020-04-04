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

    if (isset($_POST['userid'])) {
        $userID = $_POST['userid'];
        $name = "'".$_POST['name']."'";
        $college = "'".$_POST['college']."'";
        $major = "'".$_POST['major']."'";
        $action=$_POST['action'];
// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
if($action=="EDIT"){
    $sql="update userinfo set realname=".$name.", college=".$college.", major=".$major." where id=".$userID;
    if ($conn->query($sql) === TRUE) {
        echo "edit successfully";
    } else {
      echo "edit failed";
    }
}
    
    }


?>