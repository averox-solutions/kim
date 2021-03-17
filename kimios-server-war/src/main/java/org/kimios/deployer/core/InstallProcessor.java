/*
 * Kimios - Document Management System Software
 * Copyright (C) 2008-2018  DevLib'
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
package org.kimios.deployer.core;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.commons.lang.StringUtils;
import org.kimios.deployer.web.DeploymentManager;
import org.kimios.deployer.web.WebDeployerViewGenerator;
import org.kimios.kernel.dms.hibernate.HDMEntityFactory;
import org.kimios.utils.spring.KimiosWebApplicationContext;
import org.kimios.utils.spring.SpringWebContextLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
;

/**
 *
 *
 */
public class InstallProcessor {
    private static Logger log = LoggerFactory.getLogger(InstallProcessor.class);

    Properties driverListProperties = new Properties();

    Properties serverProperties = new Properties();

    public void init() {
        try {
            driverListProperties.load(InstallProcessor.class.getResourceAsStream("/driver-list.properties"));
        } catch (Exception e) {
            log.error("Error while loading driver properties", e);
        }
    }


    public void checkDatabase(String databaseType, String host, String port, String user, String password,
                              String dbName, boolean shouldCreateDb) throws SQLException {
        /*
          Build Url
        */

        log.info("Used TPL URL: " + databaseType + (shouldCreateDb ? ".jdbcurl" : ".jdbcdburl"));
        String templateUrl = driverListProperties.get(
                databaseType + (shouldCreateDb ? ".jdbcurl" : ".jdbcdburl")
        ).toString();
        String builedUrl = shouldCreateDb ? String.format(templateUrl, host, port) :
                String.format(templateUrl, host, port, dbName);

        log.info("Builded Url: " + builedUrl);

        try {

            Class.forName(driverListProperties.get(databaseType + ".driver").toString());
        } catch (Exception e) {

        }

        /*
           Check connecton
        */
        log.info("Password " + password);
        Connection conn = DriverManager.getConnection(builedUrl, user, password);
        log.info("Connection active " + conn);
        conn.close();
    }

    public void createDatabase(String databaseType, String host, String port, String user, String password,
                               String dbName, boolean createDb) throws Exception {


        DBManager dbm = null;
        if (createDb) {
            String templateUrl = driverListProperties.get(databaseType + ".jdbcurl").toString();
            String builedUrl = String.format(templateUrl, host, port);
            DBManager.init(host, user, dbName, password,
                    driverListProperties.get(databaseType + ".driver").toString(),
                    builedUrl);
            dbm = DBManager.getInstance();
            String req = String.format(driverListProperties.getProperty(databaseType + ".createdb"),
                    dbName);
            dbm.execute(req);
            dbm.disconnect();
        }
        /*
           Connect with database name
        */
        String templateUrl = driverListProperties.get(databaseType + ".jdbcdburl").toString();
        String builedUrl = String.format(templateUrl, host, port, dbName);
        DBManager.init(host, user, dbName, password, driverListProperties.get(databaseType + ".driver").toString(),
                builedUrl);
        dbm = DBManager.getInstance();
        dbm.connectDb();
    }


    public void createPath(String path) throws Exception {
        File file = new File(path);
        if (file.exists()) {
            String[] files = file.list();
            if (files.length > 0) {
                throw new Exception("The given directory path is not empty: " + path);
            }
        } else {
            boolean createdDirectory = file.mkdirs();
            if (createdDirectory) {
                log.debug("Directory created: " + path);
            } else {
                throw new Exception("Unable to create path: " + path);
            }
        }
    }

