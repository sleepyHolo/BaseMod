package basemod.patches.com.megacrit.cardcrawl.helpers.RelicLibrary;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
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
	
}
