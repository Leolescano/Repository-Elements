package utils;

import exceptions.InvalidDatesException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;
import javax.swing.*;
import models.*;

public class Util {
  public static Element enterDates(Element element) {
    if (element instanceof Product) {
      return createProduct();
    }
    if (element instanceof Country) {
      return createCountry();
    }
    if (element instanceof User) {
      return createUser();
    }
    return null;
  }

  public static Element createProduct() {
    Product product = new Product();
    boolean valid = false;

    String nameProduct = null;
    while (!valid) {
      nameProduct = enterName("product");
      if (nameProduct != null) {
        valid = validateName(nameProduct);
      } else {
        return null;
      }
    }
    product.setName(nameProduct.toLowerCase()); // lo guardo todo en minuscula

    product.setStock(validateStock());

    return product;
  }

  public static Element createCountry() {
    Country country = new Country();
    boolean valid = false;

    String nameCountry = null;
    while (!valid) {
      nameCountry = enterName("country");
      if (nameCountry != null) {
        valid = validateName(nameCountry);
      } else {
        return null;
      }
    }

    country.setName(nameCountry);

    country.setIsoCode(validateIso());

    return country;
  }

  public static Element createUser() {
    User user = new User();
    boolean valid = false;
    // user.setName(validateUserName());

    String firstName = null;
    while (!valid) {
      firstName =
          JOptionPane.showInputDialog(null, "Enter the first name", "Minimum three letters");

      if (firstName != null) {
        valid = validateName(firstName);
      } else {
        return null;
      }
    }
    user.setFirstName(firstName);

    valid = false;
    String lastName = null;
    while (!valid) {
      lastName = JOptionPane.showInputDialog(null, "Enter the last name", "Minimum three letters");
      if (lastName != null) {
        valid = validateName(lastName);
      } else {
        return null;
      }
    }
    user.setLastName(lastName);

    user.setEmail(validateEmail());

    return user;
  }

  public static String enterName(String typeElement) {

    String name =
        JOptionPane.showInputDialog(
            null, "Enter the name of the " + typeElement, "Minimum three letters");
    if (name != null) {
      return name;
    }
    return null;
  }

  public static boolean validateName(String name) {

    // Más de dos letras con espacios y sin caracteres especiales
    String pattern = "^(?=.*[a-zA-Z].*[a-zA-Z].*[a-zA-Z])[a-zA-Z ]+$";
    try {
      boolean valid = Pattern.matches(pattern, name);
      if (valid) {
        return true;
      } else {
        throw new InvalidDatesException("You have to enter a valid name.");
      }
    } catch (InvalidDatesException e) {
      JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  public static int validateStock() {
    do {
      String stock =
          JOptionPane.showInputDialog(
              null, "Enter the quantity in Stock", "cannot be a negative number");

      if (stock != null) {
        try {
          if (Integer.parseInt(stock) >= 0) {
            return Integer.parseInt(stock);
          } else {
            throw new InvalidDatesException("Stock cannot be a negative number.");
          }
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(
              null, "You must enter an integer", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidDatesException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
      }
    } while (true);
  }

  public static String validateIso() {
    String pattern = "^[A-Z]{2}$";
    do {
      String isoCode =
          JOptionPane.showInputDialog(
              null, "Enter the ISO country code ('US')", "Two capital letters");

      if (isoCode != null) {
        try {
          boolean valid = Pattern.matches(pattern, isoCode);
          if (valid) {
            return isoCode;
          } else {
            throw new InvalidDatesException("This is NOT a valid ISO 3166-1 alpha-2 country code.");
          }
        } catch (InvalidDatesException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
      }
    } while (true);
  }

  public static String validateUserName() {
    // más de 2 letras y deve comenzar con el caracter especial #
    String pattern = "^#[a-zA-Z0-9]{3,}$";
    do {
      try {
        String userName =
            JOptionPane.showInputDialog(
                null,
                "Enter the username, it is mandatory that it starts with '#'"
                    + "and have a minimum of 3 letters or numbers: ",
                "more than 2 letters and must begin with the special character #");
        boolean valid = Pattern.matches(pattern, userName);
        if (valid) {
          return userName;
        } else {
          throw new InvalidDatesException("The entered is not valid username.");
        }
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
      }
    } while (true);
  }

  public static String validateEmail() {

    // valida que tiene que ser un string valido para un direccion de e-mail
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    do {

      String email = JOptionPane.showInputDialog(null, "Enter email address", "#####@###.###");
      if (email != null) {
        try {
          boolean valid = Pattern.matches(emailRegex, email);
          if (valid) {
            return email.toLowerCase();
          } else {
            throw new InvalidDatesException("The entered is not valid email address.");
          }
        } catch (InvalidDatesException e) {
          JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
      }
    } while (true);
  }

  public static Set<String> crateListDomains(Repository<Element> repository) {

    Set<String> listDomains = new LinkedHashSet<>();

    for (int i = 0; i < repository.getRepository().size(); i++) {
      User user = (User) repository.getRepository().get(i);
      String email = user.getEmail();
      for (int j = 0; j < email.length(); j++) {
        if (email.charAt(j) == '@') {
          email = email.substring(j);
          listDomains.add(email);
        }
      }
    }
    return listDomains;
  }
}
