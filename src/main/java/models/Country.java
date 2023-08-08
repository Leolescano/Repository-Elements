package models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Country extends Element {

  @Getter @Setter private String isoCode;
  @Getter @Setter private double kmCuadrado = Math.random() * 10000;
  @Getter @Setter private double poblacion = Math.random() * 10000;

  public Country(String name, String isoCode) {
    super(name);
    this.isoCode = isoCode;
  }

  @Override
  public String toString() {
    return String.format("""
		Country Name: %s
		ISO Code: %s
		""",
        this.name, this.isoCode);
  }
}
