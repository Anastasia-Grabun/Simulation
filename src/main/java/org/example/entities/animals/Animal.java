package org.example.entities.animals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.example.entities.Entity;
import org.example.entities.animals.enums.Gender;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Animal extends Entity {
    private Gender gender;
    private int attackDamage;
}
