package Menu;

import Commands.*;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList<String> listOfCommand = new LinkedList<>();
    static {
//        listOfCommand = new LinkedList<>();
        listOfCommand.add("Select");
        listOfCommand.add("InsertAssemblage");
        listOfCommand.add("Insert");
//        listOfCommand.add("SelectAssemblageName");
        listOfCommand.add("Update");
        listOfCommand.add("Delete");

    }

    public static void startMenu() {
        while(true) {
            printMenu();
            System.out.print(" > ");
            switch (checkUnsignedInt()) {
                case 1:
                    printMusicInfo();
                    break;
                case 2:
                    addCollectionToDisk();
                    break;
                case 3:
                    addMusicToCollection();
                    break;
                case 4:
                    deleteMusicFromCollection();
                    break;
                case 5:
                    deleteCollection();
                    break;
                default:
                    System.out.println(" Даного пункту не існує! Повторіть спробу!");
                    break;
            }
        }
    }

    public static void startMenu2() {
        while(true) {
//            printMenu();
            System.out.println("\n\tВведіть команду(для детальної інформації введіть help):");
            System.out.print(" > ");
            String command = scanner.next();
            scanner.nextLine();
            switch (command) {
                case "help":
                    for (String c : listOfCommand)
                        System.out.println(c);
                    break;
                case "Select":
                    printMusicInfo();
                    break;
                case "InsertAssemblage":
                    addCollectionToDisk();
                    break;
                case "Insert":
                    addMusicToCollection();
                    break;
//                case "Delete...:
//                    deleteMusicFromCollection();
//                    break;
//                case "Update...:
//                    deleteMusicFromCollection();
//                    break;
                case "Delete":
                    deleteCollection();
                    break;
                default:
                    System.out.println(" Даної команди не існує! Повторіть спробу!");
                    break;
            }
        }
    }
    private static void printMenu() {
        System.out.println(" Оберіть пункт з меню:");
        System.out.println("\t 1.Вивести музичні композиції.");
//        System.out.println("\t 2.Вивести інформацію про музичні збірки.");
        System.out.println("\t 2.Додати збірку музичних композицій до диску.");
        System.out.println("\t 3.Додати музичну композицію.");
        System.out.println("\t 4.Видалити музичну композицію.");
        System.out.println("\t 5.Видалити збірку музични композицій.");
    }
    private static void printMusicInfo() {
        System.out.println("Введіть назву збірки: ");
        System.out.println("\t Серед наявних є:");
        SelectAssemblageNamesCommand sanc = new SelectAssemblageNamesCommand();
        sanc.execute();
        if(sanc.getAssemblageNames() == null) {
            System.out.println("Збірки у базі даних відсутні! Спочатку додайте збірку!");
            return;
        }
        for (String el : sanc.getAssemblageNames())
            System.out.println(el);
        String assemblageName = scanner.nextLine();
//        assemblageName = scanner.nextLine();

//        System.out.println(" Оберіть інформацію, яку потрібно вивести:");
//        System.out.println("\t 1.Вивести збірку за назвою.");
//        System.out.println("\t 2.Вивести композицію за назвою.");
//        System.out.println("\t 3.Вивести композиції за жанром.");
//        System.out.print(" > ");
//        switch (checkUnsignedInt()) {
//            case 1:
//                System.out.println(" Введіть назву збірки:");
//                System.out.print(" > ");
//                printCollectionByName(scanner.nextLine());
//                break;
//            case 2:
//                System.out.println(" Введіть назву композиції:");
//                System.out.print(" > ");
//                printMusicByName(scanner.nextLine());
//                break;
//            case 3:
//                System.out.println(" Введіть жанр:");
//                System.out.print(" > ");
//                printMusicByGenre(scanner.nextLine());
//                break;
//        }
        SelectAssemblageCommand command = new SelectAssemblageCommand(assemblageName);
        command.execute();
        List<Composition> assemblage = command.getAssemblage();
        for (Composition el : assemblage)
            System.out.println(el);
    }
    private static void printMusicByName(String musicName) {

    }
    private static void printCollectionByName(String collectionName) {

    }
    private static void printMusicByGenre(String genre) {

    }







    private static void addCollectionToDisk() {
        List<Composition> collectionOfComposition = null;

        System.out.println( "Введіть назву збірки:");
        System.out.print(" > ");
        String assemblageName = scanner.nextLine();
        if(RecordingStudio.isCollectionExist(assemblageName)){
            System.out.println("Колекція з таким іменем вже існує!");
            return;
        }

        while (true) {
            (new InsertCommand(createMusic(assemblageName))).execute();

            System.out.println(" Введіть одиницю, щоб завершити:");
            System.out.print(" > ");
            if(checkUnsignedInt() == 1)
                break;
        }
    }


    private static Composition createMusic(String assemblageName) {
        Composition composition = null;

        System.out.println(" Введіть назву композиції, яку потрібно додати:");
        System.out.print(" > ");
        String name = scanner.nextLine();
        System.out.println(" Введіть жанр композиції:");
        System.out.print(" > ");
        String genre = scanner.nextLine();
    //    while (music == null)
    //        music = RecordingStudio.musicByGenre(scanner.nextLine());
    //    music.setName(name);
        System.out.println(" Введіть тривалість композиції:");
        System.out.print(" > ");
        double duration = checkUnsignedDouble();
    //    music.setDuration(checkUnsignedDouble());
        composition = new Composition(0, name, duration, genre, assemblageName);

        return composition;
    }



    private static void addMusicToCollection() {
        int numOfAssemblageNames = 0;
//        String collectionName = null;
////        int i;
//        System.out.println(" Введіть назву збірки, у яку потрібно додати композицію:");
//        System.out.println("Оновлення ще не завезли!");

        SelectAssemblageNamesCommand selectAssemblageNamesCommand = new SelectAssemblageNamesCommand();
        selectAssemblageNamesCommand.execute();
        List<String> assemblageNames = selectAssemblageNamesCommand.getAssemblageNames();

        System.out.println("\t Усі наявні збірки: ");
        for (String assemblageName : assemblageNames) {
            numOfAssemblageNames++;
            System.out.println(numOfAssemblageNames + ". " + assemblageName);
        }

        while(true) {
            System.out.println(" Введіть номер збірки: ");
            System.out.print(" > ");
            numOfAssemblageNames = checkUnsignedInt();
            if(numOfAssemblageNames > 0 && numOfAssemblageNames <= assemblageNames.size())
                break;
            System.out.println(" Введений некоректний номер! Повторіть спробу!");
        }
        while (true) {
            (new InsertCommand(createMusic(assemblageNames.get(numOfAssemblageNames-1)))).execute();

            System.out.println(" Введіть одиницю, щоб завершити:");
            System.out.print(" > ");
            if(checkUnsignedInt() == 1)
                break;
        }

//        System.out.println("\t Серед наявних є:");
//        while (true) {}
//        System.out.println("\t 1.Створити нову збірку");
//        i = checkUnsignedInt();
//        if(i == 1){
//            System.out.printf("Введіть назву збірки:");
//            createCollection(scanner.nextLine());
//        }


//        RecordingStudio.addMusicToCollectionByCollectionName(createMusic("Something"), collectionName);
    }




    private static void updateComposition() {
        int id = 0;
        while(id == 0) {
            System.out.println("Введіть id композиції(0 - вивести композиції):");
            System.out.print(" > ");
            id = checkUnsignedInt();
            if (id == 0)
                printMusicInfo();
        }
        SelectByIdCommand sbic = new SelectByIdCommand(id);
        if(!sbic.execute()){
            System.out.println(" Даної композиції не існує!");
            return;
        }
        Composition composition = createMusic(sbic.getValue().getAssemblageName());
        (new UpdateByIdCommand(id, composition)).execute();
    }







    private static void deleteMusicFromCollection() {
        System.out.println("Оновлення ще не завезли!");
//        System.out.println("\t Введіть назву збірки, композицію з якої потрібно видалити:");
//        System.out.print(" > ");
//        System.out.println(scanner.nextLine());
//        System.out.println("\t Введіть назву композиції, яку потрібно видалити:");
//        System.out.print(" > ");
//        System.out.println(scanner.nextLine());
    }
    private static void deleteCollection() {
        System.out.println("\t Введіть назву збірки, яку потрібно видалити:");
        System.out.print(" > ");
        String assemblageName = scanner.nextLine();
        //System.out.println(scanner.nextLine());
        (new DeleteAssemblageCommand(assemblageName)).execute();

    }




    private static double checkUnsignedDouble(){
        double number;
        while(true) {
            try {
                number = scanner.nextDouble();
            } catch (Exception ex) {
                System.out.print("Введений рядок не є дійсним числом!\n Повторіть спробу: ");
                scanner.nextLine();
                continue;
            }
            break;
        }
        scanner.nextLine();
        return number;
    }
    private static int checkUnsignedInt(){
        int number;
        while(true) {
            try {
                number = scanner.nextInt();
            } catch (Exception ex) {
                System.out.print("Введений рядок не є цілим числом!\n Повторіть спробу: ");
                scanner.nextLine();
                continue;
            }
            if (number < 0) {
                System.out.print("Кількість елементів не може бути меншою одиниці! \n\tПовторіть спробу:");
                continue;
            }
            break;
        }
        scanner.nextLine();
        return number;
    }
}
