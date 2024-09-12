package fuzs.helditemtooltips.mixin.client.accessor;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface GuiAccessor {

    @Accessor("overlayMessageTime")
    int helditemtooltips$getOverlayMessageTime();
}
