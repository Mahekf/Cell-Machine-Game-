/* import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Cellule {
	
    private TypeCell typeCell;      
    private Action action;  
    public Orientation orientationCell;
    private Case tile;
    private boolean hasMoved = false;
    
    // Constructeur
    public Cellule(TypeCell type, Action action, Orientation orientation, Case tile) {
        this.typeCell = type;
        this.action = action;
        this.orientationCell = orientation; 
        this.tile = tile; 
    }

    // Constructeur 2
    public Cellule(TypeCell type, Action action, Case tile) {
        this.typeCell = type;
        this.action = action;
        this.tile = tile; 
        this.orientationCell = null;
    }

    // Getters
    public TypeCell getType() {
        return typeCell;
    }

    public Action getAction() {
        return action;
    }

    public Orientation getOrientation() {
        return orientationCell;
    }
    public Case getCase() {
        return this.tile;
    }


    // Par Mahek Fatma :  


    //Méthode pour vérifier s'il y a des cellules dans la direction de la cellule 
    public boolean hasCellInDirection(Cellule cell, Tableau tab) {
        if (cell == null || tab == null) return false;
    
        int currentX = cell.tile.getX();
        int currentY = cell.tile.getY();
    
        // Direction de déplacement en fonction de l'orientation de la cellule
        int di = orientationCell.di();  // Déplacement vertical (Nord/Sud)
        int dj = orientationCell.dj();  // Déplacement horizontal (Ouest/Est)
    
        int newX = currentX + di;
        int newY = currentY + dj;
    
        // Vérifier si la position suivante est dans les limites
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            // Vérifier si la cellule est exactement dans la direction et pas dans une direction adjacente
            if (newX == currentX + di && newY == currentY + dj && tab.getCellule(newX, newY) != null) {
                System.out.println("Il y a des cellules dans la meme direction de la " + cell);

                return true; // Il y a une cellule dans la direction exacte
            }
            newX += di;
            newY += dj;
        }
    
        System.out.println("Il n'y a PAS de cellules dans la meme direction");
        return false; // Aucune cellule trouvée dans la direction
    }

    //Méthode qui retourne la celulle qui est en meme direction que la cellule courante  
    public Cellule getFirstCellInDirection(Cellule cell, Tableau tab) {
        if (cell == null || tab == null) return null;
    
        int currentX = cell.tile.getX();
        int currentY = cell.tile.getY();
    
        int di = cell.orientationCell.di();
        int dj = cell.orientationCell.dj();
    
        int newX = currentX + di;
        int newY = currentY + dj;
        int index = 1; // Pour suivre le nombre de cellules parcourues
    
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            Cellule foundCell = tab.getCellule(newX, newY);
            if (foundCell != null) {
                System.out.println("Cellule trouvée dans la meme direction que la " + cell + " " + "est : " + foundCell);
                return foundCell;
            }
            newX += di;
            newY += dj;
            index++;
        }
        System.out.println("Aucune cellule trouvée dans la meme direction que la " + cell);
        return null;
    }

    public boolean hasCellInDirectionOpposite(Cellule cellule, Tableau tab) {
        if (cellule == null || tab == null) return false;
    
        // Récupérer la position de la cellule et l'orientation opposée
        int currentX = cellule.getCase().getX();
        int currentY = cellule.getCase().getY();
        int dirOppose = cellule.getOrientation().turn(2).ordinal(); // Rotation de 180° pour l'orientation opposée
        int di = Orientation.values()[dirOppose].di(); // Déplacement vertical dans la direction opposée
        int dj = Orientation.values()[dirOppose].dj(); // Déplacement horizontal dans la direction opposée
    
        int newX = currentX + di;
        int newY = currentY + dj;
    
        // Vérifier que la nouvelle position est dans les bornes du tableau
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            // Chercher une cellule dans cette direction
            if (tab.getCellule(newX, newY) != null) {
                System.out.println("Il y a une cellule dans la direction opposée de la" + cellule);
                return true; // Une cellule est trouvée dans la direction opposée
            }
            // Continuer à explorer dans la direction opposée
            newX += di;
            newY += dj;
        }
    
        System.out.println("Il n'y a PAS de cellule dans la direction opposée de la " + cellule);
        return false; // Aucune cellule trouvée dans cette direction
    }

    public Cellule getFirstCellInDirectionOpposite(Cellule cellule, Tableau tab) {
        if (cellule == null || tab == null) return null;
    
        // Récupérer la position de la cellule et l'orientation opposée
        int currentX = cellule.getCase().getX();
        int currentY = cellule.getCase().getY();
        int dirOppose = cellule.getOrientation().turn(2).ordinal(); // Rotation de 180° pour l'orientation opposée
        int di = Orientation.values()[dirOppose].di(); // Déplacement vertical dans la direction opposée
        int dj = Orientation.values()[dirOppose].dj(); // Déplacement horizontal dans la direction opposée
    
        int newX = currentX + di;
        int newY = currentY + dj;
    
        // Vérifier que la nouvelle position est dans les bornes du tableau
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            Cellule foundCell = tab.getCellule(newX, newY);
            if (foundCell != null) {
                System.out.println("Cellule trouvée dans la direction opposée  de la" + " " + cellule + "est : " + foundCell);
                return foundCell; // Retourner la première cellule trouvée dans la direction opposée
            }
            // Continuer à explorer dans la direction opposée
            newX += di;
            newY += dj;
        }
    
        System.out.println("Aucune cellule trouvée dans la direction opposée de la " + cellule);
        return null; // Aucune cellule trouvée dans cette direction
    }

    // Vérifie s'il y a une cellule à côté de "celluleCourante"
    public boolean hasCellCote(Tableau tab, Cellule celluleCourante) {
    int x = celluleCourante.getCase().getX();
    int y = celluleCourante.getCase().getY();
    Orientation orientation = celluleCourante.getOrientation();

    int newX = x + orientation.dj();
    int newY = y + orientation.di();

    // Vérifier si la position est dans les limites de la grille
    if (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
        Cellule celluleVoisine = tab.getCellule(newX, newY);
        
        // Vérifier si la cellule voisine existe et a la même orientation
        return celluleVoisine != null; // + && celluleVoisine.getOrientation().equals(orientation)
    }
    return false; 
}

    public boolean hasCollisionWith(Tableau tab, Cellule celluleDeplacee) {
        
        int newX = celluleDeplacee.getCase().getX() + celluleDeplacee.getOrientation().dj(); // dj = déplacement horizontal (x)
        int newY = celluleDeplacee.getCase().getY() + celluleDeplacee.getOrientation().di(); // di = déplacement vertical (y)
            if (tab.getCellule(newX, newY).getTypCell() == TypeCell.TELEPORTOR) {
            System.out.println("Attention une collision");
            return true; 
        } else {

        System.out.println("Pas de collision");
        return false; 
       }
    }

    // Cellule Ennemy & Trash 
    public void remove(Tableau tab, Cellule Cellcourante, Cellule Celldeplacee) { 

        if (Cellcourante.getType() != TypeCell.ENEMY || Cellcourante.getType() != TypeCell.TRASH) {
            return; 
        }
           if ((Cellcourante.getType() == TypeCell.ENEMY || Cellcourante.getType() == TypeCell.TRASH) && (hasCollisionWith(tab, Celldeplacee))) {
            //La cellule ENNEMY/TRASH se suppriment lorsqu'il y a une collision 
            tab.retirerCellule(Cellcourante.getCase().getX(), Cellcourante.getCase().getY());
            //De meme, la cellule déplacée (Ex : MOVE) se supprime aussi 
            tab.retirerCellule(Celldeplacee.getCase().getX(), Celldeplacee.getCase().getY());
            System.out.println("Cellule ENEMY est mort !!"); 
        }
    }

    //Permet de générer 2 nombre aléatoire 
        public static int[] random(int largeur, int hauteur) {
        Random random = new Random();
        int x = random.nextInt(largeur);  // Génère un nombre entre 0 et largeur-1
        int y = random.nextInt(hauteur);  // Génère un nombre entre 0 et hauteur-1
        return new int[]{x, y};
    }

   // Cellul Teleportor  
    public void Teleporter(Tableau tab, Cellule CellTelep, Cellule Celldeplacee) {
        if (CellTelep.getType() != TypeCell.TELEPORTOR) {
            return; // Rien à faire si ce n'est pas un téléporteur
        }
        // Vérifier que la cellule déplacée existe avant toute opération
        if (Celldeplacee == null) {
        System.out.println("ERREUR: Cellule deplacée est NULL !");
        return;
    }
        //Si les deux dont teleporteur 
        if (CellTelep.getType() == TypeCell.TELEPORTOR && Celldeplacee.getType() == TypeCell.TELEPORTOR && (hasCollisionWith(tab, Celldeplacee))) {
            return; //Aucune action car le but de cette cellule est de téléporter d'autres types de cellules et non pas eux meme 
        }
        if (CellTelep.getType() == TypeCell.TELEPORTOR && (hasCollisionWith(tab, Celldeplacee))) {
            System.out.println("Téléportation en cours de " + Celldeplacee);
            tab.retirerCellule(Celldeplacee.getCase().getX(), Celldeplacee.getCase().getY());
            System.out.println("Cellule retirée avec succès"); 
           
            // Avoir les zones constructibles 
            int [] nmbreZC = tab.getNombreColonnesLignesConstructibles();
            int [] xy = random(nmbreZC[0], nmbreZC[1]); 
            int i = xy[1]; //1er élément -- largeur
            int j = xy[0]; // 2e élément -- hauteur 

        if (i <= tab.getlargeur() && j <= tab.getHauteur() && tab.estPositionValide(i, j) && j != Celldeplacee.getCase().getY() && i != Celldeplacee.getCase().getX() && tab.getCellule(i, j) == null && tab.estConstructible(i, j)) {
            tab.ajouterCelluleSansVerification(Celldeplacee, i, j);
            System.out.println("La " + Celldeplacee + " est téléporté en position : " + j + ";" + i);
        }
    }
}

// Méthodes pour l'algorithme de déplacement =:

 public boolean estDeplacableHorizantale() {
    if ((this.getTypCell() == TypeCell.MOVE || 
    this.getTypCell() == TypeCell.DIRECTIONAL || 
    this.getTypCell() == TypeCell.SLIDE)  &&
    (this.getOrientation() == Orientation.EAST || this.getOrientation() == Orientation.WEST)) {
        return true;
    }
    if (this.getTypCell() == TypeCell.PUSHER) {
        return true; // Orientation ignorée pour PUSHER
    }
    return false; 
}

public boolean estDeplacableVerticale() {
    if ((this.getTypCell() == TypeCell.MOVE || 
    this.getTypCell() == TypeCell.DIRECTIONAL || 
    this.getTypCell() == TypeCell.SLIDE)  &&
    (this.getOrientation() == Orientation.NORTH || this.getOrientation() == Orientation.SOUTH)) {
        return true;
    }
    if (this.getTypCell() == TypeCell.PUSHER) {
        return true; // Orientation ignorée pour PUSHER
    }
    return false;
}

// Traitement Horizantale
public static void moveHorizantale(Tableau grid) {
        for (int row = 0; row < grid.getHauteur(); row++) {
            for (int col = 0; col < grid.getlargeur(); col++) {
                Cellule cell = grid.getCellule(col, row);
                if (cell != null && cell.estDeplacableHorizantale()) {
                    List<Cellule> segment = getHorizontalSegment(row, col, grid);
                    if (!segment.isEmpty()) {
                        traiterSegmentHorizantal(segment, grid, row, col);
                        //col += segment.size() - 1; // sauter le segment traité
                        return;
                    }
                }
            }
        }
    }

// Extrait un segment de cellules déplaçables sur la ligne "row" à partir de la colonne "startCol"
    private static List<Cellule> getHorizontalSegment(int row, int startCol, Tableau grid) {
        List<Cellule> segment = new ArrayList<>();
        for (int col = startCol; col < grid.getlargeur(); col++) {
            Cellule c = grid.getCellule(col, row);
            if (c == null || !c.estDeplacableHorizantale()) break;
            segment.add(c);
        }
        return segment;
    }

    // Applique la logique de poids sur un segment pour déplacer ses cellules
    private static void traiterSegmentHorizantal(List<Cellule> segment, Tableau grid, int row, int startCol) {
        List<Integer> poidsPrefixes = new ArrayList<>();
        int poids = 0;

        // Calcul des poids cumulés
        for (Cellule cell : segment) {
            if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.EAST) poids++;
            else if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.WEST) poids--;
            poidsPrefixes.add(poids);
        }

        // Cas 1 : plus grand préfixe de poids < 0 se terminant par '<'
        int indexNegatif = -1;
        for (int i = 0; i < poidsPrefixes.size(); i++) {
            if (poidsPrefixes.get(i) < 0
                && segment.get(i).getTypCell() == TypeCell.MOVE
                && segment.get(i).getOrientation() == Orientation.WEST) {
                indexNegatif = i;
            }
            System.out.println("Poids des préfixes : " + poidsPrefixes);
        }
        if (indexNegatif != -1) {
            // déplacer le préfixe vers la gauche
            for (int i = 0; i <= indexNegatif; i++) {
                moveLeft(grid, row, startCol + i);
            }
            // traiter récursivement le suffixe
            if (indexNegatif + 1 < segment.size()) {
                traiterSegmentHorizantal(
                    segment.subList(indexNegatif + 1, segment.size()),
                    grid,
                    row,
                    startCol + indexNegatif + 1
                );
            }
            return;
        }

        // Cas 2/3 : tous les poids >= 0
        int indexZero = -1;
        for (int i = 0; i < poidsPrefixes.size(); i++) {
            if (poidsPrefixes.get(i) == 0) indexZero = i;
        }
        // suffixe vers la droite
        for (int i = indexZero + 1; i < segment.size(); i++) {
            moveRight(grid, row, startCol + i);
        }
    } 

       private static void traiterUnPasSegmentHorizontal(List<Cellule> segment, Tableau grid, int row, int startCol) {
        int poids = 0;
        int indexNeg = -1;
        // calcul et recherche du premier pusher '<' négatif
        for (int i = 0; i < segment.size(); i++) {
            Cellule c = segment.get(i);
            if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.EAST) poids++;
            else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.WEST) poids--;
            if (poids < 0 && c.getOrientation() == Orientation.WEST) {
                indexNeg = i;
                break;
            }
        }
        if (indexNeg != -1) {
            // déplacer le préfixe entier d'une case à gauche
            for (int i = 0; i <= indexNeg; i++) moveLeft(grid, row, startCol + i);
        } else {
            // trouver le début du suffixe (dernier indice où poids==0)
            int sum = 0, idxZero = -1;
            for (int i = 0; i < segment.size(); i++) {
                Cellule c = segment.get(i);
                if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.EAST) sum++;
                else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.WEST) sum--;
                if (sum == 0) idxZero = i;
            }
            if (idxZero + 1 < segment.size()) {
                // déplacer seulement la première cellule du suffixe vers la droite
                moveRight(grid, row, startCol + idxZero + 1);
            }
        }
    }


    // Déplace la cellule vers la gauche si possible
    private static void moveLeft(Tableau grid, int row, int col) {
        if (col > 0 && grid.estPositionValide(col - 1, row)) {
            Cellule c = grid.getCellule(col, row);
            grid.setCellule(col - 1, row, c);
            grid.setCellule(col, row, null);
        }
    }
    
    // Déplace la cellule vers la droite + TUER L'ENNEMY
    private static void moveRight(Tableau grid, int row, int col) {
    int targetCol = col + 1;
    // Vérifie qu'on reste dans la grille
    if (targetCol < grid.getlargeur() && grid.estPositionValide(targetCol, row)) {
        // Si la case de destination contient un ennemi, on le supprime d'abord
        Cellule cible = grid.getCellule(targetCol, row);
        if (cible != null && cible.getType() == TypeCell.ENEMY) {
            grid.setCellule(targetCol, row, null);
            // (Optionnel) Tu peux aussi déclencher un effet sonore ou visuel ici
        }
        // Puis on déplace la cellule courante
        Cellule c = grid.getCellule(col, row);
        grid.setCellule(targetCol, row, c);
        grid.setCellule(col, row, null);
    }
}


// Traitement Verticale
 public static void moveVertical(Tableau grid) {
        for (int col = 0; col < grid.getlargeur(); col++) {
            for (int row = 0; row < grid.getHauteur(); row++) {
                Cellule cell = grid.getCellule(col, row);
                if (cell != null && cell.estDeplacableVerticale()) {
                    List<Cellule> segment = getVerticalSegment(col, row, grid);
                    if (!segment.isEmpty()) {
                        traiterSegmentVertical(segment, grid, col, row);
                        //row += segment.size() - 1;
                        return;
                    }
                }
            }
        }
    }

    private static List<Cellule> getVerticalSegment(int col, int startRow, Tableau grid) {
        List<Cellule> segment = new ArrayList<>();
        for (int r = startRow; r < grid.getHauteur(); r++) {
            Cellule cell = grid.getCellule(col, r);
            if (cell == null || !cell.estDeplacableVerticale()) break;
            segment.add(cell);
        }
        return segment;
    }

    private static void traiterSegmentVertical(List<Cellule> segment, Tableau grid, int col, int startRow) {
        List<Integer> poids = new ArrayList<>();
        int sum = 0;
        for (Cellule cell : segment) {
            if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.SOUTH) sum++;
            else if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.NORTH) sum--;
            poids.add(sum);
        }

        int idxNeg = -1;
        for (int i = 0; i < poids.size(); i++) {
            if (poids.get(i) < 0 && segment.get(i).getOrientation() == Orientation.NORTH) {
                idxNeg = i;
            }
        }
        if (idxNeg != -1) {
            for (int i = 0; i <= idxNeg; i++) moveUp(grid, col, startRow + i);
            if (idxNeg + 1 < segment.size()) {
                traiterSegmentVertical(
                    segment.subList(idxNeg + 1, segment.size()),
                    grid,
                    col,
                    startRow + idxNeg + 1
                );
            }
            return;
        }

        int idxZero = -1;
        for (int i = 0; i < poids.size(); i++) if (poids.get(i) == 0) idxZero = i;
        for (int i = idxZero + 1; i < segment.size(); i++) moveDown(grid, col, startRow + i);
    } 

    private static void traiterUnPasSegmentVertical(List<Cellule> segment, Tableau grid, int col, int startRow) {
        int sum = 0;
        int idxNeg = -1;
        for (int i = 0; i < segment.size(); i++) {
            Cellule c = segment.get(i);
            if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.SOUTH) sum++;
            else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.NORTH) sum--;
            if (sum < 0 && c.getOrientation() == Orientation.NORTH) {
                idxNeg = i;
                break;
            }
        }
        if (idxNeg != -1) {
            for (int i = 0; i <= idxNeg; i++) moveUp(grid, col, startRow + i);
        } else {
            int idxZero = -1;
            sum = 0;
            for (int i = 0; i < segment.size(); i++) {
                Cellule c = segment.get(i);
                if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.SOUTH) sum++;
                else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.NORTH) sum--;
                if (sum == 0) idxZero = i;
            }
            if (idxZero + 1 < segment.size()) moveDown(grid, col, startRow + idxZero + 1);
        }
    }


    private static void moveUp(Tableau g, int col, int row) {
        if (row > 0 && g.estPositionValide(col, row - 1)) {
            Cellule c = g.getCellule(col, row);
            g.setCellule(col, row - 1, c);
            g.setCellule(col, row, null);
        }
    }
    private static void moveDown(Tableau g, int col, int row) {
        if (row < g.getHauteur() - 1 && g.estPositionValide(col, row + 1)) {
            Cellule c = g.getCellule(col, row);
            g.setCellule(col, row + 1, c);
            g.setCellule(col, row, null);
        }
    }
// Appliquer les deux enchaînés
    public static void moveBoth(Tableau grid) {
        moveHorizantale(grid);
        moveVertical(grid);
    }
    public boolean estDeplacable() {
      if(this.getType() == typeCell.MOVE || this.getType() == typeCell.PUSHER || this.getType() == typeCell.DIRECTIONAL || this.getType() == typeCell.SLIDE) {
        return true; 
      } else {
        return false;
      }
    }

 //-- Par Rime : 

    public void setCase(Case c) {
        this.tile = c;
    }
    // Méthodes pour l'enum "ACTION"
    public void deplacer() {
        if (tile == null) {
            System.out.println("Erreur : la cellule n'est pas positionnée sur une case.");
            return;
        }

        int newX = tile.getX() + orientationCell.dj();
        int newY = tile.getY() + orientationCell.di();
        tile = new Case(newX, newY);
    }

    private void pousser(Cellule pusher, Cellule courante) {
        if (isPusherDevant(pusher, courante)) {
            pusher.deplacer();
            courante.deplacer();
        } else {
            System.out.println("Le pusher est seul et ne peut pas se déplacer.");
        }
    }

    private void slider(Cellule slide, Cellule courante) {
        if (isPusherDevant(slide, courante) && isVertical(slide) && isVertical(courante)) {
            slide.deplacer();
            courante.deplacer();
        } else {
            System.out.println("Le slide bloque.");
        }
    }

    public void directional(Cellule dir, Cellule courante) {
        if (isPusherDevant(dir, courante) && dir.getOrientation()==courante.getOrientation()) {
            dir.deplacer();
            courante.deplacer();
        } else {
            System.out.println("Le directional bloque.");
        }
    }

    public boolean isVertical(Cellule slide) {
        return slide.getOrientation() == Orientation.NORTH || slide.getOrientation() == Orientation.SOUTH;
    }

    public boolean isHorizontal(Cellule slide) {
        return slide.getOrientation() == Orientation.WEST || slide.getOrientation() == Orientation.EAST;
    }
    public boolean memeAxe(Cellule c1, Cellule c2) {
        return (isVertical(c1) && isVertical(c2)) || (isHorizontal(c1) && isHorizontal(c2));
    }

    public boolean isPusherDevant(Cellule pusher, Cellule courante) {
        if (pusher.getType() != TypeCell.PUSHER) {
            return false;
        }

        Orientation orientation = courante.getOrientation(); // Utilisation de l'orientation propre à chaque cellule
        
        return (pusher.tile.getX() == courante.tile.getX() + orientation.dj()) &&
               (pusher.tile.getY() == courante.tile.getY() + orientation.di());
    }

    
    
    // Action ROTATE
    public void tourner() {
        if (typeCell == TypeCell.SPINNER_DIRECT||typeCell == TypeCell.SPINNER_INDIRECT) {
            orientationCell = orientationCell.turn(1);
        } else if (typeCell == TypeCell.SPINNER_INDIRECT) {
            orientationCell = orientationCell.turn(-1);
        } else {
            System.out.println("La cellule n'est pas un spinner et ne peut pas tourner.");
        }
    }

    public void tourner(int k) {
        for (int i = 0; i < k; i++) {
            tourner();
        }
    }

      public void interagirAvecSpinner(Cellule courante, Tableau tableau) {
        int targetX = courante.tile.getX() + courante.getOrientation().dj();
        int targetY = courante.tile.getY() + courante.getOrientation().di();

        Cellule spinner = tableau.getCellule(targetX, targetY);
        if (spinner != null && (spinner.getType() == TypeCell.SPINNER_DIRECT || spinner.getType() == TypeCell.SPINNER_INDIRECT)) {
            if (spinner.getType() == TypeCell.SPINNER_DIRECT) {
                courante.setOrientation(courante.getOrientation().turn(1));
            } else {
                courante.setOrientation(courante.getOrientation().turn(-1));
            }
        }
    } 

    public boolean isSlideDevant(Cellule slide, Cellule courante) {
        if (slide.getType() != TypeCell.SLIDE) {
            return false;
        }

        Orientation orientation = courante.getOrientation();
        
        return (slide.tile.getX() == courante.tile.getX() + orientation.dj()) &&
               (slide.tile.getY() == courante.tile.getY() + orientation.di());
    }
    public boolean isDirectionalDevant(Cellule directional, Cellule courante) {
        if (directional.getType() != TypeCell.DIRECTIONAL) {
            return false;
        }

        Orientation orientation = courante.getOrientation();
        
        return (directional.tile.getX() == courante.tile.getX() + orientation.dj()) &&
               (directional.tile.getY() == courante.tile.getY() + orientation.di());
    }
    
    
    
     public boolean isSpinnerDevant(Cellule courante, Tableau tableau) {
        // Déterminer la position devant la cellule courante
        int targetX = courante.tile.getX() + courante.getOrientation().dj();
        int targetY = courante.tile.getY() + courante.getOrientation().di();

        // Vérifier si la cellule devant est un spinner
        Cellule spinner = tableau.getCellule(targetX, targetY);
        return spinner != null && (spinner.getType() == TypeCell.SPINNER_DIRECT || spinner.getType() == TypeCell.SPINNER_INDIRECT);
    } 

    public boolean aDejaBouge() {
        return hasMoved;
    }

    public void marquerCommeBougee() {
        this.hasMoved = true;
    }

    public void reinitialiserDeplacement() {
        this.hasMoved = false;
    }

     public boolean suivantePusherPossible(Cellule courante, Cellule suivante) {
    	return courante.getAction()==Action.PUSH&&suivante.getType()==null||suivante.estDeplacable();
    }

   //-- Par Ghania : 
    
  //Action DUPLICATE : 
    // Méthode pour effectuer la duplication des cellules
    public void dupliquer(Tableau tableau, int x, int y) {
    	// Vérifier si la cellule à gauche du générateur existe
        int targetX = x - 1;
        
        Tableau tablea1;
        if (targetX >= 0 && targetX < tableau.getlargeur() && y >= 0 && y < tableau.getHauteur()) {
            Cellule celluleAGauche = tableau.getCellule(x - 1, y);
            if (celluleAGauche != null) {
                // Chercher une place vide à droite
                int newX = x + 1;
                while (newX < tableau.getlargeur() && tableau.getCellule(newX, y) != null) {
                    newX++;  
                }
                
                if (newX < tableau.getlargeur()) {
                    // Si une place vide à droite a été trouvée, ajouter la copie
                    ajouterCopie(tableau, celluleAGauche, newX, y);
                } else {
                    // Si aucune place à droite, essayer de déplacer les cellules vers la droite
                    if (!decalerVersDroite(tableau, x, y)) {
                        // Si le décalage vers la droite échoue, essayer de déplacer vers la gauche
                        decalerVersGauche(tableau, x, y);
                    }
                }
            }
        }
    }
    
 // Méthode pour ajouter la copie de la cellule
    private void ajouterCopie(Tableau tableau, Cellule celluleAGauche, int x, int y) {
    	Case caseCopie = new Case(0, 0); // Crée une Case à la position (0, 0)
        //ICI modifié par Mahek Fatma ----
    	Cellule copie = new Cellule(celluleAGauche.getTypCell(), celluleAGauche.getAction(), celluleAGauche.getOrientation(), caseCopie);
        tableau.ajouterCelluleSansVerification(copie, x, y); 
        // ----
    }

    // Méthode pour déplacer les cellules vers la droite
    private boolean decalerVersDroite(Tableau tableau, int x, int y) {
        int shiftRight = x + 1;
        while (shiftRight < tableau.getlargeur() && tableau.getCellule(shiftRight, y) == null) {
            shiftRight++;
        }
        
        if (shiftRight < tableau.getlargeur()) {
            // Déplacer les cellules vers la droite
        	for (int i = x; i >= 0; i--) {
                Cellule currentCell = tableau.getCellule(i, y);
                if (currentCell != null) {
                    tableau.ajouterCellule(currentCell, i + 1, y);
                }
            }
            // Ajouter la copie
            for (int i = x; i < tableau.getlargeur(); i++) {
            ajouterCopie(tableau, tableau.getCellule(x - 1, y), x + 1, y);
            return true;
        }}
        return false;
    }

    // Méthode pour déplacer les cellules vers la gauche
    private void decalerVersGauche(Tableau tableau, int x, int y) {
        int shiftLeft = x - 1;
        while (shiftLeft >= 0 && tableau.getCellule(shiftLeft, y) == null) {
            shiftLeft--;
        }

        if (shiftLeft >= 0) {
            // Déplacer les cellules vers la gauche
            for (int i = x; i >= 0; i--) {
                Cellule currentCell = tableau.getCellule(i, y);
                if (currentCell != null) {
                    tableau.ajouterCellule(currentCell, i - 1, y);
                }
            }
            // Ajouter la copie
            for (int i = x; i < tableau.getlargeur(); i++) {
            	 ajouterCopie(tableau, tableau.getCellule(x - 1, y), x - 1, y);
            }
           
        }
    }
 


    // Getters pour la position
    public Case getTile() {
        return this.tile;
    }

    public TypeCell getTypCell() {
    return this.typeCell; 
    }

    @Override
    public String toString() {
        return "Cellule {" + typeCell + ", " + action + ", " + orientationCell + "}";
    }
    public void setOrientation(Orientation orientation) {
        this.orientationCell = orientation;  // Mise à jour de l'orientation de la cellule
    }
} */

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Cellule {
	
    private TypeCell typeCell;      
    private Action action;  
    public Orientation orientationCell;
    private Case tile;
    private boolean hasMoved = false;
    
    // Constructeur
    public Cellule(TypeCell type, Action action, Orientation orientation, Case tile) {
        this.typeCell = type;
        this.action = action;
        this.orientationCell = orientation; 
        this.tile = tile; 
    }

    // Constructeur 2
    public Cellule(TypeCell type, Action action, Case tile) {
        this.typeCell = type;
        this.action = action;
        this.tile = tile; 
        this.orientationCell = null;
    }

    // Getters
    public TypeCell getType() {
        return typeCell;
    }

    public Action getAction() {
        return action;
    }

    public Orientation getOrientation() {
        return orientationCell;
    }
    public Case getCase() {
        return this.tile;
    }


    // Par Mahek Fatma :  


    //Méthode pour vérifier s'il y a des cellules dans la direction de la cellule 
    public boolean hasCellInDirection(Cellule cell, Tableau tab) {
        if (cell == null || tab == null) return false;
    
        int currentX = cell.tile.getX();
        int currentY = cell.tile.getY();
    
        // Direction de déplacement en fonction de l'orientation de la cellule
        int di = orientationCell.di();  // Déplacement vertical (Nord/Sud)
        int dj = orientationCell.dj();  // Déplacement horizontal (Ouest/Est)
    
        int newX = currentX + di;
        int newY = currentY + dj;
    
        // Vérifier si la position suivante est dans les limites
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            // Vérifier si la cellule est exactement dans la direction et pas dans une direction adjacente
            if (newX == currentX + di && newY == currentY + dj && tab.getCellule(newX, newY) != null) {
                System.out.println("Il y a des cellules dans la meme direction de la " + cell);

                return true; // Il y a une cellule dans la direction exacte
            }
            newX += di;
            newY += dj;
        }
    
        System.out.println("Il n'y a PAS de cellules dans la meme direction");
        return false; // Aucune cellule trouvée dans la direction
    }

    //Méthode qui retourne la celulle qui est en meme direction que la cellule courante  
    public Cellule getFirstCellInDirection(Cellule cell, Tableau tab) {
        if (cell == null || tab == null) return null;
    
        int currentX = cell.tile.getX();
        int currentY = cell.tile.getY();
    
        int di = cell.orientationCell.di();
        int dj = cell.orientationCell.dj();
    
        int newX = currentX + di;
        int newY = currentY + dj;
        int index = 1; // Pour suivre le nombre de cellules parcourues
    
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            Cellule foundCell = tab.getCellule(newX, newY);
            if (foundCell != null) {
                System.out.println("Cellule trouvée dans la meme direction que la " + cell + " " + "est : " + foundCell);
                return foundCell;
            }
            newX += di;
            newY += dj;
            index++;
        }
        System.out.println("Aucune cellule trouvée dans la meme direction que la " + cell);
        return null;
    }

    public boolean hasCellInDirectionOpposite(Cellule cellule, Tableau tab) {
        if (cellule == null || tab == null) return false;
    
        // Récupérer la position de la cellule et l'orientation opposée
        int currentX = cellule.getCase().getX();
        int currentY = cellule.getCase().getY();
        int dirOppose = cellule.getOrientation().turn(2).ordinal(); // Rotation de 180° pour l'orientation opposée
        int di = Orientation.values()[dirOppose].di(); // Déplacement vertical dans la direction opposée
        int dj = Orientation.values()[dirOppose].dj(); // Déplacement horizontal dans la direction opposée
    
        int newX = currentX + di;
        int newY = currentY + dj;
    
        // Vérifier que la nouvelle position est dans les bornes du tableau
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            // Chercher une cellule dans cette direction
            if (tab.getCellule(newX, newY) != null) {
                System.out.println("Il y a une cellule dans la direction opposée de la" + cellule);
                return true; // Une cellule est trouvée dans la direction opposée
            }
            // Continuer à explorer dans la direction opposée
            newX += di;
            newY += dj;
        }
    
        System.out.println("Il n'y a PAS de cellule dans la direction opposée de la " + cellule);
        return false; // Aucune cellule trouvée dans cette direction
    }

    public Cellule getFirstCellInDirectionOpposite(Cellule cellule, Tableau tab) {
        if (cellule == null || tab == null) return null;
    
        // Récupérer la position de la cellule et l'orientation opposée
        int currentX = cellule.getCase().getX();
        int currentY = cellule.getCase().getY();
        int dirOppose = cellule.getOrientation().turn(2).ordinal(); // Rotation de 180° pour l'orientation opposée
        int di = Orientation.values()[dirOppose].di(); // Déplacement vertical dans la direction opposée
        int dj = Orientation.values()[dirOppose].dj(); // Déplacement horizontal dans la direction opposée
    
        int newX = currentX + di;
        int newY = currentY + dj;
    
        // Vérifier que la nouvelle position est dans les bornes du tableau
        while (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
            Cellule foundCell = tab.getCellule(newX, newY);
            if (foundCell != null) {
                System.out.println("Cellule trouvée dans la direction opposée  de la" + " " + cellule + "est : " + foundCell);
                return foundCell; // Retourner la première cellule trouvée dans la direction opposée
            }
            // Continuer à explorer dans la direction opposée
            newX += di;
            newY += dj;
        }
    
        System.out.println("Aucune cellule trouvée dans la direction opposée de la " + cellule);
        return null; // Aucune cellule trouvée dans cette direction
    }

    // Vérifie s'il y a une cellule à côté de "celluleCourante"
    public boolean hasCellCote(Tableau tab, Cellule celluleCourante) {
    int x = celluleCourante.getCase().getX();
    int y = celluleCourante.getCase().getY();
    Orientation orientation = celluleCourante.getOrientation();

    int newX = x + orientation.dj();
    int newY = y + orientation.di();

    // Vérifier si la position est dans les limites de la grille
    if (newX >= 0 && newX < tab.getlargeur() && newY >= 0 && newY < tab.getHauteur()) {
        Cellule celluleVoisine = tab.getCellule(newX, newY);
        
        // Vérifier si la cellule voisine existe et a la même orientation
        return celluleVoisine != null; // + && celluleVoisine.getOrientation().equals(orientation)
    }
    return false; 
}

    public boolean hasCollisionWith(Tableau tab, Cellule celluleDeplacee) {
        
        int newX = celluleDeplacee.getCase().getX() + celluleDeplacee.getOrientation().dj(); // dj = déplacement horizontal (x)
        int newY = celluleDeplacee.getCase().getY() + celluleDeplacee.getOrientation().di(); // di = déplacement vertical (y)
            if (tab.getCellule(newX, newY).getTypCell() == TypeCell.TELEPORTOR) {
            System.out.println("Attention une collision");
            return true; 
        } else {

        System.out.println("Pas de collision");
        return false; 
       }
    }

    // Cellule Ennemy & Trash 
    public void remove(Tableau tab, Cellule Cellcourante, Cellule Celldeplacee) { 

        if (Cellcourante.getType() != TypeCell.ENEMY || Cellcourante.getType() != TypeCell.TRASH) {
            return; 
        }
           if ((Cellcourante.getType() == TypeCell.ENEMY || Cellcourante.getType() == TypeCell.TRASH) && (hasCollisionWith(tab, Celldeplacee))) {
            //La cellule ENNEMY/TRASH se suppriment lorsqu'il y a une collision 
            tab.retirerCellule(Cellcourante.getCase().getX(), Cellcourante.getCase().getY());
            //De meme, la cellule déplacée (Ex : MOVE) se supprime aussi 
            tab.retirerCellule(Celldeplacee.getCase().getX(), Celldeplacee.getCase().getY());
            System.out.println("Cellule ENEMY est mort !!"); 
        }
    }

    //Permet de générer 2 nombre aléatoire 
        public static int[] random(int largeur, int hauteur) {
        Random random = new Random();
        int x = random.nextInt(largeur);  // Génère un nombre entre 0 et largeur-1
        int y = random.nextInt(hauteur);  // Génère un nombre entre 0 et hauteur-1
        return new int[]{x, y};
    }

   // Cellul Teleportor  
    public void Teleporter(Tableau tab, Cellule CellTelep, Cellule Celldeplacee) {
        if (CellTelep.getType() != TypeCell.TELEPORTOR) {
            return; // Rien à faire si ce n'est pas un téléporteur
        }
        // Vérifier que la cellule déplacée existe avant toute opération
        if (Celldeplacee == null) {
        System.out.println("ERREUR: Cellule deplacée est NULL !");
        return;
    }
        //Si les deux dont teleporteur 
        if (CellTelep.getType() == TypeCell.TELEPORTOR && Celldeplacee.getType() == TypeCell.TELEPORTOR && (hasCollisionWith(tab, Celldeplacee))) {
            return; //Aucune action car le but de cette cellule est de téléporter d'autres types de cellules et non pas eux meme 
        }
        if (CellTelep.getType() == TypeCell.TELEPORTOR && (hasCollisionWith(tab, Celldeplacee))) {
            System.out.println("Téléportation en cours de " + Celldeplacee);
            tab.retirerCellule(Celldeplacee.getCase().getX(), Celldeplacee.getCase().getY());
            System.out.println("Cellule retirée avec succès"); 
           
            // Avoir les zones constructibles 
            int [] nmbreZC = tab.getNombreColonnesLignesConstructibles();
            int [] xy = random(nmbreZC[0], nmbreZC[1]); 
            int i = xy[1]; //1er élément -- largeur
            int j = xy[0]; // 2e élément -- hauteur 

        if (i <= tab.getlargeur() && j <= tab.getHauteur() && tab.estPositionValide(i, j) && j != Celldeplacee.getCase().getY() && i != Celldeplacee.getCase().getX() && tab.getCellule(i, j) == null) { //  && tab.estConstructible(i, j)
            tab.ajouterCelluleSansVerification(Celldeplacee, i, j);
            System.out.println("La " + Celldeplacee + " est téléporté en position : " + j + ";" + i);
        }
    }
}

