package aplication;

import utils.Util;
import ux.UX;
// Implementación del Repositorio
// Vamos a utilizar la tarea de la Semana 1 y agregar las siguientes operaciones a nuestro
// repositorio:
// • getAllSortedBy: que devolverá todos los elementos de nuestro repositorio
// ordenados por el campo dado. Por ejemplo, countryRepository.getAllSortedBy("isoCode")
// Para el repositorio de Usuario necesitamos poder:
// • getDomains: que devolverá una colección que contiene todos los diferentes dominios que tiene
// nuestro usuario.
// Por ejemplo, si tengo applaudoUser@applaudostudios.com y applaudoUser2@gmail.com
// el resultado debería ser applaudostudios.com y gmail.com
// También vamos a añadir una validación antes de agregar un elemento a nuestra colección de
// repositorios:
// • El elemento debería existir sólo una vez en la colección siguiendo las siguientes condiciones:
// o país: si el código ISO ya se ha utilizado, omitir la adición.
// o usuario: si el correo electrónico del usuario ya existe, omitir la adición.
// o producto: utilizar el nombre del producto como identificador único, teniendo en cuenta que el
// nombre
// no distingue entre mayúsculas y minúsculas. Si un elemento ya existe, entonces debería
// incrementar el valor de stock en lugar de agregar uno nuevo.

public class Program {
  public static void main(String[] args) {
    UX ux = new UX();
    ux.showMenu();
    Util.SC.close();
  }
}
