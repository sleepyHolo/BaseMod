package basemod.patches.com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;

@SpirePatch(cls="com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption", method="update")
public class PostCampfireHook {
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getClassName().equals("com.megacrit.cardcrawl.rooms.CampfireUI") && f.getFieldName().equals("somethingSelected") && f.isWriter()) {
                    f.replace("{ $0.somethingSelected = basemod.BaseMod.publishPostCampfire(); }");
                }
            }
        };
    }
}