package springinthepub;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class RandomGenerator {
    static String nameFilePath = "names.txt";
    static String ageFilePath = "ages.txt";
    static String litersFilePath = "litersToDrink.txt";
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Integer> ages = new ArrayList<>();
    static ArrayList<Integer> liters = new ArrayList<>();

    public static ArrayList<String> generateNameList() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        ClassLoader classLoader = RandomGenerator.class.getClassLoader();
        File file = new File(classLoader.getResource(nameFilePath).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                list.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Integer> generateAgeList() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        ClassLoader classLoader = RandomGenerator.class.getClassLoader();
        File file = new File(classLoader.getResource(ageFilePath).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                int line = Integer.parseInt(scanner.nextLine());
                list.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Integer> generatelitersList() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        ClassLoader classLoader = RandomGenerator.class.getClassLoader();
        File file = new File(classLoader.getResource(litersFilePath).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                int line = Integer.parseInt(scanner.nextLine());
                list.add(line);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    public RandomGenerator() throws FileNotFoundException {
    }

    public static void main(String[] arg) throws IOException {
        System.out.println(generatelitersList());
    }

}
