package View;

import Commands.SelectAssemblageCommand;
import Commands.SelectByIdCommand;
import Model.Compositions.Composition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;

public class View extends JFrame { //implements ActionListener {
    public static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
    public static final Point STARTPOINT = new Point(400, 220);

    public View() {
        // ------------------------------ Створення вікна методом наслідування ------------------------------ //
        super("Figure title");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        Figure figure1 = new Figure(Color.green, 0, "Figure 1");
//        Figure figure2 = new Figure(Color.CYAN, 1, "Figure 2");
//        Figure figure3 = new Figure(Color.red, 0, "Figure 3");

        // ------------------------------ Додаємо фігури в багатослойні вікна ------------------------------ //
//        JLayeredPane lp = getLayeredPane();

//        figure1.setBounds(40, 25, 90,90);
//        figure2.setBounds(60, 120, 230,180);
//        figure3.setBounds(90, 70, 90,90);
//
//        lp.add(figure1, JLayeredPane.PALETTE_LAYER);
//        lp.add(figure2, JLayeredPane.POPUP_LAYER);
//        lp.add(figure3, JLayeredPane.POPUP_LAYER);

//        lp.setPosition(figure3, 0); // ??? Зміна позиції одної з фігур. Робить дану фігуру першою в слої.

//        JPanel contents = new JPanel();
        JPanel contents = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
//                g.setColor(Color.BLUE);
//                g.fillRect(0,0,100,100);
            }
        };
        // ----------------------------------------- Додаємо текст ---------------------------------------- //
//        contents.add(new MyComponent());


        DefaultListModel<String> listModel = new DefaultListModel();
        // ----------------------------------------- Додаємо кнопки ---------------------------------------- //
        final int[] actionIndex = {0};
        final java.util.List<Composition>[] listOfAssemblage = new List[]{new LinkedList<>()};
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(String.valueOf(SelectByIdCommand.class).equals(e.getActionCommand())) {
                    SelectByIdCommand sBIdC = new SelectByIdCommand(1);
                    sBIdC.execute();
//                    JLabel label = new JLabel(sBIdC.getValue().toString());

                    Font font = new Font("Bitstream Charter", Font.BOLD, 14);
                    Graphics g = contents.getGraphics();
                    g.setFont(font);
//                    g.drawString(sBIdC.getValue().toString(), 20, 60);


                    JLabel label = new JLabel();
                    label.setFont(font);
                    label.setText (sBIdC.getValue().toString());




//                    label.paint(g);

                    getContentPane().add(label);
                }
                if(String.valueOf(SelectAssemblageCommand.class).equals(e.getActionCommand())) {
                    SelectAssemblageCommand sAC = new SelectAssemblageCommand("Led Zeppelin IV");
                    sAC.execute();
                    listOfAssemblage[0] = sAC.getAssemblage();
                    JLabel label = new JLabel();

                    Font font = new Font("Bitstream Charter", Font.BOLD, 14);
                    Graphics g = contents.getGraphics();
                    g.setFont(font);
                    for(int i = 0; i < sAC.getAssemblage().size(); i++) {
                        g.drawString(listOfAssemblage[0].get(i).toString(), 20, 60 + 30 * i);
                        listModel.addElement(listOfAssemblage[0].get(i).toString());
                    }

                    label.paint(g);
                    getContentPane().add(label);
                }
            }
        };


        JButton bSBIdC = new JButton("SelectByIdCommand");
//        button.setVerticalTextPosition(AbstractButton.CENTER);
//        button.setHorizontalTextPosition(AbstractButton.LEADING);
//        button.setBounds(20, 20, "SelectByIdCommand".length()*10, 50);
        Dimension size = bSBIdC.getPreferredSize();
        bSBIdC.setBounds(20, 20, size.width, size.height);
        bSBIdC.setActionCommand(String.valueOf(SelectByIdCommand.class));
        bSBIdC.addActionListener(action);

        int preciousWidth = size.width;
        JButton bAC = new JButton("SelectAssemblageCommand");
//        button.setVerticalTextPosition(AbstractButton.CENTER);
//        button.setHorizontalTextPosition(AbstractButton.LEADING);
//        button.setBounds(20, 20, "SelectByIdCommand".length()*10, 50);
        size = bAC.getPreferredSize();
        bAC.setBounds(20 * 2 + preciousWidth, 20, size.width, size.height);
        bAC.setActionCommand(String.valueOf(SelectAssemblageCommand.class));
        bAC.addActionListener(action);


        contents.setLayout(new FlowLayout());
        contents.add(bSBIdC);
        contents.add(bAC);
//        contents.add(new JButton("ТАК"));
//        contents.add(new JButton("НІ"));
//        // Змінюємо панель вмісту.
        setContentPane(contents);
