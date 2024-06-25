import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CaesarCipherGUI extends JFrame {
    private JTextArea textArea;
    private JTextField keyField;

    public CaesarCipherGUI() {
        setTitle("Caesar Cipher File Encryption/Decryption");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel(new FlowLayout());

        keyField = new JTextField(5);
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key = Integer.parseInt(keyField.getText());
                String encryptedText = encrypt(textArea.getText(), key);
                textArea.setText(encryptedText);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int key = Integer.parseInt(keyField.getText());
                String decryptedText = decrypt(textArea.getText(), key);
                textArea.setText(decryptedText);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToFile(textArea.getText());
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loadedText = loadFromFile();
                textArea.setText(loadedText);
            }
        });

        optionsPanel.add(new JLabel("Key:"));
        optionsPanel.add(keyField);
        optionsPanel.add(encryptButton);
        optionsPanel.add(decryptButton);
        optionsPanel.add(saveButton);
        optionsPanel.add(loadButton);

        panel.add(optionsPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                encryptedText.append((char) ((c - base + key) % 26 + base));
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    private String decrypt(String text, int key) {
        return encrypt(text, -key);
    }

    private void saveToFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (PrintWriter writer = new PrintWriter(fileToSave)) {
                writer.write(content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileToLoad))) {
                StringBuilder loadedText = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    loadedText.append(line).append("\n");
                }
                return loadedText.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CaesarCipherGUI();
            }
        });
    }
}
