package basemod.interfaces;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

public interface ModelRenderSubscriber {
	void receiveModelRender(ModelBatch batch, Environment env);
}
