import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {
    private StudentManager manager = new StudentManager();
    private JTextArea displayArea;

    public MainFrame() {
        setTitle("Student Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(loadButton);
        panel.add(saveButton);
        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        removeButton.addActionListener(e -> removeStudent());
        loadButton.addActionListener(e -> loadStudents());
        saveButton.addActionListener(e -> saveStudents());
    }

    private void addStudent() {
        String id = JOptionPane.showInputDialog("Enter ID:");
        String name = JOptionPane.showInputDialog("Enter Name:");
        int age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age:"));
        manager.addStudent(new Student(id, name, age));
        refreshDisplay();
    }

    private void removeStudent() {
        String id = JOptionPane.showInputDialog("Enter ID to remove:");
        manager.removeStudent(id);
        refreshDisplay();
    }

    private void loadStudents() {
        try {
            manager.loadFromFile();
            refreshDisplay();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load: " + e.getMessage());
        }
    }

    private void saveStudents() {
        try {
            manager.saveToFile();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to save: " + e.getMessage());
        }
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        for (Student s : manager.getAllStudents()) {
            sb.append(s.toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
