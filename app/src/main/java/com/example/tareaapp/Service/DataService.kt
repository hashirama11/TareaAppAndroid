package com.example.tareaapp.Service

// Creando un Map Mutable Vacio

val tareas = mutableMapOf<Long, String>()

// Funcion de Creacion de tareas CREATE

fun crearTarea(persistenciaDataService: PersistenciaDataService) {
    tareas[persistenciaDataService.getId()] = persistenciaDataService.getTarea()
}

// Funcion de Lectura de tarea READ
fun leerTarea(id : Long): String? {
    val tarea : String? = tareas[id]
    return tarea
}

// Funcion de Actualizacion de tarea UPDATE
fun actualizarTarea(id : Long, tarea : String) {
    tareas[id] = tarea
}

// Funcion de Eliminacion de tarea DELETE
fun deleteTarea(id : Long) {
    tareas.remove(id)
}

// Funcion de Lectura de todas las tareas
fun leerTareas(): List<String> {
    return tareas.values.toList()
}