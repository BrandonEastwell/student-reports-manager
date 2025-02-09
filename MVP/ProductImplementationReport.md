<h1>Product Implementation</h1>

<h2>Technical Diagrams</h2>
![img_1.png](../images/class%20diagrams.png)

<h2>Technical Description</h2>
<p>The student report system was entirely designed using UML diagrams to be translated into the relationship of an object 
orientated system built using Java. The src.GUI class was the first class to be designed and built to produce a mockup version
of what the interface would look like with placeholder buttons which continued to be the class to support all src.GUI functions
such as intractable buttons and scroll elements. Once we began implementing the reports chart functions individual classes
were created for each individual action which are instantiated in the main where its methods for data processing are called.
For instance the src.studentTable class is responsible for the handling of student data attributes into a java swing table. Each
class is parsed the student data array as well as the index and names of the current selected modules from the JList component.
Student data array is initialised with a src.CSVRead objects method that iterates through the student data text file and stores
values into an array which is returned once a scanner reaches the end of a line, if a student had no value in a specific field
the value is replaced with a -1 to make filtering of the student data easier by checking for values that do not equal -1. 
Validation is in place that checks if the uploaded file is formatted correctly and is ignored if not generating an error message. 
Our file upload function is a function our team is particularly proud of implementing through the use of a configuration file which 
is handled in the src.configReader class, this level of data persistence has enabled us to save the file name of the uploaded 
file as a property in the config file which is read from within the src.CSVRead class on application startup and on a new file upload. 
The configuration file can be beneficial by storing other persistent app properties to enhance functionality. Further, the studentTable 
class includes functionality for student ID specific queries, which our GUI includes a search button and a text field where the user 
can enter a 7-digit ID that is then parsed to a method in studentTable that processes a table to display the students performance per 
module. The createPDF class is instantiated in the "Save as PDF" button action listener, which when a createPDF object is created, 
parsing the itextpdf document object and the new file location selected by the user through a JFileChooser component, both the pdfTable
and pdfGraph methods are called which independently handle the exporting of the tables and graphs objects to the PDF document by rendering
them as images. This is especially functional as we enable the user to choose where to save the file in their Windows explorer.</p>

<h2>Algorithms and Data Structures</h2>
<p>2D String arrays were used to store the values of the student table by comparing whether a student had marks in a selected
module from the JList component, the 2D string array has the time complexity of O(n*m) where n is the number of arrays and m is
the number of attributes in each internal array. An Arrays.sort algorithm was performed on the string array to order it by descending marks
which on an array of non-primitive types the recursive merge sort algorithm with time complexity of O(nLogn) is applied, as 
merge sort divides an array into two halves, calls itself on the two halves then merges the two sorted halves.</p>

<h2>Imported Libraries</h2>
<p>JFreeChart - A third party chart library that makes it easy to generate a wide variety of charts such as histograms and
scatter plot graphs to be used with JSwing.</p>

<p>itextpdf - A pdf conversion library that enables Swing and FreeChart components to be rendered as an image into a PDF file.</p>

<h2>Known Issues</h2>
<p>When a second file is uploaded to the program with valid formatting while the program has already calculated the module
list of the first file in the same runtime, a second module selection list is generated next to the current module list showing
2 separate module selection lists.</p>

<p>Program freezes upon uploading first file, to fix reopen the program, and it will automatically detect the file previously
uploaded but lets you interact with the interface.</p>
