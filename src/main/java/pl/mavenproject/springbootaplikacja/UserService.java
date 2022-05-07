package pl.mavenproject.springbootaplikacja;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserService {
    @Autowired
    private QueryExecute queryExecute;
    @Autowired
    private HelpfulMethods helpfulMethods;

    ObjectMapper objectMapper = new ObjectMapper();
    List<User> users = new ArrayList<>();


    @GetMapping
    public String startWindow() {

        return "Hello on my website";
    }

    @PostMapping("/users")
    public ResponseEntity addUser(@RequestBody User user) {
        queryExecute.executeQuery("INSERT INTO public.users(\"Name\", \"Surname\", \"Age\") VALUES " +
                "('" + user.getName() + "','" + user.getSurname() + "'," + user.getAge() + ");");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users")
    @SneakyThrows
    public String printUsers() {
        users = helpfulMethods.downloadListOfUsers();

        return objectMapper.writeValueAsString(users);
    }

    @GetMapping("/users/{username}")
    public String printUser(@PathVariable("username") String username) {
        users = helpfulMethods.downloadListOfUsers();
        List<String> resultList = users.stream()
                .filter(user -> user.getName().equals(username))
                .map(user -> "Name: " + username + ", Surname: " + user.getSurname() + ", Age: " + user.getAge())
                .collect(Collectors.toList());
        if (resultList.size() > 0) {

            return resultList.get(0);
        }

        return "Post was not found";
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("username") String username) {
        users = helpfulMethods.downloadListOfUsers();
        for (User user : users) {
            if (user.getName().equals(username)) {
                queryExecute.executeQuery("DELETE FROM public.users WHERE \"Name\" LIKE '" + username + "' ;");

                return ResponseEntity.ok(HttpStatus.OK);
            }
        }

        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }


}
