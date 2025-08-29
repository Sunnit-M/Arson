package net.justsunnit.arson.mixin.anvil;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {

    @Shadow
    @Final
    private Property levelCost;

    // modify the next cost calculation
    @Inject(method = "getNextCost(I)I", at = @At("HEAD"), cancellable = true)
    private static void injectedNextCost(int cost, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue((int)Math.round(cost * 1.5 + 1));
    }

    // modifies the max cost level
    @ModifyConstant(method = "updateResult()V", constant = @Constant(intValue = 40))
    private int injectedMaxLevel(int value) {
        return Integer.MAX_VALUE;
    }

    // modified the max cost level replacement
    @ModifyConstant(method = "updateResult()V", constant = @Constant(intValue = 39))
    private int injectedMaxLevelReplace(int value) {
        return Integer.MAX_VALUE - 1;
    }

    @Inject(method = "updateResult()V", at = @At("RETURN"))
    private void injectedLevlCostCap(CallbackInfo ci) {
        if (this.levelCost.get() >= 60) {
            this.levelCost.set(60);
        }
    }
}
