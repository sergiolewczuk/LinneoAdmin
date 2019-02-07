UPDATE operaciones_detalle
LEFT JOIN
    inscripciones_carreras
ON
    (inscripciones_carreras.id_detalle_operacion = operaciones_detalle.id)
set
    operaciones_detalle.relacion = inscripciones_carreras.id_detalle_operacion
   WHERE operaciones_detalle.tipo LIKE '%Ins%'