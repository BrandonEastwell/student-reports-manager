import com.itextpdf.text.Document;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUI extends JFrame {
    JFrame frame;
    JButton searchStudent;
    JPanel mainPanel;
    JList modulesList = new JList<>();
    JScrollPane dataScroller;
    JMenu file;
    JMenuItem quit;
    JPanel buttonPanel;
    JMenuItem F1;
    JMenuItem F2;
    JTextField student;
    
    public GUI() {
        //initializing all swing components and frame
        frame = new JFrame("PERFORMANCE REPORT");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, Toolkit.getDefaultToolkit().getScreenSize().height - 100);
        frame.setLocationRelativeTo(null);
        searchStudent = new JButton("SEARCH");//button
        student = new JTextField("search by student id");
        student.setForeground(Color.gray);
        student.setPreferredSize(new Dimension(200, 35));
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        file = new JMenu("File");
        menuBar.add(file);
        quit = new JMenuItem("Exit");
        F1 = new JMenuItem("File Open");
        F2 = new JMenuItem("Save as PDF");
        file.add(F1);
        file.add(F2);
        file.add(quit);

        //setting headers and subheadings
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS));
        headingPanel.setOpaque(false);
        JTextArea heading = new JTextArea("PERFORMANCE REPORT");
        heading.setEditable(false);
        heading.setFont(new Font("BLACK", Font.BOLD, 25));
        heading.setForeground(Color.black);
        heading.setOpaque(false);
        JTextArea subHeading = new JTextArea("Students Detailed View");
        subHeading.setEditable(false);
        subHeading.setFont(new Font("LIGHT_GRAY", Font.BOLD, 15));
        subHeading.setForeground(Color.darkGray);
        subHeading.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(subHeading);

        buttonPanel = new JPanel();
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setOpaque(false);

        //creating the menu panel to manage the layout of buttons, and main panel to display data
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuPanel.add(headingPanel);
        menuPanel.add(Box.createHorizontalStrut(75));
        menuPanel.add(buttonPanel);
        menuPanel.setBackground(Color.white);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);
        dataScroller = new JScrollPane(mainPanel);
        dataScroller.setVisible(false);
        dataScroller.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        dataScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        dataScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        dataScroller.setViewportView(mainPanel);
        dataScroller.setBackground(Color.decode("#D3D3D3"));
        dataScroller.getVerticalScrollBar().setUnitIncrement(30);

        quit.addActionListener(new ActionListener() {
            //exits and closes the program on click
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == quit) {
                    frame.setVisible(false);
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        student.addFocusListener(new FocusListener() {
            //handles the placeholder text in the text field
            @Override
            public void focusGained(FocusEvent e) {
                if (student.getText().equals("search by student id")) {
                    student.setText("");
                    student.setForeground(Color.black);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (student.getText().isEmpty()) {
                    student.setText("search by student id");
                    student.setForeground(Color.gray);
                }
            }
        });
        //Adding Components to the frame.

        frame.getContentPane().add(BorderLayout.NORTH, menuPanel);
        frame.getContentPane().add(BorderLayout.CENTER, dataScroller);
    }
    public void getModuleList(List<List<String>> stuData) {
        //adds the module options to the list from the data file and adds the list component to the button panel
        modulesList = new JList<>();
        if (stuData.size() != 0) { //iterate through file formatting
            String[] modules = new String[stuData.get(0).size() - 2];
            for (int i = 0; i < modules.length - 2; i++) {
                modules[i] = stuData.get(0).get(i + 2);
            }
            JLabel label = new JLabel("MODULE");//text field
            buttonPanel.add(label);
            modulesList = new JList<>(modules);
            modulesList.setVisibleRowCount(6);
            JScrollPane listSP = new JScrollPane(modulesList);
            modulesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //for multiple module selections
            buttonPanel.add(listSP);
            buttonPanel.add(student);
            buttonPanel.add(searchStudent);
            frame.validate();
        }
    }
    public void titlePage() {
        //title page to be added to the top of the report panel before calling data
        JPanel title = new JPanel();
        title.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextArea header = new JTextArea("STUDENT RESULTS STATISTICS");
        header.setEditable(false);
        header.setFont(new Font("Monospaced", Font.BOLD, 22));
        header.setForeground(Color.white);
        header.setOpaque(false);
        title.setBackground(Color.decode("#004466"));
        title.add(header);
        mainPanel.add(title);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //instantiate all classes, create objects
        configReader config = new configReader();
        GUI gui = new GUI();
        gui.frame.setVisible(true);
        CSVRead stuData = new CSVRead();
        //read the data from the stored data file in prop.config
        try {
            stuData.readfile(new File(config.getDataFile()));
            gui.getModuleList(stuData.getStuData());
        } catch (Exception e) { //if data path in prop.config doesn't exist
            JOptionPane.showMessageDialog(null, "Wrong or no file path, please upload a txt or csv file", "REPORT SYSTEM" , JOptionPane.ERROR_MESSAGE);
        }
        //GUI data objects
        final studentTable studentTable = new studentTable();
        final histogramGraph studentHistogram = new histogramGraph();
        final moduleTable moduleTable = new moduleTable();
        final scatterplotGraph scatterplot = new scatterplotGraph();
        gui.searchStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //validates the entered student in student id text field
                String stuID = gui.student.getText();
                try {
                    Integer.parseInt(stuID);
                    if (stuID.length() == 7) {
                        String[] modules = new String[gui.modulesList.getModel().getSize()]; //obtains all selectable modules from the list
                        for (int i = 0; i < gui.modulesList.getModel().getSize(); i++) {
                            modules[i] = String.valueOf(gui.modulesList.getModel().getElementAt(i));
                        }
                        gui.mainPanel.removeAll();
                        gui.dataScroller.setVisible(true);
                        gui.titlePage();
                        //calls method to process the entered student IDs data into a table
                        studentTable.createTableStudentOnly(stuData.getStuData(), Integer.parseInt(stuID), modules);
                        //display the table
                        gui.mainPanel.add(BorderLayout.CENTER, studentTable.subHead1);
                        gui.mainPanel.add(BorderLayout.CENTER, new JScrollPane(studentTable.performanceTable));
                        gui.mainPanel.repaint();
                        gui.frame.validate();
                    } else {
                        JOptionPane.showMessageDialog(null, "Student ID not found", "REPORT SYSTEM", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Student ID must be a number", "REPORT SYSTEM", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //adds selection listener to lists if not null (no data from data file is obtainable)
        if (gui.modulesList.getModel().getSize() != 0) {
            gui.modulesList.addListSelectionListener(new ListSelectionListener() {
                //when a user clicks an option in the list
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    //dynamically updates displayed data by calling methods on every selection
                    gui.mainPanel.removeAll();
                    gui.dataScroller.setVisible(true);
                    gui.titlePage();
                    studentTable.createTable(stuData.getStuData(), gui.modulesList.getSelectedIndices(), gui.modulesList.getSelectedValuesList());
                    studentHistogram.createHistogram(stuData.getStuData(), gui.modulesList.getSelectedIndices(), gui.modulesList.getSelectedValuesList());
                    moduleTable.createTable(stuData.getStuData(), gui.modulesList.getSelectedIndices(), gui.modulesList.getSelectedValuesList());
                    scatterplot.createScatterplot(stuData.getStuData(), gui.modulesList.getSelectedIndices(), gui.modulesList.getSelectedValuesList());
                    //display data to the main panel
                    gui.mainPanel.add(BorderLayout.CENTER, studentTable.subHead1);
                    gui.mainPanel.add(BorderLayout.CENTER, new JScrollPane(studentTable.performanceTable));
                    gui.mainPanel.add(BorderLayout.CENTER, moduleTable.subHead1);
                    gui.mainPanel.add(BorderLayout.CENTER, new JScrollPane(moduleTable.moduleTable));
                    gui.mainPanel.add(BorderLayout.CENTER, studentHistogram.CP);
                    gui.mainPanel.add(BorderLayout.CENTER, scatterplot.CP);
                    if (studentTable.performanceTable.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "0 students found in all selected modules", "REPORT SYSTEM", JOptionPane.WARNING_MESSAGE);
                    }
                    gui.mainPanel.repaint();
                    gui.frame.validate();
                }
            });
        }
        gui.F1.addActionListener(new ActionListener() {
            //upload a data file to the system
            @Override
            public void actionPerformed(ActionEvent e) {
                //file chooser to navigate window explorer
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                FileNameExtensionFilter filter = new FileNameExtensionFilter("plain text files", "csv", "txt");
                //filters only text based files
                chooser.setFileFilter(filter);
                int r = chooser.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    //writes selected file to the config reader to be stored in the prop.config file
                    String datafile = String.valueOf(chooser.getSelectedFile());
                    configReader write = new configReader();
                    try {
                        write.writeDataFile(datafile);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        stuData.readfile(new File(datafile));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    gui.getModuleList(stuData.getStuData());
                    gui.frame.validate();
                }
            }
        });
        gui.F2.addActionListener(new ActionListener() {
            //Export to PDF option
            @Override
            public void actionPerformed(ActionEvent e) {
                //file chooser to navigate and create a new PDF file in window explorer
                JFileChooser chooser= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Portable Document Format", "pdf");
                //filters only PDF files
                chooser.setFileFilter(filter);
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile(); //gets selected file path
                    Document document = new Document(); //creates new document object to export to
                    try {
                        //createPDF object handles exporting all data components to the PDF file
                        createPDF writer = new createPDF(document, file);
                        writer.pdfTable(studentTable.performanceTable, "Student Table");
                        writer.pdfTable(moduleTable.moduleTable, "Module Table");
                        writer.pdfGraph(studentHistogram.histogram, "Histogram");
                        writer.pdfGraph(scatterplot.scatterPlot, "Scatter Plot");
                        JOptionPane.showMessageDialog(null, "Report successfully saved to " + file.getAbsolutePath() + " .pdf", "REPORT SYSTEM", JOptionPane.PLAIN_MESSAGE);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    finally {
                        if (document.isOpen()) {
                            document.close();
                        }
                    }
                }
            }
        });
    }
}
