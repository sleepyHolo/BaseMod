package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.utils.UBJsonReader;

import basemod.interfaces.ModelRenderSubscriber;

public class BaseModRender2 implements ModelRenderSubscriber {

	public Model myModel;
	public ModelInstance myInstance = null;
	public AnimationController controller;

	public void create() {
		// Model loader needs a binary json reader to decode
		UBJsonReader jsonReader = new UBJsonReader();
		// Create a model loader passing in our json reader
		G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);
		// Now load the model by name
		// Note, the model (g3db file ) and textures need to be added to the
		// assets folder of the Android proj

		myModel = modelLoader.loadModel(Gdx.files.internal("data/Spider_3.g3db"));

		// Now create an instance. Instance holds the positioning data, etc
		// of an instance of your model
		myInstance = new ModelInstance(myModel, 0, 0, 10.0f);

		// fbx-conv is supposed to perform this rotation for you... it
		// doesnt seem to
		myInstance.transform.rotate(1, 0, 0, -90);

		// You use an AnimationController to um, control animations. Each
		// control is tied to the model instance
		controller = new AnimationController(myInstance);
		// Pick the current animation by name
		controller.setAnimation("Spider_Armature|Jump", 1, new AnimationListener() {

			@Override
			public void onEnd(AnimationDesc animation) {
				// this will be called when the current animation is done.
				// queue up another animation called "Jump".
				// Passing a negative to loop count loops forever. 1f for
				// speed is normal speed.
				controller.queue("Spider_Armature|Jump", -1, 1f, null, 0f);
			}

			@Override
			public void onLoop(AnimationDesc animation) {
				// TODO Auto-generated method stub

			}

		});
	}

	@Override
	public void receiveModelRender(ModelBatch batch, Environment env) {
		if (myInstance == null) {
			create();
		}
		controller.update(Gdx.graphics.getDeltaTime());
		batch.render(myInstance, env);
	}

}