// Méthodes pour l'algorithme de déplacement =:

public boolean estDeplacableHorizantale() {
    if ((this.getTypCell() == TypeCell.MOVE || 
    this.getTypCell() == TypeCell.DIRECTIONAL || this.getTypCell() == TypeCell.GENERATOR||
    this.getTypCell() == TypeCell.SLIDE)  &&
    (this.getOrientation() == Orientation.EAST || this.getOrientation() == Orientation.WEST)) {
        return true;
    }
    if (this.getTypCell() == TypeCell.PUSHER) {
        return true; // Orientation ignorée pour PUSHER
    }
    return false; 
}



public boolean estDeplacableVerticale() {
    if ((this.getTypCell() == TypeCell.MOVE || 
    this.getTypCell() == TypeCell.DIRECTIONAL ||  this.getTypCell() == TypeCell.GENERATOR||
    this.getTypCell() == TypeCell.SLIDE)  &&
    (this.getOrientation() == Orientation.NORTH || this.getOrientation() == Orientation.SOUTH)) {
        return true;
    }
    if (this.getTypCell() == TypeCell.PUSHER) {
        return true; // Orientation ignorée pour PUSHER
    }
    return false;
}

// Traitement Horizantale
public static void moveHorizantale(Tableau grid) {
        for (int row = 0; row < grid.getHauteur(); row++) {
            for (int col = 0; col < grid.getlargeur(); col++) {
                Cellule cell = grid.getCellule(col, row);
                if (cell != null && cell.estDeplacableHorizantale()) {
                    List<Cellule> segment = getHorizontalSegment(row, col, grid);
                    if (!segment.isEmpty()) {
                        traiterSegmentHorizantal(segment, grid, row, col);
                        //col += segment.size() - 1; // sauter le segment traité
                        return;
                    }
                }
            }
        }
    }

