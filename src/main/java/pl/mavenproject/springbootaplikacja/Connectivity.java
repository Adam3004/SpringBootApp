package pl.mavenproject.springbootaplikacja;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@NoArgsConstructor
public class Connectivity {

    @Value("") // field to fill
    private String URL;

    @Value("postgres")
    private String USER;

    @Value("") //add your password
    private String PASSWORD;

    private Connection connection;

    public Connection connect() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();

                return null;
            }
        }

        return connection;
    }
}
