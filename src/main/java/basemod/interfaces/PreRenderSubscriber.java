package basemod.interfaces;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface PreRenderSubscriber {
	void receiveCameraRender(OrthographicCamera camera);
}
