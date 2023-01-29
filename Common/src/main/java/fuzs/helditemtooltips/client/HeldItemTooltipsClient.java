package fuzs.helditemtooltips.client;

import fuzs.helditemtooltips.HeldItemTooltips;
import fuzs.helditemtooltips.client.gui.screens.inventory.tooltip.HoverTextManager;
import fuzs.helditemtooltips.client.gui.screens.inventory.tooltip.TooltipComponents;
import fuzs.helditemtooltips.config.ClientConfig;
import fuzs.puzzleslib.client.core.ClientModConstructor;

public class HeldItemTooltipsClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ClientConfig config = HeldItemTooltips.CONFIG.get(ClientConfig.class);
        HoverTextManager.register(TooltipComponents.ITEM_NAME, config.itemName);
        HoverTextManager.register(TooltipComponents.ADDITIONAL, config.additional);
        HoverTextManager.register(TooltipComponents.ENCHANTMENTS, config.enchantments);
        HoverTextManager.register(TooltipComponents.COLORING, config.coloring);
        HoverTextManager.register(TooltipComponents.LORE, config.lore);
        HoverTextManager.register(TooltipComponents.MODIFIERS, config.modifiers);
        HoverTextManager.register(TooltipComponents.UNBREAKABLE, config.unbreakable);
        HoverTextManager.register(TooltipComponents.DURABILITY, config.durability);
        HoverTextManager.register(TooltipComponents.IDENTIFIER, config.identifier);
        HoverTextManager.register(TooltipComponents.NBT_COUNT, config.nbtCount);
    }
}
