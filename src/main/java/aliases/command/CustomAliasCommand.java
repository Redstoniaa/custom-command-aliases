package aliases.command;

import aliases.utils.RegisterAlias;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CustomAliasCommand {
    private static final String BASE_COMMAND = "base-command";
    private static final String ALIAS_NAME = "alias-name";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("customalias")
                .then(literal("register")
                        .then(argument(BASE_COMMAND, word()).suggests(CustomAliasCommand::suggestCommands)
                                .then(argument(ALIAS_NAME, word())
                                        .executes(CustomAliasCommand::registerNewAlias)))));
    }

    private static int registerNewAlias(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        MinecraftServer server = source.getServer();
        CommandManager commandManager = server.getCommandManager();
        String command = getString(context, BASE_COMMAND);
        String alias = getString(context, ALIAS_NAME);

        boolean success = RegisterAlias.registerAliases(commandManager.getDispatcher(), command, alias);
        if (!success)
            throw new SimpleCommandExceptionType(Text.literal("Command /%s does not exist.".formatted(command))).create();
        RegisterAlias.updateCommandTrees(server);

        source.sendMessage(Text.literal("registered thingy"));
        return 1;
    }

    private static CompletableFuture<Suggestions> suggestCommands(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        for (CommandNode<ServerCommandSource> node : context.getRootNode().getChildren())
            builder.suggest(node.getName());
        return builder.buildFuture();
    }
}
