package fuzs.helditemtooltips.client.core;

import fuzs.puzzleslib.util.PuzzlesUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public interface ClientAbstractions {
    ClientAbstractions INSTANCE = PuzzlesUtil.loadServiceProvider(ClientAbstractions.class);

    void getTooltipLines(ItemStack stack, TooltipFlag context, List<Component> lines);
}
