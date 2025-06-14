package JDBCChallenge;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JdbcChallenge {
    public static void main(String[] args) {
        //Let's create establish first a connection with the database
        var dataSource=new MysqlDataSource();
        dataSource.setServerName(System.getenv("MYSQL_SERVERNAME")); //The server is stord on the host machine
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));
        dataSource.setPort(3306);

        try {
            dataSource.setContinueBatchOnError(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(Connection connection=dataSource.getConnection()){
            System.out.println("Connection Successfully established !!!");
            //addNewColumn(connection,"quantity");
            addOrder(connection);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addNewColumn(Connection conn,String columnName) throws SQLException {
        String sql="ALTER TABLE storefront.order_details ADD COLUMN "+columnName+" Int ";
        try(Statement st =conn.createStatement()){
            st.execute(sql);
            System.out.println("Column "+columnName+" added successfully");
        }catch (SQLException e){
            System.out.println("Column "+columnName+" adding failed !!");
            throw new SQLException(e);
        }
    }

    public static void addOrder(Connection connection) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        int insertedOrder=-1;
        int insertedCount=0;
        String addOrder="INSERT INTO storefront.order (order_date) VALUES (?)";
        String addItem="INSERT INTO storefront.order_details (item_description,order_id,quantity) VALUES (?,?,?)";
        List<String> records=null;
        try {
            records= Files.readAllLines(Path.of("src/JDBCChallenge/Orders.csv"));
            System.out.println("Orders are "+records);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(PreparedStatement psOrder=connection.prepareStatement(addOrder,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psNewItem=connection.prepareStatement(addItem)) {
            connection.setAutoCommit(false);
            for(String order :records){
                String type=order.split(",")[0];
                if(Objects.equals(type, "order")){
                    psOrder.setString(1, String.valueOf(LocalDateTime.parse(order.split(",")[1],formatter)));

                    insertedCount=psOrder.executeUpdate();
                    if(insertedCount>0){
                        ResultSet resultSet= psOrder.getGeneratedKeys();
                        if(resultSet.next()){
                            insertedOrder=resultSet.getInt(1);
                        }
                    }
                }else{
                    addItem(psNewItem,insertedOrder,order.split(",")[2], Integer.parseInt(order.split(",")[1]));
                }
            }
            psNewItem.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public static  void addItem(PreparedStatement psAddItem,int orderId,String description,int quantity) throws SQLException {
        psAddItem.setString(1,description);
        psAddItem.setInt(2,orderId);
        psAddItem.setInt(3,quantity);
        psAddItem.addBatch();
    }
}
