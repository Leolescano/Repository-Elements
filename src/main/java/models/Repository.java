package models;

import exceptions.InvalidDatesException;
import interfaces.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import lombok.Getter;
import utils.Util;

import javax.swing.*;

public class Repository<T> implements Save<T>, Count, Find<T>, ShowRepository, DeleteElement {

  @Getter private List<T> repository;

  public Repository() {
    this.repository = new ArrayList<>();
  }

  @Override
  public void save(T element) {
    boolean repeat = false;

    if (element instanceof Product) {
      for (T elem : this.repository) {
        if (((Product) elem).getName().equals(((Product) element).getName())) {
          repeat = true;
          ((Product) elem).setStock(((Product) elem).getStock() + ((Product) element).getStock());
          break;
        }
      }
      if (!repeat) {
        this.repository.add(element);
        JOptionPane.showMessageDialog (null,"The product was saved successfully", "Saved Successfully", JOptionPane.INFORMATION_MESSAGE);

      } else {
        JOptionPane.showMessageDialog (null, "The product already existed in the repository, so only the stock was updated", "Confirmation", JOptionPane.WARNING_MESSAGE);
      }
    }

    if (element instanceof Country) {
      for (T elem : this.repository) {
        if (((Country) elem).getIsoCode().equals(((Country) element).getIsoCode())) {
          repeat = true;
          break;
        }
      }
      if (!repeat) {
        this.repository.add(element);
        JOptionPane.showMessageDialog (null,"The country was saved successfully", "Saved Successfully", JOptionPane.INFORMATION_MESSAGE);

      } else {
        JOptionPane.showMessageDialog (null, "The ISO code was already used in the repository. Enter the country data again", "Attention", JOptionPane.WARNING_MESSAGE);
      }
    }
    if (element instanceof User) {
      for (T elem : this.repository) {
        if (((User) elem).getEmail().equals(((User) element).getEmail())) {
          repeat = true;
          break;
        }
      }
      if (!repeat) {
        this.repository.add(element);
        JOptionPane.showMessageDialog (null,"The user was saved successfully", "Saved Successfully", JOptionPane.INFORMATION_MESSAGE);

      } else {
        JOptionPane.showMessageDialog (null, "That e-mail has already been registered in the repository", "Attention", JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  @Override
  public void count(String typeRepository) {
    if (this.repository.size() > 0) {
      JOptionPane.showMessageDialog(null, "The repository contains" + this.repository.size() + " " + typeRepository, "Information", JOptionPane.INFORMATION_MESSAGE);

    } else {
      JOptionPane.showMessageDialog(null, "The repository is empty", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public T find(String typeRepository) {
    String indexStr;
    if (this.repository.size() == 0) {
      JOptionPane.showMessageDialog(null, "The repository is empty", "Information", JOptionPane.INFORMATION_MESSAGE);
      return null;
    }
    while (true) {
      try {
        indexStr = JOptionPane.showInputDialog(null, "Enter the position of the " + typeRepository + " within the repository", "Search");
        int index = Integer.parseInt(indexStr) - 1;

        if (index < this.repository.size() && index >= 0) {
          return this.repository.get(index);
        } else {
          throw new InvalidDatesException("That position does not exist in the repository");
        }
      } catch (InputMismatchException e) {
        JOptionPane.showMessageDialog(null, "You must enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "Attention", JOptionPane.ERROR_MESSAGE);
        return null;
      }
    }
  }

  public void getAllSortedBy(String atributo) { // name stock iso
    Comparator comparator =
        Comparator.comparing(
            element -> {
              try {
                Method method =
                    element.getClass()
                        .getMethod(
                            "get" + atributo.substring(0, 1).toUpperCase() + atributo.substring(1));
                return (Comparable) method.invoke(element);
              } catch (NoSuchMethodException
                  | IllegalAccessException
                  | InvocationTargetException e) {
                throw new RuntimeException(e);
              }
            });
    this.repository.sort(comparator);
  }

  @Override
  public void showRepository() {
    if (this.repository.size() > 0) {
      int cont = 1;
      //System.out.println();
      for (T element : this.repository) {
        JOptionPane.showMessageDialog(null,  """
                Index:%d ->
                %s
                """.formatted(cont, element));
        cont++;
      }
    } else {
      JOptionPane.showMessageDialog(null, "The repository is empty", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public void deleteElement() {
    int index;
    if (this.repository.size() == 0) {
      JOptionPane.showMessageDialog(null, "The repository is empty", "Information", JOptionPane.INFORMATION_MESSAGE);
    }
    while (true) {
      try {
        System.out.print("Enter the position of the element to delete: ");
        index = Util.SC.nextInt() - 1;
        Util.SC.nextLine();
        if (index < this.repository.size() && index >= 0) {
          this.repository.remove(index);
          System.out.println("The item was successfully removed.");
          return;
        } else {
          throw new InvalidDatesException("That position does not exist in the repository");
        }
      } catch (InputMismatchException e) {
        System.out.println("You must enter an integer.");
        Util.SC.nextLine();
      } catch (InvalidDatesException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
