package JPA.music;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @Column(name = "song_id")
    private int songId;

    @Column(name = "track_number")
    private int trackNumber;

    @Column(name = "song_title")
    private String songTitle;

    public Song() {
    }

    public Song(String songTitle) {
        this.songTitle = songTitle;
    }

    public Song(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }
}
