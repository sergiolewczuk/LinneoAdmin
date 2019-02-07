/* SELECCIONAR LAS CUOTAS QUE NO TENGAN DEVENGAMIENTO */
SELECT cuotas_alumnado.id, cuotas_alumnado.id_alumno, cuotas_alumnado.id_carrera,
asientos_detalle.detalle, asientos_detalle.id_movimiento,
IFNULL(IFNULL(CONCAT(carrera_grados.num_curso,'-',carrera_grados.div_curso, ' ', carrera.nombre),CONCAT(carrera_grados.div_curso,' ',carrera.nombre)), carrera.nombre) AS info_curso,
carrera.nivel,carrera.monto_cuota
FROM cuotas_alumnado
JOIN carrera ON (carrera.id = cuotas_alumnado.id_carrera)
LEFT JOIN carrera_grados ON (carrera_grados.id = cuotas_alumnado.id_grado)
LEFT JOIN asientos_detalle ON (cuotas_alumnado.id = asientos_detalle.cuota_id)
LEFT JOIN asientos ON (asientos.id_asiento = asientos_detalle.id_asiento)
WHERE cuotas_alumnado.mes_t = 3
GROUP BY cuotas_alumnado.id