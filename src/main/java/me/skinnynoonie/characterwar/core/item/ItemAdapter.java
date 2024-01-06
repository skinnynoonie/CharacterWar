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
import java.util.ArrayList;
import java.util.List;

public class ItemAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = json.getAsJsonObject();

            Material material = Material.valueOf(jsonObject.get("material").getAsString());
            String name = jsonObject.get("name").getAsString();
            List<Component> lore = this.getPropertyAsComponentList(jsonObject);
            boolean unbreakable = jsonObject.get("unbreakable").getAsBoolean();

            return new ItemBuilder(material)
                    .setName(name)
                    .setLoreWithComponents(lore)
                    .setUnbreakable(unbreakable)
                    .build();
        } catch (Exception e) {
            // This exception should not really happen due to missing values because of the fallback value provider.
            throw new JsonParseException(e);
        }
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
            this.addPropertyLore(serializedItem, itemMeta);
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

    private List<Component> getPropertyAsComponentList(JsonObject jsonObject) {
        JsonElement lore = jsonObject.get("lore");
        if (lore == JsonNull.INSTANCE) {
            return null;
        }

        List<Component> components = new ArrayList<>();
        for (JsonElement element : lore.getAsJsonArray()) {
            Component deserializedLore = MiniMessage.miniMessage().deserialize(element.getAsString());
            components.add(deserializedLore);
        }
        return components;
    }

    private void addPropertyLore(JsonObject serializedItem, ItemMeta itemMeta) {
        if (!itemMeta.hasLore()) {
            return;
        }
        serializedItem.add("lore", new JsonArray());
        for (Component loreLine : itemMeta.lore()) {
            String serializedLoreLine = MiniMessage.miniMessage().serializeOr(loreLine, "");
            serializedItem.getAsJsonArray("lore").add(serializedLoreLine);
        }
    }

}
