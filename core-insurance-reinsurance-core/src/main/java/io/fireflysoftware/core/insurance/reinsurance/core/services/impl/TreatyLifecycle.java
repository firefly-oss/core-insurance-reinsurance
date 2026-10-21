/*
 * Copyright 2026 Firefly Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package io.fireflysoftware.core.insurance.reinsurance.core.services.impl;

import io.fireflysoftware.core.insurance.reinsurance.interfaces.enums.TreatyStatus;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Allowed moves for a treaty. A draft is the only status that can be removed.
 */
final class TreatyLifecycle {

    private static final Map<TreatyStatus, Set<TreatyStatus>> ALLOWED = Map.of(
            TreatyStatus.DRAFT, EnumSet.of(TreatyStatus.ACTIVE),
            TreatyStatus.ACTIVE, EnumSet.of(TreatyStatus.RUN_OFF, TreatyStatus.EXPIRED),
            TreatyStatus.RUN_OFF, EnumSet.of(TreatyStatus.EXPIRED)
    );

    private TreatyLifecycle() {
    }

    static boolean canTransition(TreatyStatus from, TreatyStatus to) {
        if (from == to) {
            return true;
        }
        Set<TreatyStatus> next = ALLOWED.get(from);
        return next != null && next.contains(to);
    }
}
