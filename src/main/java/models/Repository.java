package models;

import exceptions.InvalidDatesException;
import interfaces.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.*;
import lombok.Getter;

public class Repository<T>
    implements Save<T>, Count, Find<T>, ShowRepository, DeleteElement, SortedBy {

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
        JOptionPane.showMessageDialog(
            null,
            "The product was saved successfully",
            "CONFIRMATION",
            JOptionPane.INFORMATION_MESSAGE);

      } else {
        JOptionPane.showMessageDialog(
            null,
            "The product already existed in the repository, so only the stock was updated",
            "CONFIRMATION",
            JOptionPane.WARNING_MESSAGE);
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
        JOptionPane.showMessageDialog(
            null,
            "The country was saved successfully",
            "CONFIRMATION",
            JOptionPane.INFORMATION_MESSAGE);

      } else {
        JOptionPane.showMessageDialog(
            null,
            "The ISO code was already used in the repository. Enter the country data again",
            "ATTENTION",
            JOptionPane.WARNING_MESSAGE);
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
        JOptionPane.showMessageDialog(
            null,
            "The user was saved successfully",
            "CONFIRMATION",
            JOptionPane.INFORMATION_MESSAGE);

      } else {
        JOptionPane.showMessageDialog(
            null,
            "That e-mail has already been registered in the repository",
            "ATTENTION",
            JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  @Override
  public void count(String typeRepository) {
    if (this.repository.size() > 0) {
      JOptionPane.showMessageDialog(
          null,
          "The repository contains " + this.repository.size() + " " + typeRepository,
          "INFORMATION",
          JOptionPane.INFORMATION_MESSAGE);

    } else {
      JOptionPane.showMessageDialog(
          null, "The repository is empty", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public T find(String typeRepository) {
    String indexStr;
    if (this.repository.size() == 0) {
      JOptionPane.showMessageDialog(
          null, "The repository is empty", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
      return null;
    }
    while (true) {
      try {
        indexStr =
            JOptionPane.showInputDialog(
                null,
                "Enter the position of the " + typeRepository + " within the repository",
                "SEARCH",
                JOptionPane.QUESTION_MESSAGE);

        if (indexStr == null) {
          return null;
        }
        int index = Integer.parseInt(indexStr) - 1;

        if (index < this.repository.size() && index >= 0) {
          return this.repository.get(index);
        } else {
          throw new InvalidDatesException("That position does not exist in the repository");
        }
      } catch (InputMismatchException e) {
        JOptionPane.showMessageDialog(
            null, "You must enter an integer", "INVALID DATE", JOptionPane.ERROR_MESSAGE);
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "ATTENTION", JOptionPane.ERROR_MESSAGE);
        return null;
      }
    }
  }

  @Override
  public void getAllSortedBy(String atributo) {
    Comparator comparator =
        Comparator.comparing(
            element -> {
              try {
                Method method =
                    element
                        .getClass()
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
  public void showRepository(String typeRepository) {
    if (this.repository.size() > 0) {
      int cont = 1;
      Collection<T> items = this.repository;

      T[] array = (T[]) items.toArray(new Object[items.size()]);
      JList<T> list = new JList<>(array);
      JOptionPane.showMessageDialog(
          null,
          new JScrollPane(list),
          typeRepository.toUpperCase(),
          JOptionPane.INFORMATION_MESSAGE);

      //      for (T element : this.repository) {
      //        JOptionPane.showMessageDialog(
      //            null,
      //            """
      //                %s -> %d
      //                %s
      //                """
      //                .formatted(typeRepository, cont, element));
      //        cont++;
      //      }
    } else {
      JOptionPane.showMessageDialog(
          null, "The repository is empty", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  @Override
  public void deleteElement(String typeRepository) {
    int index;
    String indexStr;
    if (this.repository.size() == 0) {
      JOptionPane.showMessageDialog(
          null, "The repository is empty", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
    }
    while (true) {
      try {
        indexStr =
            JOptionPane.showInputDialog(
                null,
                "Enter the position of the " + typeRepository + " to delete",
                "DELETE",
                JOptionPane.QUESTION_MESSAGE);

        if (indexStr == null) {
          return;
        }

        index = Integer.parseInt(indexStr) - 1;
        if (index < this.repository.size() && index >= 0) {
          Element element = (Element) this.repository.get(index);
          this.repository.remove(index);
          JOptionPane.showMessageDialog(
              null,
              """
                          %s
                          Was successfully removed
                          """
                  .formatted(element),
              "DELETE",
              JOptionPane.INFORMATION_MESSAGE);
          return;
        } else {
          throw new InvalidDatesException("That position does not exist in the repository");
        }
      } catch (InputMismatchException e) {
        JOptionPane.showMessageDialog(
            null, "You must enter an integer", "ERROR", JOptionPane.ERROR_MESSAGE);
      } catch (InvalidDatesException e) {
        JOptionPane.showMessageDialog(null, e.getMessage(), "ATTENTION", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}
