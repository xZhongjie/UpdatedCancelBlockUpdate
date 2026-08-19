/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.mixin;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import phoupraw.mcmod.cancelblockupdate.inject.IGameRules_Type;

@Mixin(GameRules.Type.class)
class MGameRules_Type implements IGameRules_Type {

    private GameRules.Key<?> key;

    @SuppressWarnings("unchecked")
    @Override
    public <T extends GameRules.Rule<T>> GameRules.Key<T> getKey() {
        return (GameRules.Key<T>) key;
    }

    @Override
    public <T extends GameRules.Rule<T>> void setKey(GameRules.Key<T> key) {
        this.key = key;
    }

}
