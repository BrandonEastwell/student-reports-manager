
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class moduleTable {
    JTable moduleTable;
    String[] header = {"Module", "Difficulty", "Average Student Mark in Module"};

    JTextArea subHead1 = new JTextArea();
    public moduleTable() {

    }
    public void createTable(List<List<String>> stuData, int[] selectedModulesIndex, List selectedModulesNames) {
        ArrayList<String[]> tableValuesList = new ArrayList<>();
        int[] averageMarks = new int[stuData.get(0).size() - 2];
        int highestAverage = 0, lowestAverage = 100;
        int overallAverageScore = 0;
        //iterate through every module
        for (int y = 0; y < stuData.get(0).size() - 2; y++) {
            int count = 0;
            int total = 0;
            for (int i = 1; i < stuData.size() - 1; i++) {
                int x;
                x = Integer.parseInt(stuData.get(i).get(y + 2));
                if (x != -1) {
                    total += x;
                    count++;
                }
            }
            if (count != 0) { //student count is not 0
                total = total / count; //get average mark of module
                averageMarks[y] = total;
                overallAverageScore += total;
                if (total > highestAverage) { //stores largest average of all modules
                    highestAverage = total;
                }
                if (total < lowestAverage) { //stores lowest average of all modules
                    lowestAverage = total;
                }
            }
        }
        overallAverageScore = overallAverageScore / (stuData.get(0).size() - 2);
        int topBoundary = (int) (((highestAverage - overallAverageScore) / 1.75) + overallAverageScore); //top performers boundary relative to average student performance
        int lowBoundary = (int) (((overallAverageScore - lowestAverage))); //low performers boundary relative to average student performance
        if (topBoundary > 100) { //boundary limits
            topBoundary = 100;
        }
        if (lowBoundary < 0) {
            lowBoundary = 0;
        }
        int moduleCount = 0;
        for (int i: selectedModulesIndex) {
            if (averageMarks[i] >= topBoundary) {
                String[] module = {(String) selectedModulesNames.get(moduleCount), "Easy", String.valueOf(averageMarks[i])};
                tableValuesList.add(module);
            } else if (averageMarks[i] <= lowBoundary) {
                String[] module = {(String) selectedModulesNames.get(moduleCount), "Hard", String.valueOf(averageMarks[i])};
                tableValuesList.add(module);
            } else {
                String[] module = {(String) selectedModulesNames.get(moduleCount), "Moderate", String.valueOf(averageMarks[i])};
                tableValuesList.add(module);
            }
            moduleCount++;
        }
        String[][] modulesRecord = new String[tableValuesList.size()][1];
        for (int i = 0; i < tableValuesList.size(); i++) {
            modulesRecord[i] = tableValuesList.get(i);
        }
        subHead1.setText("Module Difficulty     Hard Difficulty >" + lowBoundary + "     Easy Difficulty <" + topBoundary);
        subHead1.setEditable(false);
        subHead1.setFont(new Font("Monospaced", Font.BOLD, 20));
        subHead1.setForeground(Color.black);
        subHead1.setOpaque(false);
        moduleTable = new JTable(modulesRecord, header);
    }
}
