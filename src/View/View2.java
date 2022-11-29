package View;

import Commands.SelectAssemblageNamesCommand;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class View2 {
    private static JFrame jFrame;
    private static JPanel jPanel = new JPanel();
    private static final Font FONT = new Font("Verdana", Font.PLAIN, 11);
    private static final int windowInitialWidth = 500;
    private static final int dialogInitialHeight = 400;
    private static Dimension dimension;


    static JFrame getFrame() {
        JFrame jFrame = new JFrame("То є моє вікно!");

//        jFrame.setVisible(true);
//        UIManager.put("Button.font", View2.FONT);
//        UIManager.put("Label.font", View2.FONT);
//        JFrame.setDefaultLookAndFeelDecorated(false);
//        JDialog.setDefaultLookAndFeelDecorated(false);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - windowInitialWidth/2, dimension.height/2 - dialogInitialHeight/2, windowInitialWidth, dialogInitialHeight);
        jFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        return jFrame;
    }



    static {
        jFrame = getFrame();
        jFrame.setResizable(false);

        Container container = jFrame.getContentPane();



//        container.setLayout(new GridLayout(12,5,2,10));



        JButton button = new JButton("Push me!!!");
//        jPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Створюємо діалогове вікно
                Dialog MyDialogForSelectAssemblege = new MyDialogForSelectAssemblege();
                MyDialogForSelectAssemblege.setVisible(true);
            }
        });


//        container.setLayout(new GridLayout(5, 1, 15, 30));
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
//        constraints.weighty = 4;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10,30,10,30);
//        button.setBorder(new EmptyBorder(15,15,15,15));
        container.add(button, constraints);
//        container.add(button);
//        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
//        constraints.gridx = 0;
        constraints.gridy = 1;
        container.add(new JButton("Push2"), constraints);
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        constraints.weightx = 0.5;
//        constraints.gridx = 0;
        constraints.gridy = 2;
        container.add(new JButton("Push3"), constraints);
//        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
//        constraints.gridx = 0;
        constraints.gridy = 3;
        container.add(new JButton("Push4"), constraints);
        JButton buttonExit = new JButton("Exit");
