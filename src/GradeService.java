import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class GradeService {
    private Document doc;
    private static final String XML_FILE_PATH = "src/grades.xml";

    public GradeService() {
        doc = XMLParser.parseXMLFile(XML_FILE_PATH);
    }

    public void displayGrades() {
        NodeList courseList = doc.getElementsByTagName("course");
        System.out.println("\n\nAcademic Year: 2022-2023");
        System.out.println("Semester: 1st");
        System.out.println("_________________________________________");

        for (int i = 0; i < courseList.getLength(); i++) {
            Node node = courseList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element courseElement = (Element) node;
                displayCourseInfo(courseElement);
            }
        }
    }

    public void addGrade() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Course ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the grade: ");
        String grade = scanner.nextLine();

        Element gradesElement = doc.getDocumentElement();

        Element courseElement = doc.createElement("course");
        createAndAppendElement(courseElement, "id", id);
        createAndAppendElement(courseElement, "name", name);
        createAndAppendElement(courseElement, "grade", grade);

        gradesElement.appendChild(courseElement);

        saveChanges();

        System.out.println("\nGrade added successfully!\n");
    }

    private void displayCourseInfo(Element courseElement) {
        System.out.println("Course ID: " + getChildValue(courseElement, "id"));
        System.out.println("Course Name: " + getChildValue(courseElement, "name"));
        System.out.println("Grade: " + getChildValue(courseElement, "grade"));
        System.out.println("_________________________________________");
    }

    public void editGrade() {
        Scanner scanner = new Scanner(System.in);
        NodeList courseList = doc.getElementsByTagName("course");

        System.out.println("Current Grades:");
        displayGrades();

        System.out.print("Enter the Course ID to edit: ");
        String editId = scanner.nextLine();

        for (int i = 0; i < courseList.getLength(); i++) {
            Node node = courseList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element courseElement = (Element) node;
                String courseId = getChildValue(courseElement, "id");
                if (editId.equals(courseId)) {
                    editCourse(courseElement, scanner);
                    saveChanges();
                    System.out.println("\nCourse edited successfully!\n");
                    return;
                }
            }
        }

        System.out.println("Course with ID " + editId + " not found.");
    }

    private void editCourse(Element courseElement, Scanner scanner) {
        System.out.print("Enter new Course ID: ");
        String newId = scanner.nextLine();

        System.out.print("Enter new Course Name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter the new grade: ");
        String newGrade = scanner.nextLine();

        courseElement.getElementsByTagName("id").item(0).setTextContent(newId);
        courseElement.getElementsByTagName("name").item(0).setTextContent(newName);
        courseElement.getElementsByTagName("grade").item(0).setTextContent(newGrade);
    }

    public void deleteGrade() {
        Scanner scanner = new Scanner(System.in);
        NodeList courseList = doc.getElementsByTagName("course");

        System.out.println("Current Grades:");
        displayGrades();

        System.out.print("Enter the Course ID to delete: ");
        String deleteId = scanner.next();

        for (int i = 0; i < courseList.getLength(); i++) {
            Node node = courseList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element courseElement = (Element) node;
                String courseId = getChildValue(courseElement, "id");
                if (deleteId.equals(courseId)) {
                    node.getParentNode().removeChild(node);
                    saveChanges();
                    System.out.println("\nCourse deleted successfully!\n");
                    return;
                }
            }
        }

        System.out.println("Course with ID " + deleteId + " not found.");
    }

    private void createAndAppendElement(Element parentElement, String tagName, String textContent) {
        Element element = doc.createElement(tagName);
        element.setTextContent(textContent);
        parentElement.appendChild(element);
    }

    private String getChildValue(Element parentElement, String childTagName) {
        return XMLParser.getChildValue(parentElement, childTagName);
    }

    private void saveChanges() {
        try {

            DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
            LSSerializer lsSerializer = domImplementation.createLSSerializer();
            lsSerializer.getDomConfig().setParameter("format-pretty-print", true);

            LSOutput lsOutput = domImplementation.createLSOutput();
            lsOutput.setEncoding("UTF-8");
            Writer stringWriter = new StringWriter();
            lsOutput.setCharacterStream(stringWriter);


            lsSerializer.write(doc, lsOutput);

            Files.writeString(Paths.get(XML_FILE_PATH), stringWriter.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
