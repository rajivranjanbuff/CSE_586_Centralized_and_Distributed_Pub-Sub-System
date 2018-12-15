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
    shell_exec('/usr/local/bin/docker build -t python-new-world .');
    $result = shell_exec('/usr/local/bin/docker run python-new-world');
    echo "The output is below";
    echo "<br />";
    echo "<br />";
    echo $result;
    ?>

</body>
</html>