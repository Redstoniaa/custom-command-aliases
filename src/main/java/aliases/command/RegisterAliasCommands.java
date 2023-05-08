package aliases.command;

import aliases.AliasDeclaration;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.command.ServerCommandSource;

import static aliases.Config.aliases;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class RegisterAliasCommands {
    public static void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (AliasDeclaration aliasGroup : aliases) {
            CommandNode<ServerCommandSource> commandNode = dispatcher.getRoot().getChild(aliasGroup.command);
            if (commandNode != null) {
                for (String alias : aliasGroup.aliases) {
                    LiteralArgumentBuilder<ServerCommandSource> builder = literal(alias);
                    dispatcher.register(builder.redirect(commandNode));
                    // can be shortened but compiler doesn't like for some reason
                }
            }
        }
    }
}
