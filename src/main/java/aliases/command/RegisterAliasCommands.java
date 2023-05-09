package aliases.command;

import aliases.utils.AliasDeclaration;
import aliases.utils.RegisterAlias;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import static aliases.utils.Config.aliases;

public class RegisterAliasCommands {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        for (AliasDeclaration declaration : aliases) {
            RegisterAlias.registerAliases(dispatcher, declaration.command, declaration.aliases);
        }
    }
}
