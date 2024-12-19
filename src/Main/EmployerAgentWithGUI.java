package Main;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class EmployerAgentWithGUI extends Agent {
    private Map<String, Integer> availableJobs;
    private JTextField jobTitleField;
    private JTextField salaryField;
    private DefaultTableModel tableModel;

    @Override
    protected void setup() {
        // Inițializarea joburilor disponibile
        availableJobs = new HashMap<>();

        // Crearea interfeței grafice pentru adăugarea unui job
        JFrame frame = new JFrame("Job Manager");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Crearea câmpurilor de text pentru job title și salary
        JPanel panel = new JPanel(new FlowLayout());
        jobTitleField = new JTextField(15);
        salaryField = new JTextField(10);
        JButton addButton = new JButton("Add Job");

        // Crearea unui tabel pentru a vizualiza joburile disponibile
        String[] columnNames = {"Job Title", "Salary"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable jobTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(jobTable);
        
        // Adăugarea componentelor la fereastră
        panel.add(new JLabel("Job Title:"));
        panel.add(jobTitleField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);
        panel.add(addButton);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Funcționalitatea butonului de adăugare a joburilor
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jobTitle = jobTitleField.getText();
                String salary = salaryField.getText();

                // Verificăm dacă jobul și salariul sunt valide
                if (!jobTitle.isEmpty() && !salary.isEmpty()) {
                    try {
                        int salaryInt = Integer.parseInt(salary);
                        // Adăugăm jobul în sistem
                        addJob(jobTitle, salaryInt);
                        // Actualizăm tabelul
                        updateJobTable();
                        // Afișăm mesaj de confirmare
                        System.out.println("Added job: " + jobTitle + " with salary: " + salaryInt);
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid salary input!");
                    }
                } else {
                    System.out.println("Invalid input!");
                }
            }
        });

        frame.setVisible(true);

        // Comportamentul care ascultă cererile de la JobSeeker
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null) {
                    String requestedJob = message.getContent();
                    ACLMessage reply = message.createReply();
                    if (availableJobs.containsKey(requestedJob)) {
                        reply.setPerformative(ACLMessage.AGREE);
                        reply.setContent(requestedJob + " available with salary: " + availableJobs.get(requestedJob));
                    } else {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("Job not found");
                    }
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }

    // Metodă pentru a adăuga joburi în hartă
    private void addJob(String jobTitle, int salary) {
        availableJobs.put(jobTitle, salary);
    }

    // Metodă pentru a actualiza tabelul cu joburi disponibile
    private void updateJobTable() {
        // Șterge vechile date din tabel
        tableModel.setRowCount(0);

        // Adaugă toate joburile disponibile în tabel
        for (Map.Entry<String, Integer> entry : availableJobs.entrySet()) {
            String jobTitle = entry.getKey();
            Integer salary = entry.getValue();
            tableModel.addRow(new Object[]{jobTitle, salary});
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Employer agent " + getAID().getName() + " is terminating.");
    }
}