// Extrait un segment de cellules déplaçables sur la ligne "row" à partir de la colonne "startCol"
    private static List<Cellule> getHorizontalSegment(int row, int startCol, Tableau grid) {
        List<Cellule> segment = new ArrayList<>();
        for (int col = startCol; col < grid.getlargeur(); col++) {
            Cellule c = grid.getCellule(col, row);
            if (c == null || !c.estDeplacableHorizantale()) break;
            segment.add(c);
        }
        return segment;
    }

    // Applique la logique de poids sur un segment pour déplacer ses cellules
    private static void traiterSegmentHorizantal(List<Cellule> segment, Tableau grid, int row, int startCol) {
        List<Integer> poidsPrefixes = new ArrayList<>();
        int poids = 0;

        // Calcul des poids cumulés
        for (Cellule cell : segment) {
            if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.EAST) poids++;
            else if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.WEST) poids--;
            poidsPrefixes.add(poids);
        }

        // Cas 1 : plus grand préfixe de poids < 0 se terminant par '<'
        int indexNegatif = -1;
        for (int i = 0; i < poidsPrefixes.size(); i++) {
            if (poidsPrefixes.get(i) < 0
                && segment.get(i).getTypCell() == TypeCell.MOVE
                && segment.get(i).getOrientation() == Orientation.WEST) {
                indexNegatif = i;
            }
            System.out.println("Poids des préfixes : " + poidsPrefixes);
        }
        // déplacer le préfixe vers la gauche
        if (indexNegatif != -1) {
            for (int i = 0; i <= indexNegatif; i++) {
                moveLeft(grid, row, startCol + i);
            }
            // traiter récursivement le suffixe
            if (indexNegatif + 1 < segment.size()) {
                traiterSegmentHorizantal(
                    segment.subList(indexNegatif + 1, segment.size()),
                    grid,
                    row,
                    startCol + indexNegatif + 1
                );
            }
            return;
        }

        // Cas 2/3 : tous les poids >= 0
        int indexZero = -1;
        for (int i = 0; i < poidsPrefixes.size(); i++) {
            if (poidsPrefixes.get(i) == 0) indexZero = i; // La chaine reste immobile 
        }
        // suffixe vers la droite : d'ou on commence par le suffixe i = indexZero
        for (int i = indexZero + 1; i < segment.size(); i++) {
            moveRight(grid, row, startCol + i);
        }
    } 

       private static void traiterUnPasSegmentHorizontal(List<Cellule> segment, Tableau grid, int row, int startCol) {
        int poids = 0;
        int indexNeg = -1;
        // calcul et recherche du premier pusher '<' négatif
        for (int i = 0; i < segment.size(); i++) {
            Cellule c = segment.get(i);
            if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.EAST) poids++;
            else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.WEST) poids--;
            if (poids < 0 && c.getOrientation() == Orientation.WEST) {
                indexNeg = i;
                break;
            }
        }
        if (indexNeg != -1) {
            // déplacer le préfixe entier d'une case à gauche
            for (int i = 0; i <= indexNeg; i++) moveLeft(grid, row, startCol + i);
        } else {
            // trouver le début du suffixe (dernier indice où poids==0)
            int sum = 0, idxZero = -1;
            for (int i = 0; i < segment.size(); i++) {
                Cellule c = segment.get(i);
                if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.EAST) sum++;
                else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.WEST) sum--;
                if (sum == 0) idxZero = i;
            }
            if (idxZero + 1 < segment.size()) {
                // déplacer seulement la première cellule du suffixe vers la droite
                moveRight(grid, row, startCol + idxZero + 1);
            }
        }
    }


    // Déplace la cellule vers la gauche si possible
    private static void moveLeft(Tableau grid, int row, int col) {
        if (col > 0 && grid.estPositionValide(col - 1, row)) {
            Cellule c = grid.getCellule(col, row);
            grid.setCellule(col - 1, row, c);
            grid.setCellule(col, row, null);
        }
    }
    
    // Déplace la cellule vers la droite + TUER L'ENNEMY
    private static void moveRight(Tableau grid, int row, int col) {
    int targetCol = col + 1;
    // Vérifie qu'on reste dans la grille
    if (targetCol < grid.getlargeur() && grid.estPositionValide(targetCol, row)) {
        // Si la case de destination contient un ennemi, on le supprime d'abord
        Cellule cible = grid.getCellule(targetCol, row);
        if (cible != null && cible.getType() == TypeCell.ENEMY) {
            grid.setCellule(targetCol, row, null);
            // (Optionnel) Tu peux aussi déclencher un effet sonore ou visuel ici
        }
        // Puis on déplace la cellule courante
        Cellule c = grid.getCellule(col, row);
        grid.setCellule(targetCol, row, c);
        grid.setCellule(col, row, null);
    }
}


