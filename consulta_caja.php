<?php

if($conexion=mysqli_connect('localhost:3306','root','','escuela_web')){

	if($_SERVER['REQUEST_METHOD']=='POST'){

			$cadena_json = file_get_contents('php://input');
										//recibe informacion por HTTP, en este caso una cadena JSON
			$datos = json_decode($cadena_json, true);
			$c = $datos['c'];
			$v = $datos['v'];

			if ($c == 'nc') {
				$sql="SELECT * FROM alumnos WHERE Num_Control LIKE '%$v%'";
			}elseif ($c == 'n') {
				$sql="SELECT * FROM alumnos WHERE Nombre_Alumno LIKE '%$v%'";
			}elseif ($c == 'pa') {
				$sql="SELECT * FROM alumnos WHERE Primer_Ap_Alumno LIKE '%$v%'";
			}


			
			$res=mysqli_query($conexion,$sql);
			$dat['alumnos']=array();
			while ($fila=mysqli_fetch_assoc($res)) {
				$alumno=array();
				$alumno['nc']=$fila['Num_Control'];
				$alumno['n']=$fila['Nombre_Alumno'];
				$alumno['pa']=$fila['Primer_Ap_Alumno'];
				$alumno['sa']=$fila['Segundo_Ap_Alumno'];
				$alumno['e']=$fila['Edad'];
				$alumno['s']=$fila['Semestre'];
				$alumno['c']=$fila['Carrera'];

				array_push($dat['alumnos'], $alumno);
			}

			echo json_encode($dat);
	}

}else{
	die("Error de conexion".mysqli_connect_error());
}

?>