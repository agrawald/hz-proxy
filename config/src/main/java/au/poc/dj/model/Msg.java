package au.poc.dj.model;

import lombok.*;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Msg implements Serializable {
    private String step;
    private long id;
}