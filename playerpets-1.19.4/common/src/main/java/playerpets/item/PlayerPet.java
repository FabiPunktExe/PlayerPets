package playerpets.item;

import com.google.common.collect.Lists;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import playerpets.PlayerPets;
import playerpets.item.pet.CreeperPet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PlayerPet extends Item {
    private Rarity rarity;
    private int durability;
    private int minDamageToEat;
    private int repairOnEat;
    private List<ItemLike> food;

    public PlayerPet(Rarity rarity, int durability, int minDamageToEat, int repairOnEat, ItemLike... food) {
        this(new Properties(), rarity, durability, minDamageToEat, repairOnEat, food);
    }

    public PlayerPet(Properties settings, Rarity rarity, int durability, int minDamageToEat, int repairOnEat, ItemLike... food) {
        super(settings.stacksTo(1).durability(durability));
        this.rarity = rarity;
        this.durability = durability;
        this.minDamageToEat = minDamageToEat;
        this.repairOnEat = repairOnEat;
        this.food = Lists.newArrayList(food);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide && stack.getDamageValue() >= minDamageToEat && entity.tickCount % (20 * 5) == 0 && entity instanceof Player) {
            Player p = (Player) entity;
            for (int i = 0; i < 9; i++) {
                ItemStack is = p.getInventory().getItem(i);
                if (is != null && !is.isEmpty() && food.contains(is.getItem())) {
                    is.setCount(is.getCount() - 1);
                    stack.setDamageValue(Math.min(Math.max(stack.getDamageValue() - repairOnEat, 0), stack.getMaxDamage()));
                    p.getInventory().setItem(i, is);
                    p.getInventory().setItem(slot, stack);
                    break;
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        if (rarity != null) list.add(rarity.text);
    }

    public Rarity getRarity() {
        return rarity;
    }

    public List<ItemLike> getFood() {
        return food;
    }

    public static boolean isActive(ItemStack is) {
        return is.getItem() instanceof PlayerPet && (is.getMaxDamage() == 0 || is.getDamageValue() < is.getMaxDamage());
    }

    public static boolean isActive(ItemStack is, int slot) {
        return slot < 9 && isActive(is);
    }

    public static boolean hasActivePet(Entity entity, Supplier<? extends PlayerPet> pet) {
        return hasActivePet(entity, pet.get());
    }

    public static boolean hasActivePet(Entity entity, PlayerPet pet) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (int i = 0; i < 9; i++) {
                ItemStack is = player.getInventory().getItem(i);
                if (is != null && is.getItem() == pet && isActive(is)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getActivePetSlot(Entity entity, PlayerPet pet) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            for (int i = 0; i < 9; i++) {
                ItemStack is = player.getInventory().getItem(i);
                if (is != null && is.getItem() == pet && isActive(is)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static enum Rarity {
        COMMON(Component.translatable("playerpets.rarity.common").withStyle(ChatFormatting.GREEN)),
        RARE(Component.translatable("playerpets.rarity.rare").withStyle(ChatFormatting.BLUE)),
        LEGENDARY(Component.translatable("playerpets.rarity.legendary").withStyle(ChatFormatting.GOLD));

        private Component text;

        Rarity(Component text) {
            this.text = text;
        }

        public Component getText() {
            return text;
        }
    }
}