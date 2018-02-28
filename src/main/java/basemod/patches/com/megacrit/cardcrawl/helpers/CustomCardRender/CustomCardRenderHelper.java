package basemod.patches.com.megacrit.cardcrawl.helpers.CustomCardRender;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

import basemod.abstracts.CustomCardWithRender;

public class CustomCardRenderHelper {
	public static void renderCardBG(SpriteBatch sb, float x, float y, CustomCardWithRender card) {
		
		Texture bgTexture = card.bGTexture;
		
		try {
			Method renderHelperMethod = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("renderHelper", SpriteBatch.class,
					Color.class, Texture.class, float.class, float.class);
			
			renderHelperMethod.setAccessible(true);
			
			Field renderColorField = card.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("renderColor");
			renderColorField.setAccessible(true);
			Color renderColor = (Color) renderColorField.get(card);
			renderHelperMethod.invoke(card, sb, renderColor, bgTexture, x, y);
		}catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public static void renderCardBGPortrait(SpriteBatch sb, CustomCardWithRender card) {
		Texture bgTexture = card.bGTexture_p;
		
		sb.draw(bgTexture,
				Settings.WIDTH / 2.0f - 512.0f,
				Settings.HEIGHT / 2.0f - 512.0f,
				512.0f,
				512.0f,
				1024.0f,
				1024.0f,
				Settings.scale,
				Settings.scale,
				0.0f,
				0,
				0,
				1024,
				1024,
				false,
				false);
	}
}
