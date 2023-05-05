package aliases.utils;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static aliases.AliasesMod.MOD_ID;

public class Config {
    public static void LoadConfigFile() {
        Path p = FabricLoader.getInstance().getConfigDir().resolve("%s.json".formatted(MOD_ID));
        try {
            Files.newInputStream(p);
        } catch (IOException e) {
            return;
        }
    }
}
