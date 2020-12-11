import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LogAndReg
{
    Scanner scan = new Scanner(System.in);

    String login;
    String password;

     ArrayList<LogAndPass> logandpassword = new ArrayList<LogAndPass>();

    public void reg()
    {
        LogAndPass logandpass = new LogAndPass();
        System.out.println("Введите логин:");
        logandpass.login = scan.nextLine();

        System.out.println("Введите пароль:");
        logandpass.password = scan.nextLine();

        logandpassword.add(logandpass);

        sqlWriter();
    }

    public void log()
    {
        for(LogAndPass x : logandpassword)
        {

            System.out.println("Введите логин:");
            login = scan.nextLine();

            System.out.println("Введите пароль:");
            password = scan.nextLine();
            if((login == x.login) && (password == x.password))
            {
                System.out.println("Добро пожаловать!" + login);
            }
        }
    }

    public void sqlWriter()
    {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1"))
        {
            PreparedStatement statement = connection.prepareStatement("insert into logandpass values (?,?)");
            System.out.printf("%-30.30s  %-30.30s%n", "login", "password");

            for(LogAndPass x : logandpassword)
            {
                statement.setString(1, x.login);
                statement.setString(2, x.password);
                statement.execute();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void sqlReader()
    {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "1"))
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM logandpass");
            while (resultSet.next()) {

                LogAndPass logandpass = new LogAndPass();

                logandpass.login= resultSet.getString("login");

                logandpass.password=resultSet.getString("pass");


                logandpassword.add(logandpass);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
