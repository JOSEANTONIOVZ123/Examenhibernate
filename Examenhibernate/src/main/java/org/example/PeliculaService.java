package org.example;
import org.example.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PeliculaService {

    private SessionFactory sessionFactory;

    public PeliculaService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void registrarPelicula(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la película no puede estar vacío.");
        }

        Pelicula pelicula = new Pelicula(titulo);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.save(pelicula);
            session.getTransaction().commit();
            System.out.println("Película guardada exitosamente: " + pelicula);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

    public Pelicula buscarPeliculaPorTitulo(String tituloPelicula) {
        return null;
    }
}
