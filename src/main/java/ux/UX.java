package ux;

import interfaces.UXInterface;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import models.*;
import utils.Util;

public class UX implements UXInterface {

  public void showMenu() {
    UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 16));
    UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));

    boolean flag = true;
    Repository<Element> productRepository = new Repository<>();
    Repository<Element> countryRepository = new Repository<>();
    Repository<Element> userRepository = new Repository<>();
    while (flag) {
      String respuesta =
          JOptionPane.showInputDialog(
              null,
              """
                               Choose repository to work:

                               P) 'P'roducts repository
                               C) 'C'ountries repository
                               U) 'U'sers repository

                               Click cancel to exit the program.

                              """,
              "MENU",
              JOptionPane.INFORMATION_MESSAGE);

      if (respuesta != null) {
        switch (respuesta.toUpperCase()) {
          case "P" -> showMenuRepository(productRepository, "Products");
          case "C" -> showMenuRepository(countryRepository, "Countries");
          case "U" -> showMenuRepositoryUser(userRepository, "Users");
        }
      } else {
        String exit =
            JOptionPane.showInputDialog(
                null,
                """
                                Are you sure you want to log out?
                                Y/N
                                """);
        if (exit != null) {
          if (exit.equalsIgnoreCase("Y")) {
            flag = false;
          }
        }
      }
    }
    JOptionPane.showMessageDialog(
        null, "You logged out of the system", "EXIT", JOptionPane.INFORMATION_MESSAGE);
  }

  public void showMenuRepository(Repository<Element> repository, String typeRepository) {
    Element element = null;

    boolean flag = true;
    switch (typeRepository) {
      case "Products" -> element = new Product();
      case "Countries" -> element = new Country();
    }
    while (flag) {
      String op =
          JOptionPane.showInputDialog(
              null,
              """
              What would you like to do with the %s repository?

              1) Save to repository
              2) Count to repository
              3) Find to repository
              4) Sort the repository by a specific field
              5) Show Repository
              6) Delete element

              Click cancel to previous menu

              """
                  .formatted(typeRepository));
      if (op != null) {
        switch (op) {
          case "1" -> {
            assert element != null;
            repository.save(Util.enterDates(element));
          }
          case "2" -> repository.count(typeRepository);
          case "3" -> {
            Element elem = repository.find(typeRepository);
            if (elem != null) {
              JOptionPane.showMessageDialog(null, elem, "RESULT", JOptionPane.INFORMATION_MESSAGE);
            }
          }
          case "4" -> {
            assert element != null;
            showMenuSort(repository, element);
          }
          case "5" -> repository.showRepository(typeRepository);
          case "6" -> repository.deleteElement(typeRepository);
        }
      } else {
        flag = false;
      }
    }
  }

  public void showMenuRepositoryUser(Repository<Element> repository, String typeRepository) {
    Element element = new User();

    boolean flag = true;
    while (flag) {
      String op =
          JOptionPane.showInputDialog(
              null,
              """
              What would you like to do with the user repository?

              1) Save to repository
              2) Count to repository
              3) Find to repository
              4) Sort the repository by a specific field
              5) Show Repository
              6) Show list domains
              7) Delete element

              Click cancel to previous menu

              """);
      if (op != null) {
        switch (op) {
          case "1" -> repository.save(Util.enterDates(element));
          case "2" -> repository.count(typeRepository);
          case "3" -> {
            Element elem = repository.find(typeRepository);
            if (elem != null) {
              JOptionPane.showMessageDialog(null, elem, "RESULT", JOptionPane.INFORMATION_MESSAGE);
            }
          }
          case "4" -> showMenuSort(repository, element);
          case "5" -> repository.showRepository(typeRepository);
          case "6" -> {
            Set<String> lisDomains = Util.crateListDomains(repository);
            JList<String> list = new JList<>(lisDomains.toArray(new String[0]));
            JOptionPane.showMessageDialog(
                null, new JScrollPane(list), "DOMAINS", JOptionPane.INFORMATION_MESSAGE);
          }
          case "7" -> repository.deleteElement(typeRepository);
        }
      } else {
        flag = false;
      }
    }
  }

  public void showMenuSort(Repository<Element> repository, Element element) {
    if (repository.getRepository().isEmpty()) {
      JOptionPane.showMessageDialog(
          null, "The repository is empty", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    Class<?> clazz = element.getClass();
    List<String> listAttributes = new ArrayList<>();
    while (clazz != null) {
      for (Field field : clazz.getDeclaredFields()) {
        listAttributes.add(field.getName());
      }
      clazz = clazz.getSuperclass();
    }

    String op;
    int opNumber;
    // Crear las opciones para el cuadro de diálogo
    String[] options = new String[listAttributes.size()];
    for (int i = 0; i < listAttributes.size(); i++) {
      String charBegin = String.valueOf(listAttributes.get(i).charAt(0)).toUpperCase();
      options[i] = (i + 1) + ") " + charBegin + listAttributes.get(i).substring(1);
    }

    // Mostrar el cuadro de diálogo con las opciones
    op =
        (String)
            JOptionPane.showInputDialog(
                null, "Sort by:", "MENU", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    if (op != null) {
      opNumber = Integer.parseInt(op.substring(0, 1)) - 1;
      for (int i = 0; i < listAttributes.size(); i++) {
        if (opNumber == i) {
          repository.getAllSortedBy(listAttributes.get(i));
          JOptionPane.showMessageDialog(
              null, "Sorted successfully by criteria of " + listAttributes.get(i));
          return;
        }
      }
    }
  }
}
