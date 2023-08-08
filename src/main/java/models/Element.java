package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public abstract class Element {

  @Getter @Setter protected String name;
}
