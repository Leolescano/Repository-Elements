package ux;

import interfaces.Count;
import interfaces.Find;
import interfaces.Save;
import interfaces.UXInterface;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import models.*;
import utils.Util;

import javax.swing.*;

public class UX implements UXInterface {

  public void showMenu() {
    String op;
    boolean flag = true;
    Repository<Element> productRepository = new Repository<>();
    Repository<Element> countryRepository = new Repository<>();
    Repository<Element> userRepository = new Repository<>();
    while (flag) {
      String respuesta = JOptionPane.showInputDialog(null, """
               Choose repository to work:
                      P) 'P'roducts repository
                      C) 'C'ountries repository
                      U) 'U'sers repository
                      F) 'F'inish jobs);
              """);
      switch (respuesta.toUpperCase()) {
        case "P" -> showMenuRepository(productRepository, "Products");
        case "C" -> showMenuRepository(countryRepository, "Countries");
        case "U" -> showMenuRepositoryUser(userRepository, "Users");
        case "F" -> {
          String exit = JOptionPane.showInputDialog(null, "Are you sure you want to log out? Y/N");
          if (exit.equalsIgnoreCase("Y")) {
            flag = false;
          }
        }
      }
    }
    JOptionPane.showMessageDialog(null, "You logged out of the system");
  }

  public void showMenuRepository(Repository<Element> repository, String typeRepository) {
    String op;
    boolean flag = true;
    Element element = null;
    switch (typeRepository) {
      case "Products" -> element = new Product();
      case "Countries" -> element = new Country();
    }
    while (flag) {
      String respuesta = JOptionPane.showInputDialog(null, """
              What would you like to do with the %s repository?
                     1) Save to repository
                     2) Count to repository
                     3) Find to repository
                     4) Sort the repository by a specific field
                     5) Show Repository
                     6) Delete element
                     7) Back to previous menu
              """, typeRepository);
      switch (respuesta) {
        case "1" -> {
          assert element != null;
          repository.save(Util.enterDates(element));
        }
        case "2" -> {
          repository.count(typeRepository);
        }
        case "3" -> {
          Element elem = repository.find(typeRepository);
          if (elem != null) {
            System.out.println(elem);
          }
        }
        case "4" -> {
          assert element != null;
          showMenuSort(repository, element);
        }
        case "5" -> {
          repository.showRepository();
        }
        case "6" -> {
          repository.deleteElement();
        }
        case "7" -> flag = false;
      }
    }
  }

  public void showMenuRepositoryUser(Repository<Element> repository, String typeRepository) {
    Element element = new User();
    String op;
    boolean flag = true;
    while (flag) {
      String respuesta = JOptionPane.showInputDialog(null, """
              What would you like to do with the user repository?
                        1) Save to repository
                        2) Count to repository
                        3) Find to repository
                        4) Sort the repository by a specific field
                        5) Show Repository
                        6) Show list dominions
                        7) Delete element
                        8) Back to previous menu;
              """);
      switch (respuesta) {
        case "1" -> {
          repository.save(Util.enterDates(element));
        }
        case "2" -> {
          repository.count(typeRepository);
        }
        case "3" -> {
          Element elem = repository.find(typeRepository);
          if (elem != null) {
            System.out.println(elem);
          }
        }
        case "4" -> {
          showMenuSort(repository, element);
        }
        case "5" -> {
          repository.showRepository();
        }
        case "6" -> {
          List<String> lista = Util.listDominios(repository);
          for (String dominio : lista) {
            System.out.println(dominio);
          }
        }
        case "7" -> {
          repository.deleteElement();
        }
        case "8" -> flag = false;
      }
    }
  }

  public void showMenuSort(Repository<Element> repository, Element element) {
    Class<?> clazz = element.getClass();
    List<String> listAtributos = new ArrayList<>();
    while (clazz != null) {
      for (Field field : clazz.getDeclaredFields()) {
        listAtributos.add(field.getName());
      }
      clazz = clazz.getSuperclass();
    }
    String op;
    while (true) {
      // Crear las opciones para el cuadro de diálogo
      String[] options = new String[listAtributos.size() + 1];
      for (int i = 0; i < listAtributos.size(); i++) {
        String charBegin = String.valueOf(listAtributos.get(i).charAt(0)).toUpperCase();
        options[i] = (i + 1) + ") " + charBegin + listAtributos.get(i).substring(1);
      }
      options[listAtributos.size()] = (listAtributos.size() + 1) + ") Back to previous menu";

      // Mostrar el cuadro de diálogo con las opciones
      op = (String) JOptionPane.showInputDialog(
              null,
              "Sort by:",
              "Menu",
              JOptionPane.QUESTION_MESSAGE,
              null,
              options,
              options[0]
      );

      int i = 1;
      for (; i <= listAtributos.size(); i++) {
        if (op.equals(Integer.toString(i))) {
          repository.getAllSortedBy(listAtributos.get((Integer.parseInt(op)) - 1));
          System.out.println(
              "Ordenados con exito por el criterio de "
                  + listAtributos.get((Integer.parseInt(op)) - 1));
          break;
        }
      }
      // Si es igual a la opcion de salida que se calcula de forma dinamica
      if (op.equals(Integer.toString((listAtributos.size())))) {
        return;
      }
    }
  }
}
