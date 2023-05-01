package playerpets.item.pet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import playerpets.PlayerPets;
import playerpets.item.PlayerPet;

import java.util.List;
import java.util.function.Supplier;

public class SpiderPet extends PlayerPet {
    public static final Supplier<SpiderPet> INSTANCE = PlayerPets.ITEMS.register("spider_pet", () -> new SpiderPet());

    public SpiderPet() {
        super(new Properties(), Rarity.COMMON, 15, 5, 7, Items.GUNPOWDER, Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.RABBIT, Items.PORKCHOP);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (entity instanceof Player && PlayerPet.isActive(stack, slot)) {
            Player p = (Player) entity;
            p.addEffect(new MobEffectInstance(MobEffects.JUMP, 10, 3, false, false, false));
            if (entity.tickCount % (20 * 60) == 0 && !p.isCreative() && !p.isSpectator()) {
                stack.setDamageValue(Math.min(Math.max(stack.getDamageValue() + 1, 0), stack.getMaxDamage()));
                p.getInventory().setItem(slot, stack);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.translatable("pet.spider.1").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("effect.minecraft.jump_boost").withStyle(ChatFormatting.GRAY));
        list.add(Component.translatable("playerpets.food.favourite").withStyle(ChatFormatting.GRAY).append(": ").append(Component.translatable("playerpets.food.raw_meat")));
    }
}