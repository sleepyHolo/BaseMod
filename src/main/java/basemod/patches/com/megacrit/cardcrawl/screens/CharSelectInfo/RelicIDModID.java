package basemod.patches.com.megacrit.cardcrawl.screens.CharSelectInfo;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

@SpirePatch(
        cls="com.megacrit.cardcrawl.screens.CharSelectInfo",
        method="ctor",
        paramtypes={"java.lang.String",
                "java.lang.String",
                "int",
                "int",
                "int",
                "int",
                "int",
                "com.megacrit.cardcrawl.characters.AbstractPlayer$PlayerClass",
                "java.util.ArrayList",
                "java.util.ArrayList",
                "boolean"
        }
)
public class RelicIDModID
{
    public static void Prefix(CharSelectInfo __instance, String name, String flavorText, int currentHp, int maxHp, int maxOrbs, int gold, int cardDraw,
                              AbstractPlayer.PlayerClass color, ArrayList<String> relics, ArrayList<String> deck, boolean resumeGame)
    {
        String modName = null;
        for (int i=0; i<relics.size(); ++i) {
            if (!relics.get(i).equals(Circlet.ID) && !BaseMod.hasModID(relics.get(i))) {
                if (modName == null) {
                    modName = BaseMod.findCallingModName();
                }
                if (modName != null && !relics.get(i).startsWith(modName + ":")) {
                    relics.set(i, modName + ":" + relics.get(i));
                }
            }
        }

        for (int i=0; i<deck.size(); ++i) {
            if (!BaseMod.hasModID(deck.get(i))) {
                if (modName == null) {
                    modName = BaseMod.findCallingModName();
                }
                if (modName != null && !deck.get(i).startsWith(modName + ":")) {
                    deck.set(i, modName + ":" + deck.get(i));
                }
            }
        }
    }
}
