package com.University.TempPaper.Controllers;

import com.University.TempPaper.Commands.*;
import com.University.TempPaper.Exceptions.VariableIsNull;
import com.University.TempPaper.Exceptions.*;
import com.University.TempPaper.Model.Composition;
import com.University.TempPaper.dao.RecordingStudio;

import java.util.LinkedList;
import java.util.Scanner;

public class Menu extends Editor {
    private Editor editor;
    private Scanner scanner = new Scanner(System.in);
    private LinkedList<String> listOfCommand = new LinkedList<>();

    {
        listOfCommand.add("SelectAssemblage");
        listOfCommand.add("InsertAssemblageName");
        listOfCommand.add("InsertComposition");
        listOfCommand.add("SelectCompositionsByDuration");
        listOfCommand.add("SelectCompositionByGenreName");
        listOfCommand.add("SelectAssemblageName");
        listOfCommand.add("UpdateComposition");
        listOfCommand.add("DeleteCompositionByIdFromAssemblage");
        listOfCommand.add("DeleteAssemblage");
        listOfCommand.add("Exit");
    }
    private boolean execute(Command command) throws StatementDontReturnValueException, VariableIsNull, ZeroRowChangedException {
        return command.execute();
    }

    public void startMenu() {
        new RecordingStudio();

//        try {
//            execute(new DeleteCompositionGenreCommand(editor, "Хардрок", 120));
//        } catch (StatementDontReturnValueException e) {
//            throw new RuntimeException(e);
//        } catch (VariableIsNull e) {
//            throw new RuntimeException(e);
//        } catch (ZeroRowChangedException e) {
//            throw new RuntimeException(e);
//        }

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
                case "InsertAssemblageName":
                    addAssemblageName();
                    break;
                case "InsertComposition":
                    addCompositionsToAssemblage();
                    break;
                case "SelectCompositionsByDuration":
                    printMusicInfoByDuration();
                    break;
                case "SelectCompositionByGenreName":
                    printCompositionsByGenre();
                    break;
                case "UpdateComposition":
                    updateComposition();
                    break;
                case "DeleteCompositionByIdFromAssemblage":
                    deleteCompositionFromAssemblage();
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

    private void addAssemblageName() {
        String assemblageName;

        do {
            System.out.println( "Введіть назву збірки:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
            try {
                execute(new InsertAssemblageNameCommand(editor, assemblageName));
                break;
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
            } catch (VariableIsNull e) {
                System.out.println(e.getMessage());
            } catch (ZeroRowChangedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(" Введіть пустий рядок, щоб завершити спробу:");
            System.out.print(" > ");
            assemblageName = scanner.nextLine();
        } while (assemblageName.trim().equals("\n"));
    }


    private Composition createComposition(String assemblageName) {
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

    private void addCompositionsToAssemblage() {
        String assemblageName;

        do {
            System.out.println("Введіть назву збірки: ");
            try {
                printAllAssemblageNames();
            } catch (StatementDontReturnValueException e) {
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(" > ");
            assemblageName = scanner.nextLine();

            try {
                execute(new InsertCompositionCommand(editor, createComposition(assemblageName)));
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
                execute(new SelectCompositionsByGenreNameCommand(editor, genreName));
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


    private void deleteCompositionFromAssemblage() {
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


    private void updateComposition() {
        System.out.println("\t Введіть назву збірки, у якій потрібно редагувати композицію:");
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
            System.out.println("Введіть нову інформація для композиції:");
            composition = createComposition(composition.getAssemblageName());
            execute(new UpdateCompositionByIdCommand(editor, composition));
        } catch (StatementDontReturnValueException ignored) {
        } catch (VariableIsNull e) {
            System.out.println(e.getMessage());
        } catch (ZeroRowChangedException e) {
            System.out.println(e.getMessage());
        }
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
