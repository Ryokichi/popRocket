package interno.poprocket.objetos;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class Combustivel extends ProgressBar{
	
	private float MAXCOMB = 2500;	

	public float combustivel = MAXCOMB;
	public float difCombustivel, combustivelAnt;
	
	public void consumir(float dt) {
		combustivel -= dt*166;
		if (combustivel < 0) combustivel = 0;
	}
	
	public float nivel() {
		return combustivel;
	}
	
	public void addCombustivel (float nvl) {
		combustivel += nvl;
		if (combustivel > MAXCOMB) {
			combustivel = MAXCOMB;
		}
			
	}
	
	public Combustivel (int width, int height) {
		super(0f, 2500f, 1f, false, new ProgressBarStyle());
		getStyle().background = Utils.getColoredDrawable(width, height, Color.LIGHT_GRAY);
		getStyle().knob = Utils.getColoredDrawable(0, height, Color.GREEN);
		getStyle().knobBefore = Utils.getColoredDrawable(width, height, Color.GREEN);
		
		setWidth(width);
		setHeight(height);
		
		setAnimateDuration(0.0f);
		setValue(2500f);
		
		setAnimateDuration(0.25f);
	}
}
