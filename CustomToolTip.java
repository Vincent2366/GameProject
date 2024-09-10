package com.self.learn;

import javax.swing.*;
import java.awt.*;

// Custom Tooltip Panel to display game instructions
class CustomToolTip extends JDialog {
    private JTextArea textArea;

    public CustomToolTip(JFrame parent) {
        super(parent);
        setUndecorated(true);
        setLayout(new BorderLayout());
        setSize(300, 200);
        setLocationRelativeTo(parent);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setText(String text) {
        textArea.setText(text);
    }
}

