package pl.mavenproject.springbootaplikacja;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NonNull
public class User {
    private @NonNull String name;
    private @NonNull String surname;
    private @NonNull int age;
}
