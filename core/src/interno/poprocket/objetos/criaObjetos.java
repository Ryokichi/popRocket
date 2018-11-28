package interno.poprocket.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class criaObjetos {
	public static Sprite[] Latinhas(int qtd) {
		int idx;
		float x, y;
		Sprite array_latinhas[] = new Sprite[qtd];
		
		for (int i = 0; i < array_latinhas.length; i++) {
			x = MathUtils.random(2050, 4000);
			y = MathUtils.random(600, 6000);
			
			idx = i % 2;
			array_latinhas[i] = new Sprite(new Texture(Gdx.files.internal("img/latinha"+idx+".png")));
			array_latinhas[i].setPosition(x, y);
			array_latinhas[i].setScale(0.4f);
		}		
		return array_latinhas;
	}
	
	public static Sprite[] Nuvens(int qtd) {
		float x, y, w, h;
		int tipo;
		Sprite array_nuvens[] = new Sprite[qtd];
		
		for (int i = 0; i < array_nuvens.length; i++) {
			x = MathUtils.random(0, 1000);
			y = MathUtils.random(500, 5000);
			tipo = MathUtils.random(1,3);
			
			array_nuvens[i] = new Sprite(new Texture(Gdx.files.internal("img/nuvem_0"+ tipo +".png")));
			array_nuvens[i].setPosition(x, y);
			
			w = array_nuvens[i].getWidth();
			h = array_nuvens[i].getHeight();
			array_nuvens[i].setScale(3f);
		}		
		return array_nuvens;
	}
	
	public static Sprite[] NuvensFundo(int qtd) {
		float x, y;		
		Sprite array_nuvens[] = new Sprite[qtd];
		
		for (int i = 0; i < array_nuvens.length; i++) {
			x = MathUtils.random(0, 1000);
			y = MathUtils.random(500, 5000);	
			
			array_nuvens[i] = new Sprite(new Texture(Gdx.files.internal("img/nuvem_04.png")));
			array_nuvens[i].setPosition(x, y);
			
		}		
		return array_nuvens;
	}	

}
