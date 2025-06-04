package JavaDataBase;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class MusicDML {

    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/music",System.getenv("MYSQL_USER"),System.getenv("MYSQL_PASS"));
                Statement statement =connection.createStatement();
                ){
//            String artist="Neil Young";
//            String query="SELECT * FROM artists WHERE artist_name='%s'".formatted(artist);
//            boolean result=statement.execute(query); //Returns true every a select query is executed
//            System.out.println("result= "+result);
//            var resultset=statement.getResultSet();
//            boolean found=(resultset!=null&&resultset.next());
//            System.out.println("found= "+found);
            String tableName="music.artists";
            String columnName="artist_name";
            String columnValue="OUEDRAOGO Safi";
            if(!executeSelect(statement,tableName,columnName,columnValue)){
                System.out.println("Maybe we should add this record");
              //  insertRecord(statement,tableName,new String[]{columnName},new String[]{columnValue});
                insertArtistAlbum(statement,columnValue,columnName);
            }else{
               // deleteRecord(statement,tableName,columnName,columnValue);
                updateRecord(statement,tableName,columnName,columnValue,columnName,columnValue.toUpperCase());
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private static  boolean printRecords(ResultSet resultSet)throws SQLException{
        boolean foundData=false;
        var meta =resultSet.getMetaData();

        System.out.println("===================");

        for(int i=1;i<=meta.getColumnCount();i++){
            System.out.printf("%-15s",meta.getColumnName(i));
        }
        System.out.println();
        while(resultSet.next()){
            for(int i=1;i<=meta.getColumnCount();i++){
                System.out.printf("%-15s",resultSet.getString(i));
            }
            System.out.println();
            foundData=true;
        }

        return foundData;

    }

    private static boolean executeSelect(Statement statement , String table ,String columnName , String columnValue)throws SQLException{

        String query="SELECT * FROM %s WHERE %s='%s'".formatted(table,columnName,columnValue);
        var rs=statement.executeQuery(query); // executeQuery returns a ResultSet
        if(rs!=null){
            return  printRecords(rs);
        }

        return false;
    }

    private static  boolean insertRecord(Statement statement, String table,String[] columnNames,String[] columnValues)throws SQLException{

        String colNames=String.join(",",columnNames);
        String colValues=String.join("','",columnValues);

        String query="INSERT INTO %s (%s) VALUES ('%s')".formatted(table,colNames,colValues);
        System.out.println(query);
        boolean insertResult=statement.execute(query);
        System.out.println("insertResult is "+insertResult);
        int recordAdded=statement.getUpdateCount();
        System.out.println("The number of record added is "+recordAdded);
        return insertResult;
    }

    private static boolean deleteRecord(Statement statement,String table,String columnName,String columnValue)throws SQLException{

        String query="DELETE FROM %s WHERE %s ='%s'".formatted(table,columnName,columnValue);
        statement.execute(query);
        int deletedCount=statement.getUpdateCount();
        if(deletedCount>0){
            executeSelect(statement,table,columnName,columnValue);
        }

        return deletedCount>0;
    }

    private static boolean updateRecord(Statement statement,String table,String matchedColumn,String matchedValue,String updatedColumn,String updatedValue)throws SQLException{

        String query="UPDATE %s SET %s ='%s' WHERE %s='%s'".formatted(table,updatedColumn,updatedValue,matchedColumn,matchedValue);
        statement.execute(query);
        int updatedCount=statement.getUpdateCount();
        if(updatedCount>0){
            executeSelect(statement,table,updatedColumn,updatedValue);
        }

        return updatedCount>0;
    }

    private static  void insertArtistAlbum(Statement statement,
                                           String artisteName,
                                           String albumName)throws SQLException{
        String artistInsert="INSERT INTO music.artists (artist_name) VALUES (%s)".formatted(statement.enquoteLiteral(artisteName));
        System.out.println(artistInsert);
        statement.execute(artistInsert,Statement.RETURN_GENERATED_KEYS);//Possibilité de recupérer l'id génerer
        ResultSet rs=statement.getGeneratedKeys();
        int artistId=(rs!=null&& rs.next())?rs.getInt(1):-1;
        String albumInsert="INSERT INTO music.albums (album_name,artist_id) VALUES(%s,%d)".formatted(statement.enquoteLiteral(albumName),artistId);
        System.out.println(albumInsert);
        statement.execute(albumInsert,Statement.RETURN_GENERATED_KEYS);
        rs=statement.getGeneratedKeys();
        int albumId=(rs!=null && rs.next())? rs.getInt(1):-1;
        String[] songs =new String[]{
                "Your're No Good",
                "Talkin' New York",
                "In My Time of Dyin'",
                "Man of Constant Sorrow",
                "Fixin' to Die",
                "Pretty Peggy-0",
                "Highway 51 Blues"
        };

        String songInsert="INSERT INTO music.songs (track_number,song_title,album_id)VALUES(%d,%s,%d)";

        for(int i=0;i<songs.length;i++){
            String songQuery=songInsert.formatted(i+1,statement.enquoteLiteral(songs[i]),albumId);
            statement.execute(songQuery);
            System.out.println(songQuery);
        }
    }
}
