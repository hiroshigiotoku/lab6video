package com.example.springbootsindb.entity;

import javax.persistence.*;
import java.io.Serializable;


public class Foto implements Serializable {

    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}
