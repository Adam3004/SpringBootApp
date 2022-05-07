package pl.mavenproject.springbootaplikacja;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@AllArgsConstructor
public class QueryExecute {

    @Autowired
    private Connectivity connectivity;


    public ResultSet executeSelect(String selectQuery) {
        try {
            Connection connection = connectivity.connect();
            Statement statement = connection.createStatement();

            return statement.executeQuery(selectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void executeQuery(String query) {
        Connection connection = connectivity.connect();
        try {
            Statement statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
