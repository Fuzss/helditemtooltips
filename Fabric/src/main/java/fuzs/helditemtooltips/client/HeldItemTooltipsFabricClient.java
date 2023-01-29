package fuzs.helditemtooltips.client;

import fuzs.helditemtooltips.HeldItemTooltips;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class HeldItemTooltipsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientFactories.INSTANCE.clientModConstructor(HeldItemTooltips.MOD_ID).accept(new HeldItemTooltipsClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {

        });
    }
}
