package aliases.utils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class RegisterAlias {
    //find better class name
    public static boolean registerAliases(CommandDispatcher<FabricClientCommandSource> dispatcher, String command, String... aliases) {
        CommandNode<FabricClientCommandSource> commandNode = dispatcher.getRoot().getChild(command);
        if (commandNode == null)
            return false;

        for (String alias : aliases) {
            dispatcher.register(literal(alias).redirect(commandNode));
        }

        return true;
    }

    public static void updateCommandTrees(MinecraftServer server) {
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        CommandManager commandManager = server.getCommandManager();
        for (ServerPlayerEntity player : players) {
            commandManager.sendCommandTree(player);
        }
    }
}
