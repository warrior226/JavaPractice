import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Properties;

public class Main {
    private final static  String CONN_STRING="jdbc:mysql://localhost:3306/music";
    public static void main(String[] args) {

        Properties props = new Properties();
        try{
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        String albumName="Tapestry";
        String query="SELECT * FROM music.albumview WHERE album_name='%s'".formatted(albumName);

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try(
                var connection= dataSource.getConnection(
                        props.getProperty("user"),
                        System.getenv("MYSQL_PASS"));
                Statement statement = connection.createStatement()
        ){
            ResultSet resultSet= statement.executeQuery(query);
            var meta=resultSet.getMetaData();
            for(int i=1;i<= meta.getColumnCount();i++){
                System.out.printf("%d %s %s %n",i,meta.getColumnName(i),meta.getColumnTypeName(i));
            }
            System.out.printf("===================== %n");
            while (resultSet.next()){
                System.out.printf("%d %s %s %n",resultSet.getInt("track_number"),resultSet.getString("artist_name"),resultSet.getString("song_title"));
            }
        }catch(SQLException e){
            throw  new RuntimeException(e);
        }

        //With DriverManager
//        try(
//               Connection connection=DriverManager.getConnection(CONN_STRING,props.getProperty("user"),System.getenv("MYSQL_PASS"));
//               Statement statement= connection.createStatement()
//        ){
//            ResultSet resultSet= statement.executeQuery(query);
//
//            while (resultSet.next()){
//                System.out.printf("%d %s %n",resultSet.getInt(1),resultSet.getString("artist_name"));
//            }
//        }catch(SQLException e){
//            throw  new RuntimeException(e);
//        }

    }


}

