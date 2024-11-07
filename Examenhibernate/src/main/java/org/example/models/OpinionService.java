package org.example.models;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OpinionService {

    private SessionFactory sessionFactory;

    public OpinionService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Opinion> obtenerOpinionesPorUsuario(String correoUsuario) {
        Session session = sessionFactory.openSession();
        List<Opinion> opiniones = null;

        try {
            Query<Opinion> query = session.createQuery("FROM Opinion WHERE usuario = :correoUsuario", Opinion.class);
            query.setParameter("correoUsuario", correoUsuario);
            opiniones = query.list();
        } finally {
            session.close();
        }

        return opiniones;
    }

    public void añadirOpinion(String descripcion, String usuario, int puntuacion, Pelicula pelicula) {
        if (puntuacion < 0 || puntuacion > 10) {
            throw new IllegalArgumentException("La puntuación debe estar entre 0 y 10.");
        }

        Opinion opinion = new Opinion(descripcion, usuario, puntuacion, pelicula);

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try {
            session.save(opinion);
            session.getTransaction().commit();
            System.out.println("Opinión añadida exitosamente: " + opinion);
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }
}
