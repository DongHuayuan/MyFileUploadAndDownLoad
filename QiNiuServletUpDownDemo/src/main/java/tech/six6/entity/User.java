package tech.six6.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private int id;
    private String username;
    private String password;

}
