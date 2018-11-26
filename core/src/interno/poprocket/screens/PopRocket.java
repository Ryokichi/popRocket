package interno.poprocket.screens;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.badlogic.gdx.Game;

import interno.db.SqliteConn;



public class PopRocket extends Game {
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private OptionsScreen optionsScreen;
	private LoadDataScreen loadDataScreen;
	private CreditsScreen creditsScreen;
	
	private SqliteConn db = new SqliteConn();
	
	public final int LOADING   = 0;
	public final int MENU      = 1;
	public final int GAME      = 2;
	public final int OPTIONS   = 3;
	public final int LOAD_DATA = 4;	
	public final int CREDITS   = 5;
	
	public int slot = 0;
	public int pontos = 0;
	public double dist_percorrida = 0;
	
	public boolean debugMode = true;
	
	public void changeScreen (int screen) {		
		switch (screen) {		
		    case LOADING:
		    	if (loadingScreen == null) loadingScreen = new LoadingScreen (this);
		    	this.setScreen(loadingScreen);
		    	break;
		    case  MENU:
		    	if (menuScreen == null) menuScreen = new MenuScreen (this);
		    	this.setScreen(menuScreen);
		    	break;
		    case GAME:
		    	if (gameScreen == null) gameScreen = new GameScreen (this);
		    	this.setScreen(gameScreen);
		    	break;
		    case OPTIONS:
		    	if (optionsScreen == null) optionsScreen = new OptionsScreen (this);
		    	this.setScreen(optionsScreen);
		    	break;
		    case LOAD_DATA:
		    	if (loadDataScreen == null) loadDataScreen = new LoadDataScreen (this);
		    	this.setScreen(loadDataScreen);
		    	break;
		    case CREDITS:
		    	if (creditsScreen == null) creditsScreen = new CreditsScreen (this);
		    	this.setScreen(creditsScreen);
		    	break;
		 }
	}

	@Override
	public void create() {		
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);		
	}
	
	@Override
	public void dispose() {
		
	}

	public void cosultaDB(int i) {		
		ResultSet rs = db.consulta("SELECT * FROM pontos WHERE slot = " + slot);
    	try {
			while (rs.next()) {
				this.slot = rs.getInt("slot");
				this.pontos = rs.getInt("pontos");
				this.dist_percorrida = rs.getDouble("distancia");						
				
				System.out.print("-> ");
			    System.out.println(rs.getInt("id") +  "\t" + 
			                       rs.getInt("slot") + "\t" +
			                       rs.getInt("pontos") + "\t" +
			                       rs.getDouble("distancia"));
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}