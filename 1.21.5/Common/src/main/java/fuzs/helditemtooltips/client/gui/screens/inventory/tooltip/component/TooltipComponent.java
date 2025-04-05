package fuzs.helditemtooltips.client.gui.screens.inventory.tooltip.component;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

@FunctionalInterface
public interface TooltipComponent {

    void addToTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, Consumer<Component> componentConsumer, TooltipFlag tooltipFlag);

    default boolean alwaysUpdate() {
        return false;
    }
}