// Traitement Verticale
 public static void moveVertical(Tableau grid) {
        for (int col = 0; col < grid.getlargeur(); col++) {
            for (int row = 0; row < grid.getHauteur(); row++) {
                Cellule cell = grid.getCellule(col, row);
                if (cell != null && cell.estDeplacableVerticale()) {
                    List<Cellule> segment = getVerticalSegment(col, row, grid);
                    if (!segment.isEmpty()) {
                        traiterSegmentVertical(segment, grid, col, row);
                        //row += segment.size() - 1;
                        return;
                    }
                }
            }
        }
    }

    private static List<Cellule> getVerticalSegment(int col, int startRow, Tableau grid) {
        List<Cellule> segment = new ArrayList<>();
        for (int r = startRow; r < grid.getHauteur(); r++) {
            Cellule cell = grid.getCellule(col, r);
            if (cell == null || !cell.estDeplacableVerticale()) break;
            segment.add(cell);
        }
        return segment;
    }

    private static void traiterSegmentVertical(List<Cellule> segment, Tableau grid, int col, int startRow) {
        List<Integer> poids = new ArrayList<>();
        int sum = 0;
        for (Cellule cell : segment) {
            if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.SOUTH) sum++;
            else if (cell.getTypCell() == TypeCell.MOVE && cell.getOrientation() == Orientation.NORTH) sum--;
            poids.add(sum);
        }

        int idxNeg = -1;
        for (int i = 0; i < poids.size(); i++) {
            if (poids.get(i) < 0 && segment.get(i).getOrientation() == Orientation.NORTH) {
                idxNeg = i;
            }
        }
        if (idxNeg != -1) {
            for (int i = 0; i <= idxNeg; i++) moveUp(grid, col, startRow + i);
            if (idxNeg + 1 < segment.size()) {
                traiterSegmentVertical(
                    segment.subList(idxNeg + 1, segment.size()),
                    grid,
                    col,
                    startRow + idxNeg + 1
                );
            }
            return;
        }

        int idxZero = -1;
        for (int i = 0; i < poids.size(); i++) if (poids.get(i) == 0) idxZero = i;
        for (int i = idxZero + 1; i < segment.size(); i++) moveDown(grid, col, startRow + i);
    } 

    private static void traiterUnPasSegmentVertical(List<Cellule> segment, Tableau grid, int col, int startRow) {
        int sum = 0;
        int idxNeg = -1;
        for (int i = 0; i < segment.size(); i++) {
            Cellule c = segment.get(i);
            if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.SOUTH) sum++;
            else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.NORTH) sum--;
            if (sum < 0 && c.getOrientation() == Orientation.NORTH) {
                idxNeg = i;
                break;
            }
        }
        if (idxNeg != -1) {
            for (int i = 0; i <= idxNeg; i++) moveUp(grid, col, startRow + i);
        } else {
            int idxZero = -1;
            sum = 0;
            for (int i = 0; i < segment.size(); i++) {
                Cellule c = segment.get(i);
                if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.SOUTH) sum++;
                else if (c.getTypCell() == TypeCell.MOVE && c.getOrientation() == Orientation.NORTH) sum--;
                if (sum == 0) idxZero = i;
            }
            if (idxZero + 1 < segment.size()) moveDown(grid, col, startRow + idxZero + 1);
        }
    }


    private static void moveUp(Tableau g, int col, int row) {
        if (row > 0 && g.estPositionValide(col, row - 1)) {
            Cellule c = g.getCellule(col, row);
            g.setCellule(col, row - 1, c);
            g.setCellule(col, row, null);
        }
    }
    private static void moveDown(Tableau g, int col, int row) {
        if (row < g.getHauteur() - 1 && g.estPositionValide(col, row + 1)) {
            Cellule c = g.getCellule(col, row);
            g.setCellule(col, row + 1, c);
            g.setCellule(col, row, null);
        }
    }
