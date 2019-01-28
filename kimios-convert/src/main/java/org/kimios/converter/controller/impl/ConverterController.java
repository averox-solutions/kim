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

package org.kimios.converter.controller.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.kimios.api.Converter;
import org.kimios.api.InputSource;
import org.kimios.converter.ConverterCacheHandler;
import org.kimios.converter.ConverterDescriptor;
import org.kimios.kernel.controller.AKimiosController;
import org.kimios.converter.controller.IConverterController;
import org.kimios.converter.ConverterFactory;
import org.kimios.exceptions.ConverterException;
import org.kimios.converter.source.InputSourceFactory;
import org.kimios.converter.source.impl.FileInputSource;
import org.kimios.kernel.dms.model.Document;
import org.kimios.kernel.dms.model.DocumentVersion;
import org.kimios.exceptions.AccessDeniedException;
import org.kimios.kernel.security.model.Session;
import org.kimios.utils.configuration.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Transactional
public class ConverterController extends AKimiosController implements IConverterController {

    private static Logger log = LoggerFactory.getLogger(ConverterController.class);

<<<<<<< HEAD

    private ConverterFactory converterFactory;

    public ConverterFactory getConverterFactory() {
        return converterFactory;
    }

    public void setConverterFactory(ConverterFactory converterFactory) {
        this.converterFactory = converterFactory;
    }

=======
>>>>>>> [kimios-convert kimios-convert-service] Add Preview Method in ConvertService (rest only). Add protected attribute externalUrl on abstract Converter.
    public InputSource convertDocumentVersion(Session session, Long documentVersionId,
                                              String converterImpl, String outputFormat) throws ConverterException {

        String retainedMimeType = null;
        try {
            // Check rights
            DocumentVersion version = dmsFactoryInstantiator.getDocumentVersionFactory().getDocumentVersion(documentVersionId);
            if (version == null || !getSecurityAgent().isReadable(version.getDocument(), session.getUserName(), session.getUserSource(), session.getGroups())) {
                throw new AccessDeniedException();
            }
            if (ConverterCacheHandler.cacheExist(version.getUid())) {
                if(log.isDebugEnabled())
                    log.debug("input source already exists in cache, returning it");
                return ConverterCacheHandler.load(version.getUid());
            }
            // Build InputSource
            if(log.isDebugEnabled())
                log.debug("building inputSource for {}",version.getDocument().getName());
<<<<<<< HEAD
            InputSource source = InputSourceFactory.getInputSource(version, UUID.randomUUID().toString());
            // Get converter
            if(log.isDebugEnabled())
                log.debug("converter implementation: " + converterImpl);
            Converter converter = converterFactory.getConverter(converterImpl, outputFormat);
=======
            InputSource source = InputSourceFactory.getInputSource(version);
            // Get converter
            if(log.isDebugEnabled())
                log.debug("converter implementation: " + converterImpl);
            Converter converter = ConverterFactory.getConverter(converterImpl, outputFormat);
>>>>>>> [kimios-convert kimios-convert-service] Add Preview Method in ConvertService (rest only). Add protected attribute externalUrl on abstract Converter.
            retainedMimeType = converter.converterTargetMimeType();
            if(retainedMimeType == null){
                log.warn("{} not available for converter {}", retainedMimeType, converterImpl);
                throw new ConverterException("MimeTypeNotFound");
            }
            if(log.isDebugEnabled()){
                log.debug("converter will output");
            }
            // Convert and return the result source
            InputSource inputSource = converter.convertInputSource(source);
            ConverterCacheHandler.cachePreviewData(documentVersionId, inputSource);
            return inputSource;
        } catch (Exception e) {
            log.error("error while generating error view", e);
            if(e instanceof ConverterException && retainedMimeType != null &&
                    retainedMimeType.equals("text/html")){
                //return custom html error
                try{
                    File tempFile  = File.createTempFile("kmsprev", "");
                    FileUtils.writeStringToFile(tempFile,
                            "<html><body>An error happen during preview process!<br /><br />" +
                                    "Please Contact Your Administrator !</body></html>"
                    );
                    return new FileInputSource(tempFile, "text/html");
                }   catch (Exception ex){

                }
            }
            throw new ConverterException(e);
        }
    }

