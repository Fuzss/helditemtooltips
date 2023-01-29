package fuzs.helditemtooltips;

import fuzs.puzzleslib.core.CommonFactories;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(HeldItemTooltips.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class HeldItemTooltipsForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CommonFactories.INSTANCE.modConstructor(HeldItemTooltips.MOD_ID).accept(new HeldItemTooltips());
    }
}