//        super.add(contents);
//        super.validate();
//        super.repaint();

        MyGlassPane myGlassPane = new MyGlassPane();
        setGlassPane(myGlassPane);

        // ----------------------------------- Використання прозорої панелі JOptionPane ----------------------------------- //

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }

            @Override // При закритті вікна викликається даний метод.
            public void windowClosing(WindowEvent e) { // Функція яка створює вікно при закритті основного вікна.
                Object[] options = {"Так", "Ні!"};
                int rc = JOptionPane.showOptionDialog( // Створюємо вікно.
                        e.getWindow(), "Закрити вікно?", "Підтвердження", // Заголовок, повідомлення.
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, // Створюємо дві кнопки та задаємо тип вікна.
                        null, options, options[0] // null - задає тип іконки, options - встановлює текст в кнопки,
                        // а options[0] вказує, яку кнопку потрібно підкреслити.
                        // WindowEvent e(event) - параметр, який в прозорій панелі  Swing JOptionPane
                        // відкриває діалогове вікно підтвердження.
                );
                if (rc == 0) { // Якщо обрали першу копку(з текстом за індексом 0 у масиві options), то:
                    e.getWindow().setVisible(false);
                    System.exit(0); // Закриваємо вікно.
                }
            }
        });

//        JLabel label = new JLabel("Використання прозорої панелі при закритті вікна");
//        Font font = new Font("Bitstream Charter", Font.BOLD, 20);
//        label.setFont(font);
//        getContentPane().add(label);


        // ----------------------------------- Використання списку JList ----------------------------------- //
//        DefaultListModel<String> listModel = new DefaultListModel();
//        listModel.addElement("Jane Doe");
//        listModel.addElement("John Smith");
//        listModel.addElement("Kathy Green");

        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        getContentPane().add(listScroller);


        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - STARTPOINT.x / 2, STARTPOINT.x, dimension.height / 2 - STARTPOINT.y / 2, STARTPOINT.y); // Виставляємо вікно по середині екрану.
        setLocationRelativeTo(null); // ???
//        setSize(400, 220);
        setVisible(true);

        UIManager.put("Button.font", View.FONT);
        UIManager.put("Label.font", View.FONT);
        JFrame.setDefaultLookAndFeelDecorated(false);
        JDialog.setDefaultLookAndFeelDecorated(false);
    }


    public void addWindowListenerForSelectAssemblageCommand() {
//        WindowEvent windowEvent = new WindowEvent();
//        WindowListener windowListener = super.getWindowListeners();
//        windowListener.getE
//        super.

        JFrame jFrame = new JFrame();
////        jFrame.
//        Object[] options = {"Так", "Ні!"};
//        int rc = JOptionPane.showOptionDialog( // Створюємо вікно.
//                e.getWindow(), "Закрити вікно?", "Підтвердження", // Заголовок, повідомлення.
//                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, // Створюємо дві кнопки та задаємо тип вікна.
//                null, options, options[0] );
//        if (rc == 0) { // Якщо обрали першу копку(з текстом за індексом 0 у масиві options), то:
//            e.getWindow().setVisible(false);
//            System.exit(0); // Закриваємо вікно.
//        }
//        WindowL
        jFrame.setVisible(true);
        addWindowListener((WindowListener) jFrame);

    }


//    @Override
//    public void actionPerformed(ActionEvent e) {
////    Action action = new AbstractAction() {
//        int actionIndex = 0;
//        List<Composition> listOfAssemblage = new LinkedList<>();
//        String command = e.getActionCommand();
//        DefaultListModel<String> listModel = new DefaultListModel();
//
//        if (String.valueOf(SelectByIdCommand.class).equals(command)) {
//            SelectByIdCommand sBIdC = new SelectByIdCommand(1);
//            sBIdC.execute();
////                    JLabel label = new JLabel(sBIdC.getValue().toString());
//
//            Font font = new Font("Bitstream Charter", Font.BOLD, 14);
//            Graphics g = contents.getGraphics();
//            g.setFont(font);
////                    g.drawString(sBIdC.getValue().toString(), 20, 60);
//
//
//            JLabel label = new JLabel();
//            label.setFont(font);
//            label.setText(sBIdC.getValue().toString());
//
//
////                    label.paint(g);
//
//            getContentPane().add(label);
//        }
//        if (String.valueOf(SelectAssemblageCommand.class).
//
//                equals(e.getActionCommand())) {
//            SelectAssemblageCommand sAC = new SelectAssemblageCommand("Led Zeppelin IV");
//            sAC.execute();
//            listOfAssemblage = sAC.getAssemblage();
//            JLabel label = new JLabel();
//
//            Font font = new Font("Bitstream Charter", Font.BOLD, 14);
//            Graphics g = contents.getGraphics();
//            g.setFont(font);
//            for (int i = 0; i < sAC.getAssemblage().size(); i++) {
//                g.drawString(listOfAssemblage.get(i).toString(), 20, 60 + 30 * i);
//                listModel.addElement(listOfAssemblage.get(i).toString());
//            }
//
//            label.paint(g);
//            getContentPane().add(label);
//        }
//    }



    class MyGlassPane extends JComponent
            implements ItemListener {
        //React to change button clicks.
        public void itemStateChanged(ItemEvent e) {
            setVisible(e.getStateChange() == ItemEvent.SELECTED);
        }
    }








    class MyComponent extends  JComponent {
        @Override
        protected  void paintComponent(Graphics g) {
            Font font = new Font("Bitstream Charter", Font.BOLD, 20);
            Graphics2D g2 = (Graphics2D)g;
            g2.setFont(font);
            g2.drawString("Студія звукозапису", 50, 50);
        }
    }

























}