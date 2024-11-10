package basemod.patches.com.megacrit.cardcrawl.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.characters.Watcher;
import com.megacrit.cardcrawl.core.Settings;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch2(
		clz = Watcher.class,
		method = "loadEyeAnimation"
)
public class FixWatcherVisualScale
{
	@SpireInstrumentPatch
	public static ExprEditor instrument()
	{
		return new ExprEditor() {
			@Override
			public void edit(FieldAccess f) throws CannotCompileException
			{
				if (f.isReader() && f.getClassName().equals(Settings.class.getName()) && f.getFieldName().equals("scale")) {
					f.replace("$_ = " + Settings.class.getName() + ".renderScale;");
				}
			}
		};
	}
}
