package interno.poprocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen {
	final PopRocket game;
	
	static final int WORLD_WIDTH  = 1000;
	static final int WORLD_HEIGHT = 1000;

	private OrthographicCamera cam;
	private SpriteBatch batch;

	private Sprite mapSprite1, mapSprite2, rocket;
	private float rotationSpeed;
	private float h_spd = 2, v_spd = 2;
	
	
	public GameScreen (final PopRocket gam) {
		this.game = gam;
		
		rotationSpeed = 0.1f;

		mapSprite1 = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
		mapSprite1.setPosition(0, 0);
		mapSprite1.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		mapSprite2 = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
		mapSprite2.setPosition(WORLD_WIDTH, 0);
		mapSprite2.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		rocket = new Sprite(new Texture(Gdx.files.internal("img/rocket0.png")));
		rocket.setPosition(Gdx.graphics.getWidth() / 2f, 10);
		rocket.setSize(50,25);
		

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Constructs a new OrthographicCamera, using the given viewport width and height
		// Height is multiplied by aspect ratio.
		cam = new OrthographicCamera(60, 60 * (h / w));

		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);		
		cam.update();

		batch = new SpriteBatch();
	}
	
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		//cam.position.set(rocket.getX(), rocket.getY(), 0);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		checkLimitsForRocket();
		
		if (mapSprite1.getX() <= -WORLD_WIDTH) {
			mapSprite1.setX(WORLD_WIDTH);
		}
		if (mapSprite2.getX() <= -WORLD_WIDTH) {
			mapSprite2.setX(WORLD_WIDTH);
		}
		

		batch.begin();
		mapSprite1.draw(batch);
		mapSprite2.draw(batch);
		rocket.draw(batch);
		batch.end();	
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
			cam.zoom = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			//cam.translate(-3, 0, 0);
			mapSprite1.translateX(h_spd);
			mapSprite2.translateX(h_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			//cam.translate(3, 0, 0);
			mapSprite1.translateX(-h_spd);
			mapSprite2.translateX(-h_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			cam.translate(0, -3, 0);
			rocket.translateY(-h_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			cam.translate(0, 3, 0);
			rocket.translateY(h_spd);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			cam.rotate(-rotationSpeed, 0, 0, 1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			cam.rotate(rotationSpeed, 0, 0, 1);
		}

		cam.zoom = MathUtils.clamp(cam.zoom, 4.5f, WORLD_WIDTH/cam.viewportWidth);		

		float effectiveViewportWidth  = cam.viewportWidth * cam.zoom;
		float effectiveViewportHeight = cam.viewportHeight * cam.zoom;	
				
		cam.position.x = MathUtils.clamp(rocket.getX(), effectiveViewportWidth  / 2f, WORLD_HEIGHT - effectiveViewportWidth  / 2f);
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
		// TODO Auto-generated method stub		
	}
	
	private void checkLimitsForRocket () {
		if (rocket.getY() < 10) {
			rocket.setY(10);
		}
		if (rocket.getY() > WORLD_HEIGHT) {
			rocket.setY(WORLD_HEIGHT);
		}
	}

}
