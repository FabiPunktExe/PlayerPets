package playerpets.item.pet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import playerpets.PlayerPets;
import playerpets.item.PlayerPet;

import java.util.List;
import java.util.function.Supplier;

public class WitherPet extends PlayerPet {
    public static final Supplier<WitherPet> INSTANCE = PlayerPets.ITEMS.register("wither_pet", () -> new WitherPet());

    public WitherPet() {
        super(new Properties(), Rarity.LEGENDARY, 5, 3, 4, Items.COAL);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack is = player.getItemInHand(interactionHand);
        if (PlayerPet.isActive(is)) {
            if (!level.isClientSide) {
                if (player.isCrouching() && is.getDamageValue() <= durability - 2) {
                    shootWitherSkull(true, player, level);
                    is.setDamageValue(is.getDamageValue() + 2);
                } else if (!player.isCrouching()) {
                    shootWitherSkull(false, player, level);
                    is.setDamageValue(is.getDamageValue() + 1);
                }
            }
            return InteractionResultHolder.success(is);
        } else return InteractionResultHolder.pass(is);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("[RIGHT] ").withStyle(ChatFormatting.YELLOW).append(Component.translatable("pet.wither.1").withStyle(ChatFormatting.GRAY)));
        list.add(Component.literal("[RIGHT + SNEAK] ").withStyle(ChatFormatting.YELLOW).append(Component.translatable("pet.wither.2").withStyle(ChatFormatting.GRAY)));
        list.add(Component.translatable("playerpets.food.favourite").withStyle(ChatFormatting.GRAY).append(": ").append(Component.translatable("item.minecraft.coal")));
    }

    private void shootWitherSkull(boolean charged, Player player, Level level) {
        Vec3 look = player.getLookAngle();
        WitherSkull witherskull = new WitherSkull(level, player, look.x, look.y, look.z);
        witherskull.setOwner(player);
        witherskull.setDangerous(charged);
        witherskull.setPosRaw(player.getX() + look.x, player.getEyeY() + look.y, player.getZ() + look.z);
        level.addFreshEntity(witherskull);
    }
}