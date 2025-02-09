# REPORT MANAGEMENT SYSTEM

## Getting Started

### Dependencies

* JRE/JDK 1.6.0 or higher
* JCommon-1.0.23 [jar-download](https://jar-download.com/artifacts/org.jfree/jfreechart/1.0.19/source-code)
* JFreeChart-1.0.19 [jar-download](https://jar-download.com/artifacts/org.jfree/jfreechart/1.0.19/source-code)
* itextpdf-5.5.13.3 [jar-download](https://jar-download.com/artifacts/com.itextpdf/itextpdf/5.5.13/source-code)

### Installing

* Download and run .jar from windows manager - [GITLAB Download](https://cseegit.essex.ac.uk/22-23-ce201-col/22-23_CE201-col_team-01/-/blob/d5e51e064de067113e0117e785ecf80ad19d3046/REPORT_MANAGER-col_team-01.jar)


### Executing program

#### How to compile and run the program from the command line
* Make current directory the path to the source files
```
C:\> cd C:\src
```
* This makes C:\src the current directory
```
C:\src
```
* Then compile by entering the class paths of itextpdf and JFreeChart libraries with the main class, for example:
```
C:\src> javac -classpath "C:\lib\library.jar;C:\lib\library2.jar" main.java
```
* Example of executing the compiler with the required dependencies
```
C:\src> javac -classpath "C:\Users\brand\IdeaProjects\22-23_CE201-col_team-01\out\production\22-23_CE201-col_team-01;C:\Program Files\Java\jdk-17.0.5\lib\itextp
df-5.5.13.3.jar;C:\Program Files\Java\jdk-17.0.5\lib\jfreechart-1.0.19.jar;C:\Program Files\Java\jdk-17.0.5\lib\jfreechart-1.0.19\lib\junit-4.11.jar;C:\Program 
Files\Java\jdk-17.0.5\lib\jfreechart-1.0.19\lib\jcommon-1.0.23.jar;C:\Program Files\Java\jdk-17.0.5\lib\jfreechart-1.0.19\lib\hamcrest-core-1.3.jar" *.java
```
* To run the compiled class execute the following line
```
C:\src> java GUI
```
* CSV files do not need to be present, file can be uploaded through the program

<br>

#### How to run the executable jar file from command line
* Make current directory the path to the jar file, for example:
```
C:\> cd C:\22-23_CE201-col_team-01
```
* Then execute the jar file
```
C:\22-23_CE201-col_team-01> java -jar REPORT_MANAGER-col_team-01.jar
```
Link to the .jar to download and directly run from windows manager - [GITLAB Download](https://cseegit.essex.ac.uk/22-23-ce201-col/22-23_CE201-col_team-01/-/blob/d5e51e064de067113e0117e785ecf80ad19d3046/REPORT_MANAGER-col_team-01.jar)

<br>

### Known Bugs
Program freezes upon uploading first file, to fix reopen the program, and it will automatically detect the file previously
uploaded but lets you interact with the interface.

When a second file is uploaded to the program with valid formatting while the program has already calculated the module
list of the first file in the same runtime, a second module selection list is generated next to the current module list showing
2 separate module selection lists.

## Authors

Brandon Eastwell


## License

This project is for educational purposes only.
