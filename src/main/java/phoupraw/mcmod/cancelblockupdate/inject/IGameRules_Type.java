/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * Modified from CancelBlockUpdate (https://github.com/Phoupraw/CancelBlockUpdate)
 * by xZhongjie (https://github.com/xZhongjie/UpdatedCancelBlockUpdate)
 */

package phoupraw.mcmod.cancelblockupdate.inject;

import net.minecraft.world.GameRules;

public interface IGameRules_Type {

    default <T extends GameRules.Rule<T>> GameRules.Key<T> getKey() {
        throw new IllegalStateException();
    }
    default <T extends GameRules.Rule<T>> void setKey(GameRules.Key<T> key) {
        throw new IllegalStateException();
    }

}
