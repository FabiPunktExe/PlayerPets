package playerpets;

import com.google.common.collect.Lists;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import playerpets.item.Nuggets;
import playerpets.item.PlayerPet;
import playerpets.item.pet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PlayerPets {
    public static final String MODID = "playerpets";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MODID, Registries.ITEM);
    public static final CreativeTabRegistry.TabSupplier TAB = CreativeTabRegistry.create(
            new ResourceLocation(MODID, "playerpets"),
            () -> new ItemStack(CreeperPet.INSTANCE.get())
    );
    public static final List<Supplier<? extends PlayerPet>> PETS = Lists.newArrayList(
            CreeperPet.INSTANCE, EndermanPet.INSTANCE, SpiderPet.INSTANCE, WitherPet.INSTANCE
    );
    
    public static void init() {
        PETS.forEach(pet -> CreativeTabRegistry.append(TAB, pet));
        Nuggets.NUGGETS.forEach(nugget -> CreativeTabRegistry.append(TAB, nugget));
        ITEMS.register();
    }
}