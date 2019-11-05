package ak.app.service;

import ak.app.exceptions.MyException;
import ak.app.model.CarColor;
import ak.app.service.enums.SortType;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

class UserDataService {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Method for manual car generator
     *
     * @return
     */
    String manualModel() {
        String model;
        do {
            System.out.print("Model (only big letters): ");
            model = scanner.next();
            if (!model.matches("[A-Z]+")) {
                System.out.print("Valid model, try again: ");
            }
        } while (!model.matches("[A-Z]+"));
        return model;
    }

    BigDecimal manualPrice() {
        String price;
        do {
            System.out.print("Price from 20 000 to 500 000: ");
            price = scanner.next();
            if (!(isLessPriceCorrect(price) || isHigherPriceCorrect(price))) {
                System.out.println("Valid price, try again: ");
            }
        } while (!(isLessPriceCorrect(price) || isHigherPriceCorrect(price)));
        return new BigDecimal(price);
    }

    /**
     * Manual Components
     *
     * @return
     */
    Set<String> manualComponents() {
        Set<String> components = new LinkedHashSet<>();
        int size = howManyComponents();
        for (int i = 0; i < size; i++) {
            components.add(manualComponent());
        }
        return components;
    }

    String manualComponent() {
        String component;
        do {
            System.out.println("Enter component: ");
            component = scanner.next();
            if (!component.matches("[a-zA-Z]+")) {
                System.out.println("Valid component, try again");
            }
        } while (!component.matches("[a-zA-Z]+"));

        return component;
    }

    CarColor manualColorType() {
        String text;
        do {
            showColorSortType();
            text = scanner.next();
            if (!text.matches("[1-4]")) {
                System.out.print("Wrong, try again: ");
            }
        } while (!text.matches("[1-4]"));

        return CarColor.values()[Integer.parseInt(text) - 1];
    }

    int howManyComponents() {
        String howMany;
        do {
            System.out.println("How many components? [1-5]: ");
            howMany = scanner.next();
            if (!howMany.matches("[1-5]")) {
                System.out.print("Wrong, try again: ");
            }
        } while (!howMany.matches("[1-5]"));
        return Integer.parseInt(howMany);
    }

    // END of manual components
    //END of methods for manual car generator

    private void showColorSortType() {
        System.out.println("Color [1-4]");
        System.out.println("1 - red");
        System.out.println("2 - white");
        System.out.println("3 - black");
        System.out.println("4 - green");

        System.out.print("Which option?: ");
    }


    SortType getSortType() {
        showColorSortType();
        String text = scanner.nextLine();
        if (!text.matches("[1-4]")) {
            throw new MyException("SORT TYPE IS NOT CORRECT");
        }

        return SortType.values()[Integer.parseInt(text) - 1];
    }

    /**
     * 1. checkAscendingOrDescending() - return int:
     * Ascending - 1
     * Descending - 2
     * 2. isAscendingOrDescending() - return boolean
     *
     * @return boolean
     */
    boolean isAscendingOrDescending() {
        switch (checkAscendingOrDescending()) {
            case 1:
                return true;
            case 2:
                return false;
            default:
                return true;
        }
    }

    private int checkAscendingOrDescending() {
        Scanner scanner = new Scanner(System.in);
        String s, pattern = "[1-2]";

        System.out.print("1 - Ascending | 2 - Descending: ");
        do {
            s = scanner.next();
            if (!s.matches(pattern)) {
                System.out.println("\nWrong number\nTry Again");
            }
        } while (!s.matches(pattern));
        return Integer.parseInt(s);
    }

    //============================ E N D of Ascending and Descending validation =================================


    /**
     * Method responsible for user chose -> which option in menu
     */
    int whichOption() {
        show();
        Scanner s = new Scanner(System.in);
        String option;

        do {
            System.out.print("\nYour option [0,12] : ");
            option = s.next();
            if (isCorrectOption(option)) {
                System.out.println("Wrong number - try again");
            }
        } while (isCorrectOption(option));

        return Integer.parseInt(option);
    }

    private boolean isCorrectOption(String n) {
        return !n.matches("[0-9]|10|11|12");
    }

