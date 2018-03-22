package basemod.patches.com.megacrit.cardcrawl.helpers.EnergyOrbRender;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomPlayer;



public class EnergyOrbRenderHelper {
	
	public static void render(EnergyPanel panel, SpriteBatch sb) {
		if(AbstractDungeon.player instanceof CustomPlayer) {
			if(EnergyPanel.totalCount > 0) {
				((CustomPlayer)AbstractDungeon.player).renderOrb(panel, sb);
			}else {
				((CustomPlayer)AbstractDungeon.player).renderDisabledOrb(panel, sb);
			}
		}else {
			Field totalCountField;
			try {
				totalCountField = panel.getClass().getDeclaredField("totalCount");
				totalCountField.setAccessible(true);
				int totalCount = totalCountField.getInt(panel);
				if (totalCount == 0) {
					Method renderRedOrbDisabled = panel.getClass().getDeclaredMethod("renderRedOrbDisabled", SpriteBatch.class);
					renderRedOrbDisabled.setAccessible(true);
					renderRedOrbDisabled.invoke(panel, sb);
				} else {
					Method renderRedOrb = panel.getClass().getDeclaredMethod("renderRedOrb", SpriteBatch.class);
					renderRedOrb.setAccessible(true);
					renderRedOrb.invoke(panel, sb);			
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}
