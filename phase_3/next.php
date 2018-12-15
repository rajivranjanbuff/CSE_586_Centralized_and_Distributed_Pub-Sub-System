<!DOCTYPE html>
<html>
  <head>
    <title>Output Area</title>
    </head>
<body>
    <?php 

    $code = $_POST['source']; 
    $myfile = fopen("abc.py", "w");
    fwrite($myfile, $code);
    shell_exec('/usr/local/bin/docker build -t peiworld/csci652:latest .');
    $result = shell_exec('/usr/local/bin/docker run peiworld/csci652:latest');
    echo "The output is below";
    echo "<br />";
    echo "<br />";
    echo $result;
    ?>

</body>
</html>