package com.example.tareaapp.Service

// Creando un Map Mutable Vacio

val tareas = mutableMapOf<Long, Pair<String, Boolean>>()

// Funcion de Creacion de tareas CREATE

fun crearTarea(persistenciaDataService: PersistenciaDataService) {
    val id = persistenciaDataService.getId()
    val tarea = persistenciaDataService.getTarea()
    val estado = persistenciaDataService.getEstado()

    val valor = Pair(tarea, estado)
    tareas[id] = valor
}

// Funcion de Lectura de tarea READ
fun leerTarea(id : Long): String? {
    if (!tareas.containsKey(id)) {
        return null
    }else{
        return tareas[id]?.first
    }
}

// Funcion de Actualizacion de tarea UPDATE
fun actualizarTarea(id : Long, tarea : String) {
    if (tareas.containsKey(id)) {
        tareas[id] = Pair(tarea, tareas[id]?.second ?: false)
    }
}

// Funcion de Eliminacion de tarea DELETE por id
fun deleteTarea(id: Long): Pair<String, Boolean>? {
    val tareaEliminada = tareas.remove(id)
    return tareaEliminada
}

// Funcion de Eliminacion de tarea segun su contenido
fun deleteTareaPorContenido(contenidoBuscado: String): Boolean { // Devuelve true si algo se eliminó
    var claveAEliminar: Long? = null
    for (entry in tareas.entries) {
        if (entry.value.first == contenidoBuscado) {
            claveAEliminar = entry.key
            break // Eliminamos la primera tarea encontrada con ese contenido
        }
    }

    return if (claveAEliminar != null) {
        tareas.remove(claveAEliminar)
        true
    } else {
        false
    }
}

// Funcion para modificar el estado de una tarea (buscando por contenido)
fun cambiarEstadoPorContenido(contenidoBuscado: String, nuevoEstado: Boolean): Boolean {
    val claveDeTarea = obtenerClavePorContenido(contenidoBuscado)

    return if (claveDeTarea != null) {
        val tareaActual = tareas[claveDeTarea]
        if (tareaActual != null) {
            // Creamos un nuevo Pair con el contenido original y el nuevo estado
            tareas[claveDeTarea] = Pair(tareaActual.first, nuevoEstado)
            true
        } else {
            false
        }
    } else {
        false
    }
}

// Funcion para obtener la clave de una tarea segun su contenido
fun obtenerClavePorContenido(contenidoBuscado: String): Long? {
    for (entry in tareas.entries) {
        if (entry.value.first == contenidoBuscado) {
            return entry.key
        }
    }
    return null // No se encontró ninguna tarea con ese contenido
}

// Alternativa: Función para modificar el estado de una tarea (buscando por ID) - Más directo
fun cambiarEstadoPorId(id: Long, nuevoEstado: Boolean): Boolean {
    val tareaActual = tareas[id]
    return if (tareaActual != null) {
        tareas[id] = Pair(tareaActual.first, nuevoEstado)
        true
    } else {
        false
    }
}


// Funcion de Lectura de todas las tareas (solo el contenido String)
fun leerContenidoDeTodasLasTareas(): List<String> {
    return tareas.values.map { it.first }
}

// Funcion de Lectura de toda la información de las tareas (Lista de Pairs)
fun leerInfoCompletaDeTodasLasTareas(): List<Pair<String, Boolean>> {
    return tareas.values.toList()
}


