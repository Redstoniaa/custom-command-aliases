package aliases;

import aliases.utils.Config;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class AliasesMod implements ModInitializer {
	public static final String MOD_ID = "custom-command-aliases";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		//LOGGER.info("Hello Fabric world!");
		Config.LoadConfigFile();
		CommandRegistrationCallback.EVENT.register(this::registerAliases);
	}

	public void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
		CommandNode<ServerCommandSource> fill = dispatcher.getRoot().getChild("fill");
		LiteralArgumentBuilder<ServerCommandSource> fillering = literal("fillings");
		dispatcher.register(fillering.redirect(fill));
	}
}
