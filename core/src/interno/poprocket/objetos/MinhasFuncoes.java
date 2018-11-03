package interno.poprocket.objetos;

import com.badlogic.gdx.math.MathUtils;
///novo
public class MinhasFuncoes {
	public float[] novaPosicao(float map_width, float map_height) {
		float x = MathUtils.random(map_width, 2*map_width);
		float y = MathUtils.random(60, map_height);
		float pos[] = {x, y};
		return pos;
	}

}
