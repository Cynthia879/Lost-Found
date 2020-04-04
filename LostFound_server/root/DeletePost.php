<?php
 /* to save the results of the survey from survey app*/
    header('Content-Type: text/html; charset=utf-8');
    $servername = "localhost";
    $username = "root";
    $password = "usbw";
    $dbname = "myDB";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if (isset($_POST['title'])) {
        $title = "'".$_POST['title']."'";
// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
    $sql = "delete from postinfo where title=".$title;
    $result = $conn->query($sql);
    if($conn->query($sql) === TRUE){
        echo "";
    }else{
        echo "";
    }
    $sql = "delete from replyinfo where title=".$title;
    $result = $conn->query($sql);
    if($conn->query($sql) === TRUE){
        echo "delete success";
    }else{
        echo "delete falied";
    }
}
?>