package server;


import java.sql.*;

public class DbAuthService implements AuthService{

    private static Connection connection;
    private static PreparedStatement psGetNickname;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psChangeNick;

    public static boolean connect () {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:clientsdata.db");
            prepareAllStatements();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void prepareAllStatements() throws SQLException {
        psGetNickname = connection.prepareStatement("SELECT nickname FROM clients WHERE login = ? AND password ?;");
        psRegistration = connection.prepareStatement("INSERT INTO clients(login, password, nickname) VALUES (?, ?,? );");
        psChangeNick = connection.prepareStatement("UPDATE clients SET nickname = ? WHERE nickname ?;");
    }

    public static void disconnect(){
        try {
            psGetNickname.close();
            psRegistration.close();
            psChangeNick.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {

        try {
            psGetNickname.setString(1, login);
            psGetNickname.setString(2,password);
            ResultSet rs = psGetNickname.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {

        try {
            psRegistration.setString(1, login);
            psRegistration.setString(2, password);
            psRegistration.setString(3, nickname);
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean changeNick(String oldNickname, String newNickname){

        try {
            psChangeNick.setString(1,newNickname);
            psChangeNick.setString(2,oldNickname);
            psChangeNick.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }
}
