package aliases;

import aliases.AliasDeclaration;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static aliases.AliasesMod.*;

public class Config {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("%s.json".formatted(MOD_ID));
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static List<AliasDeclaration> aliases;

    public static void load() {
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
