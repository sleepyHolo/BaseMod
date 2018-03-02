package basemod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import basemod.interfaces.PreRenderSubscriber;
import basemod.interfaces.RenderSubscriber;

public class BaseModRender implements RenderSubscriber, PreRenderSubscriber {

//    public CameraInputController camController = null;
	
	public Environment environment;
	public ModelBatch modelBatch;
    public AssetManager assets;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
	public Model model;
	public ModelInstance instance;
    public FrameBuffer frameBuffer;
	public Texture texture;
    public TextureRegion textureRegion;
    public OrthographicCamera myCamera = null;
    
	private void create() {
		myCamera =  new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		modelBatch = new ModelBatch();
	
	      environment = new Environment();
	      environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
	      environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
//        @SuppressWarnings("rawtypes")
//		ModelLoader loader = new ObjLoader();
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(150.0f, 150.0f, 150.0f, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position| VertexAttributes.Usage.Normal);
        //model = loader.loadModel(Gdx.files.internal("data/teapot.obj"));
        instance = new ModelInstance(model, 0, 0, 10.0f);
        frameBuffer = new FrameBuffer(Format.RGBA8888,Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false);
	}
	
	@Override
	public void receiveRender(SpriteBatch sb) {
		sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        sb.draw(textureRegion, 0, 0);
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	@Override
	public void receiveCameraRender(OrthographicCamera camera) {
		if (myCamera == null) {
			create();
		}

		myCamera.near = 1.0f;
		myCamera.far = 300.0f;
		myCamera.position.z = 200.0f;
		myCamera.update();
		instance.transform.rotate(new Vector3(.2f, .2f, .2f), 1f);
		System.out.println("trying to render a box");
		frameBuffer.begin();
	    Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        modelBatch.begin(myCamera);
        modelBatch.render(instance, environment);
        modelBatch.end();
        frameBuffer.end();
        texture = frameBuffer.getColorBufferTexture();
        textureRegion = new TextureRegion(texture);
        textureRegion.flip(false, true);
	}

}
