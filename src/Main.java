import Commands.SelectAssemblageCommand;
import Commands.SelectByIdCommand;
import Model.Compositions.Composition;
import dao.Recording.RecordingStudio;

import java.awt.*;
import java.util.Scanner;
import Menu.Menu;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
//        System.out.println("============================ Перша ітерація ============================");
//        Composition composition = new Composition(1,"Stairway to Heaven", 8.3, "Хардрок", "Led Zeppelin IV");
//        RecordingStudio recordingStudio = new RecordingStudio();
//        recordingStudio.test1(composition);
//        List<Composition> assemblage = recordingStudio.test2("Led Zeppelin IV");
//        for (Composition el : assemblage)
//            System.out.println(el);
//
//        System.out.println("============================ Друга ітерація ============================");
//        composition.setDuration(483);
//        recordingStudio.test3(1, composition);
//        assemblage = recordingStudio.test2("Led Zeppelin IV");
//        for (Composition el : assemblage)
//            System.out.println(el);

//        System.out.println("============================ Третя ітерація ============================");
//        composition.setId(2);
//        composition.setName("Black Dog");
//        composition.setDuration(296);
//        composition.setGenre("Хардрок");
//        composition.setAssemblageName("Led Zeppelin IV");
//        recordingStudio.test1(composition);
//
//        recordingStudio.test4("Led Zeppelin IV")
//
//        assemblage = recordingStudio.test2("Led Zeppelin IV");
//        Composition composition = new Composition(3,"Going to California", 212, "Хардрок", "Led Zeppelin IV");
//        recordingStudio.test1(composition);
//        assemblage = recordingStudio.test2("Led Zeppelin IV");
//        for (Composition el : assemblage)
//            System.out.println(el);




//        UIManager.put("Button.font", JPanelEx.FONT);
//        UIManager.put("Label.font", JPanelEx.FONT);
//        JFrame.setDefaultLookAndFeelDecorated(false);
//        JDialog.setDefaultLookAndFeelDecorated(false);
//        new JPanelEx();



        RecordingStudio recordingStudio = new RecordingStudio();

        recordingStudio.test0();

//        System.out.println(" Введіть команду 'Command':");
//        System.out.println(" > ");
//        while(!scanner.next().equals("Command")) {
//            System.out.println(" Введена неправильна команда!");
//            System.out.println(" > ");
//        }
//        Menu.startMenu2();
    }
}
