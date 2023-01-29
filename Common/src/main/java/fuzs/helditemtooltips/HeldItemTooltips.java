package fuzs.helditemtooltips;

import fuzs.helditemtooltips.config.ClientConfig;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CommonFactories;
import fuzs.puzzleslib.core.ModConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HeldItemTooltips implements ModConstructor {
    public static final String MOD_ID = "helditemtooltips";
    public static final String MOD_NAME = "Held Item Tooltips";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = CommonFactories.INSTANCE.clientConfig(ClientConfig.class, () -> new ClientConfig());

    @Override
    public void onConstructMod() {
        CONFIG.bakeConfigs(MOD_ID);
    }
}
