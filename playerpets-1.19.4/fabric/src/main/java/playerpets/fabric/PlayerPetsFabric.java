package playerpets.fabric;

import net.fabricmc.api.ModInitializer;
import playerpets.PlayerPets;

public class PlayerPetsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        PlayerPets.init();
    }
}