package phoupraw.mcmod.cancelblockupdate.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.gamerules.GameRule;

public final class CBURegistries {

    public static final ResourceKey<Registry<GameRule<Boolean>>> BOOL_RULE_KEY = ResourceKey.createRegistryKey(CBUIdentifiers.BOOL_RULE);
    @SuppressWarnings("deprecation")
    public static final MappedRegistry<GameRule<Boolean>> BOOL_RULE = FabricRegistryBuilder.create(BOOL_RULE_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    private CBURegistries() {
    }

}
