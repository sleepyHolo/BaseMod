package basemod.abstracts;

import com.google.gson.JsonElement;

public interface CustomSaveableRaw
{
    public abstract JsonElement onSaveRaw();
    public abstract void onLoadRaw(JsonElement value);
}