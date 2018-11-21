package interno.poprocket.screens;

import com.badlogic.gdx.Game;



public class PopRocket extends Game {
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private OptionsScreen optionsScreen;
	private LoadDataScreen loadDataScreen;
	private CreditsScreen creditsScreen;
	
	public final int LOADING   = 0;
	public final int MENU      = 1;
	public final int GAME      = 2;
	public final int OPTIONS   = 3;
	public final int LOAD_DATA = 4;	
	public final int CREDITS   = 5;
	
	public int slot;
	public int pontos;
	public double dist_percorrida;
	
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

}