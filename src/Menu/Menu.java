package Menu;

import Commands.*;
import Exceptions.VariableIsNull;
import Exceptions.StatementDontReturnValueException;
import Exceptions.ZeroRowChangedException;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu extends Editor {
//    public static Composition composition;
//    RecordingStudio recordingStudio;
    private Editor editor;

//    public static Composition getComposition() {
//        return composition;
//    }
//
//    public static List<Composition> getCompositions() {
//        return compositions;
//    }
//    public static List<String> getAssemblageNames() {
//        return assemblageNames;
//    }

    private boolean execute(Command command) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        return command.execute();
    }
    private Scanner scanner = new Scanner(System.in);
    private LinkedList<String> listOfCommand = new LinkedList<>();
    {
//        listOfCommand = new LinkedList<>();
        listOfCommand.add("SelectAssemblage");
        listOfCommand.add("InsertAssemblage");
        listOfCommand.add("InsertComposition");
        listOfCommand.add("SelectCompositionsByDuration");
        listOfCommand.add("SelectCompositionByGenreName");
//        listOfCommand.add("SelectAssemblageName");
//        listOfCommand.add("Update");
        listOfCommand.add("DeleteCompositionByIdFromAssemblage");
        listOfCommand.add("DeleteAssemblage");
        listOfCommand.add("Exit");
    }
