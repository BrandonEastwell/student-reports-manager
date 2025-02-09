
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class CSVRead {
    static List<List<String>> stuData;
    public CSVRead() {

    }
    public List<List<String>> getStuData() {
        return stuData;
    }
    public void readfile(File datafile) throws FileNotFoundException {
        List<List<String>> studentData = new ArrayList<>();
        //validating file extension to check if its text based
        String extension = "";
        int ext = datafile.getName().lastIndexOf('.');
        if (ext > 0) {
            extension = datafile.getName().substring(ext+1);
        }
        if (extension.equals("txt") || extension.equals("csv")) {
            //read the first line of text file and validate formatting
            try {
                Scanner sc = new Scanner(datafile);
                boolean validated = true;
                String line = sc.nextLine();
                String[] headerFormat = line.split(",", -1);
                //checks if formatting has a course and student reg header
                if (headerFormat.length > 2) {
                    if (!Objects.equals(headerFormat[0], "Student RegNo") && !Objects.equals(headerFormat[1], "Course")) {
                        validated = false;
                        JOptionPane.showMessageDialog(null, "File formatted incorrectly, please upload a valid data file", "REPORT SYSTEM", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    validated = false;
                    JOptionPane.showMessageDialog(null, "File formatted incorrectly, please upload a valid data file", "REPORT SYSTEM", JOptionPane.ERROR_MESSAGE);
                }
                //if valid iterates through the file adding each attribute to a String list
                while (sc.hasNextLine() && validated) {
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
        } else {
            JOptionPane.showMessageDialog(null, "Wrong or no file, please upload a txt or csv file", "REPORT SYSTEM" , JOptionPane.ERROR_MESSAGE);
        }
        stuData = studentData;
    }
}
