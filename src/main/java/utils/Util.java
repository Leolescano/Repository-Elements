package utils;

import exceptions.InvalidDatesException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import models.*;

import javax.swing.*;

public class Util {
  public static final Scanner SC = new Scanner(System.in);

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
      valid = validateName(nameProduct);
    }
    product.setName(nameProduct.toLowerCase());

    product.setStock(validateStock());

    return product;
  }

  public static Element createCountry() {
    Country country = new Country();
    boolean valid = false;

    String nameCountry = null;
    while (!valid) {
      nameCountry = enterName("country");
      valid = validateName(nameCountry);
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
      firstName = JOptionPane.showInputDialog(null, "Enter the first name");
      valid = validateName(firstName);
    }
    user.setFirstName(firstName);

    valid = false;
    String lastName = null;
    while (!valid) {
      lastName = JOptionPane.showInputDialog(null, "Enter the last name");
      valid = validateName(lastName);
    }
    user.setLastName(lastName);

    user.setEmail(validateEmail());

    return user;
  }

  public static String enterName(String typeElement) {
    return JOptionPane.showInputDialog(null, "Enter the name of the " + typeElement + ":", "Product dates", JOptionPane.PLAIN_MESSAGE);
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
      JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
  }

  public static String validateIso() {
    String pattern = "^[A-Z]{2}$";
    do {
      try {
        String isoCode = JOptionPane.showInputDialog(null, "Enter the ISO country code ('US')", "Country dates");
        boolean valid = Pattern.matches(pattern, isoCode);
        if (valid) {
          return isoCode;
        } else {
          throw new InvalidDatesException("This is NOT a valid ISO 3166-1 alpha-2 country code.");
        }
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    } while (true);
  }

  public static int validateStock() {
    while (true) {
      try {
        System.out.print("Enter the quantity in Stock: ");
        String stock = JOptionPane.showInputDialog(null, "Enter the quantity in Stock");
        if (Integer.parseInt(stock) < 0) {
          throw new InvalidDatesException("Stock cannot be a negative number.");
        }
        return Integer.parseInt(stock);
      } catch (InputMismatchException e) {
          JOptionPane.showMessageDialog(null, "You must enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public static String validateUserName() {
    // más de 3 letras y deve comenzar con el caracter especial #
    String pattern = "^#[a-zA-Z0-9]{3,}$";
    do {
      try {
        String userName = JOptionPane.showInputDialog(null, "Enter the username, it is mandatory that it starts with '#'"
                + "and have a minimum of 3 letters or numbers: ");
        boolean valid = Pattern.matches(pattern, userName);
        if (valid) {
          return userName;
        } else {
          throw new InvalidDatesException("The entered is not valid username.");
        }
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    } while (true);
  }

  public static String validateEmail() {
    // valida que tiene que ser un string valido para un direccion de e-mail
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    do {
      try {
        String email = JOptionPane.showInputDialog(null, "Enter email address");
        boolean valid = Pattern.matches(emailRegex, email);
        if (valid) {
          return email.toLowerCase();
        } else {
          throw new InvalidDatesException("The entered is not valid email address.");
        }
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    } while (true);
  }

  public static List<String> listDominios(Repository<Element> repository) {
    List<String> lista = new ArrayList<>();
    for (int i = 0; i < repository.getRepository().size(); i++) {
      User user = (User) repository.getRepository().get(i);
      String email = user.getEmail();
      for (int j = 0; j < email.length(); j++) {
        if (email.charAt(j) == '@') {
          email = email.substring(j);
          boolean repeat = false;
          for (String dominio : lista) {
            if (email.equals(dominio)) {
              repeat = true;
              break;
            }
          }
          if (!repeat) {
            lista.add(email);
          }
        }
      }
    }
    return lista;
  }
}