// Appliquer les deux enchaînés
    public static void moveBoth(Tableau grid) {
        moveHorizantale(grid);
        moveVertical(grid);
    }
    public boolean estDeplacable() {
      if(this.getType() == typeCell.MOVE || this.getType() == typeCell.PUSHER || this.getType() == typeCell.DIRECTIONAL || this.getType() == typeCell.SLIDE|| this.getTypCell() == TypeCell.GENERATOR) { 
        return true; 
      } else {
        return false;
      }
    }

 //-- Par Rime : 

    public void setCase(Case c) {
        this.tile = c;
    }
    // Méthodes pour l'enum "ACTION"
    public void deplacer() {
        if (tile == null) {
            System.out.println("Erreur : la cellule n'est pas positionnée sur une case.");
            return;
        }

        int newX = tile.getX() + orientationCell.dj();
        int newY = tile.getY() + orientationCell.di();
        tile = new Case(newX, newY);
    }

 

    public void directional(Cellule dir, Cellule courante) {
        if (isPusherDevant(dir, courante) && dir.getOrientation()==courante.getOrientation()) {
            dir.deplacer();
            courante.deplacer();
        } else {
            System.out.println("Le directional bloque.");
        }
    }

    public boolean isVertical(Cellule slide) {
        return slide.getOrientation() == Orientation.NORTH || slide.getOrientation() == Orientation.SOUTH;
    }

    public boolean isHorizontal(Cellule slide) {
        return slide.getOrientation() == Orientation.WEST || slide.getOrientation() == Orientation.EAST;
    }
    public boolean memeAxe(Cellule c1, Cellule c2) {
        return (isVertical(c1) && isVertical(c2)) || (isHorizontal(c1) && isHorizontal(c2));
    }

    public boolean isPusherDevant(Cellule pusher, Cellule courante) {
        if (pusher.getType() != TypeCell.PUSHER) {
            return false;
        }

        Orientation orientation = courante.getOrientation(); // Utilisation de l'orientation propre à chaque cellule
        
        return (pusher.tile.getX() == courante.tile.getX() + orientation.dj()) &&
               (pusher.tile.getY() == courante.tile.getY() + orientation.di());
    }

    
    
    // Action ROTATE
    public void tourner() {
        if (typeCell == TypeCell.SPINNER_DIRECT||typeCell == TypeCell.SPINNER_INDIRECT) {
            orientationCell = orientationCell.turn(1);
        } else if (typeCell == TypeCell.SPINNER_INDIRECT) {
            orientationCell = orientationCell.turn(-1);
        } else {
            System.out.println("La cellule n'est pas un spinner et ne peut pas tourner.");
        }
    }

    public void tourner(int k) {
        for (int i = 0; i < k; i++) {
            tourner();
        }
    }

    public void interagirAvecSpinner(Cellule courante, Tableau tableau) {
        int x = courante.tile.getX();
        int y = courante.tile.getY();

        // Vérifie autour de la cellule pour trouver un spinner
        Cellule spinner = null;

        // Haut
        spinner = tableau.getCellule(x, y - 1);
        if (estSpinner(spinner)) {
            appliquerRotationDepuisSpinner(spinner,courante);
            return;
        }

        // Bas
        spinner = tableau.getCellule(x, y + 1);
        if (estSpinner(spinner)) {
            appliquerRotationDepuisSpinner(spinner,courante);
            return;
        }

        // Gauche
        spinner = tableau.getCellule(x - 1, y);
        if (estSpinner(spinner)) {
            appliquerRotationDepuisSpinner(spinner,courante);
            return;
        }

        // Droite
        spinner = tableau.getCellule(x + 1, y);
        if (estSpinner(spinner)) {
            appliquerRotationDepuisSpinner(spinner,courante);
            return;
        }
    }

    // Méthode utilitaire pour identifier un spinner
    private boolean estSpinner(Cellule c) {
        return c != null && (c.getType() == TypeCell.SPINNER_DIRECT || c.getType() == TypeCell.SPINNER_INDIRECT);
    }

    // Appliquer une rotation selon le type de spinner
    private void appliquerRotationDepuisSpinner(Cellule spinner,Cellule courante) {
        if (spinner.getType() == TypeCell.SPINNER_DIRECT) {
                courante.setOrientation(courante.getOrientation().turn(1));
            } else {
                courante.setOrientation(courante.getOrientation().turn(-1));
            }
        
    }


    public boolean isSlideDevant(Cellule slide, Cellule courante) {
        if (slide.getType() != TypeCell.SLIDE) {
            return false;
        }

        Orientation orientation = courante.getOrientation();
        
        return (slide.tile.getX() == courante.tile.getX() + orientation.dj()) &&
               (slide.tile.getY() == courante.tile.getY() + orientation.di());
    }
    public boolean isDirectionalDevant(Cellule directional, Cellule courante) {
        if (directional.getType() != TypeCell.DIRECTIONAL) {
            return false;
        }

        Orientation orientation = courante.getOrientation();
        
        return (directional.tile.getX() == courante.tile.getX() + orientation.dj()) &&
               (directional.tile.getY() == courante.tile.getY() + orientation.di());
    }
    
    
    
    public boolean isSpinnerDevant(Cellule courante, Tableau tableau) {
    	 int x = courante.tile.getX();
    	    int y = courante.tile.getY();

    	    // Vérifie à gauche
    	    Cellule spinner = tableau.getCellule(x - 1, y);
    	    if (spinner != null && (spinner.getType() == TypeCell.SPINNER_DIRECT || spinner.getType() == TypeCell.SPINNER_INDIRECT)) {
    	        return true;
    	    }

    	    // Vérifie à droite
    	    spinner = tableau.getCellule(x + 1, y);
    	    if (spinner != null && (spinner.getType() == TypeCell.SPINNER_DIRECT || spinner.getType() == TypeCell.SPINNER_INDIRECT)) {
    	        return true;
    	    }

    	    // Vérifie en haut
    	    spinner = tableau.getCellule(x, y - 1);
    	    if (spinner != null && (spinner.getType() == TypeCell.SPINNER_DIRECT || spinner.getType() == TypeCell.SPINNER_INDIRECT)) {
    	        return true;
    	    }

    	    // Vérifie en bas
    	    spinner = tableau.getCellule(x, y + 1);
    	    if (spinner != null && (spinner.getType() == TypeCell.SPINNER_DIRECT || spinner.getType() == TypeCell.SPINNER_INDIRECT)) {
    	        return true;
    	    }

    	    return false;
    }
    


    public boolean aDejaBouge() {
        return hasMoved;
    }

    public void marquerCommeBougee() {
        this.hasMoved = true;
    }

    public void reinitialiserDeplacement() {
        this.hasMoved = false;
    }

    public boolean suivantePusherPossible(Cellule courante, Cellule suivante) {
    	return courante.getAction()==Action.PUSH&&suivante.getType()==null||suivante.estDeplacable()||suivante.getType()==TypeCell.ENEMY;
    }

    
   //-- Par Ghania : 
    
  //Action DUPLICATE : 
    // Méthode pour effectuer la duplication des cellules
     public void dupliquer(Tableau tableau, int x, int y) {
    	    Cellule generateur = tableau.getCellule(x, y);
    	    if (generateur == null || generateur.getOrientation() == null) return;

    	    // Déterminer la direction dans laquelle le générateur duplique
    	    int dx = 0, dy = 0;
    	    switch (generateur.getOrientation()) {
    	        case NORTH: dy = -1; break;
    	        case SOUTH: dy = 1; break;
    	        case EAST:  dx = 1; break;
    	        case WEST:  dx = -1; break;
    	        default: return;
    	    }

    	    // Calcul de la position de duplication (vers l'avant du générateur)
    	    int destX = x + dx;
    	    int destY = y + dy;

    	    // Vérifier que la destination est dans les limites
    	    if (destX < 0 || destX >= tableau.getlargeur() || destY < 0 || destY >= tableau.getHauteur()) {
    	        return;
    	    }

    	    // S'il y a déjà une cellule à destination, essayer de décaler
    	    if (tableau.getCellule(destX, destY) != null) {
    	        if (!decaler(tableau, destX, destY, dx, dy)) {
    	            return; // On ne peut pas dupliquer si pas de place
    	        }
    	    }

    	    // Regarder autour du générateur (haut, bas, gauche, droite)
    	    int[][] voisins = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
    	    for (int[] dir : voisins) {
    	        int nx = x + dir[0];
    	        int ny = y + dir[1];

    	        if (nx >= 0 && nx < tableau.getlargeur() && ny >= 0 && ny < tableau.getHauteur()) {
    	            Cellule voisine = tableau.getCellule(nx, ny);
    	            if (voisine != null&&voisine.getRelationAvecGenerateur(tableau)=="DUPLICATE") {
    	                // On copie la première cellule adjacente trouvée
    	                ajouterCopie(tableau, voisine, destX, destY);
    	                return;
    	            }else if(voisine !=null&&getRelationAvecGenerateur(tableau)!="DUPLICATE") {
    	            	generateur.setType(TypeCell.PUSHER);
    	            }
    	        }
    	    }
    	}


     private void setType(TypeCell pusher) {
    	 this.typeCell = pusher;
	
}

	private boolean decaler(Tableau tableau, int x, int y, int dx, int dy) {
    	    int nextX = x + dx;
    	    int nextY = y + dy;

    	    // Vérifier que la prochaine position est dans les limites
    	    if (nextX < 0 || nextX >= tableau.getlargeur() || nextY < 0 || nextY >= tableau.getHauteur()) {
    	        return false;
    	    }

    	    // Si la case suivante est vide, déplacer
    	    if (tableau.getCellule(nextX, nextY) == null) {
    	        Cellule cellule = tableau.getCellule(x, y);
    	        if (cellule != null) {
    	            tableau.ajouterCellule(cellule, nextX, nextY);
    	            return true;
    	        }
    	    } else {
    	        // Essayer de décaler récursivement
    	        if (decaler(tableau, nextX, nextY, dx, dy)) {
    	            Cellule cellule = tableau.getCellule(x, y);
    	            if (cellule != null) {
    	                tableau.ajouterCellule(cellule, nextX, nextY);
    	                return true;
    	            }
    	        }
    	    }
    	    return false;
    	}

    
 // Méthode pour ajouter la copie de la cellule
     private void ajouterCopie(Tableau tableau, Cellule celluleSource, int x, int y) {
    	    Case caseCopie = new Case(0, 0); 
    	    Cellule copie = new Cellule(celluleSource.getTypCell(), celluleSource.getAction(), celluleSource.getOrientation(), caseCopie);
    	    tableau.ajouterCelluleSansVerification(copie, x, y);
    	}


    // Méthode pour déplacer les cellules vers la droite
    private boolean decalerVersDroite(Tableau tableau, int x, int y) {
        int shiftRight = x + 1;
        while (shiftRight < tableau.getlargeur() && tableau.getCellule(shiftRight, y) == null) {
            shiftRight++;
        }
        
        if (shiftRight < tableau.getlargeur()) {
            // Déplacer les cellules vers la droite
        	for (int i = x; i >= 0; i--) {
                Cellule currentCell = tableau.getCellule(i, y);
                if (currentCell != null) {
                    tableau.ajouterCellule(currentCell, i + 1, y);
                }
            }
            // Ajouter la copie
            for (int i = x; i < tableau.getlargeur(); i++) {
            ajouterCopie(tableau, tableau.getCellule(x - 1, y), x + 1, y);
            return true;
        }}
        return false;
    }

    // Méthode pour déplacer les cellules vers la gauche
    private void decalerVersGauche(Tableau tableau, int x, int y) {
        int shiftLeft = x - 1;
        while (shiftLeft >= 0 && tableau.getCellule(shiftLeft, y) == null) {
            shiftLeft--;
        }

        if (shiftLeft >= 0) {
            // Déplacer les cellules vers la gauche
            for (int i = x; i >= 0; i--) {
                Cellule currentCell = tableau.getCellule(i, y);
                if (currentCell != null) {
                    tableau.ajouterCellule(currentCell, i - 1, y);
                }
            }
            // Ajouter la copie
            for (int i = x; i < tableau.getlargeur(); i++) {
            	 ajouterCopie(tableau, tableau.getCellule(x - 1, y), x - 1, y);
            }
           
        }
    }
 
    
    public String getRelationAvecGenerateur(Tableau tableau) {
        int x = tile.getX();
        int y = tile.getY();

        for (Orientation o : Orientation.values()) {
            int nx = x + o.dj();
            int ny = y + o.di();

            if (nx >= 0 && nx < tableau.getlargeur() && ny >= 0 && ny < tableau.getHauteur()) {
                Cellule voisin = tableau.getCellule(nx, ny);

                if (voisin != null && voisin.getType() == TypeCell.GENERATOR) {
                    Orientation orientationGen = voisin.getOrientation();

                    int devantX = voisin.getTile().getX() + orientationGen.dj();
                    int devantY = voisin.getTile().getY() + orientationGen.di();

                    int derriereX = voisin.getTile().getX() - orientationGen.dj();
                    int derriereY = voisin.getTile().getY() - orientationGen.di();

                    // Si le MOVE est devant le générateur (dans le sens de la flèche)
                    if (x == devantX && y == devantY) {
                        return "MOVE";
                    }

                    // Si le MOVE est derrière le générateur (opposé à la flèche)
                    if (x == derriereX && y == derriereY) {
                        return "DUPLICATE";
                    }else {
                    	voisin.setAction(Action.PUSH);
                        return "PUSH";
                    }

                    // Sinon, c'est sur le côté
                    
                   
                }
            }
        }

        return "NONE";
    }

    // Getters pour la position
    public Case getTile() {
        return this.tile;
    }

    public TypeCell getTypCell() {
    return this.typeCell; 
    }

    @Override
    public String toString() {
        return "Cellule {" + typeCell + ", " + action + ", " + orientationCell + "}";
    }
    public void setOrientation(Orientation orientation) {
        this.orientationCell = orientation;  // Mise à jour de l'orientation de la cellule
    }

	public void setAction(Action action) {
		this.action = action;
		
	}
}
