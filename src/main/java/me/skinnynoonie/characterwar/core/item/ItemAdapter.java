package me.skinnynoonie.characterwar.core.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Type;

public class ItemAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Material material = Material.valueOf(jsonObject.get("material").getAsString());
        String name = jsonObject.get("name").getAsString();
        boolean unbreakable = jsonObject.get("unbreakable").getAsBoolean();

        return new ItemBuilder(material)
                .setName(name)
                .setLore()
                .setUnbreakable(unbreakable)
                .build();
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        MiniMessage mm = MiniMessage.miniMessage();
        ItemMeta itemMeta = src.getItemMeta();
        JsonObject serializedItem = this.getBlankSerializedItem();

        serializedItem.addProperty("material", src.getType().toString());
        if (itemMeta != null) {
            serializedItem.addProperty("name", mm.serializeOr(itemMeta.displayName(), null) );
            serializedItem.addProperty("unbreakable", itemMeta.isUnbreakable());

            if (itemMeta.hasLore()) {
                serializedItem.add("lore", new JsonArray());
                for (Component loreLine : itemMeta.lore()) {
                    serializedItem.getAsJsonArray("lore").add(mm.serializeOr(loreLine, ""));
                }
            }
        }

        return serializedItem;
    }

    private JsonObject getBlankSerializedItem() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("material", JsonNull.INSTANCE);
        jsonObject.add("name", JsonNull.INSTANCE);
        jsonObject.add("lore", JsonNull.INSTANCE);
        jsonObject.add("unbreakable", new JsonPrimitive(false));
        return jsonObject;
    }

}
