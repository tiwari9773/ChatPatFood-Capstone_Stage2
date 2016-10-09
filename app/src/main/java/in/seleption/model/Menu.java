package in.seleption.model;

/**
 * Created by Lokesh on 09-11-2015.
 */
public class Menu {

    int menuId;
    String name;

    int drwableId;

    public Menu(int menuId, String name) {
        this.menuId = menuId;
        this.name = name;
    }

    public Menu(int menuId, int drwableId, String name) {
        this.menuId = menuId;
        this.drwableId = drwableId;
        this.name = name;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public int getDrwableId() {
        return drwableId;
    }
}
