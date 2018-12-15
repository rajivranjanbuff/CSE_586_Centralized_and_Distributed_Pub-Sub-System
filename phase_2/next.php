<!DOCTYPE html>
<html>
  <head>
    <title>Output Area</title>
    </head>
<body>
    <?php 

    $code = $_POST['source']; 
    // $code1 = $_POST['source1'].PHP_EOL;; 
    // $code2 = $_POST['source2'].PHP_EOL;; 
    $myfile = fopen("abc.txt", "w");
    fwrite($myfile, $code);
    // fwrite($myfile, $code1);
    // fwrite($myfile, $code2);
    shell_exec('javac DriveMain.java');
    shell_exec('/usr/local/bin/docker build -t hello .');
    $result = shell_exec('/usr/local/bin/docker run hello ');
    echo "The output is below";
    echo "<br />";
    echo "<br />";
    //echo $result;
    $result = shell_exec('java DriveMain');

    
    //echo gettype($result);
//     //echo $result;
   $oparray = preg_split('/\s+/', trim($result));
// //     for ($x = 0; $x < sizeof($oparray); $x++) {
// //     echo $oparray[$x];
// //     echo "</br>";


// // } 
    echo $oparray[0];
    echo "</br>";
    echo $oparray[1];
    echo "</br>";
    
    echo $oparray[2];
    echo "</br>";
    echo "</br>";
    echo $oparray[3];
    echo "</br>";

    echo $oparray[4];
    echo "</br>";
    echo $oparray[5];
    echo "</br>";
    echo "</br>";
    echo $oparray[6];
    echo "</br>";
    echo $oparray[7];

    echo "</br>";
    echo $oparray[8];
    echo "</br>";
    echo "</br>";
    echo $oparray[9];
    echo "</br>";
    echo $oparray[10];
    echo "</br>";
    echo $oparray[11];
    echo "</br>";
    echo "</br>";

    echo $oparray[12];
    echo "</br>";
    echo $oparray[13];
    echo "</br>";
    echo $oparray[14];
    echo "</br>";
    echo "</br>";

    echo $oparray[15];
    echo "</br>";
    echo $oparray[16];
    echo "</br>";
    echo $oparray[17];
    echo "</br>";
    echo $oparray[18];
    echo "</br>";
    echo "</br>";

    echo $oparray[19];
    echo "</br>";
    echo $oparray[20];
    echo "</br>";
    echo $oparray[21];
    echo "</br>";
    echo $oparray[22];
    echo "</br>";
    
    ?>

</body>
</html>