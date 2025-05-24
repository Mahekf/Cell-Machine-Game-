import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Levels extends JFrame {
    private BufferedImage image;  // Variable pour l'image
    Tableau tableau; 

    public Levels() {
        // Charger l'image
        try {
            image = ImageIO.read(new File("images /cover.png"));  //Le chemin de l'image
        } catch (IOException e) {
            System.err.println("Erreur de chargement de l'image : " + e.getMessage());
        }

        // Configuration de la fenêtre
        setTitle("Welcome");
        setSize(600, 800);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        //Panneau pour afficher l'image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    // Dessine l'image redimensionnée pour remplir la fenêtre
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());  //Utilisation de GridBagLayout pour centrer les boutons

        //Création des boutons
        JButton boutonJ = new JButton("Level 1");
        boutonJ.addActionListener(e -> {
            System.out.println("Le bouton 'Level 1' a été cliqué !"); 
           
            MainN1.lancerFenetre();
            this.dispose();
        });

        // ===== Level 2 =====
           JButton boutonB = new JButton("Level 2");
           boutonB.addActionListener(e -> {
               System.out.println("Le bouton 'Level 2' a été cliqué !"); 
            
               MainN2.lancerFenetre();
               this.dispose();
           });

        // ===== Level 3 =====
        JButton boutonR = new JButton("Level 3");
           boutonR.addActionListener(e -> {
               System.out.println("Le bouton 'Level 3' a été cliqué !"); 
             
               MainN3.lancerFenetre();
               this.dispose();
           });

         // ===== Level 4 =====
         JButton boutonV = new JButton("Level 4");
         boutonV.addActionListener(e -> {
             System.out.println("Le bouton 'Level 4' a été cliqué !"); 
            
             MainN4.lancerFenetre();
             this.dispose();
         });

         // ===== Level 5 =====
         JButton boutonS = new JButton("Level 5");
         boutonS.addActionListener(e -> {
             System.out.println("Le bouton 'Level 5' a été cliqué !"); 
    
             MainN5.lancerFenetre();
             this.dispose();
         });

               // ===== Level 6 =====
               JButton bouton6 = new JButton("Level 6");
               bouton6.addActionListener(e -> {
                   System.out.println("Le bouton 'Level 6' a été cliqué !"); 
          
                   MainN6.lancerFenetre();
                   this.dispose();
               });
               
            // ===== Level 7 =====
               JButton bouton7 = new JButton("Level 7");
               bouton7.addActionListener(e -> {
                   System.out.println("Le bouton 'Level 7' a été cliqué !"); 
          
                   MainN7.lancerFenetre();
                   this.dispose();
               });
               // ===== Level 8 =====
               JButton bouton8 = new JButton("Level 8");
               bouton8.addActionListener(e -> {
                   System.out.println("Le bouton 'Level 8' a été cliqué !"); 
          
                   MainN8.lancerFenetre();
                   this.dispose();
               });
            // ===== Level 9 =====
               JButton bouton9 = new JButton("Level 9");
               bouton9.addActionListener(e -> {
                   System.out.println("Le bouton 'Level 9' a été cliqué !"); 
          
                   MainN9.lancerFenetre();
                   this.dispose();
               });
      



        //Panel pour les boutons (centré sur l'image)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  //Rend le fond du panel transparent
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));  //Disposition verticale

        // Ajout des boutons au panel
        buttonPanel.add(boutonJ);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));  //Espacement entre les boutons 
        buttonPanel.add(boutonB);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(boutonR);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(boutonV);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(boutonS);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(bouton6);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(bouton7);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(bouton8);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(bouton9);

        // Ajout du panel de boutons au panel d'arrière-plan
        backgroundPanel.add(buttonPanel);

        // Ajout du panel d'arrière-plan à la fenêtre principale
        add(backgroundPanel);

        setLocationRelativeTo(null);  // Centre la fenêtre sur l'écran
        setVisible(true);  // Rend la fenêtre visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Levels ());
    }
}




