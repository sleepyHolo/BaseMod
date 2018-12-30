package basemod.patches.com.megacrit.cardcrawl.cutscenes.Cutscene;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SpirePatch(
		clz=Cutscene.class,
		method=SpirePatch.CONSTRUCTOR
)
public class CustomCutscenes
{
	public static void Postfix(Cutscene __instance, AbstractPlayer.PlayerClass chosenClass)
	{
		for (AbstractPlayer player : CardCrawlGame.characterManager.getAllCharacters()) {
			if (player.chosenClass == chosenClass && player instanceof CustomPlayer) {
				Texture customBg = ((CustomPlayer) player).getCutsceneBg();
				if (customBg != null) {
					try {
						Field f = Cutscene.class.getDeclaredField("bgImg");
						f.setAccessible(true);

						Texture oldBg = (Texture) f.get(__instance);
						oldBg.dispose();
						f.set(__instance, customBg);
					} catch (IllegalAccessException | NoSuchFieldException e) {
						e.printStackTrace();
					}
				}

				List<CutscenePanel> customPanels = ((CustomPlayer) player).getCutscenePanels();
				if (customPanels != null) {
					try {
						Field f = Cutscene.class.getDeclaredField("panels");
						f.setAccessible(true);

						ArrayList<CutscenePanel> panels = (ArrayList<CutscenePanel>) f.get(__instance);
						for (CutscenePanel panel : panels) {
							panel.dispose();
						}
						panels.clear();
						panels.addAll(customPanels);
					} catch (IllegalAccessException | NoSuchFieldException e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}
}
