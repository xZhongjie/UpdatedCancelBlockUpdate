package phoupraw.mcmod.cancelblockupdate.registry;

import net.minecraft.resources.Identifier;
import phoupraw.mcmod.cancelblockupdate.CancelBlockUpdate;

public final class CBUIdentifiers {

    public static final Identifier
      CHANNEL = of("channel"),
      REQUEST_SYNC = of("request_sync"),
      OFF = of("off"),
      REPLACE = of("replace"),
      BOOL_RULE = of("bool_rule");

    public static Identifier of(String path) {
        return Identifier.fromNamespaceAndPath(CancelBlockUpdate.MOD_ID, path);
    }

    private CBUIdentifiers() {
    }

}
