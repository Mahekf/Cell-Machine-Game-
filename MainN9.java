import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MainN9 extends JFrame{
	private static final long serialVersionUID = 1L;
	public static volatile boolean restartDemande = false;
    public static void lancerFenetre() {
        SwingUtilities.invokeLater(() -> {
          // Créer un tableau et initialiser les zones constructibles
        Tableau tableau = new Tableau(13, 9);
        for (int x = 4; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }

        Cellule cellule1 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(5, 2));
        Cellule cellule2 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(6, 3));
        Cellule cellule3 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(7, 4));
        Cellule cellule4 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(8, 5));
        Cellule cellule5 = new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(8, 7));   
        
        Cellule spinner = new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.NORTH, new Case(5,7));

        Cellule e1 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(11, 2));
        Cellule e2 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(12, 2));
        Cellule e3 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(0, 2));
        Cellule e4 = new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(1, 2));
        
    
        tableau.ajouterCellule(cellule1, 5,2);
        tableau.ajouterCellule(cellule2, 6,3);
        tableau.ajouterCellule(cellule3, 7,4);
        tableau.ajouterCellule(cellule4, 8,5);
        tableau.ajouterCellule(cellule5, 8,7);
        tableau.ajouterCellule(spinner, 5,7);
        

        tableau.ajouterCellule(e1, 11, 2);
        tableau.ajouterCellule(e2, 12, 2);
        tableau.ajouterCellule(e3, 0, 2);
        tableau.ajouterCellule(e4, 1, 2);

            // --- Création de la fenêtre ---
            JFrame frame = new JFrame("Level 9");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setSize(530, 570);
            frame.setSize(660, 520);
            frame.setLayout(new BorderLayout());

            // --- Ajout du panneau de jeu ---
            NiveauGenerale gridPanel = new NiveauGenerale(tableau);
            frame.add(gridPanel, BorderLayout.CENTER);

            // --- Création et ajout du bouton Re-start ---
            JButton restartButton = new JButton("Restart");
            restartButton.addActionListener(e -> {
            	restartDemande = true;
                System.out.println("Restart button clicked !!");
                gridPanel.setDeplacement(true); //Pour que les cellules soit déplaçable 
                // Exemple de réinitialisation : même logique que lors du premier lancement
                boolean[][] zones = new boolean[13][9];
                for (int x = 4; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        zones[x][y] = true;
                    }
                }
                List<Cellule> cellulesInit = new ArrayList<>();
            // Créer une cellule à déplacer et ajouter un pusher
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(5, 2)));
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(6, 3)));
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(7, 4)));
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(8, 5)));
                cellulesInit.add(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(8, 7)));
           
                cellulesInit.add(new Cellule(TypeCell.SPINNER_DIRECT, Action.ROTATE, Orientation.NORTH, new Case(5, 7)));

                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(11, 2)));
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(12, 2)));
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(0, 2)));
                cellulesInit.add(new Cellule(TypeCell.ENEMY, Action.MOVE, Orientation.SOUTH, new Case(1, 2)));

                tableau.reinitialiserJeu(zones, cellulesInit);
                gridPanel.repaint();
            });

            // Ajout à droite des boutons existants
               //frame.add(restartButton, BorderLayout.SOUTH);
                Component comp = gridPanel.getComponent(0);
                if (comp instanceof JPanel) {
                JPanel buttonPanel = (JPanel) comp;
                buttonPanel.add(restartButton);  // on ajoute le Re-start à droite des autres
                buttonPanel.revalidate(); // mise à jour du layout
                buttonPanel.repaint();
            }
            // On affiche tout
                frame.revalidate();
                frame.repaint(); 


            // --- Affichage final ---
                frame.setLocationRelativeTo(null); // centre la fenêtre
                frame.setVisible(true);
        });
    } 

        public static void main(String[] args) {
        lancerFenetre();
    } 
} 