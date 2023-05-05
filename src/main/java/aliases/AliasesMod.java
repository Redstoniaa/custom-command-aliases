package aliases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class AliasesMod implements ModInitializer {
	public static final String MOD_ID = "custom-command-aliases";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Map<String, String> aliases;

	@Override
	public void onInitialize() {
		//LOGGER.info("Hello Fabric world!");
		loadConfig();
		CommandRegistrationCallback.EVENT.register(this::registerAliases);
	}

	public void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
		CommandNode<ServerCommandSource> fill = dispatcher.getRoot().getChild("fill");

		LiteralArgumentBuilder<ServerCommandSource> fillering = literal("fillings");
		dispatcher.register(fillering.redirect(fill));
	}

	public static void loadConfig() {
		Path p = FabricLoader.getInstance().getConfigDir().resolve("%s.json".formatted(MOD_ID));
		InputStream fileContent;
		try {
			fileContent = Files.newInputStream(p);
		} catch (IOException e) {
			return;
		}

		Gson gson = new Gson();
		aliases = gson.fromJson(fileContent.toString(), new TypeToken<Map<String, String>>(){}.getType());
	}
}
