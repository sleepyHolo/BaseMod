package basemod.abstracts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationDesc;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import basemod.interfaces.ModelRenderSubscriber;

public class CustomPlayerWithRender extends AbstractPlayer implements ModelRenderSubscriber {

	public Model myModel;
	public ModelInstance myInstance = null;
	public AnimationController controller;

	private String modelString;
	private String animationString;
	private boolean rescaled = false;

	public CustomPlayerWithRender(String name, PlayerClass setClass, String model, String animation) {
		super(name, setClass);

		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 220.0F * Settings.scale);

		this.modelString = model;
		this.animationString = animation;
		
		this.atlas = new TextureAtlas();
		
		BaseMod.subscribeToModelRender(this);
	}

	@Override
	public void renderPlayerImage(SpriteBatch sb) {
		BaseMod.publishAnimationRender(sb);
	}

	public void create() {
		try {
			// Model loader needs a binary json reader to decode
			JsonReader jsonReader = new JsonReader();

			// Create a model loader passing in our json reader
			G3dModelLoader modelLoader = new G3dModelLoader(jsonReader);

			// Now load the model by name
			myModel = modelLoader.loadModel(Gdx.files.internal(modelString));

			// Necessary to get transparent textures working - I don't know why
			for (Material mat : myModel.materials) {
				mat.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
			}

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
			controller.setAnimation(animationString, 1, new AnimationListener() {

				@Override
				public void onEnd(AnimationDesc animation) {
					// this will be called when the current animation is done.
					// Passing a negative to loop count loops forever. 1f for
					// speed is normal speed.
					controller.queue(animationString, -1, 1f, null, 0f);
				}

				@Override
				public void onLoop(AnimationDesc animation) {
					// TODO Auto-generated method stub

				}

			});
		// catch block to prevent it failing silently
		} catch (Exception e) {
			e.printStackTrace();
			
			// not being able to load character image is a fatal error
			Gdx.app.exit();
		}
	}

	@Override
	public void receiveModelRender(ModelBatch batch, Environment env) {
		if (myInstance == null) {
			create();
		}
		controller.update(Gdx.graphics.getDeltaTime());
		Vector3 loc = myInstance.transform.getTranslation(new Vector3());
		AbstractPlayer player = AbstractDungeon.player;
		if (player != null) {
			loc.x = player.drawX + player.animX - Gdx.graphics.getWidth() / 2;
			loc.y = player.drawY + player.animY + AbstractDungeon.sceneOffsetY - Gdx.graphics.getHeight() / 2;
			myInstance.transform.setTranslation(loc);
			// only scale *once*
			if (!rescaled) {
				// do the z scale instead of y scale - not entirely sure why but it probably has to do with
				// how blender and libgdx differ in their idea of what direction is "up"
				myInstance.transform.scale(Settings.scale, 1.0f, Settings.scale);
				rescaled = true;
			}
			batch.render(myInstance, env);
		}
	}

}
