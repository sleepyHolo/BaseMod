package basemod.patches.com.megacrit.cardcrawl.core.CardCrawlGame;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.core.CardCrawlGame", method="render")
public class PreRenderHook {
	public static final Logger logger = LogManager.getLogger(BaseMod.class.getName());
	
    public static void Prefix(Object __obj_instance) {
    	CardCrawlGame instance = (CardCrawlGame) __obj_instance;
        Field cameraField;
		try {
			cameraField = CardCrawlGame.class.getDeclaredField("camera");
	        cameraField.setAccessible(true);
	        OrthographicCamera camera = (OrthographicCamera) cameraField.get(instance);
	        BaseMod.publishPreRender(camera);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error("could not get camera for render hook ");
			logger.error("error was: " + e.toString());
			e.printStackTrace();
		}

    }
}
