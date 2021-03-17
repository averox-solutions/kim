/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2017  DevLib'
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * aong with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.kimios.kernel.registries;

import org.kimios.api.reporting.ReportImpl;
import org.kimios.utils.extension.ExtensionRegistry;
import org.kimios.utils.extension.IExtensionRegistryManager;

/**
 * Created by farf on 13/01/16.
 */
public class ReportRegistry extends ExtensionRegistry<ReportImpl> {

    public ReportRegistry(IExtensionRegistryManager extensionRegistryManager) {
        super(extensionRegistryManager);
    }

}
