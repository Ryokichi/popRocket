package interno.poprocket.objetos;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Rocket extends Sprite {	
	private double vel_x   = 0;
	private double vel_y   = 0;
    private double vel_abs = 0;
	private double acel    = 6;
	private double prop    = 20;
	private double mass    = 1.5;
	private double coef_d  = 0;    ////coeficiente de arrasto  (Drag)
	private double coef_l  = 0.07; ////coeficiente de sustentação (Lift)
	private double rho     = 2;    ////densidade do ar
	private double Af      = 0;    ////área frontal do foguete
	private double As      = 0;    ////área superior do foguete
	
	private NumberFormat  nf = NumberFormat.getInstance(Locale.US);
	private MinhasFuncoes mf = new MinhasFuncoes();
	
	public Rocket() {
		super(new Texture(Gdx.files.internal("img/rocket0.png")));
		
		////Converte double em string de duas casa decimais
	    nf.setMaximumFractionDigits(2);
	    nf.setMinimumFractionDigits(0);
	    nf.setMaximumIntegerDigits(20);
	    nf.setRoundingMode(RoundingMode.HALF_DOWN);
	}
	
	public void acelerar(float dt) {
		double acel = (this.prop / this.mass);
		vel_x += acel * MathUtils.cosDeg(this.getRotation()) * dt;
		vel_y += acel * MathUtils.sinDeg(this.getRotation()) * dt;		
	}
	
	public void atualizaVelocidades(float dt, double gravidade) {
		vel_x = vel_x - arrastoX() * dt;
		vel_y = vel_y - (gravidade) * dt ;
//		this.updatetVelocidadeAbs();
	}
	
	public void aumentaVel(double x, double y) {
		vel_x += x;
		vel_y += y;
	}
	
	public void setVelocidade (double x, double y) {
		this.vel_x = x;
		this.vel_y = y;
	}
	
	public void setVelX (double vel) {	
		vel_x = (vel > 0) ? vel : 0;
	}
	
	public void setVelY (double vel) {
		vel_y = vel;
	}
	
	public double getVelX() {
		return mf.d2(vel_x);
	}
	
	public double getVelY() {
		return mf.d2(vel_y);
	}	
	
	public void setAceleracao(double a) {
		acel = a;
	}
	
	public double getVelocidadeAbs() {
		return mf.d2(vel_abs);
	}
	
	public double getVelocidade() {
		return mf.d2(vel_abs);
	}
	
	public void updatetVelocidadeAbs () {		
		vel_abs = Math.sqrt(Math.pow(vel_x, 2) + Math.pow(vel_y, 2));
		if (vel_abs < 0) vel_abs = 0;
	}
	
	public double sustentacao() {
		double fl   = 0;  ////force lift
		//double cos  = MathUtils.cosDeg(this.getRotation());
		//double sin  = MathUtils.sinDeg(this.getRotation());
				
		//fl = coef_l * rho/2 * As * Math.pow(vel_x, 2);
		fl = coef_l * As * Math.pow(vel_x, 2);	
	    return mf.d2(fl);
	}
	
	public double arrastoX() {
		double fd  = 0;  ////force drag
		double cos = MathUtils.cosDeg(this.getRotation());
		double sin = MathUtils.sinDeg(this.getRotation());
		
		fd = coef_d * rho/2 * ((Af*cos)+(As*sin)) * Math.pow(vel_x*cos, 2);		
	    return mf.d2(fd);
	}
	
	public double arrastoY() {
		double fd  = 0;  ////force drag
		double cos = MathUtils.cosDeg(this.getRotation());
//		double sin = MathUtils.sinDeg(this.getRotation());
		double up_down = (vel_y > 0) ? 1 : -1;
	
		fd = coef_d * rho/2 * As * Math.pow(vel_y*cos, 2);
	    return mf.d2(fd * up_down);
	}
}
