package interno.poprocket;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class PopRockets extends ApplicationAdapter {
//public class PopRocket implements ApplicationListener {

		static final int WORLD_WIDTH  = 1000;
		static final int WORLD_HEIGHT = 1000;

		private OrthographicCamera cam;
		private SpriteBatch batch;

		private Sprite mapSprite;
		private float rotationSpeed;

		@Override
		public void create() {
			rotationSpeed = 0.1f;

			mapSprite = new Sprite(new Texture(Gdx.files.internal("img/bkg.png")));
			mapSprite.setPosition(0, 0);
			mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

			float w = Gdx.graphics.getWidth();
			float h = Gdx.graphics.getHeight();

			// Constructs a new OrthographicCamera, using the given viewport width and height
			// Height is multiplied by aspect ratio.
			cam = new OrthographicCamera(60, 60 * (h / w));

			cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
			cam.update();

			batch = new SpriteBatch();
		}

		@Override
		public void render() {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			handleInput();
			cam.update();
			batch.setProjectionMatrix(cam.combined);			

			batch.begin();
			mapSprite.draw(batch);
			batch.end();
		}

		private void handleInput() {
			if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
				cam.zoom = 0;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				cam.zoom += 0.02;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
				cam.zoom -= 0.02;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				cam.translate(-3, 0, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				cam.translate(3, 0, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				cam.translate(0, -3, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
				cam.translate(0, 3, 0);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				cam.rotate(-rotationSpeed, 0, 0, 1);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.E)) {
				cam.rotate(rotationSpeed, 0, 0, 1);
			}

			cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, WORLD_WIDTH/cam.viewportWidth);

			float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
			float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

			cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth  / 2f, WORLD_HEIGHT - effectiveViewportWidth  / 2f);
			cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
		}

		@Override
		public void resize(int width, int height) {
			cam.viewportWidth  = 100f;
			cam.viewportHeight = 100f * height/width;
			cam.update();
		}

		@Override
		public void resume() {
		}

		@Override
		public void dispose() {
			mapSprite.getTexture().dispose();
			batch.dispose();
		}

		@Override
		public void pause() {
			
		}	
}
