package interno.poprocket;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class criaObjetos {
	
	public static Estrela[] Estrelas (int qtd) {
		float x, y;
		Estrela array_estrelas[] = new Estrela[qtd];
		
		for (int i = 0; i < array_estrelas.length; i++) {
			x = MathUtils.random(0, 500);
			y = MathUtils.random(0, 500);
			
			array_estrelas[i] = new Estrela(new Texture(Gdx.files.internal("img/rocket0.png")));
			array_estrelas[i].setPosition(x, y);
			array_estrelas[i].setSize(50, 25);
		}
		
		return array_estrelas;		
	}
}
