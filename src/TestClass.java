
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class TestClass {

    public TestClass() {

    }
    @Test
    public void configReader() {
        //test for properties class functions to get file path from config file
        Properties prop;
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream("prop.config")) {
            prop.load(fis);
        } catch (IOException ignored) {

        }
        assert(Objects.equals(prop.getProperty("DataFileName"), "C:\\Users\\brand\\IdeaProjects\\22-23_CE201-col_team-01\\DataFileFor2022-23-problem statement.csv"));
    }
    @Test
    public void dataCSVfile() {
        //test for reading and storing the data as expected from data file
        File datafile = new File("C:\\Users\\brand\\IdeaProjects\\22-23_CE201-col_team-01\\DataFileFor2022-23-problem statement.csv");
        List<List<String>> studentData = new ArrayList<>();
            try {
                Scanner sc = new Scanner(datafile);
                String line = sc.nextLine();
                while (sc.hasNextLine()) {
                    List<String> x = new ArrayList<>();
                    String[] splitData = line.split(",", -1);
                    for (int i = 0; i < splitData.length; i++) {
                        if (splitData[i].isEmpty()) {
                            splitData[i] = String.valueOf(-1);
                        }
                    }
                    String stuRegNo = splitData[0];
                    String course = splitData[1];
                    String CE101_4_FY = splitData[2];
                    String CE101_4_SP = splitData[3];
                    String CE141_4_AU = splitData[4];
                    String CE141_4_FY = splitData[5];
                    String CE142_4_AU = splitData[6];
                    String CE142_4_FY = splitData[7];
                    String CE151_4_AU = splitData[8];
                    String CE152_4_SP = splitData[9];
                    String CE153_4_AU = splitData[10];
                    String CE154_4_SP = splitData[11];
                    String CE155_4_SP = splitData[12];
                    String CE161_4_AU = splitData[13];
                    String CE162_4_SP = splitData[14];
                    String CE163_4_AU = splitData[15];
                    String CE164_4_SP = splitData[16];
                    x.add(stuRegNo);
                    x.add(course);
                    x.add(CE101_4_FY);
                    x.add(CE101_4_SP);
                    x.add(CE141_4_AU);
                    x.add(CE141_4_FY);
                    x.add(CE142_4_AU);
                    x.add(CE142_4_FY);
                    x.add(CE151_4_AU);
                    x.add(CE152_4_SP);
                    x.add(CE153_4_AU);
                    x.add(CE154_4_SP);
                    x.add(CE155_4_SP);
                    x.add(CE161_4_AU);
                    x.add(CE162_4_SP);
                    x.add(CE163_4_AU);
                    x.add(CE164_4_SP);
                    studentData.add(x);
                    line = sc.nextLine();
                }
            } catch (FileNotFoundException | HeadlessException e) {
                JOptionPane.showMessageDialog(null, "Wrong or no file, please upload a txt or csv file", "REPORT SYSTEM" , JOptionPane.ERROR_MESSAGE);
            }
        List<String> list = Arrays.asList("2500001", "COMPUTER SCIENCE (3 YR)", "-1", "73", "-1", "44", "-1", "-1", "40", "37", "41", "47", "63", "42", "-1", "-1", "-1");
        assert(Objects.equals(studentData.get(1), list));
    }
    @Test
    public void histogramData() throws FileNotFoundException {
        //test for accurate storing of each student mark for a module
        configReader config = new configReader();
        CSVRead stuData = new CSVRead();
        stuData.readfile(new File(config.getDataFile()));
        int[] selectedModulesIndex = {1};
        List<String> selectedModulesNames = new ArrayList<>(1);
        selectedModulesNames.add("CE101-4-SP");
        ArrayList<String[]> tableValuesList = new ArrayList<>();
        int students = 0;
        int moduleCount = 0;
        double[][] histogramRecords = new double[selectedModulesIndex.length][];
        for (int y : selectedModulesIndex) {
            int x;
            List<Double> histogramValuesList = new ArrayList<Double>();
            for (int i = 1; i < stuData.getStuData().size() - 1; i++) {
                x = Integer.parseInt(stuData.getStuData().get(i).get(y + 2));

                if (x != -1) {
                    students++;
                    histogramValuesList.add((double) x);

                }
            }
            double[] histogramRecord = new double[histogramValuesList.size()];
            for (int c = 0; c < histogramValuesList.size(); c++) {
                histogramRecord[c] = histogramValuesList.get(c);
            }
            histogramRecords[moduleCount] = histogramRecord;
            moduleCount++;
        }
        System.out.println(Arrays.deepToString(histogramRecords));
    }
    @Test
    public void createModuleTableTest() throws FileNotFoundException {
        //test for predicting the module difficulty with accuracy
        configReader config = new configReader();
        CSVRead stuData = new CSVRead();
        stuData.readfile(new File(config.getDataFile()));
        int[] selectedModulesIndex = {1};
        List<String> selectedModulesNames = new ArrayList<>(1);
        selectedModulesNames.add("CE101-4-SP");
        ArrayList<String[]> tableValuesList = new ArrayList<>();
        int moduleCount = 0;
        for (int y : selectedModulesIndex) {
            int count = 0;
            int total = 0;
            for (int i = 1; i < stuData.getStuData().size() - 1; i++) {
                int x;
                x = Integer.parseInt(stuData.getStuData().get(i).get(y + 2));
                if (x != -1) {
                    total += x;
                    count++;
                }
            }
            if (count != 0) {
                total = total / count;
            }
            if (total >= 75) {
                String[] module = {selectedModulesNames.get(moduleCount), "Easy"};
                tableValuesList.add(module);
            } else if (total <= 25) {
                String[] module = {selectedModulesNames.get(moduleCount), "Hard"};
                tableValuesList.add(module);
            } else {
                String[] module = {selectedModulesNames.get(moduleCount), "Moderate"};
                tableValuesList.add(module);
            }
            moduleCount++;
        }
        assert(Objects.equals(tableValuesList.get(0)[0], "CE101-4-SP"));
        assert(Objects.equals(tableValuesList.get(0)[1], "Moderate"));
    }
    @Test
    public void createStudentTableTest() throws FileNotFoundException {
        //test for predicting student table values accurately
        configReader config = new configReader();
        CSVRead stuData = new CSVRead();
        stuData.readfile(new File(config.getDataFile()));
        int[] selectedModulesIndex = {1};
        List<String> selectedModulesNames = new ArrayList<>(1);
        selectedModulesNames.add("CE101-4-SP");
        ArrayList<String[]> tableValuesList = new ArrayList<>();
        int highestAverage = 0, lowestAverage = 100;
        int overallAverageScore = 0;
        for (int i = 1; i < stuData.getStuData().size() - 1; i++) {
            boolean valid = true;
            int x;
            int total = 0;
            for (int y : selectedModulesIndex) {
                x = Integer.parseInt(stuData.getStuData().get(i).get(y + 2));
                if (x == -1) {
                    valid = false;
                    break;
                } else {
                    total += x;
                }
            }
            if (valid) {
                total = total / selectedModulesIndex.length;
                if (total > highestAverage) {
                    highestAverage = total;
                }
                if (total < lowestAverage) {
                    lowestAverage = total;
                }
                overallAverageScore += total;
                String[] student = {stuData.getStuData().get(i).get(0), stuData.getStuData().get(i).get(1), String.valueOf(total)};
                tableValuesList.add(student);
            }
        }
        if (tableValuesList.size() != 0) {
            overallAverageScore = overallAverageScore / tableValuesList.size();
        }

        int topBoundary = (int) (((highestAverage - overallAverageScore) / 1.75) + overallAverageScore); //top performers boundary relative to average student performance
        int lowBoundary = (int) (((overallAverageScore - lowestAverage))); //low performers boundary relative to average student performance
        if (topBoundary > 100) { //boundary limits
            topBoundary = 100;
        }
        if (lowBoundary < 0) {
            lowBoundary = 0;
        }
        //apply boundary to mark values and assign them to a new categorical arraylist to be converted to a string array
        ArrayList<String[]> averageValuesList = new ArrayList<>();
        ArrayList<String[]> topValuesList = new ArrayList<>();
        ArrayList<String[]> lowValuesList = new ArrayList<>();

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
        assert(Objects.equals(record[0][0], "2500001"));
        assert(Objects.equals(record[0][3], "Top Performer"));
        assert(Objects.equals(record[0][2], "73"));
        assert(Objects.equals(record[1][0], "2500016"));
        assert(Objects.equals(record[1][3], "Average Performer"));
        assert(Objects.equals(record[1][2], "64"));
    }
}
