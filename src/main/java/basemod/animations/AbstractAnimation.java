package basemod.animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractAnimation
{
    public enum Type
    {
        NONE, SPRITE, MODEL
    }

    public abstract Type type();

    @Deprecated
    public void renderSprite(SpriteBatch batch)
    {
        AbstractPlayer player = AbstractDungeon.player;
        renderSprite(batch, player.drawX + player.animX, player.drawY + player.animY + AbstractDungeon.sceneOffsetY);
    }
    public void renderSprite(SpriteBatch batch, float x, float y) {}
    public void renderModel(ModelBatch batch, Environment env) {}
}