//        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
//        constraints.gridx = 0;
        constraints.gridy = 4;
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
        container.add(buttonExit, constraints);







        jFrame.addWindowListener(new WindowListener() {
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
//        jFrame.add(jPanel);
    }




//        JPanel contents = new JPanel() {
//            @Override
//            public void paintComponent(Graphics g) {
//                super.paintComponent(g);
////                g.setColor(Color.BLUE);
////                g.fillRect(0,0,100,100);
//            }
//        };


//        DefaultListModel<String> listModel = new DefaultListModel();


//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension dimension = toolkit.getScreenSize();
//        setBounds(dimension.width / 2 - STARTPOINT.x / 2, STARTPOINT.x, dimension.height / 2 - STARTPOINT.y / 2, STARTPOINT.y); // Виставляємо вікно по середині екрану.
//        setLocationRelativeTo(null); // ???




    static class MyDialog extends  JDialog {
        final int dialogInitialWidth = 250;
        final int dialogInitialHeight = 400;
        public MyDialog() {
            super(jFrame, "То є дочірнє вікно", true);
            setLayout(null); // Дозволяє встановлювати розташування елементів вручну.
            setResizable(false);
            setBounds(dimension.width / 2 - dialogInitialWidth/2, dimension.height / 2 - dialogInitialHeight/2, dialogInitialWidth, dialogInitialHeight); // Виставляємо вікно по середині екрану.



            DefaultListModel<String> listModel = new DefaultListModel();
            SelectAssemblageNamesCommand selectAssemblageNamesCommand = new SelectAssemblageNamesCommand();
            selectAssemblageNamesCommand.execute();
            List<String> assemblageNames = selectAssemblageNamesCommand.getAssemblageNames();
            for (String an : assemblageNames)
                listModel.addElement(an);

            listModel.addElement("Something1");
            listModel.addElement("Something2");
            listModel.addElement("Something3");
            listModel.addElement("Something4");


            JList<String> list = new JList<>(listModel);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setLayoutOrientation(JList.VERTICAL);
            list.setVisibleRowCount(-1);
            JScrollPane listScroller = new JScrollPane(list);
//            listScroller.setPreferredSize(new Dimension(50, 80));
            listScroller.setBounds(0,0, dialogInitialWidth, 100);
//            setLocationRelativeTo(null); // ???
            add(listScroller);






            JTextField jTextField = new JTextField();


            JTextField jTextField2 = new JTextField();

            jTextField.setPreferredSize(new Dimension(60, 20));
//            jTextField2.setPreferredSize(new Dimension(25, 20));
//            add(jTextField);
//            add(jTextField2, BorderLayout.LINE_END);


            JButton button = new JButton("push me!");
            button.setBounds(dialogInitialWidth/2 - 100/2,dialogInitialHeight - 30*2 - 20,100,30);
//            getContentPane().add(button);
//            add(button, BorderLayout.SOUTH);
            add(button);
        }
    }




    static class MyDialogForSelectAssemblege extends  JDialog {
        final int dialogInitialWidth = 250;
        final int dialogInitialHeight = 400;

        final   static String BUTTONPANEL = "Панель с кнопками"       ;
        final   static String TEXTPANEL   = "Панель с текстовым полем";


        public MyDialogForSelectAssemblege() {
            super(jFrame, "То є дочірнє вікно", true);
//            setLayout(null); // Дозволяє встановлювати розташування елементів вручну.
            setResizable(false);
//            setBounds(dimension.width / 2 - dialogInitialWidth/2, dimension.height / 2 - dialogInitialHeight/2, dialogInitialWidth, dialogInitialHeight); // Виставляємо вікно по середині екрану.

//            setLayout(new GridBagLayout());
//            Container container = super.getContentPane();
//            container.setLayout(new GridBagLayout());
//            GridBagConstraints constraints = new GridBagConstraints();


//            constraints.fill = GridBagConstraints.HORIZONTAL;
//            constraints.insets = new Insets(10,10,10,10);
//            constraints.weightx = 0;
//        constraints.weighty = 4;
//            constraints.gridx = 0;
//            constraints.ipady = 40;
//            constraints.gridy = 1;
//            constraints.gridwidth = 2;

            JPanel cards = new JPanel(new CardLayout());


            JComboBox<String> combobox = new JComboBox<String>(
                    new String[] {BUTTONPANEL, TEXTPANEL});
            combobox.setEditable    (false);

            combobox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    CardLayout layout = (CardLayout)(cards.getLayout());
                    layout.show(cards, (String)e.getItem());
                }
            });

            // Помещаем JComboBox в JPanel для наглядности.
            JPanel cbPanel = new JPanel();
            cbPanel.add(combobox);



            // Создание "cards".
            JPanel card1 = new JPanel();
            card1.add(new JButton("Продукты"));
            card1.add(new JButton("Одежда"  ));
            card1.add(new JButton("Обувь"   ));

            JPanel card2 = new JPanel();
            card2.add(new JTextField("TextField", 15));

            // Создаем панель с менеджером расположения CardLayout
            // Добавляем вкладки
            cards.add(card1, BUTTONPANEL);
            cards.add(card2, TEXTPANEL);

            add(cbPanel, BorderLayout.PAGE_START);
            add(cards  , BorderLayout.CENTER    );
//
//
//            DefaultListModel<String> listModel = new DefaultListModel();
//            SelectAssemblageNamesCommand selectAssemblageNamesCommand = new SelectAssemblageNamesCommand();
//            selectAssemblageNamesCommand.execute();
//            List<String> assemblageNames = selectAssemblageNamesCommand.getAssemblageNames();
//            for (String an : assemblageNames)
//                listModel.addElement(an);
//
//            listModel.addElement("Something1");
//            listModel.addElement("Something2");
//            listModel.addElement("Something3");
//            listModel.addElement("Something4");
//
//
//            JList<String> list = new JList<>(listModel);
//            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//            list.setLayoutOrientation(JList.VERTICAL);
////            list.setVisibleRowCount(-1);
//            JScrollPane listScroller = new JScrollPane(list);
////            listScroller.setPreferredSize(new Dimension(50, 80));
////            listScroller.setBounds(0, 40, dialogInitialWidth, 100);
////            setLocationRelativeTo(null); // ???
////            add(listScroller, constraints);
//
//            JPanel listPanel = new JPanel();
////            listPanel.add(listScroller);
//
//
//
//
//            JPanel jPanelAll = new JPanel();
//
//            JButton button = new JButton("push me!");
//            jPanelAll.add(button);
//            jPanelAll.add(new JButton("asd"));
//            jPanelAll.add(new JButton("asd"));
//
//
//            JLabel jLabel = new JLabel("Name: ");
//            jLabel.setFont(FONT);
//            jPanelAll.add(jLabel);
//
//            JTextField jTextField = new JTextField();
//
//            jPanelAll.add(jTextField);
////            JTextField jTextField2 = new JTextField();
////            jTextField.setPreferredSize(new Dimension(60, 20));
////            add(listPanel, BorderLayout.PAGE_START);
//            add(jPanelAll, BorderLayout.CENTER);


            pack();
        }
    }


    public static JFrame getJFrame1(){
        return jFrame;
    }














}
