package Main;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JobSeekerAgent extends Agent {
    private JFrame frame;
    private JList<String> jobList;
    private JButton searchButton;
    private JButton searchCustomButton;
    private JTextField customJobField;
    private DefaultListModel<String> listModel;

    @Override
    protected void setup() {
        // Crearea interfeței grafice
        frame = new JFrame("Job Seeker");
        frame.setLayout(new BorderLayout());
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crearea modelului de date pentru joburi
        listModel = new DefaultListModel<>();
        listModel.addElement("Software Developer");
        listModel.addElement("System Analyst");
        listModel.addElement("Data Scientist");
        listModel.addElement("Web Developer");

        // Crearea JList pentru a afișa joburile
        jobList = new JList<>(listModel);
        jobList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frame.add(new JScrollPane(jobList), BorderLayout.CENTER);

        // Câmpul de text pentru job personalizat
        JPanel panel = new JPanel();
        customJobField = new JTextField(15);
        panel.add(new JLabel("Enter Custom Job:"));
        panel.add(customJobField);

        // Buton pentru a căuta jobul personalizat
        searchCustomButton = new JButton("Search Custom Job");
        panel.add(searchCustomButton);

        // Adăugarea panoului pentru jobul personalizat
        frame.add(panel, BorderLayout.NORTH);

        // Butonul pentru a căuta jobul selectat din listă
        searchButton = new JButton("Search Job");
        frame.add(searchButton, BorderLayout.SOUTH);

        // Când apasă butonul pentru jobul din listă, se trimite cererea pentru jobul selectat
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedJob = jobList.getSelectedValue();
                if (selectedJob != null) {
                    // Trimite cererea pentru jobul selectat
                    sendJobRequest(selectedJob);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a job!");
                }
            }
        });

        // Când apasă butonul pentru jobul personalizat, se trimite cererea pentru jobul introdus
        searchCustomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customJob = customJobField.getText();
                if (!customJob.isEmpty()) {
                    // Trimite cererea pentru jobul personalizat
                    sendJobRequest(customJob);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a job!");
                }
            }
        });

        frame.setVisible(true);
    }

    private void sendJobRequest(String selectedJob) {
        // Adaugă comportamentul pentru căutarea jobului
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                // Trimite cererea către Employer
                ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
                message.addReceiver(new jade.core.AID("Employer", jade.core.AID.ISLOCALNAME));
                message.setContent(selectedJob);
                send(message);

                // Așteaptă răspunsul
                ACLMessage reply = blockingReceive();
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.AGREE) {
                        JOptionPane.showMessageDialog(frame, "Job found: " + reply.getContent());
                    } else {
                        JOptionPane.showMessageDialog(frame, "Job not found: " + reply.getContent());
                    }
                }
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("JobSeeker agent " + getAID().getName() + " is terminating.");
        frame.dispose(); // Închide fereastra GUI când agentul se închide
    }
}
