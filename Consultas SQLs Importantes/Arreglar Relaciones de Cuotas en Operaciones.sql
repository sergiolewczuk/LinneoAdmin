UPDATE operaciones_detalle LEFT JOIN
(SELECT operaciones_detalle.id, operaciones.fecha, operaciones.pk_persona, operaciones_detalle.detalle, operaciones_detalle.tipo,
CASE
	WHEN operaciones_detalle.detalle LIKE '%Marzo%'
   THEN
   	3
   WHEN operaciones_detalle.detalle LIKE '%Abril%'
   THEN
   	4
   WHEN operaciones_detalle.detalle LIKE '%Mayo%'
   THEN
   	5
   WHEN operaciones_detalle.detalle LIKE '%Junio%'
   THEN
   	6
   WHEN operaciones_detalle.detalle LIKE '%Julio%'
   THEN
   	7
   WHEN operaciones_detalle.detalle LIKE '%Agosto%'
   THEN
   	8
   WHEN operaciones_detalle.detalle LIKE '%Septiembre%'
   THEN
   	9
	WHEN operaciones_detalle.detalle LIKE '%Octubre%'
   THEN
   	10
	WHEN operaciones_detalle.detalle LIKE '%Noviembre%'
   THEN
   	11
	WHEN operaciones_detalle.detalle LIKE '%Diciembre%'
   THEN
   	12
END AS mes,

CASE
	WHEN operaciones_detalle.detalle LIKE '%Inglés%'
	THEN 
		(SELECT cuotas_alumnado.id FROM cuotas_alumnado WHERE 
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 8  AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 12 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 15 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 16 AND cuotas_alumnado.mes_t = mes OR 
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 17 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 18 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 19 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 20 AND cuotas_alumnado.mes_t = mes)
	WHEN operaciones_detalle.detalle NOT LIKE '%Inglés%'			
	THEN
		(SELECT cuotas_alumnado.id FROM cuotas_alumnado WHERE 
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 1  AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 2  AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 3  AND cuotas_alumnado.mes_t = mes OR 
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 8  AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 9  AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 10 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 11 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 13 AND cuotas_alumnado.mes_t = mes OR
				cuotas_alumnado.id_alumno = operaciones.pk_persona AND cuotas_alumnado.id_carrera = 14 AND cuotas_alumnado.mes_t = mes)
END AS id_cuota

FROM operaciones_detalle
JOIN operaciones ON operaciones.id = operaciones_detalle.id_operacion
WHERE operaciones.sector = 'Institucional' AND operaciones_detalle.tipo = 'Cuota'
AND operaciones_detalle.detalle LIKE '%2015%' OR operaciones_detalle.tipo = 'Cuota Bonificada' AND operaciones_detalle.detalle LIKE '%2015%') as tabla
ON (tabla.id = operaciones_detalle.id)

SET operaciones_detalle.relacion = tabla.id_cuota