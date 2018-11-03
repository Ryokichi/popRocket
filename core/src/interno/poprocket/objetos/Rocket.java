package interno.poprocket.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
///novo
public class Rocket extends Sprite {
	public float acel    = 0.5f;
	public float vel     = 0f;
	public float arrasto = 0.3f;
	
	
	public Rocket() {
		super(new Texture(Gdx.files.internal("img/rocket0.png")));
	}
	
	public void setAceleracao(float a) {
		this.acel = a;
	}
}
