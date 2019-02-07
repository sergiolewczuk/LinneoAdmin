SELECT *
FROM operaciones 
JOIN inscripciones_carreras ON 
 operaciones.fecha = inscripciones_carreras.fecha_inscripcion AND operaciones.hora = inscripciones_carreras.hora_inscripcion AND operaciones.pk_persona = inscripciones_carreras.id_alumno
 GROUP BY pk_persona