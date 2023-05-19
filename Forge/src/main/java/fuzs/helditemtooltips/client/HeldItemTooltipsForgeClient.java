package fuzs.helditemtooltips.client;

import fuzs.helditemtooltips.HeldItemTooltips;
import fuzs.helditemtooltips.client.handler.SelectedItemHandler;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = HeldItemTooltips.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HeldItemTooltipsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientModConstructor.construct(HeldItemTooltips.MOD_ID, HeldItemTooltipsClient::new);
        registerHandlers();
    }

    private static void registerHandlers() {
        // TODO replace with puzzles lib event for 1.19.4
        MinecraftForge.EVENT_BUS.addListener((final RenderGuiOverlayEvent.Pre evt) -> {
            if (evt.getOverlay() == VanillaGuiOverlay.ITEM_NAME.type()) {
                Minecraft minecraft = Minecraft.getInstance();
                if (!minecraft.player.isSpectator()) {
                    SelectedItemHandler.INSTANCE.onRenderGuiOverlay$ItemName(evt.getPoseStack(), evt.getWindow().getGuiScaledWidth(), evt.getWindow().getGuiScaledHeight());
                    evt.setCanceled(true);
                }
            }
        });
    }
}
