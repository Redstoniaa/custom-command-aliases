package aliases.command;

import aliases.utils.AliasDeclaration;
import aliases.utils.RegisterAlias;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

import static aliases.utils.Config.aliases;

public class RegisterAliasCommands {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (AliasDeclaration declaration : aliases) {
            RegisterAlias.registerAliases(dispatcher, declaration.command, declaration.aliases);
        }
    }
}
