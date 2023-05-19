package fuzs.helditemtooltips.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.helditemtooltips.client.handler.SelectedItemHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiFabricMixin extends GuiComponent {
    @Shadow
    private int screenWidth;
    @Shadow
    private int screenHeight;

    @Inject(method = "renderSelectedItemName", at = @At("HEAD"), cancellable = true)
    public void renderSelectedItemName(PoseStack poseStack, CallbackInfo callback) {
        // TODO replace with puzzles lib event for 1.19.4
        SelectedItemHandler.INSTANCE.onRenderGuiOverlay$ItemName(poseStack, this.screenWidth, this.screenHeight);
        callback.cancel();
    }
}
