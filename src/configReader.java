
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class configReader {
    Properties prop;
    configReader() {
        //opens a file input stream to prop.config
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream("prop.config")) {
            prop.load(fis);
        } catch (IOException ignored) {

        }

    }
    public String getDataFile() {
        //returns the file path of the data file
        return prop.getProperty("DataFileName");
    }
    public void writeDataFile(String value) throws IOException {
        //opens a file output stream and writes the data file path to the DataFileName property in prop.config
        FileOutputStream out = new FileOutputStream("prop.config");
        prop.setProperty("DataFileName", value);
        prop.store(out, null);
    }
}
