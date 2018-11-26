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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import interno.db.SqliteConn;
import interno.poprocket.objetos.MinhasFuncoes;
import interno.poprocket.objetos.Rocket;
import interno.poprocket.objetos.criaObjetos;


public class GameScreen implements Screen {
    MinhasFuncoes mf = new MinhasFuncoes();
    SqliteConn    db = new SqliteConn();
    
    static final int WORLD_HEIGHT = 10000;
	static final int WORLD_WIDTH  = 2000;
	static final int MAX_VEL      = 10;
	static final float ALT_CHAO   = 8f;
	static final double GRAVIDADE = 3;
	
	
	final PopRocket game;

	private OrthographicCamera cam;
	private SpriteBatch batch, batch2;		
	private float   rotationSpeed;	
	private float   h_spd           = 0f;
	private float   v_spd           = 0f;
	private float   tempo_burst     = 0f;
	private double  dist_percorrida = 0;
    private int     score           = 0;
    private int     slot            = 0;
    private boolean foi_lancado     = false;
    private boolean plyr_controla   = false;
    private boolean fim_jogo        = false;
	
	private Sprite  asteroides[], estrelas[], nuvens[], nuvens_fundo[];
	private Sprite  mapSprite0, mapSprite1, mapSprite2, prop;
	private Rocket  rocket;
	
	private BitmapFont font;
	private String str;
	private Stage stage;
	
	
	
	public GameScreen (final PopRocket game) {
		this.game = game;		
		this.stage = new Stage(new ScreenViewport());
		
		slot            = this.game.slot;
		score           = this.game.pontos;
		dist_percorrida = this.game.dist_percorrida;
		
		font          = new BitmapFont();		
		rotationSpeed = 0.8f;	
		asteroides    = criaObjetos.Asteroides(10);
		estrelas      = criaObjetos.Estrelas(100); 
		nuvens        = criaObjetos.Nuvens(80);
		nuvens_fundo  = criaObjetos.NuvensFundo(60);
		
		mapSprite0 = new Sprite(new Texture(Gdx.files.internal("img/bkg2.png")));
		mapSprite0.setPosition(0, 0);
		mapSprite0.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		mapSprite1 = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
		mapSprite1.setPosition(0, 0);
		mapSprite1.setSize(WORLD_WIDTH+10, WORLD_HEIGHT/20);
		
		mapSprite2 = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
		mapSprite2.setPosition(WORLD_WIDTH-2, 0);
        mapSprite2.setSize(WORLD_WIDTH+10, WORLD_HEIGHT/20);
		
		rocket = new Rocket();
		rocket.setPosition(WORLD_WIDTH/20f, 10);
		rocket.setSize(50,20);
		rocket.setOrigin(0,10);
		
		prop = new Sprite(new Texture(Gdx.files.internal("img/prop.png")));		
		prop.setSize(60, 50);
		prop.setOrigin(60, 50/2);

		// Constructs a new OrthographicCamera, using the given viewport width and height
		// Height is multiplied by aspect ratio.
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		cam = new OrthographicCamera(60, 60 * (h / w));
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);		
		cam.update();

