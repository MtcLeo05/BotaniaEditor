package com.mtcleo05.botania_editor.mixin;

import com.mtcleo05.botania_editor.config.server.MiscConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vazkii.botania.common.block.block_entity.mana.PowerGeneratorBlockEntity;

@Mixin(PowerGeneratorBlockEntity.class)
public class FluxfieldMixin {
    @Shadow(remap = false)
    private int energy;

    @Overwrite(remap = false)
    public int getCurrentMana(){
        return (int) (energy / MiscConfig.MANA_TO_FE_RATE.get());
    }

    @Overwrite(remap = false)
    public void receiveMana(int mana){
        this.energy = (int) Math.min(MiscConfig.FE_CAPACITY.get(), energy + mana * MiscConfig.MANA_TO_FE_RATE.get());
    }

    @ModifyArg(remap = false, method = "serverTick", at = @At(
            value = "INVOKE",
            target = "Ljava/lang/Math;min(II)I"
    ), index = 1)
    private static int configureTransferRate(int range) {
        return (int) (160 * MiscConfig.MANA_TO_FE_RATE.get());
    }
}
