package interno.poprocket.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
///novo
public class criaObjetos {
	
	public static Estrela[] Estrelas (int qtd) {
		float x, y;
		Estrela array_estrelas[] = new Estrela[qtd];
		
		for (int i = 0; i < array_estrelas.length; i++) {
			x = MathUtils.random(1000, 1500);
			y = MathUtils.random(0, 1000);
			
			array_estrelas[i] = new Estrela(new Texture(Gdx.files.internal("img/rocket0.png")));
			array_estrelas[i].setPosition(x, y);
			array_estrelas[i].setSize(50, 25);
		}
		
		return array_estrelas;		
	}
	
	public static Sprite[] Asteroides(int qtd) {
		float x, y;
		Sprite array_asteroides[] = new Sprite[qtd];
		
		for (int i = 0; i < array_asteroides.length; i++) {
			x = MathUtils.random(500, 1000);
			y = MathUtils.random(0, 1000);
			
			array_asteroides[i] = new Sprite(new Texture(Gdx.files.internal("img/asteroide.png")));
			array_asteroides[i].setPosition(x, y);
			array_asteroides[i].setSize(25,  25);
		}		
		return array_asteroides;
	}
}
