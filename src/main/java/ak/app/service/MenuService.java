package ak.app.service;

import ak.app.exceptions.MyException;
import ak.app.model.Car;
import ak.app.service.enums.SortType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MenuService {

    private final String jsonFileName = "/home/adam/Desktop/CARS_COLLECTION_MANAGEMENT/jsonCars/cars.json";
    private final DataGeneratorService dataGenerator = new DataGeneratorService(jsonFileName);
    private final CarService carService = new CarService(jsonFileName);
    private final UserDataService userDataService = new UserDataService();

    public void mainMenuService() {

        dataGenerator.generateDataToJson(userDataService.howManyCarsToJsonFile());


        do {
            try {
                switch (userDataService.whichOption()) {
                    case 1:
                        option1();
                        break;
                    case 2:
                        option2();
                        break;
                    case 3:
                        option3();
                        break;
                    case 4:
                        option4();
                        break;
                    case 5:
                        option5();
                        break;
                    case 6:
                        option6();
                        break;
                    case 7:
                        option7();
                        break;
                    case 8:
                        option8();
                        break;
                    case 9:
                        option9();
                        break;
                    case 10:
                        option10();
                        break;
                    case 11:
                        option11();
                        break;
                    case 12:
                        option12();
                        break;
                    case 0:
                        return;
                }

            } catch (MyException e) {
                System.err.println(e.getExceptionDateTime());
                System.err.println(e.getExceptionMessage());
            }
        } while (true);
    }

    private void option1() {
        System.out.println(carService.toString());
    }

    private void option2() {
        SortType sortType = userDataService.getSortType();
        boolean isAscendingOrDescending = userDataService.isAscendingOrDescending();
        carService.sort(sortType, isAscendingOrDescending).forEach(System.out::println);
    }

    private void option3() {
        carService.carsWithMileageGraterThanSpecified(userDataService.mileage()).forEach(System.out::println);
    }

    private void option4() {
        carService.groupedByColorSortedDescending().forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    private void option5() {
        carService.groupedByModelSortedDescending().forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    private void option6() {
        int option = userDataService.whichStatistticsOption();
        carService.statistics(option);
    }

    private void option7() {
        carService.highestPriceCar().forEach(System.out::println);
    }

    private void option8() {
        carService.sortComponentsAlphabetically().forEach(System.out::println);
    }

    private void option9() {
        carService.componentWithListOfCars().forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    private void option10() {
        BigDecimal priceFrom = userDataService.lessPrice();
        BigDecimal priceTo = userDataService.higherPrice();
        carService.carsInThePriceRange(priceFrom, priceTo).forEach(System.out::println);
    }

    private void option11() {
        carService.manualCarGenerator(
                dataGenerator.manualGenerateCar(
                        userDataService.manualModel(),
                        userDataService.manualPrice(),
                        userDataService.manualColorType(),
                        userDataService.mileage(),
                        userDataService.manualComponents()
                ));

    }

    private void option12() {
        List<Car> list = new ArrayList<>();
        dataGenerator.beforeSaved(list);

    }
}