		batch  = new SpriteBatch();
		batch2 = new SpriteBatch();
	}
	
	public void render(float dt) {		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (tempo_burst <=0) {
		   this.prop.setScale(0);
		} else {
			tempo_burst -= dt;
		}		
		
		if (!fim_jogo) {
		    ////Funções auxiliares
		    this.lerEntradas(dt);
		    this.ajustarZoomCamera();
		    this.checkLimitsForRocket(dt);
		    if (foi_lancado) { 
                this.updateObjPos(dt);
		        this.checkObjCollision();
		    }
		}		
		
		////Desenha objetos na tela
		cam.update();
		batch.setProjectionMatrix(cam.combined);
				
		batch.begin();
		    mapSprite0.draw(batch);
			mapSprite1.draw(batch);
			mapSprite2.draw(batch);	
		
			for (int i = 0; i < nuvens_fundo.length; i++ ) {			
				nuvens_fundo[i].draw(batch);			
			}
			
			for (int i = 0; i < nuvens.length; i++ ) {			
				nuvens[i].draw(batch);			
			}	
		
			for (int i = 0; i < asteroides.length; i++ ) {			
				asteroides[i].draw(batch);			
			}
		
			for (int i = 0; i < estrelas.length; i++ ) {			
				estrelas[i].draw(batch);			
			}

			prop.draw(batch);
			rocket.draw(batch);
		batch.end();
		
		this.imprimeTextos();		
		rocket.atualizaVelocidades(dt, GRAVIDADE);
	}
	
	private void ajustarZoomCamera() {
        //cam.zoom = MathUtils.clamp(cam.zoom, 4f, WORLD_WIDTH/cam.viewportWidth);
		cam.zoom = (float) MathUtils.clamp(cam.zoom, 1f, WORLD_WIDTH/cam.viewportWidth);

		float effectiveViewportWidth  = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;	
				
		//cam.position.x = MathUtils.clamp(rocket.getX(), effectiveViewportWidth  / 2f, WORLD_HEIGHT - effectiveViewportWidth  / 2f);
		//cam.position.y = MathUtils.clamp(rocket.getY(), effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
		
		cam.position.x = WORLD_WIDTH/2;
		cam.position.y = MathUtils.clamp(rocket.getY(), effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);	
	}
	
	private void imprimeTextos() {
		batch2.begin();
        str = "Altura: " + rocket.getY();
        font.draw(batch2, str, 1000*0.01f, 470*0.99f);
        str = "Vel_abs:" + rocket.getVelocidadeAbs();
        font.draw(batch2, str, 1000*0.01f, 470*0.96f);        
        str = "Vel_X : " + rocket.getVelX();
        font.draw(batch2, str, 1000*0.01f, 470*0.93f);
        str = "Vel_Y : " + rocket.getVelY();
        font.draw(batch2, str, 1000*0.01f, 470*0.90f);

        str = "Sust : " + rocket.sustentacao();
        font.draw(batch2, str, 1000*0.01f, 470*0.87f);        
        str = "Drag X : " + rocket.arrastoX();
        font.draw(batch2, str, 1000*0.01f, 470*0.84f);        
        str = "Drag Y : " + rocket.arrastoY();
        font.draw(batch2, str, 1000*0.01f, 470*0.81f);
        
        str = "Dist  : " + dist_percorrida;
        font.draw(batch2, str, 1000*0.4f, 470*0.99f);
        str = "Score: " + this.score;
        font.draw(batch2, str, 1000*0.4f, 470*0.96f);
        batch2.end();
	}
	
	private void lerEntradas(float dt) {
		////se foguete ainda não foi lancado e botão para lançar foi pressionado
		////então foguete é lancado e player tem opcao de controlar
		if (!foi_lancado && Gdx.input.isKeyPressed(Input.Keys.L)) {
			rocket.setVelocidade(90, 90);
			rocket.setRotation(45);
			plyr_controla = true;
			foi_lancado   = true;
			tempo_burst = 1f;
			this.prop.setScale(1);
			
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			rocket.setVelocidade(90, 90);
			rocket.setRotation(45);
			plyr_controla = true;
			foi_lancado   = true;
			this.prop.setScale(1);
			tempo_burst = 1f;
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			rocket.acelerar(dt);
			this.prop.setScale(1);
		}
		
		if (!plyr_controla) return;
		
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			cam.zoom = 0;
		}		
		if (Gdx.input.isKeyPressed(Input.Keys.R)) {
			db.atualiza(slot, score, dist_percorrida);
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
			//rocket.translateY(-v_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			//rocket.translateY(v_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//cam.rotate(-rotationSpeed, 0, 0, 1);
			rocket.rotate(rotationSpeed);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//cam.rotate(rotationSpeed, 0, 0, 1);
			rocket.rotate(-rotationSpeed);
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.game.changeScreen(this.game.MENU);
		}
	}
	
	
	@Override
	public void resize (int width, int height) {
		cam.viewportWidth  = WORLD_WIDTH;
		cam.viewportHeight = WORLD_WIDTH * height/width;
		cam.update();
	}
	
	public void show () {
		this.stage.clear();
//		Gdx.input.setInputProcessor(stage);		
	}
	
	public void hide () {
		
	}
	
	public void resume () {
		
	}
	
	public void dispose () {
		mapSprite0.getTexture().dispose();
		mapSprite1.getTexture().dispose();
		mapSprite2.getTexture().dispose();
		batch.dispose();		
	}

	@Override
	public void pause() {
		
	}
	
	private void checkLimitsForRocket (float dt) {			
		////Limites X e Y de mapa
		if (rocket.getY() > WORLD_HEIGHT-50) {
			rocket.setY(WORLD_HEIGHT-50);
			rocket.setRotation(0);
		}		
		if (rocket.getY() < ALT_CHAO) {			
			rocket.setVelX(h_spd*0.8 - 0.02);
			rocket.setVelY(0);			
			if (rocket.getY() < (ALT_CHAO+0.02)) {
				rocket.setRotation(0);
			}
			rocket.setY(ALT_CHAO);
		}	

		////Limites de velocidade		
		if (rocket.getVelY() <    -MAX_VEL) rocket.setVelY(-MAX_VEL);
		if (rocket.getVelY() >     MAX_VEL) rocket.setVelY( MAX_VEL);
		if (rocket.getVelX() <           0) rocket.setVelX(0);		
		if (rocket.getVelX() > MAX_VEL*1.5) rocket.setVelX(MAX_VEL*1.5);		
		
		////Limites de angulo		
		if (rocket.getRotation() >  75) rocket.setRotation( 75);
		if (rocket.getRotation() < -45) rocket.setRotation(-45);		
	}
	
	private void updateObjPos(float dt) {		
		h_spd = (float)mf.d2(rocket.getVelX());
		v_spd = (float)mf.d2(rocket.getVelY());
		
		dist_percorrida += (h_spd * dt);
		dist_percorrida = mf.d2(dist_percorrida);
		
		rocket.translateY(v_spd);
		prop.setPosition(this.rocket.getX()-this.prop.getWidth(), this.rocket.getY()-15);
		prop.setRotation(this.rocket.getRotation());
		
		mapSprite1.translateX(-h_spd);
		mapSprite2.translateX(-h_spd);
		
		if (mapSprite1.getX() <= -WORLD_WIDTH) {
			mapSprite1.setX(mapSprite2.getX() + WORLD_WIDTH-2);
		}
		if (mapSprite2.getX() <= -WORLD_WIDTH) {
			mapSprite2.setX(mapSprite1.getX() + WORLD_WIDTH-2);
		}
		
		for (int i=0; i<asteroides.length; i++) {
			if (asteroides[i].getX() < -50 || asteroides[i].getY() < 10) {
				float x = MathUtils.random(WORLD_WIDTH+50, 2*WORLD_WIDTH);
				float y = MathUtils.random(100, WORLD_HEIGHT);
				asteroides[i].setPosition(x, y);
			}
			else {
				asteroides[i].translateX(-(2+h_spd)*0.9f);
				asteroides[i].translateY(-2);
			}			
		}
		
		for (int i=0; i<estrelas.length; i++) {
			if (estrelas[i].getX() < -50) {
				float x = MathUtils.random(WORLD_WIDTH+50, WORLD_WIDTH*2);
				float y = MathUtils.random(WORLD_HEIGHT/20, WORLD_HEIGHT);
				estrelas[i].setPosition(x, y);
			}
			if (estrelas[i].getX() > WORLD_WIDTH*2) {
				float x = MathUtils.random(WORLD_WIDTH+50, WORLD_WIDTH*2);
				float y = MathUtils.random(WORLD_HEIGHT/20, WORLD_HEIGHT);
				estrelas[i].setPosition(x, y);
			}
			else {
				estrelas[i].translateX(5-h_spd);	
			}			
		}
		
		for (int i=0; i<nuvens.length; i++) {
			if (nuvens[i].getX() < -50) {				
				nuvens[i].setX(WORLD_WIDTH + 50);
			}
			else {
				nuvens[i].translateX(-h_spd*0.8f);	
			}			
		}
		
		for (int i=0; i<nuvens_fundo.length; i++) {
			if (nuvens_fundo[i].getX() < -50) {				
				nuvens_fundo[i].setX(WORLD_WIDTH + 50);
			}
			else {
				nuvens_fundo[i].translateX(-0.02f);	
			}			
		}		
	}

	
	private void checkObjCollision() {
		for (int i=0; i<estrelas.length; i++) {
			if (rocket.getBoundingRectangle().overlaps(estrelas[i].getBoundingRectangle())){
				score = score + 1;
				tempo_burst = 0.5f;
				rocket.aumentaVel(5, 5);
				prop.setScale(1);				
				float x = MathUtils.random(WORLD_WIDTH+50, 2*WORLD_WIDTH);
				float y = MathUtils.random(WORLD_HEIGHT/20, WORLD_HEIGHT);
				estrelas[i].setPosition(x, y);
			}
		}
		for (int i=0; i<asteroides.length; i++) {
			if (rocket.getBoundingRectangle().overlaps(asteroides[i].getBoundingRectangle())) {
				score = score -1;
				rocket.aumentaVel(-1, 1);
				float x = MathUtils.random(WORLD_WIDTH+50, 2*WORLD_WIDTH);
				float y = MathUtils.random(WORLD_HEIGHT/20, WORLD_HEIGHT);
				asteroides[i].setPosition(x, y);
				if (score < 0) {
					score = 0;
				}
			}
		}
	}
}
