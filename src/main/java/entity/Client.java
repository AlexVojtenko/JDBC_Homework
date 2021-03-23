package entity;

import lombok.Data;

@Data
public class Client {
    private long id;
    private String name;
    private String email;
    private long phone;
    private String about;
    private int age;
}
