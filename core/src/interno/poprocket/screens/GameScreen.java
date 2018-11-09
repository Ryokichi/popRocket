package interno.poprocket.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import interno.poprocket.objetos.MinhasFuncoes;
import interno.poprocket.objetos.Rocket;
import interno.poprocket.objetos.criaObjetos;


public class GameScreen implements Screen {
    MinhasFuncoes mf = new MinhasFuncoes();
    static final int WORLD_HEIGHT = 5000;
	static final int WORLD_WIDTH  = 1000;
	
	
	final PopRocket game;

	private OrthographicCamera cam;
	private SpriteBatch batch, batch2;
	
	private double dist_percorrida = 0;	
	private double gravidade = 10;
	private float rotationSpeed;	
	private float h_spd = 0f;
	private float v_spd = 0f;	
	
	private Sprite  asteroides[], estrelas[], nuvens[], nuvens_fundo[];
	private Sprite  mapSprite1, mapSprite2;
	private Rocket  rocket;
	
	private BitmapFont font;
	private String str;
	
	
	
	public GameScreen (final PopRocket game) {
		//this.db = new SqliteConn();
		//this.db.criaTabelas();
		
		this.game = game;		
		font     = new BitmapFont();		
		
		rotationSpeed = 0.8f;	
		asteroides = criaObjetos.Asteroides(10);
		estrelas   = criaObjetos.Estrelas(20); 
		nuvens     = criaObjetos.Nuvens(80);
		
		
		mapSprite1 = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
		mapSprite1.setPosition(0, 0);
		mapSprite1.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		mapSprite2 = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
		mapSprite2.setPosition(WORLD_WIDTH-2, 0);
		mapSprite2.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		rocket = new Rocket();
		rocket.setPosition(WORLD_WIDTH / 2f, 10);
		rocket.setSize(40,20);
		rocket.setOrigin(5, 5);		

		// Constructs a new OrthographicCamera, using the given viewport width and height
		// Height is multiplied by aspect ratio.
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(60, 60 * (h / w));
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);		
		cam.update();

