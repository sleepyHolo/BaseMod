package basemod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import basemod.interfaces.ModelRenderSubscriber;

public class BaseModRender implements ModelRenderSubscriber {

	public Model myModel;
	public ModelInstance myInstance = null;
    
	private void create() {
        ModelBuilder modelBuilder = new ModelBuilder();
        myModel = modelBuilder.createBox(150.0f, 150.0f, 150.0f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal);
        myInstance = new ModelInstance(myModel, 0, 0, 10.0f);
	}

	@Override
	public void receiveModelRender(ModelBatch batch, Environment env) {
		if (myInstance == null) {
			create();
		}
		
		myInstance.transform.rotate(new Vector3(.2f, .2f, .2f), 1f);
		batch.render(myInstance, env);
	}

}
