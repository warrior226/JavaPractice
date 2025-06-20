package JPA.music;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @Column(name ="artist_id")
    private int artistId; //The primary key

    @Column(name="artist_name")
    private  String artistName;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "artist_id")
    private List<Album> albums=new ArrayList<>();

    public List<Album> getAlbums() {
        return albums;
    }

    public Artist() {
    }

    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void addAlbum(String albumName){
        albums.add(new Album(albumName));
    }

    public void removeDuplicates(){
        var set=new TreeSet<>(albums);
        albums.clear();
        albums.addAll(set);
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", albums="+albums+

                '}';
    }
}
