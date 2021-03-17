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
package org.kimios.utils.extension;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.vfs.Vfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder
{
    private static Logger log = LoggerFactory.getLogger(ClassFinder.class);

    public static <T> Collection<Class<? extends T>> findImplement(String pkg, Class<T> impl)
    {

        ArrayList<Vfs.UrlType> urlTypes = new ArrayList<Vfs.UrlType>();
        urlTypes.add(Vfs.DefaultUrlTypes.jarFile);
        urlTypes.add(Vfs.DefaultUrlTypes.jarUrl);
        urlTypes.add(Vfs.DefaultUrlTypes.directory);
        try {
            //look in the bundle class loader
            Bundle b = FrameworkUtil.getBundle(impl);
            BundleUrlType currentBundleUrlType = new BundleUrlType(b);
            Vfs.addDefaultURLTypes(currentBundleUrlType);
        } catch (RuntimeException e) {
        } catch (Exception e) {
        } catch (LinkageError e){
        }

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(pkg))
        );
        return reflections.getSubTypesOf(impl);
    }

    public static Set findImplement(BundleUrlType bundleUrlType,
                                    String pkg,
                                    Class<?> impl,
                                    ClassLoader cl)
    {
        try {
            ArrayList<Vfs.UrlType> it = new ArrayList<Vfs.UrlType>();
            it.add(bundleUrlType);
            Vfs.setDefaultURLTypes(it);
        } catch (RuntimeException e) {
            log.error("error while loading class", e);
        } catch (Exception e) {
            log.error("error while loading class", e);
        } catch (LinkageError e){
            log.error("error while loading class", e);
        }

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .addClassLoader(cl)
                .addUrls(ClasspathHelper.forPackage(pkg))
        );
        return reflections.getSubTypesOf(impl);
    }

    public static Vector<Class<?>> findnames(String pckgname, Class<?> tosubclass)
    {
        try {
            String packagePath = pckgname.replace('.', '/');
            URLClassLoader cLoader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
            URL[] classpath = cLoader.getURLs();
            Vector<Class<?>> result = new Vector<Class<?>>();
            for (URL url : classpath) {
                File file = new File(url.toURI());
                if (file.getPath().endsWith(".jar")) {
                    JarFile jarFile = new JarFile(file);
                    for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
                        String entryName = (entries.nextElement()).getName();
                        if (entryName.matches(packagePath + "/\\w*\\.class")) { // get only class files in package dir
                            String className = entryName.replace('/', '.').substring(0, entryName.lastIndexOf('.'));
                            Class<?> clazz = Class.forName(className);
                            try {
                                Object o = clazz.newInstance();
                                if (tosubclass.isInstance(o)) {
                                    result.add(clazz);
                                }
                            } catch (InstantiationException iex) {
                                iex.printStackTrace();
                            } catch (IllegalAccessException iaex) {
                                iaex.printStackTrace();
                            }
                        }
                    }
                } else { // directory
                    File packageDirectory = new File(file.getPath() + "/" + packagePath);
                    if (packageDirectory.exists()) {
                        for (File f : packageDirectory.listFiles()) {
                            if (f.getPath().endsWith(".class")) {
                                String className =
                                        pckgname + "." + f.getName().substring(0, f.getName().lastIndexOf('.'));
                                Class<?> clazz = Class.forName(className);
                                try {
                                    Object o = clazz.newInstance();
                                    if (tosubclass.isInstance(o)) {
                                        result.add(clazz);
                                    }
                                } catch (InstantiationException iex) {
                                    iex.printStackTrace();
                                } catch (IllegalAccessException iaex) {
                                    iaex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            log.error("Error during class search", e);
            return null;
        }
    }
}
