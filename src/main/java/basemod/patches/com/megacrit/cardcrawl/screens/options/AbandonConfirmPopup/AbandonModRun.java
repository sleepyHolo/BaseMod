package basemod.patches.com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpirePatch(
		cls="com.megacrit.cardcrawl.screens.options.AbandonConfirmPopup",
		method="effect"
)
public class AbandonModRun
{
	public static void Postfix(AbandonConfirmPopup __instance)
	{
		if (!BaseMod.baseGameSaveExists()) {
			AbstractPlayer.PlayerClass savePlayerClass = BaseMod.getSaveClass();
			if (savePlayerClass != null) {
				try {
					Method abandonRun = AbandonConfirmPopup.class.getDeclaredMethod("abandonRun", AbstractPlayer.PlayerClass.class);
					abandonRun.setAccessible(true);
					abandonRun.invoke(__instance, savePlayerClass);
				} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
