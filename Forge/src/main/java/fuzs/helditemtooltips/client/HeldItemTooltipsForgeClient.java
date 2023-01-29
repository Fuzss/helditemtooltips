package fuzs.helditemtooltips.client;

import fuzs.helditemtooltips.HeldItemTooltips;
import fuzs.helditemtooltips.client.handler.SelectedItemHandler;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = HeldItemTooltips.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class HeldItemTooltipsForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientFactories.INSTANCE.clientModConstructor(HeldItemTooltips.MOD_ID).accept(new HeldItemTooltipsClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.ClientTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.END) {
                Minecraft minecraft = Minecraft.getInstance();
                SelectedItemHandler.INSTANCE.onClientTick$End(minecraft);
            }
        });
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
