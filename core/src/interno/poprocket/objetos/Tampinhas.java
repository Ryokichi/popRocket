package interno.poprocket.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Tampinhas {
	private float x=-100, y=-100, width, height, sizeX, sizeY;
	
	private float tempo = 0f;
	
	private int qtd;	
	
	private Animation animation;	
	private TextureRegion [][] tmp;
	private TextureRegion [] regiaoMovimento;
	private TextureRegion frameAtual;
	private Texture img;
	
	private Rectangle bounds;
	
	public Tampinhas (int w, int h) {		
		
		img = new Texture(Gdx.files.internal("img/tampinhas.png"));
		tmp = TextureRegion.split(img, img.getWidth()/4, img.getHeight()/4);
		
		regiaoMovimento = new TextureRegion[16];
		int idx = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				regiaoMovimento[idx] = tmp [i][j];
				idx ++;
			}
		}		
		animation = new Animation(0.1f, regiaoMovimento);
		
		this.setSize(img.getWidth()/4, img.getHeight()/4);
		this.width  = img.getWidth();
		this.height = img.getHeight();
		this.setRandomTime(0.05, 0.2);
	}
	
	public void render (final SpriteBatch batch) {
		tempo += Gdx.graphics.getDeltaTime();
	    frameAtual = (TextureRegion) animation.getKeyFrame(tempo, true);
	    batch.draw(frameAtual, this.x, this.y, this.sizeX, this.sizeY );		
	}
	
	public void setRandomTime(double min, double max) {
		this.tempo = (float)(min + (max-min)*Math.random());
	}
	
	public void setSize(float x, float y) {
		sizeX = x;
		sizeY = y;
	}
	
	public void setPosition (float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void translatePos(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public Rectangle getBoundingBox () {
		if (bounds == null) bounds = new Rectangle();
		bounds.x = this.x;
		bounds.y = this.y;
		bounds.width  = this.sizeX;
		bounds.height = this.sizeY;
		return bounds;
	}
	
	
}
