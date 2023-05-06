package aliases;

public class AliasDeclaration {
    public AliasDeclaration(String command, String... aliases) {
        this.command = command;
        this.aliases = aliases;
    }

    public String command;
    public String[] aliases;
}
