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
package org.kimios.services.impl;

import org.kimios.kernel.security.model.Session;
import org.kimios.utils.registration.RegistrationData;
import org.kimios.webservices.exceptions.DMServiceException;
import org.kimios.webservices.InformationService;

import javax.jws.WebService;
import java.util.Date;

@WebService(targetNamespace = "http://kimios.org", serviceName = "InformationService", name = "InformationService")
public class InformationServiceImpl extends CoreService implements InformationService
{
    public String getServerVersionNumber() throws DMServiceException
    {
        try {
            String s = informationController.getServerVersion();
            return s;
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    public Date getServerOnlineTime(String sessionUid) throws DMServiceException
    {
        try {
            Session session = getHelper().getSession(sessionUid);
            Date d = informationController.getServerOnlineTime(session);
            return d;
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    public String getServerName() throws DMServiceException
    {
        try {
            return informationController.getServerName();
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    @Override
    public String getTelemetryUUID() throws DMServiceException {

        try {
            return informationController.getTelemetryUUID();
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    @Override
    public void register(RegistrationData data) throws DMServiceException {
        try {
            informationController.register(data);
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    @Override
    public boolean isRegistered() throws DMServiceException {
        try {
            return informationController.isRegistered();
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }
}

