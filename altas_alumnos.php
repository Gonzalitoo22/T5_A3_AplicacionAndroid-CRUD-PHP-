<?php 
	if ($conexion = mysqli_connect('localhost:3306', 'root', '', 'escuela_web')) {
		if ($_SERVER['REQUEST_METHOD'] == 'POST') {
			$cadena_JSON = file_get_contents('php://input'); //Recibe informacion a traves de HTTP, una cadena JSON

			$datos = json_decode($cadena_JSON, true);
			//true indica que se debe retornar un vector asociativo

			$nc = $datos['nc'];
			$n = $datos['n'];
			$pa = $datos['pa'];
			$sa = $datos['sa'];
			$e = $datos['e'];
			$s = $datos['s'];
			$c = $datos['c'];

			$sql = "INSERT INTO alumnos VALUES ('$nc', '$n', '$pa', '$sa', $e, $s, '$c')";

			//echo json_encode($sql);

			$resultado = mysqli_query($conexion, $sql);

			$respuesta = array();
			if ($resultado) {
				$respuesta['exito'] = 1;
				$respuesta['msj'] = 'Insercion correcta';
				echo json_encode($respuesta);
			}else{
				$respuesta['exito'] = 0;
				$respuesta['msj'] = 'Error en la insercion';
				echo json_encode($respuesta);
			}


		}
	}else{
		die("Error en conexion " . mysqli_connect_error());
	}
?>