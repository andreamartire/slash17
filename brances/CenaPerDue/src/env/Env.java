package env;

import java.util.Random;

public class Env {

	boolean mealReady = false;
	boolean suitUp = false;
	boolean tableReady = false;
	boolean flowerTaken = false;
	boolean waitRenata = false;

	public final static char TILE = ' ';
	public final static char MEAL = 'M';
	public final static char SUIT = 'S';
	public final static char TABLE = 'T';
	public final static char FLOWERS = 'F';
	public final static char DOOR = 'D';
	public final static char GRASS = 'G';

	public char matrix[][];

	Ciccio ciccio;

	/** dopo quanti turni spostare le cose */
	int k = 0;

	/** turni utili prima che renata arrivi */
	int maxTime = 20;

	/** velocita' simulazione */
	int speed = 1;

	/** tipo di ambiente (A,B,C,D) */
	char type = 'A';

	public int posForRoom;

	public int rooms;
	
	public int doorsPosition[];

	Random rg = new Random();

	public Env(int rooms, int posForRooms) {
		this.rooms = rooms;
		this.posForRoom = posForRooms;
		matrix = new char[rooms][posForRoom];
		doorsPosition = new int[rooms-1];
		envGeneration();
		int r = Math.abs(rg.nextInt()) % (rooms-1);
		int p = Math.abs(rg.nextInt()) % posForRoom;
		while(matrix[r][p] != TILE){
			r = Math.abs(rg.nextInt()) % (rooms-1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		ciccio = new Ciccio(r,p);
	}

	private void envGeneration() {
		// Inizializzazione
		for (int i=0; i<rooms-1; i++)
			for (int j=0; j<posForRoom; j++)
				matrix[i][j] = TILE;
		// erba
		for (int j=0; j<posForRoom; j++)
			matrix[rooms-1][j] = GRASS;
		
		// generazione posizione suit univoca
		int r = Math.abs(rg.nextInt()) % (rooms-1);
		int p = Math.abs(rg.nextInt()) % posForRoom;
		while(matrix[r][p] != TILE){
			r = Math.abs(rg.nextInt()) % (rooms-1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = SUIT;
		
		// generazione posizione cena univoca
		r = Math.abs(rg.nextInt()) % (rooms-1);
		p = Math.abs(rg.nextInt()) % posForRoom;
		while(matrix[r][p] != TILE){
			r = Math.abs(rg.nextInt()) % (rooms-1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = MEAL;
		
		// generazione posizione fiori univoca
		r = Math.abs(rg.nextInt()) % (rooms-1);
		p = Math.abs(rg.nextInt()) % posForRoom;
		while(matrix[r][p] != TILE){
			r = Math.abs(rg.nextInt()) % (rooms-1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = FLOWERS;
		
		// generazione posizione tavolo univoca
		r = Math.abs(rg.nextInt()) % (rooms-1);
		p = Math.abs(rg.nextInt()) % posForRoom;
		while(matrix[r][p] != TILE){
			r = Math.abs(rg.nextInt()) % (rooms-1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = TABLE;
		
		//generazione porte
		for(int i=0; i<rooms-1; i++){
			doorsPosition[i] = Math.abs(rg.nextInt()) % posForRoom;
//			System.out.println("Pos doors " + i + " = " + doorsPosition[i]);
		}
	}

	public char get(int i, int j) {
		return matrix[i][j];
	}

	public boolean isMealReady() {
		return mealReady;
	}

	public void setMealReady(boolean mealReady) {
		this.mealReady = mealReady;
	}

	public boolean isSuitUp() {
		return suitUp;
	}

	public void setSuitUp(boolean suitUp) {
		this.suitUp = suitUp;
	}

	public boolean isTableReady() {
		return tableReady;
	}

	public void setTableReady(boolean tableReady) {
		this.tableReady = tableReady;
	}

	public boolean isFlowerTaken() {
		return flowerTaken;
	}

	public void setFlowerTaken(boolean flowerTaken) {
		this.flowerTaken = flowerTaken;
	}

	public boolean isWaitRenata() {
		return waitRenata;
	}

	public void setWaitRenata(boolean waitRenata) {
		this.waitRenata = waitRenata;
	}

	public int player_i() {
		return ciccio.getI();
	}

	public int player_j() {
		return ciccio.getJ();
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
}
