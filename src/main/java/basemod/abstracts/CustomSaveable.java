package basemod.abstracts;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

public interface CustomSaveable<T> extends CustomSaveableRaw
{
    static final Gson saveFileGson = new Gson();

    public abstract Class<T> savedType();
    public abstract T onSave();
    public abstract void onLoad(T object);

    @Override
    public default JsonElement onSaveRaw() {
        return saveFileGson.toJsonTree(onSave());
    }

    @Override
    public default void onLoadRaw(JsonElement value) {
        if (value != null) {
            T parsed = saveFileGson.fromJson(value, savedType());
            onLoad(parsed);
        } else {
            onLoad(null);
        }
    }
}