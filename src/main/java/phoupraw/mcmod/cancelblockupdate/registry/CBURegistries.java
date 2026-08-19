package phoupraw.mcmod.cancelblockupdate.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.world.rule.GameRule;

public final class CBURegistries {

    public static final RegistryKey<Registry<GameRule<Boolean>>> BOOL_RULE_KEY = RegistryKey.ofRegistry(CBUIdentifiers.BOOL_RULE);
    @SuppressWarnings({"unchecked", "deprecation"})
    public static final SimpleRegistry<GameRule<Boolean>> BOOL_RULE = FabricRegistryBuilder.createSimple(BOOL_RULE_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    private CBURegistries() {
    }

}
