enum Orientation{
	NORTH, EAST,SOUTH, WEST; 
	/* 
    NORTH (0°)
    EAST (90°)
    SOUTH (180°)
    WEST (270°)
    */
	static int[] di = {-1,0,1,0};
	static int[] dj = {0,1,0,-1};
	
	public int di() {
		return di[this.ordinal()]; ////Retourne le déplacement VERTICAL en fonction de l'orientation
	}
	
	public int dj() {
		return dj[this.ordinal()]; //Retourne le déplacement HORIZANTAL en fonction de l'orientation
	}
	
	public Orientation turn(int k) {
	    int n = values().length; 
	    return values()[(this.ordinal() + k + n) % n];
	}
    /*
    turn(1) → rotation de 90° à droite
    turn(2) → rotation de 180°
    turn(3) → rotation de 270°``
    turn(4) → retour à la position initiale (360° = modulo 4)
    */
} 

