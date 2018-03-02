package basemod.patches.com.megacrit.cardcrawl.helpers.EnergyOrbRender;

import java.lang.reflect.Field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomPlayer;



public class EnergyOrbRenderHelper {
	
	public static void render(EnergyPanel panel, SpriteBatch sb) {
		if(AbstractDungeon.player instanceof CustomPlayer) {
			if(panel.totalCount > 0) {
				((CustomPlayer)AbstractDungeon.player).renderOrb(panel, sb);
			}else {
				((CustomPlayer)AbstractDungeon.player).renderDisabledOrb(panel, sb);
			}
		}
	}
	
	
}