    public InputSource convertDocumentVersions(Session session, List<Long> documentVersionIds,
                                               String converterImpl, String outputFormat) throws ConverterException {
        String retainedMimeType = null;
        try {
            List<InputSource> sources = new ArrayList<InputSource>();
            for (Long documentVersionId : documentVersionIds) {

                // Check rights
                DocumentVersion version = dmsFactoryInstantiator.getDocumentVersionFactory().getDocumentVersion(documentVersionId);
                if (version == null || !getSecurityAgent().isReadable(version.getDocument(), session.getUserName(), session.getUserSource(), session.getGroups())) {
                    throw new AccessDeniedException();
                }

                // Build InputSource
                log.debug("Build InputSource from " + version.getDocument().getName() + "...");
                sources.add(InputSourceFactory.getInputSource(version, UUID.randomUUID().toString()));
            }
            // Cache enabled for singles versions processing only.
            if (documentVersionIds.size() == 1 && ConverterCacheHandler.cacheExist(documentVersionIds.get(0))) {
                return ConverterCacheHandler.load(documentVersionIds.get(0));
            }

            // Get converter
            log.debug("Getting Converter implementation: " + converterImpl);
<<<<<<< HEAD
            Converter converter = converterFactory.getConverter(converterImpl, outputFormat);
=======
            Converter converter = ConverterFactory.getConverter(converterImpl, outputFormat);
>>>>>>> [kimios-convert kimios-convert-service] Add Preview Method in ConvertService (rest only). Add protected attribute externalUrl on abstract Converter.

            // Convert and return the result source
            InputSource inputSource = converter.convertInputSources(sources);
            if (documentVersionIds.size() == 1 && inputSource.getPublicUrl() != null) {
                log.debug("Putting converted form in preview cache ...");
                ConverterCacheHandler.cachePreviewData(documentVersionIds.get(0), inputSource);
            }
            return inputSource;
        } catch (Exception e) {
            log.error("error while generating error view", e);
            if(e instanceof ConverterException && retainedMimeType != null &&
                    retainedMimeType.equals("text/html")){
                //return custom html error
                try{
                    File tempFile  = File.createTempFile("kmsprev", "");
                    //TODO: set clean error template
                    FileUtils.writeStringToFile(tempFile,
                            "<html><body>An error happen during preview process!<br /><br />" +
                                    "Please Contact Your Administrator !</body></html>"
                    );
                    return new FileInputSource(tempFile, "text/html");
                }   catch (Exception ex){
                    log.error("error while generating error view", ex);
                }
            }
            throw new ConverterException(e);
        }
    }

    // aliases

    public InputSource convertDocument(Session session, Long documentId,
                                       String converterImpl, String outputFormat) throws ConverterException {
        Document document = dmsFactoryInstantiator.getDocumentFactory().getDocument(documentId);
        DocumentVersion version = dmsFactoryInstantiator.getDocumentVersionFactory().getLastDocumentVersion(document);
        return convertDocumentVersion(session, version.getUid(), converterImpl, outputFormat);
    }

    public InputSource convertDocuments(Session session, List<Long> documentIds,
                                        String converterImpl, String outputFormat) throws ConverterException {
        List<Long> versionIds = new ArrayList<Long>();
        for (Long documentId : documentIds) {
            Document document = dmsFactoryInstantiator.getDocumentFactory().getDocument(documentId);
            DocumentVersion version = dmsFactoryInstantiator.getDocumentVersionFactory().getLastDocumentVersion(document);
            versionIds.add(version.getUid());
        }
        return convertDocumentVersions(session, versionIds, converterImpl, outputFormat);
    }

    public InputSource loadPreviewDataFromCache(Session session, String idPreview) throws ConverterException {
        if(ConverterCacheHandler.cacheExistsFromToken(idPreview)){
            return ConverterCacheHandler.load(idPreview);
        } else
            throw new ConverterException("PreviewNotFound");
    }

    @Override
    public Map<String, List<ConverterDescriptor>> loadDescriptors() throws Exception {
        //load descriptors from config file
        String configuration = ConfigurationManager.getValue("dms.converters.descriptor.file");
        com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
        if(configuration != null && new File(configuration).exists()){
            return mapper.readValue(new FileInputStream(configuration), new TypeReference<Map<String,List<ConverterDescriptor>>>(){});
        } else  {
            return mapper.readValue(this.getClass().getResourceAsStream("/default-converters.json"), new TypeReference<Map<String,List<ConverterDescriptor>>>(){});
        }
    }
}
