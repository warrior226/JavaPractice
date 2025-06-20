import JPA.music.Album;
import JPA.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        try(
                var sessionFactory= Persistence.createEntityManagerFactory("orm_persistence");
                EntityManager entityManager=sessionFactory.createEntityManager() //Entity Manager to manage operations with the database
        ){
            var transaction=entityManager.getTransaction();
            transaction.begin();
         //   entityManager.persist(new Artist("Muddy Water"));
            Artist artist =entityManager.find(Artist.class,201);
            artist.removeDuplicates();
            System.out.println(artist);
         //   artist.addAlbum("The Best of Patrick RAYAISSE");
            //System.out.println(artist);
            //List<Album> albums=entityManager.createQuery("SELECT a FROM Album a ",Album.class).getResultList();
           // System.out.println(albums);

            // entityManager.remove(artist);
//            Artist artist = new Artist(202,"Muddy Water");
//            entityManager.merge(artist);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }











}

