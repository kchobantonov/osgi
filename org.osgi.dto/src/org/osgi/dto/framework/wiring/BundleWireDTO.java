/*
 * Copyright (c) OSGi Alliance (2012). All Rights Reserved.
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

package org.osgi.dto.framework.wiring;

import org.osgi.dto.resource.WireDTO;

/**
 * Data Transfer Object for a BundleWire.
 * 
 * <p>
 * {@code BundleWireDTO}s can be obtained from a {@link BundleWiringDTO}.
 * 
 * @author $Id$
 * @NotThreadSafe
 */
public class BundleWireDTO extends WireDTO {
    /**
     * DTOs are serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Provider wiring for the bundle wire.
     */
    public BundleWiringDTO    providerWiring;

    /**
     * Requirer wiring for the bundle wire.
     */
    public BundleWiringDTO    requirerWiring;
}