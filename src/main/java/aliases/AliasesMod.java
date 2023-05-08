package aliases;

import aliases.command.RegisterAliasCommands;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
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
		CommandRegistrationCallback.EVENT.register(this::registerAliases);
	}

	public void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
		RegisterAliasCommands.registerAliases(dispatcher);
	}
}
