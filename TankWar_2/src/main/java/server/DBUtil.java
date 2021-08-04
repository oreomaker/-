package server;

import java.sql.*;
import org.sqlite.JDBC;

/**
 * 服务器的数据库操作.
 *
 * <p>该类能够处理服务器端注册、登录、查询战绩的数据库更改,对更改数据库内容的操作采取了@code synchronized 标识</>
 * @author Zhang Hao
 * */
public class DBUtil {
    public static Connection connection;
    public static Statement statement;

    public DBUtil() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:player.db");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 用于初始化数据库.
     *
     * 默认表为player，内有name，password，winNum（获胜次数），loseNum（失败次数）字段.
     * 含有两个初始用户leo，密码123456和yui，234567，用于测试.
     **/
    public void initDB(){
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("drop table if exists player");
            statement.executeUpdate("create table player (name string,password string,winNum int,loseNum int)");
            statement.executeUpdate("insert into player values('leo','123456',0,0)");
            statement.executeUpdate("insert into player values('yui','234567',0,0)");
            //搜索全部内容
            ResultSet rs = statement.executeQuery("select * from player");
            while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("password= "+ rs.getString("password"));
                System.out.println("win = " + rs.getInt("winNum"));
                System.out.println("lose= "+ rs.getInt("loseNum"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return String
     * @param name
     * @throws SQLException
     *
     * 用于获得用户胜利次数，返回格式为 获胜次数|失败次数.
     * */
    public String getWinData(String name) throws SQLException {
        String sql = "select winNum,loseNum from player where name=?";
        PreparedStatement ptmt = connection.prepareStatement(sql);
        ptmt.setString(1,name);
        ResultSet rs1 = ptmt.executeQuery();
        int winNum = rs1.getInt("winNum");
        int loseNum = rs1.getInt("loseNum");
        return winNum+"|"+loseNum;
    }

    /**
     * @param winner
     * @param loser
     * @throws SQLException
     *
     * 游戏结束更新用户胜负数据
     *
     * 方法传入胜者和败者用户名，搜索到历史记录后更新胜败数据.
     * */
    public synchronized void updateWinnerData(String winner, String loser) throws SQLException {
        String sql = "select winNum from player where name=?";
        PreparedStatement ptmt = connection.prepareStatement(sql);
        ptmt.setString(1, winner);
        ResultSet rs1 = ptmt.executeQuery();
        int winner_Win = rs1.getInt("winNum");

        sql = "update player set winNum=? where name = ?";
        ptmt = connection.prepareStatement(sql);
        ptmt.setInt(1,winner_Win+1);
        ptmt.setString(2,winner);
        int rs = ptmt.executeUpdate();

        sql = "select loseNum from player where name=?";
        ptmt = connection.prepareStatement(sql);
        ptmt.setString(1, loser);
        ResultSet rs2 = ptmt.executeQuery();
        int loser_lose = rs2.getInt("loseNum");

        sql = "update player set loseNum=? where name = ?";
        ptmt = connection.prepareStatement(sql);
        ptmt.setInt(1,loser_lose+1);
        ptmt.setString(2,loser);
        rs = ptmt.executeUpdate();
    }

    /**
     * @return {@code ResultSet}
     * @param name
     * @param password
     * @throws SQLException
     *
     * 用于检查用户用户名和密码.
     *
     * 返回ResultSet，当且仅当用户名和密码均正确时返回值才可查询到信息.
     * */
    public ResultSet checkLogin(String name,String password) throws SQLException {
        String sql = "select name,password from player where name=? and password=?";
        PreparedStatement ptmt = connection.prepareStatement(sql);
        ptmt.setString(1, name);
        ptmt.setString(2, password);
        ResultSet rs = ptmt.executeQuery();
        return rs;
    }

    /**
     * @param password
     * @param name
     * @return {@code String}
     * @throws SQLException
     *
     * 用于用户注册.
     *
     * 首先以用户名为条件进行查询，若有查询结果（已有相同用户名注册），则返回signup|repeat信息，
     * 注册成功，返回signup|success
     * */
    public synchronized String signUp(String name,String password) throws SQLException {
        String sql = "select name from player where name=?";
        PreparedStatement ptmt = connection.prepareStatement(sql);
        ptmt.setString(1, name);
        ResultSet rs = ptmt.executeQuery();
        //用户名重复，返回repeat信息
        if(rs.next()){
            return "signup|repeat";
        }
        //向数据库中添加用户信息
        sql = "insert into player (name,password,winNum,loseNum) values(?,?,?,?)";
        ptmt = connection.prepareStatement(sql);
        ptmt.setString(1, name);
        ptmt.setString(2, password);
        ptmt.setInt(3, 0);
        ptmt.setInt(4, 0);
        ptmt.execute();
        return "signup|success";
    }

    public static void main(String[] args) {
        new DBUtil().initDB();
    }
}
