<?php
 /* to save the results of the survey from survey app*/
    header('Content-Type: text/html; charset=utf-8');
    $servername = "localhost";
    $username = "root";
    $password = "usbw";
    $dbname = "myDB";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if (isset($_POST['userid']) && isset($_POST['password'])) {
        $userID = $_POST['userid'];
        $password ="'". $_POST['password']."'";
// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
    $sql = "select * from userinfo where id=".$userID. " and pwd=".$password;
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        echo "Login success";
    }else{
        echo "Login falied";
    }
}
?>