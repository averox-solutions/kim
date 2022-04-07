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

import org.kimios.kernel.dms.model.Workspace;
import org.kimios.kernel.security.model.Session;
import org.kimios.kernel.ws.pojo.DMEntityWrapper;
import org.kimios.webservices.exceptions.DMServiceException;
import org.kimios.webservices.WorkspaceService;

import javax.jws.WebService;
import java.util.List;
import java.util.stream.Collectors;


@WebService(targetNamespace = "http://kimios.org", serviceName = "WorkspaceService", name = "WorkspaceService")
public class WorkspaceServiceImpl extends CoreService implements WorkspaceService
{
    public org.kimios.kernel.ws.pojo.Workspace getWorkspace(String sessionUid, long workspaceUid)
            throws DMServiceException
    {

        try {

            Session session = getHelper().getSession(sessionUid);

            org.kimios.kernel.ws.pojo.Workspace ws = workspaceController.getWorkspace(session, workspaceUid).toPojo();

            return ws;
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    @Override
    public DMEntityWrapper getWorkspaceWrapper(String sessionUid, long workspaceUid) throws DMServiceException {
        try {

            Session session = getHelper().getSession(sessionUid);

            org.kimios.kernel.ws.pojo.Workspace ws = workspaceController.getWorkspace(session, workspaceUid).toPojo();

            return new DMEntityWrapper(
                            ws,
                            this.securityController.canRead(session, ws.getUid()),
                            this.securityController.canWrite(session, ws.getUid()),
                            this.securityController.hasFullAccess(session, ws.getUid())
                    );
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    public org.kimios.kernel.ws.pojo.Workspace[] getWorkspaces(String sessionUid) throws DMServiceException
    {

        try {

            Session session = getHelper().getSession(sessionUid);

            List<Workspace> v = workspaceController.getWorkspaces(session);
            org.kimios.kernel.ws.pojo.Workspace[] r = new org.kimios.kernel.ws.pojo.Workspace[v.size()];
            int i = 0;
            for (Workspace it : v) {
                r[i] = it.toPojo();
                i++;
            }

            return r;
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    @Override
    public List<DMEntityWrapper> getWorkspaceWrappers(String sessionUid) throws DMServiceException {
        try {

            Session session = getHelper().getSession(sessionUid);

            return workspaceController.getWorkspaces(session).stream()
                    .map(workspace -> workspace.toPojo())
                    .map(workspace -> new DMEntityWrapper(
                            workspace,
                            this.securityController.canRead(session, workspace.getUid()),
                            this.securityController.canWrite(session, workspace.getUid()),
                            this.securityController.hasFullAccess(session, workspace.getUid())
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    public long createWorkspace(String sessionUid, String name) throws DMServiceException
    {

        try {

            Session session = getHelper().getSession(sessionUid);

            long uid = workspaceController.createWorkspace(session, name);

            return uid;
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    public void updateWorkspace(String sessionUid, long workspaceUid, String name) throws DMServiceException
    {

        try {

            Session session = getHelper().getSession(sessionUid);

            workspaceController.updateWorkspace(session, workspaceUid, name);
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }

    public void deleteWorkspace(String sessionUid, long workspaceUid) throws DMServiceException
    {

        try {

            Session session = getHelper().getSession(sessionUid);

            workspaceController.deleteWorkspace(session, workspaceUid);
        } catch (Exception e) {
            throw getHelper().convertException(e);
        }
    }
}

