package basemod.eventUtil.util;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static basemod.eventUtil.EventUtils.eventLogger;

public class ConditionalEvent<T extends AbstractEvent> {
    public Class<T> eventClass;
    public Class<? extends AbstractPlayer> playerClass;
    public Condition spawnCondition;
    public List<String> actIDs;

    public String overrideEvent = "";

    public ConditionalEvent(Class<T> eventClass, Class<? extends AbstractPlayer> playerClass, Condition spawnCondition, String[] actIDs) {
        this.eventClass = eventClass;
        this.playerClass = playerClass;
        this.spawnCondition = spawnCondition;

        if (spawnCondition == null)
            this.spawnCondition = () -> true;

        this.actIDs = Arrays.asList(actIDs);
    }

    public AbstractEvent getEvent() {
        try {
            return eventClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            eventLogger.info("Failed to instantiate event " + eventClass.getName());
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValid() {
        return (actIDs.isEmpty() || actIDs.contains(AbstractDungeon.id)) &&
                (spawnCondition.test()) &&
                (playerClass == null || AbstractDungeon.player.getClass().equals(playerClass));
    }

    @Override
    public String toString() {
        return eventClass.getSimpleName();
    }

    public String getConditions() {
        return (playerClass != null ? playerClass.getSimpleName().toUpperCase() : "ANY") + " | " + (actIDs.isEmpty() ? "ANY" : actIDs);
    }
}