    public void generateServerPropertiesFile(Map items, String confTargetPath, ServletContext servletContext) throws Exception {
        Map<String, String> cstItems = new HashMap<String, String>();

        for (Object t : items.keySet()) {
            Object val = items.get(t);
            if (val instanceof String[] && ((String[]) val).length > 0) {
                cstItems.put(t.toString(), ((String[]) val)[0]);
            } else
                cstItems.put(t.toString(), val.toString());
        }

        /*
           Reload properties from file
        */

        serverProperties.load(HDMEntityFactory.class.getResourceAsStream(WebDeployerViewGenerator.KMS_SETTINGS_TPL_FILE_PATH));

        /*
           Generate database associated stuff
        */

        String databaseType = cstItems.get("jdbc.databasetype");
        String dbName = cstItems.get("dbName");
        String dbHost = cstItems.get("dbHost");
        String dbPort = cstItems.get("dbPort");

        cstItems.remove("dbName");
        cstItems.remove("dbHost");
        cstItems.remove("dbPort");
        cstItems.remove("installGo");
        cstItems.remove("dbCreate");

        String templateUrl = driverListProperties.get(databaseType + ".jdbcdburl").toString();
        String builedUrl = String.format(templateUrl, dbHost, dbPort, dbName);

        cstItems.put("jdbc.url", builedUrl);
        cstItems.put("jdbc.dialect", driverListProperties.get(databaseType + ".dialect").toString());
        cstItems.put("jdbc.driver", driverListProperties.get(databaseType + ".driver").toString());


        /*
            Default values
         */





        // Use database name as schema value if defaultschema property is not specified
        Object defaultSchema = driverListProperties.get(databaseType + ".defaultschema");
        cstItems.put("jdbc.schema", defaultSchema != null && !defaultSchema.toString().isEmpty()
                ? defaultSchema.toString() : dbName);

        for (String key : cstItems.keySet()) {
            String value = cstItems.get(key);
            serverProperties.setProperty(key, value);
        }

        Properties defaultProperties = new Properties();
        defaultProperties.load(InstallProcessor.class.getResourceAsStream("/kimios-default.properties"));

        log.info("Default values loaded " + defaultProperties);

        serverProperties.putAll(defaultProperties);

        for (Object finalKey : serverProperties.keySet()) {
            log.info("Server Properties " + finalKey + " set to --> " + serverProperties.get(finalKey));
        }

        /*
           Server Conf file Outputstream
        */
        File file = new File(confTargetPath);
        Writer fw = new FileWriterWithEncoding(file, "UTF-8");
        serverProperties.store(fw, "Kimios Settings (generated by the Kimios Deployer)\n\n" +
                "Copyright (c) Teclib' " + Calendar.getInstance().get(Calendar.YEAR)  + "\n" + "\n" +
                "Authors: Jerome LUDMANN, Thomas LORNET, Fabien ALIN");

        //copy kimios config file
        try {
            // Looking for Spring conf in kimios-home module jar resources
            String kimiosAppConfDirectory = servletContext.getInitParameter(KimiosWebApplicationContext.KIMIOS_APP_ATTRIBUTE_NAME);
            String kimiosHomeDirectory = System.getProperty(KimiosWebApplicationContext.KIMIOS_HOME);
            File kimiosHome = new File(kimiosHomeDirectory + "/" + kimiosAppConfDirectory);
            String configLocation = kimiosHome.getAbsolutePath() + "/conf/ctx-kimios.xml";
            Enumeration<URL> urls = KimiosWebApplicationContext.class.getClassLoader().getResources(kimiosAppConfDirectory + "/conf/ctx-kimios.xml");
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String path = url.getPath();
                if (path.matches("^.*kimios-home.*\\.jar.*$")) {
                    // copy content into file
                    try {
                        Files.copy(url.openStream(), Paths.get(configLocation));
                    } catch (FileAlreadyExistsException ex){
                        log.warn("ctx-kimios.xml file already exists");
                    }
                    break;
                }
            }
        } catch (IOException e) {
            log.error("error while generating conf", e);
            throw new RuntimeException("error while handling ctx-kimios.xml...");
        }
    }

    public void loadSpringContext(ServletContext ctx) {
        SpringWebContextLauncher.launchApp(ctx, DeploymentManager.getContextLoader());
        DeploymentManager.endInstall(DeploymentManager.getContextLoader(), ctx);
    }

    public String getKimiosHome() throws Exception {
        String kimiosHome = System.getProperty("kimios.home");
        if(StringUtils.isNotBlank(kimiosHome)){
            return kimiosHome;
        } else
            throw new Exception("kimios.home Environment variable isn't available");
    }
}
