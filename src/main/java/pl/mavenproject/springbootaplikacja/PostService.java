package pl.mavenproject.springbootaplikacja;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostService {
    @Autowired
    private QueryExecute queryExecute;
    @Autowired
    private HelpfulMethods helpfulMethods;

    ObjectMapper objectMapper = new ObjectMapper();
    List<Post> posts = new ArrayList<>();

    @SneakyThrows
    @GetMapping("/posts")
    public String printPosts() {
        posts = helpfulMethods.downloadListOfPosts();

        return objectMapper.writeValueAsString(posts);
    }

    @GetMapping("/posts/{id}")
    public String printPost(@PathVariable("id") int id) {
        posts = helpfulMethods.downloadListOfPosts();
        List<String> resultList = posts.stream()
                .filter(post -> post.getId() == id)
                .map(post -> post.getId() + ". Posted by: " + post.getPosterName() + " Message:" + post.getMessage())
                .collect(Collectors.toList());
        if (resultList.size() > 0) {

            return resultList.get(0);
        }

        return "Post was not found";

    }

    @PostMapping("/posts")
    public ResponseEntity addPost(@RequestBody Post post) {
        posts = helpfulMethods.downloadListOfPosts();
        int id = 1;
        if (posts.size() > 0) {
            id = posts.get(posts.size() - 1).getId() + 1;
        }
        queryExecute.executeQuery("INSERT INTO public.posts( \"id\", \"posterName\", message) VALUES " +
                "(" + id + ",'" + post.getPosterName() + "' ,'" + post.getMessage() + "');");

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity deletePost(@PathVariable("id") int id) {
        posts = helpfulMethods.downloadListOfPosts();
        for (Post post : posts) {
            if (post.getId() == id) {
                queryExecute.executeQuery("DELETE FROM public.posts WHERE \"id\" = " + id + " ;");

                return ResponseEntity.ok(HttpStatus.OK);
            }
        }

        return ResponseEntity.ok(HttpStatus.NOT_FOUND);
    }

}
