package fuzs.helditemtooltips;

import fuzs.puzzleslib.core.CommonFactories;
import net.fabricmc.api.ModInitializer;

public class HeldItemTooltipsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonFactories.INSTANCE.modConstructor(HeldItemTooltips.MOD_ID).accept(new HeldItemTooltips());
    }
}
