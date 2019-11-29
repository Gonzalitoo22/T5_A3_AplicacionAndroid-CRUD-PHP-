<?php

if($conexion=mysqli_connect('localhost:3306','root','','escuela_web')){

	if($_SERVER['REQUEST_METHOD']=='POST'){

			$cadena_JSON=file_get_contents('php://input');
			$datos=json_decode($cadena_JSON, true);

			$sql="SELECT * FROM alumnos";
			$res=mysqli_query($conexion,$sql);
			$datos['alumnos']=array();
			while ($fila=mysqli_fetch_assoc($res)) {
				$alumno=array();
				$alumno['nc']=$fila['Num_Control'];
				$alumno['n']=$fila['Nombre_Alumno'];
				$alumno['pa']=$fila['Primer_Ap_Alumno'];
				$alumno['sa']=$fila['Segundo_Ap_Alumno'];
				$alumno['e']=$fila['Edad'];
				$alumno['s']=$fila['Semestre'];
				$alumno['c']=$fila['Carrera'];

				array_push($datos['alumnos'], $alumno);
			}

			echo json_encode($datos);

			

	}

}else{
	die("Error de conexion".mysqli_connect_error());
}

?>