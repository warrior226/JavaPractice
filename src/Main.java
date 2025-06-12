import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class Main {
    private static final int MYSQL_DB_NOT_FOUND = 1049;
    private static  String USE_SCHEMA="Use storefront";//This is a DDL statement. It sets the default database for the session.The SQL syntax USE storefront is a database command that switches your current database context to a database named "storefront"
    public static void main(String[] args) {

        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(System.getenv("MYSQLUSER"));
        dataSource.setPassword(System.getenv("MYSQLPASS"));
        try(Connection conn=dataSource.getConnection()){
            DatabaseMetaData metaData=conn.getMetaData();
            System.out.println(metaData.getSQLStateType());
            if(!checkSchema(conn)){
                System.out.println("storefont schema does not exist");
                setUpSchema(conn);
            }
//            int newOrder=insertOrder(conn,new String[]{"shoes","shirt","socks"});
//            System.out.println("New Order = " + newOrder);
            deleteOrder(conn,12);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    private static boolean checkSchema(Connection conn)throws SQLException{
        try(Statement statement= conn.createStatement()){
            statement.execute(USE_SCHEMA);
        } catch (SQLException e) {
            System.err.println("SQLState: "+e.getSQLState());
            System.err.println("Error Code: "+e.getErrorCode());
            System.err.println("Message : "+e.getMessage());
            if(conn.getMetaData().getDatabaseProductName().equals("MySQL")&&e.getErrorCode()==MYSQL_DB_NOT_FOUND){
                return false;
            }else throw e;
        }
        return true;
    }

    private static void setUpSchema(Connection conn)throws  SQLException{
        String createSchema="CREATE SCHEMA storefront"; //This is to create database or schema named storefront
        String createOrder= """
                CREATE TABLE storefront.order(
                order_id int NOT NULL AUTO_INCREMENT,
                order_date DATETIME NOT NULL,
                PRIMARY KEY (order_id)
                )
                """;
        String createOrderDetails= """
                CREATE TABLE storefront.order_details(
                order_detail_id int NOT NULL AUTO_INCREMENT,
                item_description text ,
                order_id int DEFAULT NULL,
                PRIMARY KEY (order_detail_id),
                KEY FK_ORDERID (order_id),
                CONSTRAINT FK_ORDERID FOREIGN KEY (order_id)
                REFERENCES storefront.order (order_id) ON DELETE CASCADE
                )
                """;

        try(Statement statement=conn.createStatement()){
            System.out.println("Creating storefront DataBase");
            statement.execute(createSchema);
            if(checkSchema(conn)){
                statement.execute(createOrder);
                System.out.println("Successfully Created Order");
                statement.execute(createOrderDetails);
                System.out.println("Successfully Created Order Details");
            }
        }catch (SQLException e){
            throw new SQLException(e);
        }

    }

    private static int insertOrder(Connection conn,String[]items)throws SQLException{
        int orderId=-1;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String registeredDate=now.format(formatter);
        try(Statement statement=conn.createStatement()){
            conn.setAutoCommit(false);
            //Insert in order table
            String sql="INSERT INTO storefront.order (order_date) VALUES (%s)".formatted(statement.enquoteLiteral(registeredDate));
            System.out.println(sql);
            int inserts=statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);//Retourne le nombre de ligne qui ont été impactées


            if(inserts==1){
                var rs=statement.getGeneratedKeys();
                if(rs.next()){
                    orderId=rs.getInt(1);
                }
            }

            int count=0;

            for(var item:items){
                String sql_register_detail="INSERT INTO storefront.order_details (order_id,item_description) VALUES (%d,%s)".formatted(orderId,statement.enquoteLiteral(item));
                inserts=statement.executeUpdate(sql_register_detail);
                count+=inserts;
            }
            //Commit if it all successful
            if(count!=items.length){
                orderId=-1;
                System.out.println("Number of records inserted doesn't equal items received");
                conn.rollback();
            }else{
                conn.commit();
            }
            conn.commit();
            conn.setAutoCommit(true);
            System.out.println("Transaction completed successfully");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Transaction rolled back : "+e.getMessage());
            System.err.println("SQLState: "+e.getSQLState());
            System.err.println("Error Code: "+e.getErrorCode());
            System.err.println("Message : "+e.getMessage());
        }

        return orderId;
    }

    private static int deleteOrder(Connection conn,int orderId){
        String sql="DELETE FROM storefront.order where order_id=%d";
        String sql_query=sql.formatted(orderId);
        int deletedRows=-1;
        try(Statement statement=conn.createStatement()){
            deletedRows=statement.executeUpdate(sql_query);
            System.out.println(deletedRows+" has been deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return deletedRows;
    }

}

