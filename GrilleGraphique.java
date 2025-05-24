import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.AffineTransform;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GrilleGraphique extends JPanel {

    private static final long serialVersionUID = 1L;
    Tableau tableau;
    Image moveImage, pushImage, slideImage, spinnerImage, directionalImage, generatorImage, defaultImage;

    public GrilleGraphique(Tableau tableau) {
        this.tableau = tableau;
        setFocusable(true);

        // Chargement des images
        moveImage = chargerImage("./images /mover.png");
        pushImage = chargerImage("push.png");
        slideImage = chargerImage("slide.png");
        spinnerImage = chargerImage("spinner.png");
        directionalImage = chargerImage("directional.png");
        generatorImage = chargerImage("generator.png");
        defaultImage = chargerImage("bg.png");

        // Bouton pour exécuter les déplacements
        JButton btnExecuter = new JButton("Déplacer");
        btnExecuter.addActionListener(e -> {
            mettreAJourGrille();
            repaint();
        });

        JPanel panel = new JPanel();
        panel.add(btnExecuter);

        JFrame frame = new JFrame("Grille Graphique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private Image chargerImage(String nomFichier) {
        File fichier = new File("/Users/mahekfatma/Documents/Cell_machine_finale" + nomFichier);
        if (!fichier.exists()) {
            System.err.println("Fichier introuvable : " + fichier.getAbsolutePath());
            return null;
        }
        try {
            return ImageIO.read(fichier);
        } catch (IOException e) {
            System.err.println("Erreur de lecture de l'image : " + fichier.getAbsolutePath() + " -> " + e.getMessage());
            return null;
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int rows = tableau.getHauteur();
        int cols = tableau.getlargeur();
        int cellSize = 50;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * cellSize;
                int y = i * cellSize;

                Cellule cellule = tableau.getCellule(j, i);

                // Fond
                if (defaultImage != null) {
                    g.drawImage(defaultImage, x, y, cellSize, cellSize, this);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, cellSize, cellSize);
                }

                if (cellule != null) {
                    Image img = null;
                    switch (cellule.getType()) {
                        case MOVE: img = moveImage; break;
                        case PUSHER: img = pushImage; break;
                        case SLIDE: img = slideImage; break;
                        case SPINNER_DIRECT: img = spinnerImage; break;
                        case SPINNER_INDIRECT: img = spinnerImage; break;
                        case GENERATOR: img = generatorImage; break;
                        case DIRECTIONAL: img = directionalImage; break;
                        default: break;
                    }

                    if (img != null) {
                        Graphics2D g2d = (Graphics2D) g;
                        AffineTransform oldTransform = g2d.getTransform();

                        double centerX = x + cellSize / 2.0;
                        double centerY = y + cellSize / 2.0;
                        double angle = 0;

                        switch (cellule.getOrientation()) {
                        case EAST:  angle = 0; break;                        // EAST = 0 rad
                        case SOUTH: angle = Math.PI / 2; break;              // SOUTH = 90°
                        case WEST:  angle = Math.PI; break;                  // WEST = 180°
                        case NORTH: angle = -Math.PI / 2; break;             // NORTH = -90°
                        default: break;
                    }


                        g2d.translate(centerX, centerY);
                        g2d.rotate(angle);
                        g2d.drawImage(img, -cellSize / 2, -cellSize / 2, cellSize, cellSize, this);
                        g2d.setTransform(oldTransform);

                    }
                }

                // Bordure
                g.setColor(Color.GRAY);
                g.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void mettreAJourGrille() {
        List<Point> positionsACheck = new ArrayList<>();

        for (int j = 0; j < tableau.getHauteur(); j++) {
            for (int i = 0; i < tableau.getlargeur(); i++) {
                Cellule c = tableau.getCellule(i, j);
                if (c != null && !estCelluleFixe(c)) {
                    positionsACheck.add(new Point(i, j));
                }
            }
        }

        // Trier selon l'orientation
        positionsACheck.sort((a, b) -> {
            Cellule ca = tableau.getCellule(a.x, a.y);
            Cellule cb = tableau.getCellule(b.x, b.y);
            if (ca == null || cb == null) return 0;
            Orientation oa = ca.getOrientation();
            Orientation ob = cb.getOrientation();

            if (oa == Orientation.EAST && ob == Orientation.EAST) return Integer.compare(b.x, a.x);
            if (oa == Orientation.WEST && ob == Orientation.WEST) return Integer.compare(a.x, b.x);
            if (oa == Orientation.NORTH && ob == Orientation.NORTH) return Integer.compare(a.y, b.y);
            if (oa == Orientation.SOUTH && ob == Orientation.SOUTH) return Integer.compare(b.y, a.y);

            return 0;
        });

        for (Point p : positionsACheck) {
            int i = p.x;
            int j = p.y;
            Cellule courante = tableau.getCellule(i, j);
            if (courante == null) continue;

            if (courante.isSpinnerDevant(courante, tableau)) {
                courante.interagirAvecSpinner(courante, tableau);
            }

            if (courante.getType() == TypeCell.MOVE) {
                tableau.deplacerCellule(i, j);
                tableau.pousserCellule(i, j);
                tableau.avancerEtPousser(i, j);
            }
        }

        repaint();
    }

    private boolean estCelluleFixe(Cellule c) {
        return c.getType() == TypeCell.SPINNER_DIRECT
            || c.getType() == TypeCell.SPINNER_INDIRECT
            || c.getType() == TypeCell.DIRECTIONAL
            || c.getType() == TypeCell.SLIDE
            || c.getType() == TypeCell.PUSHER;
    }

    public static void main(String[] args) {
        Tableau tableau = new Tableau(10, 10);
        for (int x = 1; x < 10; x++) {
            for (int y = 1; y < 10; y++) {
                tableau.setZoneConstructible(x, y, true);  // Zone constructible
            }
        }
        tableau.ajouterCellule(new Cellule(TypeCell.MOVE, Action.MOVE, Orientation.NORTH, new Case(2, 9)), 2, 9);
        tableau.ajouterCellule(new Cellule(TypeCell.SPINNER_INDIRECT, Action.ROTATE, Orientation.WEST, new Case(2, 5)), 2, 5);
        //tableau.ajouterCellule(new Cellule(TypeCell.PUSHER, Action.PUSH, Orientation.NORTH, new Case(2, 8)), 2, 8);
        //tableau.ajouterCellule(new Cellule(TypeCell.SLIDE, Action.PUSH, Orientation.NORTH, new Case(2, 2)), 2, 2);
        new GrilleGraphique(tableau);
    }
}