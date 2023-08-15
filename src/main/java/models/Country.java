package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends Element {

  @Getter @Setter private String isoCode;

  public Country(String name, String isoCode) {
    super(name);
    this.isoCode = isoCode;
  }

  @Override
  public String toString() {
    return String.format("""
		 -Name: %s
		 -ISO Code: %s
		""", this.name, this.isoCode);
  }
}
