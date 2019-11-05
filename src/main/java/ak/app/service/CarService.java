package ak.app.service;

import ak.app.converters.CarsJsonConverter;
import ak.app.exceptions.MyException;
import ak.app.model.Car;
import ak.app.model.CarColor;
import ak.app.service.enums.SortType;
import ak.app.validation.CarValidator;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.groupingBy;

public class CarService {
    private final Set<Car> cars;

    CarService(String filename) {
        cars = getCarsFromJson(filename);
    }

    private Set<Car> getCarsFromJson(String filename) {

        CarValidator carValidator = new CarValidator();

        return new CarsJsonConverter(filename)
                .fromJson()
                .orElseThrow(() -> new MyException("CARS SERVICE JSON CONVERSION EXCEPTION"))
                .stream()
                .filter(car -> {
                    carValidator.validate(car).forEach((k, v) -> System.out.println(k + " " + v));
                    return !carValidator.hasErrors();
                })
                .collect(Collectors.toSet());
    }

    /**
     * Number 1
     * Show cars
     *
     * @Override toString method
     */
    @Override
    public String toString() {
        return cars.stream().map(Car::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Number 2
     * Sort type methods
     * 1 - by Model
     * 2 - by Color
     * 3 - by Price
     * 4 - by Mileage
     *
     * @param sortType
     * @param ascending
     * @return
     */
    Set<Car> sort(SortType sortType, boolean ascending) {

        if (sortType == null) {
            throw new MyException("SORT TYPE IS NOT CORRECT");
        }

        switch (sortType) {
            case MODEL:
                return ascending
                        ? sortedByModelAscending()
                        : sortedByModelDescending();
            case COLOR:
                return ascending
                        ? sortedByColorAscending()
                        : sortedByColorDescending();
            case PRICE:
                return ascending
                        ? sortedByPriceAscending()
                        : sortedByPriceDescending();
            case MILEAGE:
                return ascending
                        ? sortedByMileageAscending()
                        : sortedByMileageDescending();
        }
        return cars;
    }

    private Set<Car> sortedByModelAscending() {
        return cars
                .stream()
                .sorted(comparing(Car::getModel))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByModelDescending() {
        return cars
                .stream()
                .sorted(comparing(Car::getModel).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByColorAscending() {
        return cars
                .stream()
                .sorted(comparing(Car::getColor))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByColorDescending() {
        return cars
                .stream()
                .sorted(comparing(Car::getColor).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByPriceAscending() {
        return cars
                .stream()
                .sorted(comparing(Car::getPrice))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByPriceDescending() {
        return cars
                .stream()
                .sorted(comparing(Car::getPrice).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByMileageAscending() {
        return cars
                .stream()
                .sorted(comparing(Car::getMileage))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Car> sortedByMileageDescending() {
        return cars
                .stream()
                .sorted(comparing(Car::getMileage).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    //End of SORT methods


    /**
     * Number 3
     * Method returns collection of cars with mileage greater than specified
     *
     * @return
     */
    Set<Car> carsWithMileageGraterThanSpecified(double mileage) {
        return cars
                .stream()
                .filter(c -> isGreater(c, mileage))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private static boolean isGreater(Car c, double m) {
        if (c == null)
            return false;
        return c.getMileage() > m;
    }
    //End of carsWithMileageGraterThanSpecified methods


    /**
     * Number 4
     * Methods returns CarService Collection grouped by color, counted and sorted Descending
     *
     * @return
     */
    Map<CarColor, Long> groupedByColorSortedDescending() {
        return cars
                .stream()
                .collect(groupingBy(Car::getColor, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(comparing(Map.Entry::getValue, reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                        )
                );
    }//END of methods GROUPED by COLOR and sorted descending

    /**
     * Number 5
     * 1. private Car carHigherPrice(Car a, Car b) - return car with higher price
     * 2. public Map<String, Car> groupedByModelSortedDescending()
     *
     * @return
     */


    Map<String, Car> groupedByModelSortedDescending() {
        return cars
                .stream()
                .collect(groupingBy(Function.identity()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        k -> k.getKey().getModel(),
                        v -> v.getValue().stream().max(comparing(Car::getPrice)).get(),
                        this::carHigherPrice
                        )
                );
    }

    private Car carHigherPrice(Car a, Car b) {
        return a.getPrice().compareTo(b.getPrice()) >= 0 ? a : b;
    }

    /**
     * Number 6
     * STATISTICS
     * Eclipse Collectors2
     * Price - BigDecimalSummaryStatistics
     * Mileage - DoubleSummaryStatistics
     */
    void statistics(int option) {
        if (option == 1) {
            showStatsBigDecimal(bigDecimalSummaryStatistics());
        } else if (option == 2) {
            showStatsDouble(doubleSummaryStatistics());
        } else if (option == 3) {
            showStatsBigDecimal(bigDecimalSummaryStatistics());
            showStatsDouble(doubleSummaryStatistics());
        }
    }

    private DoubleSummaryStatistics doubleSummaryStatistics() {
        return cars
                .stream()
                .collect(Collectors.summarizingDouble(Car::getMileage));
    }

    private BigDecimalSummaryStatistics bigDecimalSummaryStatistics() {
        return cars
                .stream()
                .collect(Collectors2.summarizingBigDecimal(Car::getPrice));
    }

    private void showStatsBigDecimal(BigDecimalSummaryStatistics p) {
        System.out.println("Price \nAVERAGE: " + p.getAverage());
        System.out.println("MAX: " + p.getMax());
        System.out.println("Min: " + p.getMin());
        System.out.println();
    }

    private void showStatsDouble(DoubleSummaryStatistics m) {
        System.out.println("Mileage: \nAVERAGE: " + m.getAverage());
        System.out.println("MAX: " + m.getMax());
        System.out.println("MIN: " + m.getMin());
        System.out.println();
    }
    //End of statistic methods

    /**
     * Number 7
     * Return Car with highest price.
     * 1.private boolean isHigher(Car car) - this method find car with the highest price
     * 2.public Set Car highestPriceCar() - return One car or Car collection with the highest price
     *
     * @return
     */
    Set<Car> highestPriceCar() {
        return cars
                .stream()
                .filter(this::isHigher)
                .sorted(Comparator.comparing(Car::getPrice).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private boolean isHigher(Car car) {
        Car c1 = cars.stream().max(Comparator.comparing(Car::getPrice)).get();
        return c1.getPrice().compareTo(car.getPrice()) <= 0;
    }
    //End of highestPriceCar

    /**
     * Number 8
     * Sort Components Alphabetically
     *
     * @return
     */
    Set<Car> sortComponentsAlphabetically() {
        return cars
                .stream()
                .peek(c -> {
                    c.setComponents(c.getComponents().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new)));
                    System.out.println(c.getComponents());
                })
                .collect(Collectors.toSet());
    }


    /**
     * Number 9
     * Return
     * Component -> List Car
     *
     * @return
     */
    Map<String, List<Car>> componentWithListOfCars() {

        return cars
                .stream()
                .flatMap(k -> k.getComponents().stream())
                .distinct()
                .collect(Collectors.toMap(
                        component -> component,
                        component -> cars.stream().filter(car -> car.getComponents().contains(component)).collect(Collectors.toList())
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1, LinkedHashMap::new));
    }


    /**
     * NUMBER -> 10
     * 1. Show CarService in the price range [a,b]
     *
     * @return
     */
    Set<Car> carsInThePriceRange(BigDecimal fromPrice, BigDecimal toPrice) {

        if (fromPrice == null) {
            throw new MyException("FROM PRICE IS NULL");
        }

        if (toPrice == null) {
            throw new MyException("TO PRICE IS NULL");
        }

        if (fromPrice.compareTo(toPrice) >= 0) {
            throw new MyException("FROM PRICE IS GREATER OR EQUALS THAN TO PRICE");
        }

        return cars
                .stream()
                .filter(c -> c.getPrice().compareTo(fromPrice) >= 0 && c.getPrice().compareTo(toPrice) <= 0)
                .sorted(Comparator.comparing(Car::getModel).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
    //End of methods -> show CarService in the price RANGE

    /**
     * Number 11
     * Add Car manual
     *
     * @return
     */
    Set<Car> manualCarGenerator(Car car) {
        cars.add(car);
        return cars;
    }



}