/*
    public void startMenu() {
        editor = this;
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
 */

    public void startMenu2() {
//        recordingStudio = new RecordingStudio();
        editor = this;
        while(true) {
//            printMenu();
            System.out.println("\tВведіть команду(для детальної інформації введіть help):");
            System.out.print(" > ");
            String command = scanner.next();
            scanner.nextLine();
            switch (command) {
                case "help":
                    for (String c : listOfCommand)
                        System.out.println(c);
                    break;
                case "SelectAssemblage":
                    printMusicInfo();
                    break;
                case "InsertAssemblage":
                    addCollectionToDisk();
                    break;
                case "InsertComposition":
                    addMusicToCollection();
                    break;
                case "SelectCompositionsByDuration":
                    printMusicInfoByDuration();
                    break;
                case "SelectCompositionByGenreName":
                    printCompositionsByGenre();
                    break;
//                case "Update...:
//                    deleteMusicFromCollection();
//                    break;
                case "DeleteCompositionByIdFromAssemblage":
                    deleteMusicFromCollection();
                    break;
                case "DeleteAssemblage":
                    deleteAssemblage();
                    break;
                case "Exit":
                    return;
                default:
                    System.out.println(" Даної команди не існує! Повторіть спробу!");
                    break;
            }
        }
    }

    private void printMusicInfoByDuration() {
        System.out.println("Введіть нижню межу довжини треків: ");
        double d1 = checkUnsignedDouble();
        System.out.println("Введіть верхню межу довжини треків: ");
        double d2 = checkUnsignedDouble();
        System.out.println("\t Серед наявних є:");
        try {
            execute(new SelectCompositionsByDurationCommand(editor, d1, d2));
        } catch (StatementDontReturnValueException e) {
            System.out.println(e.getMessage());
            return;
        } catch (VariableIsNull | ZeroRowChangedException ignored) {
        }
//        if(super.assemblageNames == null) {
//            System.out.println("Збірки у базі даних відсутні! Спочатку додайте збірку!");
//            return;
//        }

        for (Composition composition : super.compositions)
            System.out.println(composition);
    }

    private void printMenu() {
        System.out.println(" Оберіть пункт з меню:");
        System.out.println("\t 1.Вивести музичні композиції.");
//        System.out.println("\t 2.Вивести інформацію про музичні збірки.");
        System.out.println("\t 2.Додати збірку музичних композицій до диску.");
        System.out.println("\t 3.Додати музичну композицію.");
        System.out.println("\t 4.Видалити музичну композицію.");
        System.out.println("\t 5.Видалити збірку музични композицій.");
    }

    private void printAllAssemblageNames() throws StatementDontReturnValueException {
        System.out.println("\t Усі наявні збірки:");
        try {
            execute(new SelectAssemblageNamesCommand(editor));
        } catch (VariableIsNull | ZeroRowChangedException ignored){}
        for (String el : super.assemblageNames)
            System.out.println(el);
    }

    private void printMusicInfo() {

        System.out.println("Введіть назву збірки: ");
        try {
            printAllAssemblageNames();
        } catch (StatementDontReturnValueException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(" > ");
        String assemblageName = scanner.nextLine();
        do {
            try {
                execute(new SelectAssemblageCommand(editor, assemblageName));
                break;
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
            } catch (VariableIsNull e) {
                System.out.println(e.getMessage());
            } catch (ZeroRowChangedException ignored) {
            }
            System.out.println("Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
        } while (assemblageName.trim().equals("\n"));
        for (Composition el : super.compositions)
            System.out.println(el);
    }
    private void printMusicByName(String musicName) {

    }
    private void printCollectionByName(String collectionName) {

    }
    private void printMusicByGenre(String genre) {

    }







    private void addCollectionToDisk() {
        List<Composition> collectionOfComposition = null;

        System.out.println( "Введіть назву збірки:");
        System.out.print(" > ");
        String assemblageName = scanner.nextLine();
        if(RecordingStudio.isCollectionExist(assemblageName)){
            System.out.println("Колекція з таким іменем вже існує!");
            return;
        }

        do {
            try {
                execute(new InsertCompositionCommand(editor, createMusic(assemblageName)));
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
            } catch (VariableIsNull e) {
                System.out.println("Спробуйте ще раз заповнити композицю. " + e.getMessage());
            } catch (ZeroRowChangedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(" Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
        } while (assemblageName.trim().equals("\n"));
    }

    private Composition createMusic(String assemblageName) {
        Composition composition = new Composition();
        LinkedList<String> genres = new LinkedList<>();

        System.out.println(" Введіть назву композиції, яку потрібно додати:");
        System.out.print(" > ");
        composition.setName(scanner.nextLine());
        System.out.println(" Введіть жанри композиції(для завершення введіть пустий рядок):");
        System.out.print(" > ");
        String genre = scanner.nextLine().trim();
        while (!genre.equals("\n")) {
            genres.add(genre);
            System.out.print(" > ");
            genre = scanner.nextLine().trim();
        }
        composition.setGenres(genres);
        System.out.println(" Введіть тривалість композиції:");
        System.out.print(" > ");
        composition.setDuration(checkUnsignedDouble());
        System.out.println(" Введіть виконавця композиції:");
        System.out.print(" > ");
        composition.setPerformer(scanner.nextLine());
        composition.setAssemblageName(assemblageName);

        return composition;
    }



    private void addMusicToCollection() {
//        int numOfAssemblageNames = 0, currentNumOfAssemblageNames = 0;
//        String currentAssemblageName = null;

//        try {
//            execute(new SelectAssemblageNamesCommand(editor));
//        } catch (StatementDontReturnValueException e) {
//            System.out.println(e.getMessage());
//        } catch (VariableIsNull | ZeroRowChangedException ignored) {}
//
//        System.out.println("\t Усі наявні збірки: ");
//        for (String assemblageName : super.assemblageNames) {
//            numOfAssemblageNames++;
//            System.out.println(numOfAssemblageNames + ". " + assemblageName);
//        }


//        while(true) {
//            System.out.println(" Введіть номер збірки: ");
//            System.out.print(" > ");
//            currentNumOfAssemblageNames = checkUnsignedInt();
//            if(currentNumOfAssemblageNames > 0 && currentNumOfAssemblageNames <= numOfAssemblageNames)
//                break;
//            System.out.println(" Введений некоректний номер! Повторіть спробу!");
//        }
        System.out.println("Введіть назву збірки: ");
        try {
            printAllAssemblageNames();
        } catch (StatementDontReturnValueException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(" > ");
        String assemblageName = scanner.nextLine();

//        currentAssemblageName = super.assemblageNames.get(currentNumOfAssemblageNames-1);
        do {
            try {
//                execute(new InsertCompositionCommand(editor, createMusic(currentAssemblageName)));
                execute(new InsertCompositionCommand(editor, createMusic(assemblageName)));
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
            } catch (VariableIsNull e) {
                System.out.println(e.getMessage());
            } catch (ZeroRowChangedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(" Введіть пустий рядок, щоб завершити:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
        } while (assemblageName.trim().equals("\n"));

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

    private void printCompositionsByGenre() {
        System.out.println("Введіть назву жанру, за яким потрібно вивести композиції: ");
        System.out.print(" > ");
        String genreName = scanner.nextLine();
        genreName = genreName.trim();
//        System.out.println("\t Серед наявних є:");
//        try {
//            execute(new SelectCompositionsByDurationCommand(editor, d1, d2));
//        } catch (StatementDontReturnValueException e) {
//            System.out.println(e.getMessage());
//            return;
//        } catch (VariableIsNull | ZeroRowChangedException ignored) {
//        }


        do {
            try {
                execute(new SelectCompositionByGenreNameCommand(editor, genreName));
                break;
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
            } catch (VariableIsNull e) {
                System.out.println(e.getMessage());
            } catch (ZeroRowChangedException ignored) {
            }
            System.out.println("Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            genreName = scanner.nextLine();
        } while (genreName.trim().equals("\n"));
        for (Composition el : super.compositions)
            System.out.println(el);
    }


//    private static void updateComposition() {
//        int id = 0;
//        while(id == 0) {
//            System.out.println("Введіть id композиції(0 - вивести композиції):");
//            System.out.print(" > ");
//            id = checkUnsignedInt();
//            if (id == 0)
//                printMusicInfo();
//        }
//        if(!execute(new SelectByIdCommand(id))){
//            System.out.println(" Даної композиції не існує!");
//            return;
//        }
//        Composition composition = createMusic(assemblageNames);
//        (new UpdateByIdCommand(id, composition)).execute();
//    }







    private void deleteMusicFromCollection() {
//        System.out.println("Оновлення ще не завезли!");

        System.out.println("\t Введіть назву збірки з якої потрібно видалити композицію:");
        try {
            printAllAssemblageNames();
        } catch (StatementDontReturnValueException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.print(" > ");
        String assemblageName = scanner.nextLine();
        do {
            try {
                execute(new SelectAssemblageCommand(editor, assemblageName));
                break;
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
            } catch (VariableIsNull e) {
                System.out.println(e.getMessage());
            } catch (ZeroRowChangedException ignored) {
            }
            System.out.println("Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
        } while (assemblageName.trim().equals(""));
        for (Composition el : super.compositions)
            System.out.println(el.getName());

        System.out.println("\t Введіть назву композиції, яку потрібно видалити:");
        System.out.print(" > ");
        String compositionName = scanner.nextLine();
        Composition composition = null;
        do {
            compositionName = compositionName.trim();
            for (Composition el : super.compositions) {
                if (compositionName.equals(el)) {
                    composition = el;
                    break;
                }
            }
            if(composition != null)
                break;
            System.out.println("Введена назва композиції є хибна!");
            System.out.println("Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            compositionName = scanner.nextLine();
        } while(compositionName.equals(""));

        try {
            execute(new DeleteCompositionByIdCommand(editor, composition.getId()));
        } catch (StatementDontReturnValueException ignored) {
        } catch (VariableIsNull e) {
            System.out.println(e.getMessage());
        } catch (ZeroRowChangedException e) {
            System.out.println(e.getMessage());
        }
    }
    private void deleteAssemblage() {
        String assemblageName = null;
        do {
            System.out.println("\t Введіть назву збірки, яку потрібно видалити:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();

            try {
                execute(new DeleteAssemblageCommand(editor, assemblageName));
                break;
            } catch (StatementDontReturnValueException ignored) {
            } catch (VariableIsNull | ZeroRowChangedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
        } while(assemblageName.equals(""));

    }




    private double checkUnsignedDouble(){
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
    private int checkUnsignedInt(){
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
