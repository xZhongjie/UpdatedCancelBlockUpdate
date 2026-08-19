/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.world.GameRules;

public final class CBURegistries {

    public static final RegistryKey<Registry<GameRules.Key<GameRules.BooleanRule>>> BOOL_RULE_KEY = RegistryKey.ofRegistry(CBUIdentifiers.BOOL_RULE);
    @SuppressWarnings({"unchecked", "deprecation"})
    public static final SimpleRegistry<GameRules.Key<GameRules.BooleanRule>> BOOL_RULE = FabricRegistryBuilder.createSimple(BOOL_RULE_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    private CBURegistries() {
    }

}
