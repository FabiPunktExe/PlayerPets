package playerpets.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import playerpets.item.PlayerPet;
import playerpets.item.pet.SpiderPet;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(at = @At("RETURN"), method = "onClimbable", cancellable = true)
    public void onClimbable(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && !isSpectator()) {
            cir.setReturnValue(horizontalCollision && PlayerPet.hasActivePet(this, SpiderPet.INSTANCE));
        }
    }
}