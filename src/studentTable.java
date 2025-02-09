
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class studentTable {
    JTable performanceTable;
    JTextArea subHead1 = new JTextArea();
    ArrayList<String[]> tableValuesList = new ArrayList<>();
    int topBoundary, lowBoundary;
    public studentTable() {
    }
    public void setBoundary(List<List<String>> stuData, int[] selectedModulesIndex, int modulesCount) {
        //calculates boundaries considering one or multiple modules
        tableValuesList.clear();
        int highestAverage = 0, lowestAverage = 100;
        int overallAverageScore = 0;
        for (int i = 1; i < stuData.size() - 1; i++) {
            boolean valid = true;
            int x;
            int total = 0;
            for (int y : selectedModulesIndex) {
                x = Integer.parseInt(stuData.get(i).get(y + 2));
                if (x == -1) {
                    valid = false;
                    break;
                } else {
                    total += x;
                }
            }
            if (valid) {
                total = total / modulesCount;
                if (total > highestAverage) { //stores highest average student mark across selected modules
                    highestAverage = total;
                }
                if (total < lowestAverage) { //stores lowest average student mark across selected modules
                    lowestAverage = total;
                }
                overallAverageScore += total;
                //also adds checked student to the table value list to be used during table creation
                String[] student = {stuData.get(i).get(0), stuData.get(i).get(1), String.valueOf(total)};
                tableValuesList.add(student);
            }
        }
        //calculates overall average score of all students
        if (tableValuesList.size() != 0) {
            overallAverageScore = overallAverageScore / tableValuesList.size();
        }

        topBoundary = (int) (((highestAverage - overallAverageScore) / 2) + overallAverageScore); //top performers boundary relative to average student performance
        lowBoundary = (int) (((overallAverageScore - lowestAverage))); //low performers boundary relative to average student performance
        if (topBoundary > 100) { //boundary limits
            topBoundary = 100;
        }
        if (lowBoundary < 0) {
            lowBoundary = 0;
        }
    }
    public void createTableStudentOnly(List<List<String>> stuData, int stuID, String[] modules) {
        int x;
        String[] header = {"Module", "Mark", "Comments"};
        String[][] record = new String[stuData.get(0).size() - 2][3];
        //iterate through every student
        for (int i = 1; i < stuData.size() - 1; i++) {
            //selects student id and compares against inputted id
            x = Integer.parseInt(stuData.get(i).get(0));
            if (stuID == x) {
                //if valid, iterate through every module
                for (int y = 2; y < stuData.get(0).size(); y++) {
                    //stores mark achieved in module
                    x = Integer.parseInt(stuData.get(i).get(y));
                    if (x != -1) { //if module was taken
                        int[] index = {y-2}; //store the index of the module relative to the index of the GUI module list
                        setBoundary(stuData, index, 1); //gets boundary data for module
                        String performer;
                        //calculates performance level based on boundary
                        if (Integer.parseInt(stuData.get(i).get(y)) >= topBoundary) {
                            performer = "Top Performer";
                        } else if (Integer.parseInt(stuData.get(i).get(y)) <= lowBoundary) {
                            performer = "Low Performer";
                        } else {
                            performer = "Average Performer";
                        }
                        //adds the module, mark and performance level of student to the table
                        record[y-2][0] = modules[y-2];
                        record[y-2][1] = stuData.get(i).get(y);
                        record[y-2][2] = performer;
                    }
                }
            }
        }
        //creates a table object out of dataset
        performanceTable = new JTable(record, header);
        performanceTable.setBackground(Color.white);
        performanceTable.setOpaque(true);
        subHead1.setText(stuID + " Performance Per Module");
        subHead1.setEditable(false);
        subHead1.setFont(new Font("Monospaced", Font.BOLD, 20));
        subHead1.setForeground(Color.black);
        subHead1.setOpaque(false);
    }
    public void createTable(List<List<String>> stuData, int[] selectedModulesIndex, List selectedModulesNames) {
        String[] header = {"Student ID", "Course", "Average Mark", "Comments"};

        //apply boundary to mark values and assign them to a new categorical arraylist to be converted to a string array
        ArrayList<String[]> averageValuesList = new ArrayList<>();
        ArrayList<String[]> topValuesList = new ArrayList<>();
        ArrayList<String[]> lowValuesList = new ArrayList<>();

        setBoundary(stuData, selectedModulesIndex, selectedModulesIndex.length);
        for (int i = 0; i < tableValuesList.size(); i++) {
            if (Integer.parseInt(tableValuesList.get(i)[2]) >= topBoundary) {
                topValuesList.add(tableValuesList.get(i));
            } else if (Integer.parseInt(tableValuesList.get(i)[2]) <= lowBoundary) {
                lowValuesList.add(tableValuesList.get(i));
            } else {
                averageValuesList.add(tableValuesList.get(i));
            }
        }

        String[][] record = new String[(averageValuesList.size() + lowValuesList.size() + topValuesList.size())][4];
        for (int i = 0; i < topValuesList.size(); i++) {
            record[i][0] = topValuesList.get(i)[0];
            record[i][1] = topValuesList.get(i)[1];
            record[i][2] = topValuesList.get(i)[2];
            record[i][3] = "Top Performer";
        }
        for (int i = topValuesList.size(); i < (averageValuesList.size() + topValuesList.size()); i++) {
            record[i][0] = averageValuesList.get(i - topValuesList.size())[0];
            record[i][1] = averageValuesList.get(i - topValuesList.size())[1];
            record[i][2] = averageValuesList.get(i - topValuesList.size())[2];
            record[i][3] = "Average Performer";
        }
        for (int i = (averageValuesList.size() + topValuesList.size()); i < (averageValuesList.size() + topValuesList.size() + lowValuesList.size()); i++) {
            record[i][0] = lowValuesList.get(i - (averageValuesList.size() + topValuesList.size()))[0];
            record[i][1] = lowValuesList.get(i - (averageValuesList.size() + topValuesList.size()))[1];
            record[i][2] = lowValuesList.get(i - (averageValuesList.size() + topValuesList.size()))[2];
            record[i][3] = "Low Performer";
        }
        averageValuesList.clear();
        topValuesList.clear();
        lowValuesList.clear();
        Arrays.sort(record, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                Double d1 = Double.valueOf(o1[2]);
                Double d2 = Double.valueOf(o2[2]);
                return d2.compareTo(d1);
            }
        });
        performanceTable = new JTable(record, header);
        performanceTable.setBackground(Color.white);
        performanceTable.setOpaque(true);
        subHead1.setText("Student Performance     Low Performers >" + lowBoundary + "     Top Performers <" + topBoundary);
        subHead1.setEditable(false);
        subHead1.setFont(new Font("Monospaced", Font.BOLD, 20));
        subHead1.setForeground(Color.black);
        subHead1.setOpaque(false);
    }
}
