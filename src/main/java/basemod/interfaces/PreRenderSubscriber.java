package basemod.interfaces;

import com.badlogic.gdx.graphics.OrthographicCamera;

public interface PreRenderSubscriber {
	void receiveCameraRender(OrthographicCamera camera);
}
