package interno.poprocket.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Rocket extends Sprite {
	public float vel_vetorial = 0f;
	public float vel_x        = 0f;
	public float vel_y        = 0f;
	public float acel         = 4f;	
	public float arrasto      = 0.15f;
    public float sustentacao  = 0.5f;
	
	public Rocket() {
		super(new Texture(Gdx.files.internal("img/rocket0.png")));
	}
	
	public void acelerar(float dt) {
		vel_x += acel * MathUtils.cosDeg(this.getRotation()) * dt;
		vel_y += acel * MathUtils.sinDeg(this.getRotation()) * dt;		
	}
	
	public void atualizaVelocidades(float dt, float h_spd, float v_spd, float gravidade) {
		float angulo_cima   = MathUtils.sinDeg(this.getRotation());
		float angulo_frente = MathUtils.cosDeg(this.getRotation());
		
		vel_x = h_spd - ((vel_x * arrasto * (1 + angulo_cima)) * dt);
		vel_y = v_spd - ((gravidade - vel_x * sustentacao * (1 + angulo_frente)) * dt);
	}
	
	public void setVelocidade (float x, float y) {
		this.vel_x = x;
		this.vel_y = y;
	}
	
	public void setVelX (float vel) {
		vel_x = vel;
	}
	
	public void setVelY (float vel) {
		vel_y = vel;
	}
	
	public void setAceleracao(float a) {
		acel = a;
	}
	
	public void setVelocidadeVetorial (float x, float y) {		
		vel_vetorial = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public float getVelocidade() {
		return vel_vetorial;
	}
}
