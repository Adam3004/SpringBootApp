package pl.mavenproject.springbootaplikacja;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NonNull
public class Post {
    private int id;
    private String posterName;
    private String message;

}
