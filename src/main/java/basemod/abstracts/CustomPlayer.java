package basemod.abstracts;

import basemod.BaseMod;
import basemod.animations.AbstractAnimation;
import basemod.animations.G3DJAnimation;
import basemod.interfaces.ModelRenderSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class CustomPlayer extends AbstractPlayer implements ModelRenderSubscriber {
	
	public static final int LAYER_COUNT = 5;
	public static final float DEFAULT_ANGLE = 0.0f;

	AbstractAnimation animation;
	
	private ArrayList<Texture> energyLayers = new ArrayList<Texture>();
	private float[] energyLayerSpeeds = null;
	private float[] angles = null;
	private Texture orbVfx;

	public CustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath,
			String model, String animation) {
		this(name, playerClass, orbTextures, orbVfxPath, null, model, animation);
	}
	
	public CustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds,
			String model, String animation) {
		this(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, new G3DJAnimation(model, animation));
	}

	public CustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, AbstractAnimation animation) {
		this(name, playerClass, orbTextures, orbVfxPath, null, animation);
	}

	public CustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds,
						AbstractAnimation animation) {
		super(name, playerClass);
		
		if (orbTextures == null || orbVfxPath == null) {
			buildDefaultOrb();
		} else {
			buildCustomOrb(orbTextures, orbVfxPath);
		}
		
		energyLayerSpeeds = layerSpeeds;
		initAngles();
		
		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 220.0F * Settings.scale);

		this.animation = animation;

		if (animation.type() != AbstractAnimation.Type.NONE) {
			this.atlas = new TextureAtlas();
		}
		
		if (animation.type() == AbstractAnimation.Type.MODEL) {
			BaseMod.subscribe(this);
		}
	}
	
	private void initAngles() {
		angles = new float[LAYER_COUNT];
		for (int i = 0; i < angles.length; i++) {
			angles[i] = DEFAULT_ANGLE;
		}
	}

	@Override
	public void receiveModelRender(ModelBatch batch, Environment env) {
		if (this != AbstractDungeon.player) {
			BaseMod.unsubscribeLater(this);
		} else {
			animation.renderModel(batch, env);
		}
	}

	@Override
	public void renderPlayerImage(SpriteBatch sb) {
		switch (animation.type()) {
			case NONE:
				super.renderPlayerImage(sb);
				break;
			case MODEL:
				BaseMod.publishAnimationRender(sb);
				break;
			case SPRITE:
				animation.renderSprite(sb);
				break;
		}
	}
	
	public Texture getOrbVfxTexture() {
		return orbVfx;
	}
	
	public static final String DEFAULT_ORB_VFX = "images/ui/topPanel/energyRedVFX.png";
	
	private void buildDefaultOrb() {
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER1);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER2);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER3);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER4);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER5);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER6);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER1D);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER2D);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER3D);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER4D);
		energyLayers.add(ImageMaster.ENERGY_RED_LAYER5D);

		orbVfx = ImageMaster.loadImage(DEFAULT_ORB_VFX);
	}
	
	private void buildCustomOrb(String[] orbTextures, String orbVfxPath) {
		for(String texPath : orbTextures) {
			energyLayers.add(ImageMaster.loadImage(texPath));
		}
		
		orbVfx = ImageMaster.loadImage(orbVfxPath);
	}
	
	public void renderOrb(EnergyPanel panel, SpriteBatch sb) {
		sb.setColor(Color.WHITE);
		
		Field orb_scale;
		Field orb_angle_1;
		Field orb_angle_2;
		Field orb_angle_3;
		Field orb_angle_4;
		Field orb_angle_5;
		
		try {
			orb_scale = panel.getClass().getDeclaredField("ORB_IMG_SCALE");
			orb_scale.setAccessible(true);
			
			float ORB_IMG_SCALE = orb_scale.getFloat(panel);
			
			orb_angle_1 = panel.getClass().getDeclaredField("angle1");
			orb_angle_1.setAccessible(true);
			
			float angle1 = orb_angle_1.getFloat(panel);
			
			orb_angle_2 = panel.getClass().getDeclaredField("angle2");
			orb_angle_2.setAccessible(true);
			
			float angle2 = orb_angle_2.getFloat(panel);
			
			orb_angle_3 = panel.getClass().getDeclaredField("angle3");
			orb_angle_3.setAccessible(true);
			
			float angle3 = orb_angle_3.getFloat(panel);
			
			orb_angle_4 = panel.getClass().getDeclaredField("angle4");
			orb_angle_4.setAccessible(true);
			
			float angle4 = orb_angle_4.getFloat(panel);
			
			orb_angle_5 = panel.getClass().getDeclaredField("angle5");
			orb_angle_5.setAccessible(true);
			
			float angle5 = orb_angle_5.getFloat(panel);
			
			if (energyLayerSpeeds != null) {
				angles[4] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[0];
				angles[3] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[1];
				angles[2] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[2];
				angles[1] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[3];
				angles[0] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[4];
				angle5 = angles[4];
				angle4 = angles[3];
				angle3 = angles[2];
				angle2 = angles[1];
				angle1 = angles[0];
			}
			
			sb.setColor(Color.WHITE);
			
		    sb.draw(energyLayers.get(0), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle1, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(1), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle2, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(2), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle3, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(3), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle4, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(4), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle5, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(5), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
		    
		    
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

	public void renderDisabledOrb(EnergyPanel panel, SpriteBatch sb) {
		Field orb_scale, orb_angle_1, orb_angle_2, orb_angle_3,
				orb_angle_4, orb_angle_5;
		
		try {
			// access private fields off the EnergyPanel
			orb_scale = panel.getClass().getDeclaredField("ORB_IMG_SCALE");
			orb_scale.setAccessible(true);
			float ORB_IMG_SCALE = orb_scale.getFloat(panel);
			
			orb_angle_1 = panel.getClass().getDeclaredField("angle1");
			orb_angle_1.setAccessible(true);
			
			float angle1 = orb_angle_1.getFloat(panel);
			
			orb_angle_2 = panel.getClass().getDeclaredField("angle2");
			orb_angle_2.setAccessible(true);
			
			float angle2 = orb_angle_2.getFloat(panel);
			
			orb_angle_3 = panel.getClass().getDeclaredField("angle3");
			orb_angle_3.setAccessible(true);
			
			float angle3 = orb_angle_3.getFloat(panel);
			
			orb_angle_4 = panel.getClass().getDeclaredField("angle4");
			orb_angle_4.setAccessible(true);
			
			float angle4 = orb_angle_4.getFloat(panel);
			
			orb_angle_5 = panel.getClass().getDeclaredField("angle5");
			orb_angle_5.setAccessible(true);
			
			float angle5 = orb_angle_5.getFloat(panel);
			
			if (energyLayerSpeeds != null) {
				angles[4] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[5];
				angles[3] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[6];
				angles[2] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[7];
				angles[1] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[8];
				angles[0] += Gdx.graphics.getDeltaTime() * energyLayerSpeeds[9];
				angle5 = angles[4];
				angle4 = angles[3];
				angle3 = angles[2];
				angle2 = angles[1];
				angle1 = angles[0];
			}
			
			// actual rendering code
			sb.setColor(Color.WHITE);
		    sb.draw(energyLayers.get(6), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle1, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(7), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle2, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(8), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle3, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(9), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle4, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(10), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle5, 0, 0, 128, 128, false, false);
		    
		    sb.draw(energyLayers.get(5), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}
