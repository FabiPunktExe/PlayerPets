package playerpets.item.pet;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import playerpets.PlayerPets;
import playerpets.item.Nuggets;
import playerpets.item.PlayerPet;

import java.util.List;
import java.util.function.Supplier;

public class EndermanPet extends PlayerPet {
    public static final Supplier<EndermanPet> INSTANCE = PlayerPets.ITEMS.register("enderman_pet", () -> new EndermanPet());

    public EndermanPet() {
        super(new Properties(), Rarity.COMMON, 6, 3, 4, Nuggets.OBSIDIAN_NUGGET::get);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack is = player.getItemInHand(interactionHand);
        if (PlayerPet.isActive(is)) {
            if (!level.isClientSide) {
                double d = player.getX();
                double e = player.getY();
                double f = player.getZ();
                for (int i = 0; i < 16; ++i) {
                    double g = player.getX() + (player.getRandom().nextDouble() - 0.5) * 16.0;
                    double h = Mth.clamp(player.getY() + (double) (player.getRandom().nextInt(16) - 8), level.getMinBuildHeight(), (level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1));
                    double j = player.getZ() + (player.getRandom().nextDouble() - 0.5) * 16.0;
                    if (player.isPassenger()) player.stopRiding();
                    Vec3 vec3 = player.position();
                    if (player.randomTeleport(g, h, j, true)) {
                        level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(player));
                        SoundEvent soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                        level.playSound(null, d, e, f, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                        player.playSound(soundEvent, 1.0F, 1.0F);
                        is.setDamageValue(is.getDamageValue() + 1);
                        break;
                    }
                }
            }
            return InteractionResultHolder.success(is);
        } else return InteractionResultHolder.pass(is);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("[RIGHT] ").withStyle(ChatFormatting.YELLOW).append(Component.translatable("pet.enderman.1").withStyle(ChatFormatting.GRAY)));
        list.add(Component.translatable("playerpets.food.favourite").withStyle(ChatFormatting.GRAY).append(": ").append(Component.translatable("item.playerpets.obsidian_nugget")));
    }
}