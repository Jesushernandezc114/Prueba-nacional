package com.example.conectamoviljhc.Model;

public class Contacto {

    String name,Email,numerotel;

    public Contacto() {
    }

    public Contacto(String name, String email, String numerotel) {
        this.name = name;
        Email = email;
        this.numerotel = numerotel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNumerotel() {
        return numerotel;
    }

    public void setNumerotel(String numerotel) {
        this.numerotel = numerotel;
    }

    @Override
    public String toString() {
        return "Contacto: " +
                "nombre= " + name + '\'' +
                " , Email= " + Email + '\'' +
                " , numero telefonico= " + numerotel + '\'' +
                ' ';
    }
}
