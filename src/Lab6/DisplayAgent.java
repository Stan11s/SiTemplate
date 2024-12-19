package Lab6;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.*;

public class DisplayAgent extends Agent {
    private DefaultListModel<String> listModel;

    @Override
    protected void setup() {
        listModel = new DefaultListModel<>();
        JFrame frame = new JFrame("Display Agent");
        JList<String> list = new JList<>(listModel);
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(list), BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    listModel.addElement(msg.getContent());
                } else {
                    block();
                }
            }
        });
    }
}
