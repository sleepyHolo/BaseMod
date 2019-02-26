package basemod.patches.com.megacrit.cardcrawl.audio.SoundMaster;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.audio.SoundMaster;

@SpirePatch(clz=SoundMaster.class, method=SpirePatch.CONSTRUCTOR)

public class AddAudio {
    public static void Postfix(SoundMaster __obj_instance) {
        BaseMod.publishAddAudio(__obj_instance);
    }
}