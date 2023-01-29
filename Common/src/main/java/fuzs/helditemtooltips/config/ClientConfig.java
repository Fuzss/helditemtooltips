package fuzs.helditemtooltips.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;
import net.minecraft.ChatFormatting;

public class ClientConfig implements ConfigCore {
    @Config(description = "")
    public boolean additionalInformation = false;
    @Config(category = "components", description = "Display name of the held item.")
    public final TooltipComponentConfig itemName = TooltipComponentConfig.simple(true, 10, 100);
    @Config(category = "components", description = "Additional information supplied by individual items such as potion effect and firework duration.")
    public final TooltipComponentConfig additional = TooltipComponentConfig.advanced(true, 20, 90, null);
    @Config(category = "components", description = "All enchantments on this item if any are present.")
    public final TooltipComponentConfig enchantments = TooltipComponentConfig.simple(true, 30, 70);
    @Config(category = "components", description = "The color of dyed items such a leather armor.")
    public final TooltipComponentConfig coloring = TooltipComponentConfig.advanced(true, 40, 30, ChatFormatting.GRAY);
    @Config(category = "components", description = "A lore tag for this item, only present on custom items.")
    public final TooltipComponentConfig lore = new TooltipComponentConfig(true, 50, 40, false, new TooltipComponentConfig.FormattingConfig(ChatFormatting.DARK_PURPLE, false, false, false, false, true));
    @Config(category = "components", description = "Attributes this item provides when used or equipped, like attack damage and armor protection.")
    public final TooltipComponentConfig modifiers = TooltipComponentConfig.simple(false, 60, 20);
    @Config(category = "components", description = "Rendered when this item has the unbreakable tag giving it infinite durability.")
    public final TooltipComponentConfig unbreakable = TooltipComponentConfig.simple(true, 70, 50, ChatFormatting.BLUE);
    @Config(category = "components", description = "Durability of this item, only shown if the item is damageable and has been used.")
    public final TooltipComponentConfig durability = TooltipComponentConfig.simple(true, 80, 80);
    @Config(category = "components", description = "Internal identifier of this item.")
    public final TooltipComponentConfig identifier = TooltipComponentConfig.simple(false, 90, 60, ChatFormatting.DARK_GRAY);
    @Config(category = "components", description = "Amount of nbt tags on this item.")
    public final TooltipComponentConfig nbtCount = TooltipComponentConfig.simple(false, 100, 10, ChatFormatting.DARK_GRAY);
}
