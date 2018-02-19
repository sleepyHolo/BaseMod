package basemod.patches.com.megacrit.cardcrawl.map.MapGenerator;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.map.MapGenerator", method="generateDungeon")
public class PathDensityMultiplier {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals("com.megacrit.cardcrawl.map.MapGenerator") && m.getMethodName().equals("createPaths")) {
                    m.replace("{ $2 = (int)($2 * basemod.BaseMod.mapPathDensityMultiplier); $_ = $proceed($$); }");
                }
            }
        };
    }
}
