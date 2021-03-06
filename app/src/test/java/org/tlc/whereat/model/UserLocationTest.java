/**
 *
 * Copyright (c) 2015-present, Total Location Test Paragraph.
 * All rights reserved.
 *
 * This file is part of Where@. Where@ is free software:
 * you can redistribute it and/or modify it under the terms of
 * the GNU General Public License (GPL), either version 3
 * of the License, or (at your option) any later version.
 *
 * Where@ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. For more details,
 * see the full license at <http://www.gnu.org/licenses/gpl-3.0.en.html>
 *
 */

package org.tlc.whereat.model;

import android.location.Location;

import com.google.gson.Gson;

import org.junit.Test;

import java.util.Arrays;

import static org.tlc.whereat.support.LocationHelpers.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class UserLocationTest {

    @Test
    public void valueOfWithId_should_convertLocationToUserLocation(){
        Location l = s17AndroidLocationMock();
        UserLocation ul = UserLocation.valueOf(S17_UUID, l);

        assertTrue(ul.equals(s17UserLocationStub()));
    }

    @Test
    public void valueOfWithoutId_should_convertLocationToUserLocation(){
        Location l = s17AndroidLocationMock();
        UserLocation ul = UserLocation.valueOf(l);

        assertTrue(ul.equals(s17UserLocationStub(ul.getId())));
    }

    @Test
    public void valueOf_should_produceUniqueIds(){
        Location l = s17AndroidLocationMock();
        UserLocation ul1 = UserLocation.valueOf(l);
        UserLocation ul2 = UserLocation.valueOf(l);

        assertThat(ul1.getId()).isNotEqualTo(ul2.getId());
        assertFalse(ul1.equals(ul2));
    }

    @Test
    public void create_should_constructUserLocation(){
        assertThat(
            UserLocation.create(S17_UUID, S17_LAT, S17_LON, S17_MILLIS))
            .isEqualTo(s17UserLocationStub());
    }

    @Test
    public void toJson_should_serializeToJson(){
        assertThat(
            s17UserLocationStub().toJson())
            .isEqualTo(S17_JSON);
    }


    @Test
    public void fromJson_should_deserializeALocation(){
        assertThat(
            UserLocation.fromJson(S17_JSON))
            .isEqualTo(s17UserLocationStub());
    }

    @Test
    public void fromJson_should_deserializeAListOfLocations(){
        assertThat(
            UserLocation.fromJsonList(API_INIT_RESPONSE))
            .isEqualTo(Arrays.asList(s17UserLocationStub(), n17UserLocationStub()));
    }

    @Test
    public void toJsonList_should_serializeToJsonList(){
        assertThat(
            UserLocation.toJsonList(Arrays.asList(s17UserLocationStub(), n17UserLocationStub())))
            .isEqualTo(API_INIT_RESPONSE);
    }

}