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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.GameRules;

public final class CBURegistries {

    public static final RegistryKey<Registry<GameRules.Key<GameRules.BooleanRule>>> BOOL_RULE_KEY = RegistryKey.ofRegistry(CBUIdentifiers.BOOL_RULE);
    public static final SimpleRegistry<GameRules.Key<GameRules.BooleanRule>> BOOL_RULE = FabricRegistryBuilder.createSimple((Class<GameRules.Key<GameRules.BooleanRule>>) null, CBUIdentifiers.BOOL_RULE).attribute(RegistryAttribute.SYNCED).buildAndRegister();

    private CBURegistries() {
    }

}
