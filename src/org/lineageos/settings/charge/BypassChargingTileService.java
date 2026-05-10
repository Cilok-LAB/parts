/*
 * Copyright (C) 2023 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.lineageos.settings.charge;

import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class BypassChargingTileService extends TileService {

    private ChargeUtils mChargeUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        mChargeUtils = new ChargeUtils(getApplicationContext());
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onClick() {
        super.onClick();
        boolean newState = !mChargeUtils.isBypassChargeEnabled();
        mChargeUtils.enableBypassCharge(newState);
        updateTile();
    }

    private void updateTile() {
        Tile tile = getQsTile();
        if (tile == null) return;
        boolean enabled = mChargeUtils.isBypassChargeEnabled();
        tile.setState(enabled ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);
        tile.setSubtitle(enabled ? "On" : "Off");
        tile.updateTile();
    }
}
