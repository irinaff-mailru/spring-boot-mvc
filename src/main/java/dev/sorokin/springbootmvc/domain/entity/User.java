package dev.sorokin.springbootmvc.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Pet> pets = new CopyOnWriteArrayList<>();
}
