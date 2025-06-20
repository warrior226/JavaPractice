 package JPA.music;


import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

 @Entity
@Table(name = "albums")
public class Album implements Comparable<Album> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//To ensure that foreign key is well saved
    @Column(name ="album_id")
    private int albumId; //The primary key

    @Column(name="album_name")
    private  String albumName;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "album_id")
    private List<Song> songs;

    public Album() {
    }

    public Album(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Album(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ",songs="+ Arrays.toString( songs.toArray()) +
                '}';
    }



     @Override
     public int compareTo(@NotNull Album o) {
         return this.albumName.compareTo(o.getAlbumName());
     }
 }
