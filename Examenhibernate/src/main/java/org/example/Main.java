package org.example;

import org.example.models.Opinion;
import org.example.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    private static PeliculaService peliculaService;


    public static void main(String[] args) {
        // Crear una configuración de Hibernate y construir la SessionFactory
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        // Abrir una nueva sesión
        Session session = sessionFactory.openSession();

        // Iniciar una transacción
        session.beginTransaction();

        try {
            Pelicula pelicula = new Pelicula("Inception");

            Opinion opinion1 = new Opinion("Amazing ", "user1@example.com", 9, pelicula);
            Opinion opinion2 = new Opinion("shit", "user2@example.com", 8, pelicula);

            pelicula.añadirOpinion(opinion1);
            pelicula.añadirOpinion(opinion2);

            session.save(pelicula);

            // Confirmar la transacción
            session.getTransaction().commit();

            System.out.println("Película y opiniones guardadas exitosamente en la base de datos.");

        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            session.close();
            sessionFactory.close();
            // Configurar Hibernate y el servicio de películas
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            peliculaService = new PeliculaService(sessionFactory);

            Scanner scanner = new Scanner(System.in);

            // Registro de nuevas películas
            while (true) {
                System.out.println("¿Deseas registrar una nueva película? (sí/no)");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("no")) {
                    break;
                }
                System.out.println("Introduce el título de la nueva película:");
                String titulo = scanner.nextLine();
                try {
                    peliculaService.registrarPelicula(titulo);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            scanner.close();
            sessionFactory.close();


           /* // Configurar Hibernate y el servicio de películas
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            peliculaService = new PeliculaService(sessionFactory);

            // Crear Scanner para la entrada de usuario
            Scanner scanner1 = new Scanner(System.in);

            // Registro de nuevas películas
            while (true) {
                System.out.println("¿Deseas registrar una nueva película? (sí/no)");
                String respuesta = scanner1.nextLine();
                if (respuesta.equalsIgnoreCase("no")) {
                    break;
                }
                System.out.println("Introduce el título de la nueva película:");
                String titulo = scanner1.nextLine();
                try {
                    peliculaService.registrarPelicula(titulo);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
            scanner1.close();
            sessionFactory.close();

            // Configuración de Hibernate y servicios
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            opinionService = new OpinionService(sessionFactory);
            peliculaService = new PeliculaService(sessionFactory);

            // Crear Scanner para entrada de usuario
            Scanner scan = new Scanner(System.in);

            // Menú de opciones
            while (true) {
                System.out.println("Elige una opción: ");
                System.out.println("1. Registrar nueva película");
                System.out.println("2. Obtener opiniones de un usuario");
                System.out.println("3. Añadir una opinión");
                System.out.println("4. Salir");
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        peliculaService.registrarPelicula(scan);
                        break;
                    case 2:
                        obtenerOpinionesDeUsuario(scan);
                        break;
                    case 3:
                        añadirNuevaOpinion(scan);
                        break;
                    case 4:
                        System.out.println("Saliendo...");
                        scanner.close();
                        sessionFactory.close();
                        return;
                    default:
                        System.out.println("Opción no válida.");

            */
        }
    }
}
