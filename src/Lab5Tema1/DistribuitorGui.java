package Lab5Tema1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class DistribuitorGui extends JFrame {
    private DistribuitorAgent myAgent;
    private JTextField value1Field, value2Field, value3Field;
    private JTextArea resultsArea;

    DistribuitorGui(DistribuitorAgent agent) {
        super(agent.getLocalName());
        myAgent = agent;

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Valoare 1:"));
        value1Field = new JTextField();
        inputPanel.add(value1Field);

        inputPanel.add(new JLabel("Valoare 2:"));
        value2Field = new JTextField();
        inputPanel.add(value2Field);

        inputPanel.add(new JLabel("Valoare 3:"));
        value3Field = new JTextField();
        inputPanel.add(value3Field);

        JButton sendButton = new JButton("Trimite valori");
        inputPanel.add(sendButton);

        resultsArea = new JTextArea(5, 20);
        resultsArea.setEditable(false);

        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int v1 = Integer.parseInt(value1Field.getText());
                    int v2 = Integer.parseInt(value2Field.getText());
                    int v3 = Integer.parseInt(value3Field.getText());
                    myAgent.sendValues(new int[]{v1, v2, v3});
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(DistribuitorGui.this, "Introduceți valori numerice valide.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                myAgent.doDelete();
            }
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    public void displayResults(int[] results) {
        resultsArea.setText("Rezultatele calculului:\n");
        for (int i = 0; i < results.length; i++) {
            resultsArea.append("Valoare " + (i + 1) + ": " + results[i] + "\n");
        }
    }

    public void showGui() {
        setVisible(true);
    }
}
