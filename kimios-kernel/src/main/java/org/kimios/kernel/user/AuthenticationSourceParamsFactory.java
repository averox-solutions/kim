/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2015  DevLib'
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
package org.kimios.kernel.user;

import org.kimios.exceptions.AuthenticationSourceException;
import org.kimios.kernel.xml.XSDException;

import java.util.Map;

public interface AuthenticationSourceParamsFactory
{
    Map<String, String> getParams(String name);

    @Deprecated
    String getParamsXml(String name);

    @Deprecated
    void createParams(String sourceName, String xml) throws AuthenticationSourceException, XSDException;

    void createParams(String sourceName, Map<String, String> params) throws AuthenticationSourceException, XSDException;

    @Deprecated
    void updateParams(String sourceName, String xml, boolean enableSso, boolean enableMailCheck) throws AuthenticationSourceException, XSDException;

    void updateParams(String sourceName, Map<String, String> params , String javaClass, boolean enableSso, boolean enableMailCheck);


}

