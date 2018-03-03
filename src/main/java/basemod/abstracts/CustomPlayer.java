package basemod.abstracts;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public abstract class CustomPlayer extends AbstractPlayer{
	
	protected static ArrayList<Texture> ENERGY_LAYERS = new ArrayList<Texture>();
	protected static Texture ORB_VFX;

	public CustomPlayer(String name, PlayerClass playerClass) {
		super(name, playerClass);
		
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER1);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER2);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER3);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER4);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER5);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER6);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER1D);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER2D);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER3D);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER4D);
		ENERGY_LAYERS.add(ImageMaster.ENERGY_RED_LAYER5D);
		
		ORB_VFX = ImageMaster.loadImage("images/ui/topPanel/energyRedVFX.png");
		
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
			
		    sb.draw(ENERGY_LAYERS.get(0), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle1, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(1), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle2, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(2), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle3, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(3), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle4, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(4), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle5, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(5), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
		    
		    
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

	public void renderDisabledOrb(EnergyPanel panel, SpriteBatch sb) {
		
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
		    sb.draw(ENERGY_LAYERS.get(6), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle1, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(7), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle2, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(8), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle3, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(9), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle4, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(10), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angle5, 0, 0, 128, 128, false, false);
		    
		    sb.draw(ENERGY_LAYERS.get(5), panel.current_x - 64.0F, panel.current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 128, 128, false, false);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	public Texture getOrbVfxTexture() {
		return ORB_VFX;
	}
}
