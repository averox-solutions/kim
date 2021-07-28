/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2016  DevLib'
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

package org.kimios.kernel.share.controller;

import org.kimios.kernel.security.model.Session;
import org.kimios.kernel.share.model.Share;

import java.util.Date;
import java.util.List;

/**
 * Created by farf on 15/02/16.
 */
public interface IShareController {


    List<Share> listEntitiesSharedByMe(Session session) throws Exception;

    List<Share> listEntitiesSharedWithMe(Session session) throws Exception;

    void removeShare(Session session, long shareId) throws Exception;

    Share shareEntity(Session session, long dmEntityId, String sharedToUserId, String sharedToUserSource,
                      boolean read, boolean write, boolean fullAcces, Date expirationDate, boolean notify) throws Exception;

    Integer disableExpiredShares(Session session) throws Exception;

    Share disableShare(Session session, long shareId) throws Exception;

    Share retrieveShare(Session session, long shareId) throws Exception;

    void updateShare(Session session, long shareId, String sharedToUserId, String sharedToUserSource, boolean read,
                     boolean write, boolean fullAcces, Date expirationDate, boolean notify) throws Exception;
}
