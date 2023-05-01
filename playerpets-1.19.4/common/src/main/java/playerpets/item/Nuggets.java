package playerpets.item;

import com.google.common.collect.Lists;
import net.minecraft.world.item.Item;
import playerpets.PlayerPets;

import java.util.List;
import java.util.function.Supplier;

public class Nuggets {
    public static final Supplier<Item> COAL_NUGGET = PlayerPets.ITEMS.register("coal_nugget", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> DIAMOND_NUGGET = PlayerPets.ITEMS.register("diamond_nugget", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> EMERALD_NUGGET = PlayerPets.ITEMS.register("emerald_nugget", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ENDER_NUGGET = PlayerPets.ITEMS.register("ender_nugget", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> LAPIS_NUGGET = PlayerPets.ITEMS.register("lapis_nugget", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> OBSIDIAN_NUGGET = PlayerPets.ITEMS.register("obsidian_nugget", () -> new Item(new Item.Properties()));
    public static final List<Supplier<Item>> NUGGETS = Lists.newArrayList(
            COAL_NUGGET, DIAMOND_NUGGET, EMERALD_NUGGET, ENDER_NUGGET, LAPIS_NUGGET, OBSIDIAN_NUGGET
    );
}