    /**
     * Helper methods for -> mileage grater than specified
     */
    double mileage() {
        Scanner scanner = new Scanner(System.in);
        String pattern = "[1-9][0-9]{2,5}", s;

        System.out.print("Specife your mileage [100,999999]: ");
        do {
            s = scanner.next();

            if (!s.matches(pattern)) {
                System.out.print("\nWrong mileage\nTry again: ");
            } else {
                System.out.println("Good work, your mileage is CORRECT!");
            }
        } while (!s.matches(pattern));

        return Double.parseDouble(s);
    }
    //end of method

    /**
     * Helper methods for SHOW STATISTICS
     * 1. whichStatistticsOption() - return int
     * 2. statisticOptionIsCorrect(String s) - return boolean
     * 3. showStatisticOptions() - void, only shows miniMenu for statistics
     */

    int whichStatistticsOption() {
        Scanner scanner = new Scanner(System.in);
        String option;
        showStatisticOptions();
        do {
            option = scanner.next();

            if (statisticOptionIsCorrect(option)) {
                System.out.println("Valid number, try again");
                System.out.print("Option number?: ");
            }

        } while (statisticOptionIsCorrect(option));

        return Integer.parseInt(option);
    }

    private boolean statisticOptionIsCorrect(String s) {
        return !s.matches("[1-3]");
    }

    private void showStatisticOptions() {
        System.out.println("1 - Price ");
        System.out.println("2 - Mileage ");
        System.out.println("3 - Both ");
        System.out.print("Option number: ");
    }
    //=====================================  E N D of STATISTICS HELPER ======================================

    /**
     * 1. isLessPriceCorrect - boolean -> is price correct
     * 2. isHigherPriceCorrect -> boolean -> is higer price correct
     * Two methods responsible for validation.
     * 3. highestPrice()
     * 4. lowerPrice()
     * Methods which converting string to big decimal object
     *
     * @param price
     * @return
     */

    private boolean isLessPriceCorrect(String price) {
        return !price.matches("[2-9][0-9]{4}|[1][0]{5}");
    }

    private boolean isHigherPriceCorrect(String price) {
        return !price.matches("[1-4][0-9]{5}|[5][0]{5}");
    }

    BigDecimal lessPrice() {
        Scanner scanner = new Scanner(System.in);
        String price;
        System.out.print("Enter price from 20 000 to 100 000: ");
        do {
            price = scanner.next();
            if (isLessPriceCorrect(price)) {
                System.out.println("Price abound values, try again");
            }
        } while (isLessPriceCorrect(price));

        return new BigDecimal(price);
    }

    BigDecimal higherPrice() {
        Scanner scanner = new Scanner(System.in);
        String price;
        System.out.print("Enter price from 100 000 to 500 000: ");
        do {
            price = scanner.next();
            if (isHigherPriceCorrect(price)) {
                System.out.println("Price abound values, try again");
            }
        } while (isHigherPriceCorrect(price));

        return new BigDecimal(price);
    }
//End of methods for range price

    int howManyCarsToJsonFile() {
        Scanner scanner = new Scanner(System.in);
        String s;
        do {
            System.out.println("How many cars you want to generate to Json file? [1-10]");
            System.out.print("Enter value: ");
            s = scanner.next().trim();
            if (isCarsToJsonValueCorrect(s)) {
                System.out.println("Wrong value, try again");
            }
        } while (isCarsToJsonValueCorrect(s));

        return Integer.parseInt(s);
    }

    private boolean isCarsToJsonValueCorrect(String s) {
        return !s.matches("[1-9]|10");
    }

    private void show() {
        System.out.println("\t\tM\tE\tN\tU\t");
        System.out.println("1 - show cars");
        System.out.println("2 - sort cars");
        System.out.println("3 - cars with mileage greater than specified");
        System.out.println("4 - grouping by COLOR");
        System.out.println("5 - grouping by MODEL");
        System.out.println("6 - show statistics");
        System.out.println("7 - car with highest price");
        System.out.println("8 - sort components");
        System.out.println("9 - return Map::key -> component, value -> list of car with this component");
        System.out.println("10 - show in the price range");
        System.out.println("11 - add Car - manual ;)");
        System.out.println("12 - read the state before adding a new car");
        System.out.println("0 - close application");
    }
}