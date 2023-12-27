package me.skinnynoonie.characterwar.core.eventinfo;

import com.google.common.base.Preconditions;
import me.skinnynoonie.characterwar.core.item.CustomItemManager;
import org.jetbrains.annotations.NotNull;

public class EventInfo {

    private final CustomItemManager customItemManager;

    public EventInfo(@NotNull CustomItemManager customItemManager) {
        Preconditions.checkNotNull(customItemManager, "customItemManager is null.");
        this.customItemManager = customItemManager;
    }

    @NotNull
    public CustomItemManager getCustomItemManager() {
        return this.customItemManager;
    }

}
