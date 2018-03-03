package basemod.abstracts;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public abstract class CustomPlayer extends AbstractPlayer{
	
	private ArrayList<Texture> energyLayers = new ArrayList<Texture>();
	private Texture orbVfx;

	public CustomPlayer(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath) {
		super(name, playerClass);
		
		if (orbTextures == null || orbVfxPath == null) {
			buildDefaultOrb();
		} else {
			buildCustomOrb(orbTextures, orbVfxPath);
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
			energyLayers.add(new Texture(Gdx.files.internal(texPath)));
		}
		
		orbVfx = new Texture(Gdx.files.internal(orbVfxPath));
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
