import javax.swing.*;
import java.awt.*;
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
        JButton searchButton = new JButton("Search Student");  

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(searchButton);  

        add(panel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addStudent());
        removeButton.addActionListener(e -> removeStudent());
        loadButton.addActionListener(e -> loadStudents());
        saveButton.addActionListener(e -> saveStudents());

        // New ActionListener for Search button
        searchButton.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Enter ID to search:");
            if (id == null || id.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID cannot be empty.");
                return;
            }
            for (Student s : manager.getAllStudents()) {
                if (s.getId().equals(id.trim())) {
                    JOptionPane.showMessageDialog(this, s.toString());
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Student not found.");
        });
    }

    private void addStudent() {
        String id = JOptionPane.showInputDialog("Enter ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.");
            return;
        }

        String name = JOptionPane.showInputDialog("Enter Name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.");
            return;
        }

        String ageInput = JOptionPane.showInputDialog("Enter Age:");
        try {
            int age = Integer.parseInt(ageInput);
            if (age < 5 || age > 120) {
                throw new NumberFormatException("Age out of range.");
            }
            manager.addStudent(new Student(id.trim(), name.trim(), age));
            refreshDisplay();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid age input.");
        }
    }

    private void removeStudent() {
        String id = JOptionPane.showInputDialog("Enter ID to remove:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.");
            return;
        }
        manager.removeStudent(id.trim());
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
