package fuzs.helditemtooltips.client.gui.screens.inventory.tooltip;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.compress.utils.Lists;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HoverTextManager {
    private static final List<TooltipComponentHolder> TOOLTIP_COMPONENT_HOLDERS = Lists.newArrayList();

    public static List<Component> getTooltipLines(ItemStack stack, @Nullable Player player, int maxLines) {

        List<TooltipComponentHolder> holders = TOOLTIP_COMPONENT_HOLDERS.stream().filter(TooltipComponentHolder::include).collect(Collectors.toList());
        holders.forEach(element -> element.tryRebuild(stack, player));
        boolean includeLastLine = ((SelectedItemElement) GameplayElements.SELECTED_ITEM).lastLine && maxLines > 1;
        if (holders.stream().mapToInt(TooltipComponentHolder::size).sum() > maxLines && includeLastLine) {
            maxLines--;
        }

        holders.sort(Comparator.comparingInt(TooltipComponentHolder::priority));
        for (TooltipComponentHolder element : holders) {
            maxLines = element.subtractLines(Math.max(maxLines, 0));
        }

        holders.sort(Comparator.comparingInt(TooltipComponentHolder::ordering));
        List<Component> lines = holders.stream()
                .map(TooltipComponentHolder::getLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        maxLines = applyAdditionalInformation(lines, stack, player, Math.max(maxLines, 0));
        if (includeLastLine && maxLines < 0) {
            lines.add(Component.translatable("container.shulkerBox.more", (-1) * maxLines).withStyle(ChatFormatting.ITALIC));
        }

        return lines;
    }

    public static void reset() {

        TOOLTIP_COMPONENT_HOLDERS.forEach(TooltipComponentHolder::clear);
    }

    private static int applyAdditionalInformation(List<Component> lines, ItemStack stack, @Nullable Player player, int maxLines) {

        if (((SelectedItemElement) GameplayElements.SELECTED_ITEM).moddedInfo) {

            int oldSize = lines.size();
            ForgeEventFactory.onItemTooltip(stack, player, lines, ITooltipFlag.TooltipFlags.NORMAL);
            if (lines.size() - oldSize > maxLines) {

                lines.subList(oldSize + maxLines, lines.size()).clear();
            }

            return maxLines - (lines.size() - oldSize);
        }

        return maxLines;
    }
}
