<?php
 /* to save the results of the survey from survey app*/
    header('Content-Type: text/html; charset=utf-8');
    $servername = "localhost";
    $username = "root";
    $password = "usbw";
    $dbname = "myDB";

    $conn = new mysqli($servername, $username, $password, $dbname);

    if (isset($_POST['title'])) {
        $title="'".$_POST['title']."'";

// 检测连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
} 
    $sql = "select * from replyinfo where title=".$title;
    echo "[";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        while($row = $result->fetch_assoc()) {
            echo "{\"title\": \"" . $row["title"]."\",".
                  "\"replyid\":\"".$row["replyid"]."\",".
                  "\"replycontent\":\"".$row["replycontent"]."\"},";
        }
    }else{
        echo "no data";
    }
        
        

}
?>