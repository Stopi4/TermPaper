package com.University.TempPaper;

import com.University.TempPaper.Controllers.Menu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    double x,y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com.University.TempPaper/HomeWindowL.fxml"));

        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Home Window");

        root.setOnMousePressed(evt -> {
            x = evt.getSceneX();
            y = evt.getSceneY();
        });
        root.setOnMouseDragged(evt -> {
            stage.setX(evt.getScreenX() - x);
            stage.setY(evt.getScreenY() - y);
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
//        Menu menu = new Menu();
//        menu.startMenu();
    }
}
//public class com.University.TempPaper.Main {
//    private static Scanner scanner = new Scanner(System.in);
//    public static void main(String[] args) {

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



//        RecordingStudio recordingStudio = new RecordingStudio();
//
//        List<Composition> assemblage = recordingStudio.test2("Led Zeppelin IV");
//        for (Composition el : assemblage)
//            System.out.println(el);

//        System.out.println(" Введіть команду 'Command':");
//        System.out.println(" > ");
//        while(!scanner.next().equals("Command")) {
//            System.out.println(" Введена неправильна команда!");
//            System.out.println(" > ");
//        }


//        com.University.TempPaper.Menu menu = new com.University.TempPaper.Menu();
//        menu.startMenu2();
//    }
//}