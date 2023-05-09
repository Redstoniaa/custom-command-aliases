package aliases;

import aliases.command.CustomAliasCommand;
import aliases.command.RegisterAliasCommands;
import aliases.utils.Config;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliasesMod implements ModInitializer {
	public static final String MOD_ID = "custom-command-aliases";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		//LOGGER.info("Hello Fabric world!");
		Config.load();
		CommandRegistrationCallback.EVENT.register(this::registerCommands);
		ClientCommandRegistrationCallback.EVENT.register(this::registerClientCommands);
	}

	public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
		//RegisterAliasCommands.register(dispatcher);
	}

	public void registerClientCommands(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
		RegisterAliasCommands.register(dispatcher);
		CustomAliasCommand.register(dispatcher);
	}
}
