// A PROJECT MADE BY TARQUIRI AND MALDONADO, UNDOUBTIFY

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class UndoubtifyApp extends JFrame {
    private ArrayList<JTextField> textChoices;
    private JLabel outputText;
    private JPanel optionsPanel;
    private static final int MAX_OPTIONS = 8;
    private static final String ACCOUNT_FILE_PATH = "C:\\Users\\ADMIN\\Desktop\\School\\ONLY FOR UNDOUBTIFY\\Undoubtify\\accounts.txt";
    private static final String PAST_ACTIVITY_FILE_PATH = "C:\\Users\\ADMIN\\Desktop\\School\\ONLY FOR UNDOUBTIFY\\Undoubtify\\past_activities.txt";

    private boolean unsavedChanges = false;

    public UndoubtifyApp() {
        setTitle("Undoubtify");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(19, 51, 55));
        setLayout(new BorderLayout());
        setResizable(false);

        // Loads the custom icon image
        try {
            Image icon = ImageIO.read(new File("undoubtify.png"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        textChoices = new ArrayList<>();

        JLabel titleLabel = new JLabel("Undoubtify", JLabel.CENTER);
        titleLabel.setFont(new Font("Harlow Solid Italic", Font.BOLD, 35));
        titleLabel.setBackground(new Color(27, 45, 59));
        titleLabel.setForeground(new Color(253, 253, 253));
        titleLabel.setOpaque(true);
        titleLabel.setPreferredSize(new Dimension(750, 50));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(80, 178, 213));

        optionsPanel = new JPanel();
        optionsPanel.setBackground(new Color(80, 178, 213));
        optionsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        centerPanel.add(optionsPanel);

        outputText = new JLabel("", JLabel.CENTER);
        outputText.setFont(new Font("Aharoni", Font.PLAIN, 40));
        outputText.setForeground(Color.WHITE);
        outputText.setPreferredSize(new Dimension(750, 100));
        centerPanel.add(outputText);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(19, 51, 55));
        bottomPanel.setLayout(new FlowLayout());

        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Unispace", Font.PLAIN, 20));
        startButton.setBackground(new Color(140, 255, 138));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pickRandom();
            }
        });
        bottomPanel.add(startButton);

        JButton addButton = new JButton("Add Box");
        addButton.setFont(new Font("Unispace", Font.PLAIN, 15));
        addButton.setBackground(new Color(131, 205, 229));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOption();
            }
        });
        bottomPanel.add(addButton);

        JButton deleteLastButton = new JButton("Delete Box");
        deleteLastButton.setFont(new Font("Unispace", Font.PLAIN, 15));
        deleteLastButton.setBackground(new Color(232, 201, 150));
        deleteLastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteLastOption();
            }
        });
        bottomPanel.add(deleteLastButton);

        JButton clearAllButton = new JButton("Clear All");
        clearAllButton.setFont(new Font("Unispace", Font.PLAIN, 15));
        clearAllButton.setBackground(new Color(232, 201, 150));
        clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllContent();
            }
        });
        bottomPanel.add(clearAllButton);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Unispace", Font.PLAIN, 15));
        closeButton.setBackground(new Color(242, 186, 188));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptToSaveOnClose();
            }
        });
        bottomPanel.add(closeButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // This line initializes the program with 3 boxes.
        for (int i = 0; i < 3; i++) {
            addOptionField();
        }
    }

    private void addOptionField() {
        if (textChoices.size() < MAX_OPTIONS) {
            JTextField option = new JTextField();
            option.setPreferredSize(new Dimension(200, 30));
            option.setHorizontalAlignment(JTextField.CENTER);
            textChoices.add(option);
            optionsPanel.add(option);
            optionsPanel.revalidate();
            optionsPanel.repaint();
        }
    }

    private void addOption() {
        if (textChoices.size() < MAX_OPTIONS) {
            addOptionField();
        } else {
            JOptionPane.showMessageDialog(this, "You cannot add more than 8 boxes!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLastOption() {
        if (textChoices.size() > 2) {
            JTextField lastOption = textChoices.remove(textChoices.size() - 1);
            optionsPanel.remove(lastOption);
            optionsPanel.revalidate();
            optionsPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "You cannot have less than 2 boxes!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearAllContent() {
        for (JTextField textField : textChoices) {
            textField.setText("");
        }
        outputText.setText("");
    }

    private void pickRandom() {
        if (!textChoices.isEmpty()) {
            Random random = new Random();
            int index = random.nextInt(textChoices.size());
            JTextField choice = textChoices.get(index);
            outputText.setText(choice.getText());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAST_ACTIVITY_FILE_PATH))) {
                writer.write("Session ended. Last Output was: " + choice.getText());
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void promptToSaveOnClose() {
        int option = JOptionPane.showConfirmDialog(this, "Do you want to save the session?", "Confirm Close", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            promptToSave();
        }
        dispose();
    }

    private void promptToSave() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAST_ACTIVITY_FILE_PATH, true))) {
            String lastOutput = outputText.getText();
            writer.write("Session ended. Last Output was: " + lastOutput);
            writer.newLine();
            for (JTextField textField : textChoices) {
                writer.write(textField.getText());
                writer.newLine();
            }
            unsavedChanges = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showAccountOptions() {
        String[] options = {"Create Account", "Load Account", "View Account", "Edit Account", "Delete Account", "View Past Activities", "Clear Past Activities"};
    
        ImageIcon accountIcon = new ImageIcon("C:\\Users\\ADMIN\\Desktop\\School\\ONLY FOR UNDOUBTIFY\\Undoubtify\\undoubtify.png");
    
        Image image = accountIcon.getImage();
        Image scaledImage = image.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        accountIcon = new ImageIcon(scaledImage);
    
        int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "Account Panel",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, accountIcon, options, options[0]);
    
        switch (choice) {
            case 0:
                if (createAccount()) {
                    launchMainApplication();
                } else {
                    showAccountOptions();
                }
                break;
            case 1:
                if (loadAccount()) {
                    launchMainApplication();
                } else {
                    showAccountOptions();
                }
                break;
            case 2:
                viewAccount();
                showAccountOptions();
                break;
            case 3:
                editAccount();
                showAccountOptions();
                break;
            case 4:
                deleteAccount();
                showAccountOptions();
                break;
            case 5:
                viewPastActivities();
                showAccountOptions();
                break;
            case 6:
                clearPastActivities();
                showAccountOptions();
                break;
            default:
                System.exit(0);
        }
    }

    private static boolean createAccount() {
        String name = JOptionPane.showInputDialog("Enter name:");
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.");
            return false;
        }

        String id = JOptionPane.showInputDialog("Enter ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID cannot be empty.");
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ACCOUNT_FILE_PATH, true))) {
            writer.write(name + " " + id);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean loadAccount() {
        String id = JOptionPane.showInputDialog("Enter ID:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID cannot be empty.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(id)) {
                    JOptionPane.showMessageDialog(null, "Welcome, " + line.split(" ")[0]);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Account not found.");
        return false;
    }

    private static void viewAccount() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCOUNT_FILE_PATH))) {
            StringBuilder accounts = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.append(line).append("\n");
            }
            JTextArea textArea = new JTextArea(accounts.toString());
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Accounts", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void editAccount() {
        String id = JOptionPane.showInputDialog("Enter ID of the account to edit:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID cannot be empty.");
            return;
        }

        File inputFile = new File(ACCOUNT_FILE_PATH);
        File tempFile = new File("temp_accounts.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(id)) {
                    String newName = JOptionPane.showInputDialog("Enter new name:");
                    if (newName == null || newName.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Name cannot be empty.");
                        return;
                    }
                    writer.write(newName + " " + id);
                    writer.newLine();
                    found = true;
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (found) {
                reader.close();
                writer.close();
                inputFile.delete();
                tempFile.renameTo(inputFile);
                JOptionPane.showMessageDialog(null, "Account updated successfully.");
            } else {
                reader.close();
                writer.close();
                tempFile.delete();
                JOptionPane.showMessageDialog(null, "Account not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteAccount() {
        String id = JOptionPane.showInputDialog("Enter ID of the account to delete:");
        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID cannot be empty.");
            return;
        }

        File inputFile = new File(ACCOUNT_FILE_PATH);
        File tempFile = new File("temp_accounts.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (!line.endsWith(id)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    found = true;
                }
            }

            if (found) {
                reader.close();
                writer.close();
                inputFile.delete();
                tempFile.renameTo(inputFile);
                JOptionPane.showMessageDialog(null, "Account deleted successfully.");
            } else {
                reader.close();
                writer.close();
                tempFile.delete();
                JOptionPane.showMessageDialog(null, "Account not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void viewPastActivities() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PAST_ACTIVITY_FILE_PATH))) {
            StringBuilder activities = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                activities.append(line).append("\n");
            }
            JTextArea textArea = new JTextArea(activities.toString());
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Past Activities", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void clearPastActivities() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAST_ACTIVITY_FILE_PATH))) {
            writer.write("");
            JOptionPane.showMessageDialog(null, "Past activities cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void launchMainApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UndoubtifyApp().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        showAccountOptions();
    }
}
