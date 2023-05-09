package aliases.command;

import aliases.utils.RegisterAlias;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class CustomAliasCommand {
    private static final String BASE_COMMAND = "base-command";
    private static final String ALIAS_NAME = "alias-name";

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("customalias")
                .then(literal("register")
                        .then(argument(BASE_COMMAND, word()).suggests(CustomAliasCommand::suggestCommands)
                                .then(argument(ALIAS_NAME, word())
                                        .executes(CustomAliasCommand::registerNewAlias)))));
    }

    private static int registerNewAlias(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
        FabricClientCommandSource source = context.getSource();
        String command = getString(context, BASE_COMMAND);
        String alias = getString(context, ALIAS_NAME);

        assert getActiveDispatcher() != null;
        boolean success = RegisterAlias.registerAliases(getActiveDispatcher(), command, alias);
        if (!success)
            throw new SimpleCommandExceptionType(Text.literal("Command /%s does not exist.".formatted(command))).create();
//        RegisterAlias.updateCommandTrees(server);

        source.sendFeedback(Text.literal("registered thingy"));
        return 1;
    }

    private static CompletableFuture<Suggestions> suggestCommands(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
        for (CommandNode<FabricClientCommandSource> node : context.getRootNode().getChildren())
            builder.suggest(node.getName());
        return builder.buildFuture();
    }
}
