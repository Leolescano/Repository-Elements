package interfaces;

import models.Element;
import models.Repository;

public interface UXInterface {
  void showMenu();

  void showMenuRepository(Repository<Element> repository, String typeRepository);
}
