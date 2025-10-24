package zfrhv.hollowvoid.mixin;

import net.minecraft.util.math.BlockBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(targets = "net.minecraft.structure.pool.StructurePoolBasedGenerator$StructurePoolGenerator")
public class StructurePoolGeneratorMixin {
    @ModifyVariable(method = "generatePiece", at = @At("STORE"), ordinal = 0)
    private BlockBox onGeneratePiece(BlockBox box) {
        return box.expand(0,10,0);
    }
}
