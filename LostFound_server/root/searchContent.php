<?php
 /* to save the results of the survey from survey app*/
    header('Content-Type: text/html; charset=utf-8');
    $servername = "localhost";
    $username = "root";
    $password = "usbw";
    $dbname = "myDB";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if (isset($_POST['action'])) {
        $action=$_POST['action'];
        if($action=="getContent"){

// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
    $sql = "select * from postinfo";
    echo "[";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        while($row = $result->fetch_assoc()) {
            echo "{\"title\": \"" . $row["title"]."\",".
                  "\"item\":\"".$row["item"]."\",".
                  "\"time\":\"".$row["time"]."\",".
                  "\"location\":\"".$row["location"]."\",".
                  "\"describe\":\"".$row["detail"]."\",".
                  "\"posterid\":\"".$row["posterID"]."\",".
                  "\"type\":\"".$row["type"]."\"},";
        }
    }else{
        echo "no data";
    }
        }
        

}
?>