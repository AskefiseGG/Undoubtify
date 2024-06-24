import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

public class UndoubtifyApp extends JFrame {
    private ArrayList<JTextField> textChoices;
    private JLabel outputText;
    private JPanel optionsPanel;
    private static final int MAX_OPTIONS = 8;

    public UndoubtifyApp() {
        setTitle("Undoubtify");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(19, 51, 55));
        setLayout(new BorderLayout());
        setResizable(false); // Locks the window size

        // Loads the custom icon image (We got help online)
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
        optionsPanel.setBackground(new Color(80, 178, 213)); // Match the background color
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

        JButton addButton = new JButton("Add");
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
                dispose();
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
            JOptionPane.showMessageDialog(this, "Cannot add more than 8 boxes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteLastOption() {
        if (textChoices.size() > 2) {
            JTextField lastOption = textChoices.remove(textChoices.size() - 1);
            optionsPanel.remove(lastOption);
            optionsPanel.revalidate();
            optionsPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Cannot delete the last two boxes. At least two boxes must remain.", "Error", JOptionPane.ERROR_MESSAGE);
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
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UndoubtifyApp().setVisible(true);
            }
        });
    }
}
