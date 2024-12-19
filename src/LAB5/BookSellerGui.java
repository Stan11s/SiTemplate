package LAB5;

import jade.core.AID;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

class BookSellerGui extends JFrame {    
    private BookSellerAgent myAgent;
    
    private JTextField titleField, priceField;
    
   
    private JList<String> addedBooksList, boughtBooksList;
    private DefaultListModel<String> addedBooksModel, boughtBooksModel;
    
    BookSellerGui(BookSellerAgent a) {
        super(a.getLocalName());
        
        myAgent = a;
        
  
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        p.add(new JLabel("Book title:"));
        titleField = new JTextField(15);
        p.add(titleField);
        p.add(new JLabel("Price:"));
        priceField = new JTextField(15);
        p.add(priceField);
        getContentPane().add(p, BorderLayout.CENTER);
      
        JButton addButton = new JButton("Add+");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    String title = titleField.getText().trim();
                    String price = priceField.getText().trim();
                    myAgent.updateCatalogue(title, Integer.parseInt(price));
                    titleField.setText("");
                    priceField.setText("");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(BookSellerGui.this, "Invalid values. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); 
                }
            }
        });
        

        p = new JPanel();
        p.add(addButton);
        getContentPane().add(p, BorderLayout.SOUTH);

        JPanel addedBooksPanel = new JPanel();
        addedBooksPanel.setLayout(new BorderLayout());
        addedBooksPanel.add(new JLabel("Added Books"), BorderLayout.NORTH);
        
   
        addedBooksModel = new DefaultListModel<>();
        addedBooksList = new JList<>(addedBooksModel);
        addedBooksPanel.add(new JScrollPane(addedBooksList), BorderLayout.CENTER);
        getContentPane().add(addedBooksPanel, BorderLayout.WEST);
        

        JPanel boughtBooksPanel = new JPanel();
        boughtBooksPanel.setLayout(new BorderLayout());
        boughtBooksPanel.add(new JLabel("Bought Books"), BorderLayout.NORTH);
        

        boughtBooksModel = new DefaultListModel<>();
        boughtBooksList = new JList<>(boughtBooksModel);
        boughtBooksPanel.add(new JScrollPane(boughtBooksList), BorderLayout.CENTER);
        getContentPane().add(boughtBooksPanel, BorderLayout.EAST);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        });
        
        setResizable(false);
    }
    
 
    public void updateAddedBooks(List<String> books) {
        addedBooksModel.clear();
        for (String book : books) {
            addedBooksModel.addElement(book);
        }
    }
    

    public void updateBoughtBooks(List<String> books) {
        boughtBooksModel.clear();
        for (String book : books) {
            boughtBooksModel.addElement(book);
        }
    }
    
    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }
}
