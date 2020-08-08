package ak.app;

import ak.app.service.MenuService;


public class App {

    public static void main(String[] args) {

        MenuService menuService = new MenuService();
        menuService.mainMenuService();

    }

}
