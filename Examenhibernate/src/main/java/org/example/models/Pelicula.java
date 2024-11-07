package org.example.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


    @Entity
    @Table(name = "pelicula")
    public class Pelicula {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false, length = 255)
        private String titulo;

        @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Opinion> opiniones = new ArrayList<>();

        // Constructor vacío
        public Pelicula() {}

        // Constructor con título
        public Pelicula(String titulo) {
            this.titulo = titulo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public List<Opinion> getOpiniones() {
            return opiniones;
        }

        public void setOpiniones(List<Opinion> opiniones) {
            this.opiniones = opiniones;
        }

        // añadir una opinión.
        public void añadirOpinion(Opinion opinion) {
            opiniones.add(opinion);
            opinion.setPelicula(this);
        }

        @Override
        public String toString() {
            return "Pelicula{" +
                    "id=" + id +
                    ", titulo='" + titulo + '\'' +
                    '}';
        }
    }



