package basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;

import basemod.BaseMod;

@SpirePatch(cls="com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen", method="setMainMenuButtons")
public class SetButtonsSwitch {
	public static void Postfix(Object __obj_instance) {
		MainMenuScreen screen = (MainMenuScreen) __obj_instance;
		if (!BaseMod.baseGameSaveExists() && BaseMod.moddedSaveExists()) {
			screen.buttons.remove(screen.buttons.size() - 1);
			screen.buttons.add(new MenuButton(MenuButton.ClickResult.ABANDON_RUN, screen.buttons.size()));
			screen.buttons.add(new MenuButton(MenuButton.ClickResult.RESUME_GAME, screen.buttons.size()));
		}
	}
}
