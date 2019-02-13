package basemod.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.*;
import com.brashmonkey.spriter.LibGdx.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdx.LibGdxLoader;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SpriterAnimation extends AbstractAnimation
{
    public static boolean drawBones = false;

    private static final float animFps = 1f / 60f;

    private float frameRegulator = 0;

    LibGdxLoader loader;
    LibGdxDrawer drawer;
    ShapeRenderer renderer;
    public PlayerTweener myPlayer;

    public SpriterAnimation(String filepath)
    {
        renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal(filepath);
        Data data = new SCMLReader(handle.read()).getData();

        loader = new LibGdxLoader(data);
        loader.load(handle.file());

        drawer = new LibGdxDrawer(loader, renderer);

        myPlayer = new PlayerTweener(data.getEntity(0));

        myPlayer.setScale(Settings.scale);
    }

    @Override
    public Type type()
    {
        return Type.SPRITE;
    }

    @Override
    public void setFlip(boolean horizontal, boolean vertical)
    {
        if ((horizontal && myPlayer.flippedX() > 0) || (!horizontal && myPlayer.flippedX() < 0)) {
            myPlayer.flipX();
        }

        if ((vertical && myPlayer.flippedY() > 0) || (!vertical && myPlayer.flippedY() < 0)) {
            myPlayer.flipY();
        }
    }

    @Override
    public void renderSprite(SpriteBatch batch, float x, float y)
    {
        drawer.batch = batch;
        // Update animation
        frameRegulator += Gdx.graphics.getDeltaTime();
        while (frameRegulator - animFps >= 0) {
            myPlayer.update();
            frameRegulator -= animFps;
        }

        // Move to correct location on screen
        AbstractPlayer player = AbstractDungeon.player;
        if (player != null) {
            myPlayer.setPosition(new Point(x, y));

            drawer.draw(myPlayer);

            if (drawBones) {
                batch.end();
                renderer.setAutoShapeType(true);
                renderer.begin();
                drawer.drawBoxes(myPlayer);
                drawer.drawBones(myPlayer);
                renderer.end();
                batch.begin();
            }
        }
    }
}
