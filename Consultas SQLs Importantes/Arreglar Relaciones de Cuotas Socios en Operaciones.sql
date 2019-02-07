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

(SELECT cuotas_socios.id FROM cuotas_socios WHERE cuotas_socios.socio = operaciones.pk_persona AND cuotas_socios.mes = mes) AS id_cuota


FROM operaciones_detalle
JOIN operaciones ON operaciones.id = operaciones_detalle.id_operacion
WHERE 
		operaciones.sector = 'Socios' AND operaciones_detalle.tipo = 'Cuota'
		
		
		) as tabla
ON (tabla.id = operaciones_detalle.id)

SET operaciones_detalle.relacion = tabla.id_cuota