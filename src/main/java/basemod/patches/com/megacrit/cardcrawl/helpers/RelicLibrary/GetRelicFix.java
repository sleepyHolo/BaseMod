package basemod.patches.com.megacrit.cardcrawl.helpers.RelicLibrary;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.Circlet;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

@SpirePatch(cls="com.megacrit.cardcrawl.helpers.RelicLibrary", method="getRelic")
public class GetRelicFix {

	public static ExprEditor Instrument() {
		return new ExprEditor() {
			@Override
			public void edit(NewExpr e) throws CannotCompileException
			{
				if (e.getClassName().equals("com.megacrit.cardcrawl.relics.Circlet")) {
					e.replace("$_ = basemod.BaseMod.getCustomRelic(key);");
				}
			}
		};
	}

	public static void Prefix(@ByRef String[] key) {
		if (!key[0].equals(Circlet.ID)) {
			String modName = BaseMod.findCallingModName();
			if (modName != null && !key[0].startsWith(modName + ":")) {
				key[0] = modName + ":" + key[0];
			}
		}
	}
	
}
