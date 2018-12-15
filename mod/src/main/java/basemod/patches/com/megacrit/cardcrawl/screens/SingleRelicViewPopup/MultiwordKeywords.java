package basemod.patches.com.megacrit.cardcrawl.screens.SingleRelicViewPopup;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch(
		clz=SingleRelicViewPopup.class,
		method="renderDescription"
)
public class MultiwordKeywords
{
	public static ExprEditor Instrument()
	{
		return new ExprEditor()
		{
			@Override
			public void edit(FieldAccess f) throws CannotCompileException
			{
				if (f.getClassName().equals(AbstractRelic.class.getName()) && f.getFieldName().equals("description")) {
					f.replace("$_ = " + MultiwordKeywords.class.getName() + ".Do($proceed($$));");
				}
			}
		};
	}

	public static String Do(String input)
	{
		return basemod.patches.com.megacrit.cardcrawl.relics.AbstractRelic.MultiwordKeywords.replaceMultiWordKeywords(input);
	}
}
