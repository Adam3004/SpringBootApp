package pl.mavenproject.springbootaplikacja;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelpfulMethods {

    @Autowired
    private QueryExecute queryExecute;

    public HelpfulMethods(QueryExecute queryExecute) {
        this.queryExecute = queryExecute;
    }

    @SneakyThrows
    public List downloadListOfUsers() {
        List<User> users = new ArrayList<>();
        ResultSet resultSet = queryExecute.executeSelect("SELECT * FROM public.users");
        while (resultSet.next()) {
            String name = resultSet.getString("Name");
            String surname = resultSet.getString("Surname");
            int age = resultSet.getInt("Age");
            User user = new User(name, surname, age);
            users.add(user);
        }

        return users;
    }

    @SneakyThrows
    public List downloadListOfPosts() {
        List<Post> posts = new ArrayList<>();
        ResultSet resultSet = queryExecute.executeSelect("SELECT * FROM public.posts");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String posterName = resultSet.getString("posterName");
            String message = resultSet.getString("message");
            Post post = new Post(id, posterName, message);
            posts.add(post);
        }

        return posts;
    }


}
