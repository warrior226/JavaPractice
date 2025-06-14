package JavaDataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class MusicCallableStatement {

    private static final int ARTIST_COLUMN=0;
    private static final int ALBUM_COLUMN=1;
    private  static  final int SONG_COLUMN=3;
    public static void main(String[] args) {
        Map<String,Map<String,String>> albums=null;
        try(var lines= Files.lines(Path.of("NewAlbums.csv"))) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
