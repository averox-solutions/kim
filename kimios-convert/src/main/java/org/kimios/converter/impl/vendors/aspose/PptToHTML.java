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

package org.kimios.converter.impl.vendors.aspose;

import com.aspose.slides.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.kimios.kernel.converter.ConverterImpl;
import org.kimios.kernel.converter.exception.BadInputSource;
import org.kimios.kernel.converter.exception.ConverterException;
import org.kimios.kernel.converter.impl.FileNameGenerator;
import org.kimios.kernel.converter.source.InputSource;
import org.kimios.kernel.converter.source.InputSourceFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Allows to convert .DOCX to HTML content
 */
public class PptToHTML extends ConverterImpl {

    private static final String[] INPUT_EXTENSIONS = new String[]{"ppt", "pptx", "odp"};
    private static final String OUTPUT_EXTENSION = "html";

    @Override
    public InputSource convertInputSource(InputSource source) throws ConverterException {

        if (!Arrays.asList(INPUT_EXTENSIONS).contains(source.getType())) {
            throw new BadInputSource(this);
        }

        String sourcePath = null;

        try {
            // Copy given resource to temporary repository
            sourcePath = temporaryRepository + "/" + source.getName() + "_" +
                    FileNameGenerator.generate() + "." + source.getType();
            IOUtils.copyLarge(source.getInputStream(), new FileOutputStream(sourcePath));

            String fileName = FileNameGenerator.generate();
            // Convert file located to sourcePath into HTML web content
            String targetPath = temporaryRepository + "/" +
                    fileName + "_dir/" + fileName + "." + OUTPUT_EXTENSION;

            String targetPathImg = targetPath + "_img";
            File imgFolder = new File(targetPathImg);
            imgFolder.mkdirs();


            // The encoding of the text file is automatically detected.
            Presentation pres = new Presentation(sourcePath);
            HtmlOptions htmlOpt = new HtmlOptions();
            htmlOpt.setHtmlFormatter(HtmlFormatter.createDocumentFormatter("", false));
            //Saving the Presentation1 to HTML
            pres.save(targetPath, SaveFormat.Html, htmlOpt);



            String content = FileUtils.readFileToString(new File(targetPath));

            content = content.replaceAll("Evaluation only.", "").replaceAll("Created with Aspose.Slides for Java 15.7.0.0.", "").replaceAll("Copyright 2004-2015 Aspose Pty Ltd.", "");

            FileUtils.writeStringToFile(new File(targetPath), content);


            // Return HTML-based InputSource
            InputSource result = InputSourceFactory.getInputSource(targetPath);
            result.setHumanName(source.getName() + "_" + source.getType() + "." + OUTPUT_EXTENSION);

            /*
                Set url, to use in cache.
             */
            result.setPublicUrl(targetPath);
            result.setMimeType(this.converterTargetMimeType());
            return result;

        } catch (Exception e) {
            throw new ConverterException(e);

        } finally {

            // Delete obsolete file
            new File(sourcePath).delete();
        }
    }

    @Override
    public InputSource convertInputSources(List<InputSource> sources) throws ConverterException {
        if(sources.size() == 1){
            return convertInputSource(sources.get(0));
        } else
            throw new ConverterException("Converter " + this.getClass().getName() + " cannot process many versions at once");

    }

    @Override
    public String converterTargetMimeType() {
        return "text/html";
    }
}
