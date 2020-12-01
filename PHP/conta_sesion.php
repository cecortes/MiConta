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
if(isset($_GET["usr"]) && isset($_GET["pwd"])) {
    $usr = $_GET["usr"];
    $pwd = $_GET["pwd"];

    //Connection Query
    $conexion=mysqli_connect($hostname,$username,$password,$database);

    //Select query
    $select = "SELECT * FROM usr WHERE usr_correo = '{$usr}' AND usr_pwd = '{$pwd}'";
    $resultado=mysqli_query($conexion, $select);

    //Validación del Select Query
    if($select) {

        //Validación de la respuesta
        if($res = mysqli_fetch_array($resultado)) {

            //Pasamos el arreglo recibido al json array
            $json['sesion'][]=$res;

        }

        //Cierre de conexión
        mysqli_close($conexion);
        echo json_encode($json);

    }
    //Null Response
    else{
        //Arreglo json null
        $respuesta["id"] = '999';
        $respuesta["usr"] = 'N';
        $respuesta["pass"] = '1';
        $respuesta["create"] = 'N';
        $json['sesion'][] = $respuesta;
        echo json_encode($json);
    }
}
//No Arguments
else{
    //Arreglo json null
    $respuesta["id"] = '999';
    $respuesta["usr"] = 'N';
    $respuesta["pass"] = '1';
    $respuesta["create"] = 'N';
    $json['sesion'][] = $respuesta;
    echo json_encode($json);
}
?>