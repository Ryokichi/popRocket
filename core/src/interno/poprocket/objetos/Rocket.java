package interno.poprocket.objetos;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Rocket extends Sprite {	
	DecimalFormat df = new DecimalFormat("###.##");
		
	private double vel_x   = 0;
	private double vel_y   = 0;
    private double vel_abs = 0;
	private double acel    = 6;	
	private double coef_d  = 0;   ////coeficiente de arrasto  (Drag)
	private double coef_l  = 0.07;      ////coeficiente de sustenta��o (Lift)
	private double rho     = 2;  ////densidade do ar
	private double Af      = 0.02;    ////�rea frontal do foguete
	private double As      = 0.6;    ////�rea superior do foguete   
	
	public Rocket() {
		super(new Texture(Gdx.files.internal("img/rocket0.png")));
	}
	
	public void acelerar(float dt) {
		vel_x += acel * MathUtils.cosDeg(this.getRotation()) * dt;
		vel_y += acel * MathUtils.sinDeg(this.getRotation()) * dt;		
	}
	
	public void atualizaVelocidades(float dt, double gravidade) {
		double cos  = MathUtils.cosDeg(this.getRotation());
		double sin  = MathUtils.sinDeg(this.getRotation());
		
		vel_x = vel_x ;
		vel_y = vel_y + (this.sustentacao() - gravidade)*dt ;
		this.updatetVelocidadeAbs();
	}
	
	public void setVelocidade (double x, double y) {
		this.vel_x = x;
		this.vel_y = y;
	}
	
	public void setVelX (double vel) {
		vel_x = vel;
	}
	
	public void setVelY (double vel) {
		vel_y = vel;
	}
	
	public double getVelX() {
		return vel_x;
	}
	
	public double getVelY() {
		return vel_y;
	}	
	
	public void setAceleracao(double a) {
		acel = a;
	}
	
	public double getVelocidadeAbs() {
		return vel_abs;
	}
	
	public double getVelocidade() {
		return vel_abs;
	}
	
	public void updatetVelocidadeAbs () {		
		vel_abs = Math.sqrt(Math.pow(vel_x, 2) + Math.pow(vel_y, 2));
	}
	
	public double sustentacao() {
		double fl   = 0;  ////force lift
		double cos  = MathUtils.cosDeg(this.getRotation());
		double sin  = MathUtils.sinDeg(this.getRotation());
				
//		fl = coef_l * rho/2 * As * Math.pow(vel_x, 2);
		fl = coef_l * As * Math.pow(vel_x, 2);	
	    return fl;
	}
	
	public double arrastoX() {
		double fd  = 0;  ////force drag
		double cos = MathUtils.cosDeg(this.getRotation());
		double sin = MathUtils.sinDeg(this.getRotation());
		
		fd = coef_d * rho/2 * (Af)*(1 + sin) * Math.pow(vel_x*cos, 2);		
	    return fd;
	}
	
	public double arrastoY() {
		double fd  = 0;  ////force drag
		double cos = MathUtils.cosDeg(this.getRotation());
		double sin = MathUtils.sinDeg(this.getRotation());
		double up_down = (vel_y > 0) ? 1 : -1;
	
		fd = coef_d * rho/2 * As * Math.pow(vel_y*cos, 2);
	    return fd * up_down;
	}
}
