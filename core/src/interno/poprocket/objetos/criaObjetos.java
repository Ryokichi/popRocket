package interno.poprocket.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class criaObjetos {
	
	public static Sprite[] Estrelas (int qtd) {
		float x, y;
		Estrela array_estrelas[] = new Estrela[qtd];
		
		for (int i = 0; i < array_estrelas.length; i++) {
			x = MathUtils.random(1050, 2000);
			y = MathUtils.random(300, 5000);
			
			array_estrelas[i] = new Estrela(new Texture(Gdx.files.internal("img/estrela.png")));
			array_estrelas[i].setPosition(x, y);
			array_estrelas[i].setSize(15, 15);
		}
		
		return array_estrelas;		
	}
	
	public static Sprite[] Asteroides(int qtd) {
		float x, y;
		Sprite array_asteroides[] = new Sprite[qtd];
		
		for (int i = 0; i < array_asteroides.length; i++) {
			x = MathUtils.random(1050, 2000);
			y = MathUtils.random(300, 5000);
			
			array_asteroides[i] = new Sprite(new Texture(Gdx.files.internal("img/asteroide.png")));
			array_asteroides[i].setPosition(x, y);
			array_asteroides[i].setSize(20, 20);
		}		
		return array_asteroides;
	}
	
	public static Sprite[] Nuvens(int qtd) {
		float x, y;
		int tipo;
		Sprite array_nuvens[] = new Sprite[qtd];
		
		for (int i = 0; i < array_nuvens.length; i++) {
			x = MathUtils.random(0, 1000);
			y = MathUtils.random(300, 5000);
			tipo = MathUtils.random(1,3);
			
			array_nuvens[i] = new Sprite(new Texture(Gdx.files.internal("img/nuvem_0"+ tipo +".png")));
			array_nuvens[i].setPosition(x, y);
			
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
