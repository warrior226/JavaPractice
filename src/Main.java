import JPA.music.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        try(
                var sessionFactory= Persistence.createEntityManagerFactory("orm_persistence");
                EntityManager entityManager=sessionFactory.createEntityManager() //Entity Manager to manage operations with the database
        ){
            var transaction=entityManager.getTransaction();
            transaction.begin();
         //   entityManager.persist(new Artist("Muddy Water"));
           // Artist artist =entityManager.find(Artist.class,202);
            // System.out.println(artist);
            // entityManager.remove(artist);
            //artist.setArtistName("Muddy Waters");
            Artist artist = new Artist(202,"Muddy Water");
            entityManager.merge(artist);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }











}

