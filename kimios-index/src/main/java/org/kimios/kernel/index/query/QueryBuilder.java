/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2012  DevLib'
 *
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kimios.kernel.index.query;

import org.apache.solr.client.solrj.util.ClientUtils;
import org.kimios.kernel.security.DMSecurityRule;
import org.kimios.kernel.security.SecurityEntityType;
import org.kimios.kernel.security.Session;
import org.kimios.kernel.user.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Build Solr Queries
 */
public class QueryBuilder {
    private static Logger log = LoggerFactory.getLogger(QueryBuilder.class);

    public static String buildAclQuery(Session session) {

        List<String> aclQueriesList = new ArrayList<String>();

        StringBuilder builder = new StringBuilder();

        /*
            Owner Query
         */

        builder.append("+(DocumentOwner:");
        builder.append(session.getUserName());
        builder.append("@");
        builder.append(session.getUserSource());
        builder.append(" OR ");
        /*
            Build list of possible ACL
         */
        List<DMSecurityRule> rules = new ArrayList<DMSecurityRule>();
        rules.add(DMSecurityRule
                .getInstance(session.getUserName(), session.getUserSource(), SecurityEntityType.USER,
                        DMSecurityRule.READRULE));
        rules.add(DMSecurityRule
                .getInstance(session.getUserName(), session.getUserSource(), SecurityEntityType.USER,
                        DMSecurityRule.WRITERULE));
        rules.add(DMSecurityRule
                .getInstance(session.getUserName(), session.getUserSource(), SecurityEntityType.USER,
                        DMSecurityRule.FULLRULE));

        for (Group g : session.getGroups()) {
            rules.add(DMSecurityRule
                    .getInstance(g.getGid(), session.getUserSource(), SecurityEntityType.GROUP,
                            DMSecurityRule.READRULE));
            rules.add(DMSecurityRule
                    .getInstance(g.getGid(), session.getUserSource(),
                            SecurityEntityType.GROUP,
                            DMSecurityRule.WRITERULE));
            rules.add(DMSecurityRule
                    .getInstance(g.getGid(), session.getUserSource(),
                            SecurityEntityType.GROUP,
                            DMSecurityRule.FULLRULE));
        }

        String or = " OR ";

        for (int u = 0; u < rules.size(); u++) {
            DMSecurityRule rule = rules.get(u);
            builder.append("DocumentACL:");
            builder.append(ClientUtils.escapeQueryChars(rule.getRuleHash()));
            if (u < (rules.size() - 1)) {
                builder.append(or);
            }
        }
        builder.append(") ");

        String bldNot = "-(DocumentACL:" + ClientUtils.escapeQueryChars(DMSecurityRule
                .getInstance(session.getUserName(), session.getUserSource(), SecurityEntityType.USER,
                        DMSecurityRule.NOACCESS).getRuleHash());
        bldNot += ")";

        builder.append(bldNot);

        log.debug("SOLR ACL Query: " + builder.toString());

        return builder.toString();
    }

    public static String documentNameQuery(String query) {

        String documentNameQuery = "";
        boolean fileExtSearch = query.toLowerCase().contains(".");
        if (fileExtSearch) {
            String docName = null;
            String extension = null;
            extension = query.toLowerCase().substring(query.indexOf(".") + 1);
            docName = query.toLowerCase().substring(0, query.indexOf("."));
            documentNameQuery = "DocumentName:*" + docName.toLowerCase() + "*";

            documentNameQuery += " AND DocumentExtension:" + extension + "*";
        } else {
            documentNameQuery = "DocumentName:*" + query.toLowerCase() + "*";
        }

        log.debug("SOLR documentNameQuery: " + documentNameQuery);
        return documentNameQuery;
    }

    public static String documentPathQuery(String query) {

        String documentPathQuery = "";
        boolean fileExtSearch = query.toLowerCase().contains(".");
        documentPathQuery = "DocumentParent:" + query + "/*";
        log.debug("SOLR documentPathQuery: " + documentPathQuery);
        return documentPathQuery;
    }
}
