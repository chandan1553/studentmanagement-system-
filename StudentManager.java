import java.io.*;
import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students = new ArrayList<>();
    private final String FILE_NAME = "students.dat";

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(String id) {
        students.removeIf(student -> student.getId().equals(id));
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public void saveToFile() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
        out.writeObject(students);
        out.close();
    }

    public void loadFromFile() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
        students = (ArrayList<Student>) in.readObject();
        in.close();
    }
}
