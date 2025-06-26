package net.justsunnit.arson.mixin.BanMixins;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.BanListCommand;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
@Mixin(BanListCommand.class)
public class BanListCommandMixin {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private static void InjectBanCommand(ServerCommandSource source, Collection<GameProfile> targets, CallbackInfoReturnable<Integer> cir){
        source.sendError(Text.literal("[ArsonUtils] The /banList command is not supported on arson").styled(style -> style.withBold(true)));
        source.sendError(Text.literal("[ArsonUtils] Use /arson.permBan or /arson.timedBan for banning players.").styled(style -> style.withBold(true)));
        cir.setReturnValue(0);
    }
}
