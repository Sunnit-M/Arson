package net.justsunnit.arson.mixin.BanMixins;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.BanIpCommand;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BanIpCommand.class)
public class BanIPCommandMixin {
    @Inject(method = "banIp", at = @At("HEAD"), cancellable = true)
    private static void InjectBanCommand(ServerCommandSource source, String targetIp, Text reason, CallbackInfoReturnable<Integer> cir){
        source.sendError(Text.literal("[ArsonUtils] The /banIP command is not supported on arson").styled(style -> style.withBold(true)));
        source.sendError(Text.literal("[ArsonUtils] Use /arson.permBan or /arson.timedBan for banning players.").styled(style -> style.withBold(true)));
        cir.setReturnValue(0);
    }
}
