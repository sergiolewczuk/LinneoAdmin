SELECT operaciones_detalle.id AS detalle_operacion,operaciones_detalle.tipo,
                    SUM(operaciones_detalle.monto),
                    CASE operaciones_detalle.tipo 
						  		WHEN 'Cuota'
						  		THEN
						  			CASE  
									WHEN operaciones_detalle.relacion IS NULL
						  			THEN /*DEUDAS 2014*/
						  			(SELECT nivel FROM deudores_temp WHERE id_alumno = operaciones.pk_persona)
						  			WHEN operaciones_detalle.relacion IS NOT NULL
						  			THEN
						  			(SELECT nivel FROM carrera JOIN cuotas_alumnado ON (carrera.id = cuotas_alumnado.id_carrera) JOIN operaciones_detalle ON (cuotas_alumnado.id = operaciones_detalle.relacion) WHERE operaciones_detalle.id = detalle_operacion)
                    			END
						  		WHEN 'Cuota Bonificada'
                   		THEN
                    			(SELECT nivel FROM carrera JOIN cuotas_alumnado ON (carrera.id = cuotas_alumnado.id_carrera) JOIN operaciones_detalle ON (cuotas_alumnado.id = operaciones_detalle.relacion) WHERE operaciones_detalle.id = detalle_operacion)
						  		WHEN 'Deuda 2013'
						  		THEN
						  			(SELECT nivel FROM deudores_temp WHERE id_alumno = operaciones.pk_persona)
							   WHEN 'Inscripción'
								THEN
									(SELECT nivel FROM carrera JOIN inscripciones_carreras ON (carrera.id = inscripciones_carreras.id_carrera) JOIN operaciones_detalle ON (inscripciones_carreras.id = operaciones_detalle.relacion) WHERE operaciones_detalle.id = detalle_operacion)	 
						  END AS niveles, 
						  CASE 
						  		WHEN operaciones_detalle.tipo = 'Cuota'
						  		THEN
						  			(SELECT num_cuenta FROM niveles_cuentas_cuotas WHERE nivel = niveles AND devengamiento = 0)
						  		WHEN operaciones_detalle.tipo = 'Inscripción'
						  		THEN
						  			(SELECT num_cuenta FROM niveles_cuentas_inscripcion WHERE nivel = niveles AND devengamiento = 0)
						  		WHEN operaciones_detalle.tipo = 'Deuda 2013'
						  		THEN
						  			(SELECT IF(niveles = 'Ex Alumnos',(SELECT valor FROM variables WHERE nombre = 'cuenta_ex_alumnos'),(SELECT num_cuenta FROM niveles_cuentas_cuotas WHERE nivel = niveles AND devengamiento = 0)))
						  		
								/*WHEN 'Cuota Bonificada' AND niveles = 'Nivel Inicial'
						  		THEN
						  			(SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_inicial')
						  		WHEN 'Cuota Bonificada' AND niveles = 'Nivel Primario'
						  		THEN
						  			(SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_primario')
						  		WHEN 'Cuota Bonificada' AND niveles = 'Nivel Secundario'
						  		THEN
						  			(SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_secundario')
						  		WHEN 'Cuota Bonificada' AND niveles = 'Nivel Terciario'
						  		THEN
						  			(SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_terciario')
						  		WHEN 'Cuota Bonificada' AND niveles = 'Nivel Lenguas'
						  		THEN
						  			(SELECT valor FROM variables WHERE nombre = 'bonificacion_nivel_lenguas')*/
						  END AS numero_cuenta					  
						  
						  FROM operaciones
                    JOIN operaciones_detalle
                    ON (operaciones.id = operaciones_detalle.id_operacion)
                    WHERE operaciones.sector = 'Institucional' AND operaciones.estado = 'A'
                    GROUP BY tipo, niveles