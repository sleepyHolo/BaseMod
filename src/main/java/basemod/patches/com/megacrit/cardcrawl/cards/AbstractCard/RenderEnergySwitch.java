package basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import basemod.BaseMod;

@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "renderEnergy")
public class RenderEnergySwitch {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
	@SpireInsertPatch(loc = 2540, localvars = { "drawX", "drawY" })
	public static void Insert(Object __obj_instance, Object sbObj, float drawX, float drawY) {
		AbstractCard card = (AbstractCard) __obj_instance;
		CardColor color = card.color;
		SpriteBatch sb = (SpriteBatch) sbObj;
		if (!color.toString().equals("RED") && !color.toString().equals("GREEN") && !color.toString().equals("BLUE")
				&& !color.toString().equals("COLORLESS") && !color.toString().equals("CURSE")) {
			Texture orbTexture = BaseMod.getEnergyOrb(color.toString());
			if (orbTexture == null) {
				orbTexture = ImageMaster.CARD_COLORLESS_ORB;
			}
			try {
				// use reflection hacks to invoke renderHelper (without float scale)
				Method renderHelperMethod;
				renderHelperMethod = card.getClass().getSuperclass().getSuperclass().getDeclaredMethod("renderHelper", SpriteBatch.class,
						Color.class, Texture.class, float.class, float.class);
				renderHelperMethod.setAccessible(true);
				Field renderColorField = card.getClass().getSuperclass().getSuperclass().getDeclaredField("renderColor");
				renderColorField.setAccessible(true);
				Color renderColor = (Color) renderColorField.get(card);
				renderHelperMethod.invoke(card, sb, renderColor, orbTexture, drawX, drawY);
			} catch (NoSuchMethodException | SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				logger.error("could not render energy for card " + card.getClass().toString() + " with color " + color.toString());
				logger.error("exception is: " + e.getMessage());
				e.printStackTrace();
			}

		}

	}
}