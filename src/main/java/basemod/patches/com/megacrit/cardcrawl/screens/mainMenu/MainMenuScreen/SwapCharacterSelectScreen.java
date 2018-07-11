package basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

import basemod.CustomCharacterSelectScreen;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;

@SpirePatch(
        cls = "com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen",
        method = SpirePatch.CONSTRUCTOR
)
public class SwapCharacterSelectScreen {


    @SpireInsertPatch(
            rloc = 23,
            localvars = {"charSelectScreen"}
    )
    public static void Insert(MainMenuScreen screen, @ByRef CharacterSelectScreen[] characterSelectScreen) {

        CustomCharacterSelectScreen newScreen = new CustomCharacterSelectScreen();
        newScreen.initialize();
        characterSelectScreen[0] = newScreen;

    }


    /*
        Change to use a Locator later
     */
//    public static class Locator extends SpireInsertLocator{
//
//        @Override
//        public int[] Locate(CtBehavior ctBehavior) throws Exception {
//
//            Matcher finalMatcher = new Matcher.FieldAccessMatcher(CharacterSelectScreen.class.getName(), "abandonedRun");
//
//            return LineFinder.findInOrder(ctBehavior, new ArrayList<Matcher>(), finalMatcher);
//        }
//    }
}
