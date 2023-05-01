package playerpets.item.pet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import playerpets.PlayerPets;
import playerpets.item.PlayerPet;

import java.util.List;
import java.util.function.Supplier;

public class CreeperPet extends PlayerPet {
    public static final Supplier<CreeperPet> INSTANCE = PlayerPets.ITEMS.register("creeper_pet", () -> new CreeperPet());

    public CreeperPet() {
        super(new Properties(), Rarity.RARE, 5, 3, 4, Items.GUNPOWDER);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack is = player.getItemInHand(interactionHand);
        if (PlayerPet.isActive(is)) {
            if (!level.isClientSide) {
                if (player.isCrouching() && is.getDamageValue() <= 1) {
                    level.explode(player, player.position().x, player.position().y, player.position().z, 6.0F, Level.ExplosionInteraction.MOB);
                    is.setDamageValue(5);
                } else {
                    level.explode(player, player.position().x, player.position().y, player.position().z, 3.0F, Level.ExplosionInteraction.MOB);
                    is.setDamageValue(is.getDamageValue() + 1);
                }
            }
            return InteractionResultHolder.success(is);
        } else return InteractionResultHolder.pass(is);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("[RIGHT] ").withStyle(ChatFormatting.YELLOW).append(Component.translatable("pet.creeper.1").withStyle(ChatFormatting.GRAY)));
        list.add(Component.literal("[RIGHT + SNEAK] ").withStyle(ChatFormatting.YELLOW).append(Component.translatable("pet.creeper.2").withStyle(ChatFormatting.GRAY)));
        list.add(Component.translatable("playerpets.food.favourite").withStyle(ChatFormatting.GRAY).append(": ").append(Component.translatable("item.minecraft.gunpowder")));
    }
}