package com.example.tareaapp.Service;

import java.util.List;

public class PersistenciaDataService {

    // Atributos de la Clase
    private String tarea;
    private Long id;

    //Constructor de la Clase
    public PersistenciaDataService(Long id, String tarea){
        this.id = id;
        this.tarea = tarea;
    }

    // Metodos de la Clase (Getters y Setters)
    public String getTarea(){
        return tarea;
    }
    public void setTarea(String tarea){
        this.tarea = tarea;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersistenciaDataService{" +
                "tarea='" + tarea + '\'' +
                ", id=" + id +
                '}';
    }
}
