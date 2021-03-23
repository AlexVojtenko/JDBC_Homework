package entity;

import lombok.Data;

@Data
public class Account {
    private long id;
    private long clientId;
    private String number;
    private double value;
}