		batch = new SpriteBatch();
		batch2 = new SpriteBatch();
	}
	
	public void render(float dt) {	
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		////Funções auxiliares
		handleInput(dt);
		updateObjPos();
		checkLimitsForRocket(dt);
				
		cam.update();
		batch.setProjectionMatrix(cam.combined);
				
		batch.begin();
		mapSprite1.draw(batch);
		mapSprite2.draw(batch);	
		
		for (int i = 0; i < nuvens.length; i++ ) {			
			nuvens[i].draw(batch);			
		}
		
		for (int i = 0; i < asteroides.length; i++ ) {			
			asteroides[i].draw(batch);			
		}
		
		for (int i = 0; i < estrelas.length; i++ ) {			
			estrelas[i].draw(batch);			
		}		
		rocket.draw(batch);
		batch.end();

		batch2.begin();
        str = "Altura: " + rocket.getY();
		font.draw(batch2, str, 1000*0.01f, 600*0.99f);
		str = "Vel_X : " + rocket.getVelX();
		font.draw(batch2, str, 1000*0.01f, 600*0.96f);
		str = "Vel_Y :" + rocket.getVelY();
		font.draw(batch2, str, 1000*0.01f, 600*0.93f);
		
		System.out.println(Gdx.graphics.getWidth());
		batch2.end();
	}	
	
	private void handleInput(float dt) {
		if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			rocket.setVelocidade(10, 10);
			rocket.setRotation(45);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			cam.zoom = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			//cam.translate(-3, 0, 0);			
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			//cam.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {            
			rocket.translateY(-v_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			rocket.translateY(v_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//cam.rotate(-rotationSpeed, 0, 0, 1);
			rocket.rotate(rotationSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//cam.rotate(rotationSpeed, 0, 0, 1);
			rocket.rotate(-rotationSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			rocket.acelerar(dt);
		}

//		cam.zoom = MathUtils.clamp(cam.zoom, 4f, WORLD_WIDTH/cam.viewportWidth);
		cam.zoom = (float) MathUtils.clamp(WORLD_WIDTH/cam.viewportWidth, 5f, WORLD_WIDTH/cam.viewportWidth);

		//float effectiveViewportWidth  = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;	
				
		//cam.position.x = MathUtils.clamp(rocket.getX(), effectiveViewportWidth  / 2f, WORLD_HEIGHT - effectiveViewportWidth  / 2f);
		//cam.position.y = MathUtils.clamp(rocket.getY(), effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
		
		cam.position.x = rocket.getX();
		cam.position.y = MathUtils.clamp(rocket.getY(), effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);	
	}
	
	
	@Override
	public void resize (int width, int height) {
		cam.viewportWidth  = 100f;
		cam.viewportHeight = 100f * height/width;
		cam.update();
	}
	
	public void show () {
		
	}
	
	public void hide () {
		
	}
	
	public void resume () {
		
	}
	
	public void dispose () {
		mapSprite1.getTexture().dispose();
		mapSprite1.getTexture().dispose();
		batch.dispose();		
	}

	@Override
	public void pause() {
		
	}
	
	private void checkLimitsForRocket (float dt) {
		dist_percorrida += h_spd * dt;
		
		h_spd = (float)rocket.getVelX();
		v_spd = (float)rocket.getVelY();
		
		rocket.atualizaVelocidades(dt, gravidade);	
			
		////Limites X e Y de mapa
		if (rocket.getY() < 10) {
			rocket.setY(10);			
			rocket.setVelY(0);
			rocket.setRotation(0);
			rocket.setVelY(0);
		}
		if (rocket.getY() > WORLD_HEIGHT-50) {
			rocket.setY(WORLD_HEIGHT-50);			
			rocket.setVelY(0);
			rocket.setRotation(0);
			rocket.setVelY(0);
		}
		
		////Limites de velocidade
		if (v_spd < -20) rocket.setVelY(-20);
		if (v_spd >  40) rocket.setVelY( 40);
		if (h_spd <   0) rocket.setVelX(  0);		
		if (h_spd >  40) rocket.setVelX( 40);
		
		////Limites de angulo		
		if (rocket.getRotation() >  60) rocket.setRotation( 60);
		if (rocket.getRotation() < -60) rocket.setRotation(-60);	
	}
	
	private void updateObjPos() {		
		rocket.translateY(v_spd);
		mapSprite1.translateX(-h_spd);
		mapSprite2.translateX(-h_spd);
		
		if (mapSprite1.getX() <= -WORLD_WIDTH) {
			mapSprite1.setX(mapSprite2.getX() + WORLD_WIDTH-2);
		}
		if (mapSprite2.getX() <= -WORLD_WIDTH) {
			mapSprite2.setX(mapSprite1.getX() + WORLD_WIDTH-2);
		}
		
		for (int i=0; i<asteroides.length; i++) {
			if (asteroides[i].getX() < -50) {
				float x = MathUtils.random(WORLD_WIDTH+50, 2*WORLD_WIDTH);
				float y = MathUtils.random(100, WORLD_HEIGHT);
				asteroides[i].setPosition(x, y);
			}
			else {
				asteroides[i].translateX(-h_spd*0.9f);	
			}			
		}
		
		for (int i=0; i<estrelas.length; i++) {
			if (estrelas[i].getX() < -50) {
				float x = MathUtils.random(WORLD_WIDTH+50, 2*WORLD_WIDTH);
				float y = MathUtils.random(100, WORLD_HEIGHT);
				estrelas[i].setPosition(x, y);
			}
			else {
				estrelas[i].translateX(-h_spd*1.2f);	
			}			
		}
		
		for (int i=0; i<nuvens.length; i++) {
			if (nuvens[i].getX() < -50) {				
				nuvens[i].setX(WORLD_WIDTH + 50);
			}
			else {
				nuvens[i].translateX(-h_spd);	
			}			
		}
	}

}
