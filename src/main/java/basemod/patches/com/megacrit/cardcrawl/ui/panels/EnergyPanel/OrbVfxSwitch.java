package basemod.patches.com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomPlayer;

@SpirePatch(cls = "com.megacrit.cardcrawl.ui.panels.EnergyPanel", method = "renderVfx")
public class OrbVfxSwitch {
	public static void Prefix(Object __obj_instance, SpriteBatch sb) {
		EnergyPanel panel = (EnergyPanel) __obj_instance;
		
		if(AbstractDungeon.player instanceof CustomPlayer) {
			
			Field gainEnergyImgobj;
			
			try {
				gainEnergyImgobj = panel.getClass().getDeclaredField("gainEnergyImg");
				gainEnergyImgobj.setAccessible(true);
					
				gainEnergyImgobj.set(panel, ((CustomPlayer)AbstractDungeon.player).getOrbVfxTexture());
				
				//Texture gainEnergyImg = (Texture)gainEnergyImgobj.get(panel);
				
				
				
			} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
