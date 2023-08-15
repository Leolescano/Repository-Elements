package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Element {

  @Getter @Setter private String firstName;

  @Getter @Setter private String lastName;

  @Getter @Setter private String email;

  public User(String username, String firstName, String lastName, String email) {
    super(username);
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  @Override
  public String toString() {
    return String.format(
        """
			 -User: %s
			 -First name: %s
			 -Last name: %s
			 -E-mail: %s
			""",
        this.name, this.firstName, this.lastName, this.email);
  }
}
