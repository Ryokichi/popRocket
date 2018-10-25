package interno.poprocket.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import interno.poprocket.PopRocket;
import interno.poprocket.PopRocket;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PopRocket(), config);
		config.title  = "PopRocket";
		config.width  = Gdx.graphics.getWidth();
		config.height = Gdx.graphics.getHeight();
	}
}
