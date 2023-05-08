package aliases.command;

import aliases.AliasDeclaration;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.command.ServerCommandSource;

import static aliases.Config.aliases;
import static net.minecraft.server.command.CommandManager.*;

public class RegisterAliasCommands {
    public static void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (AliasDeclaration aliasGroup : aliases) {
            CommandNode<ServerCommandSource> commandNode = dispatcher.getRoot().getChild(aliasGroup.command);
            if (commandNode != null) {
                for (String alias : aliasGroup.aliases) {
                    dispatcher.register(literal(alias).redirect(commandNode));
                }
            }
        }
    }
}
