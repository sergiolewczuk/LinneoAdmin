SELECT operaciones_detalle.id as numero_identificador_del_detalle, operaciones.fecha, operaciones.pk_persona, operaciones_detalle.detalle, operaciones_detalle.tipo, alquileres_contratos.id_responsable, 
lotes_responsables.apellidos, alquileres_contratos.id as contrato, operaciones_detalle.relacion,

CASE
	WHEN operaciones_detalle.detalle LIKE '%Enero%'
   THEN
   	1
   WHEN operaciones_detalle.detalle LIKE '%Febrero%'
   THEN
   	2
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
END AS mess,

(SELECT cuotas_alquileres.id FROM cuotas_alquileres 

			JOIN alquileres_contratos ON alquileres_contratos.id = cuotas_alquileres.id_contrato
			
			WHERE alquileres_contratos.id_responsable = operaciones.pk_persona AND cuotas_alquileres.mes = mess AND cuotas_alquileres.id_contrato = alquileres_contratos.id) AS id_cuota


FROM operaciones_detalle
JOIN operaciones ON operaciones.id = operaciones_detalle.id_operacion
JOIN alquileres_contratos ON alquileres_contratos.id_responsable = operaciones.pk_persona
JOIN lotes_responsables ON lotes_responsables.id = operaciones.pk_persona

WHERE 
		operaciones.sector = 'Alquileres' AND operaciones_detalle.tipo = 'Cuota'