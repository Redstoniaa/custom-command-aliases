package aliases;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.*;

public class AliasesMod implements ModInitializer {
	public static final String MOD_ID = "custom-command-aliases";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("%s.json".formatted(MOD_ID));
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static List<AliasDeclaration> aliases;

	@Override
	public void onInitialize() {
		//LOGGER.info("Hello Fabric world!");
		loadConfig();
		CommandRegistrationCallback.EVENT.register(this::registerAliases);
	}

	public void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
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

	public static void loadConfig() {
		try {
			if (Files.exists(CONFIG_PATH)) {
				final BufferedReader reader = Files.newBufferedReader(CONFIG_PATH);
				Type type = new TypeToken<List<AliasDeclaration>>(){}.getType();
				aliases = GSON.fromJson(reader, type);
				reader.close();
			}
		} catch (IOException ignore) {}
	}

//	public static void createEmptyConfig() {
//		final List<AliasDeclaration> emptyAliasList = new ArrayList<>();
//		try {
//			Files.createDirectories(CONFIG_PATH);
//			Files.deleteIfExists(CONFIG_PATH);
//
//			final BufferedWriter writer = Files.newBufferedWriter(CONFIG_PATH, StandardOpenOption.CREATE);
//			GSON.toJson(emptyAliasList, writer);
//			writer.close();
//		} catch (IOException ignore) {}
//	}
}
