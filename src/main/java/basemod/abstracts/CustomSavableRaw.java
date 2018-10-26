package basemod.abstracts;

import com.google.gson.JsonElement;

public interface CustomSavableRaw
{
    JsonElement onSaveRaw();
    void onLoadRaw(JsonElement value);
}