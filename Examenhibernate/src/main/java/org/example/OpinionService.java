package org.example;

import org.example.models.Opinion;
import org.example.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OpinionService {

    SessionFactory sf;

    public OpinionService(SessionFactory sf) {
        this.sf = sf;
    }

    public List<Opinion> obtenerOpinionesPorUsuario(String u) {
        Session s = sf.openSession();
        List<Opinion> op = null;
        try {
            Query<Opinion> q = s.createQuery("FROM Opinion WHERE usuario = :u", Opinion.class);
            q.setParameter("u", u);
            op = q.list();
        } finally {
            s.close();
        }
        return op;
    }

    public void añadirOpinion(String desc, String user, int punt, Pelicula peli) {
        if (punt < 0 || punt > 10) {
            System.out.println("Error: punt fuera de rango.");
            return;
        }

        Opinion op = new Opinion(desc, user, punt, peli);

        Session s = sf.openSession();
        s.beginTransaction();

        try {
            s.save(op);
            s.getTransaction().commit();
            System.out.println("Guardado: " + op);
        } catch (Exception e) {
            System.out.println("Error al guardar.");
            if (s.getTransaction() != null) {
                s.getTransaction().rollback();
            }
        } finally {
            s.close();
        }
    }
}
