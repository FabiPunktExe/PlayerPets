package playerpets.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import playerpets.PlayerPets;

@Mod(PlayerPets.MODID)
public class PlayerPetsForge {
    public PlayerPetsForge() {
        EventBuses.registerModEventBus(PlayerPets.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        PlayerPets.init();
    }
}