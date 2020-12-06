<?PHP

/*
    Variables Globales
    Conexión
*/

$hostname = "localhost";
$database = "conta";
$username = "root";
$password = "sylka1234";
$json = array();

//Validación de campos en la consulta
if(isset($_GET["u"]) && isset($_GET["p"])) {
    $usr = $_GET["u"];
    $pwd = $_GET["p"];

    //Connection Query
    $conexion=mysqli_connect($hostname,$username,$password,$database);

    //Insert query
    $add = "INSERT INTO usr(usr_id, usr_correo, usr_pwd)  VALUES (0,'{$usr}','{$pwd}')";
    $resultado=mysqli_query($conexion, $add);

    //Validación del Insert Query
    if($consulta){

        //Select Query
        $consulta="SELECT * FROM usr";
        $resultado=mysqli_query($conexion,$consulta);

        if($reg=mysqli_fetch_array($resultado)){
            $json['usr'][]=$reg;
        }

    //Cierre de conexión
    mysqli_close($conexion);
    echo json_encode($json);

    }
    //Null Response
    else{
        //Arreglo json null
        $results["id"]='';
        $results["usuario"]='';
        $results["pass"]='';
        $results["create"]='';
        $json['usr'][]=$results;
        echo json_encode($json);
    }
}
//No Arguments
else{
    //Arreglo json null
    $results["id"]='';
    $results["usuario"]='';
    $results["pass"]='';
    $results["create"]='';
    $json['usr'][]=$results;
    echo json_encode($json);
}

?>