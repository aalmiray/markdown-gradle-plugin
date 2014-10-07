/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kordamp.gradle.markdown

/**
 * @author Andres Almiray
 */
enum Conversion {
    MARKDOWN('markdownToHtml', [MD_EXTENSION, MARKDOWN_EXTENSION], HTML_EXTENSION),
    HTML('htmlToMarkdown', [HTML_EXTENSION], MD_EXTENSION)

    private static final String MD_EXTENSION = '.md'
    private static final String HTML_EXTENSION = '.html'
    private static final String MARKDOWN_EXTENSION = '.markdown'

    private final String methodName
    private final List<String> extensions = []
    private final String targetExtension
    private final MarkdownProcessor processor = new MarkdownProcessor()

    Conversion(String methodName, List<String> extensions, String targetExtension) {
        this.methodName = methodName
        this.extensions.addAll(extensions)
        this.targetExtension = targetExtension
    }

    boolean accept(File file) {
        for (String ext : extensions) {
            if (file.name.endsWith(ext)) return true
        }
        false
    }

    @SuppressWarnings('ConfusingMethodName')
    String targetExtension() {
        targetExtension
    }

    List<String> extensions() {
        extensions
    }

//    String exclusions() {
//        extensions.collect([]) { '**/*' + it }.join(', ')
//    }

    String convert(File file, Map configuration) {
//, String basedir, File outputDir) {
        processor."$methodName"(file.text, configuration)
        /*
        String relativeFilePath = file.parentFile.absolutePath - basedir
        File destinationParentDir = new File("${outputDir}/${relativeFilePath}")
        destinationParentDir.mkdirs()
        File target = new File("${destinationParentDir}/${stripFilenameExtension(file.name)}${targetExtension}")
        target.text = text
        */
    }

    /**
     * Strip the filename extension from the given path,
     * e.g. "mypath/myfile.txt" -> "mypath/myfile".
     *
     * @param path the file path (may be <code>null</code>)
     * @return the path with stripped filename extension,
     *         or <code>null</code> if none
     */
    static String stripFilenameExtension(String path) {
        if (path == null) {
            return null
        }
        int extIndex = path.lastIndexOf('.')
        if (extIndex == -1) {
            return path
        }
        int folderIndex = path.lastIndexOf('/')
        if (folderIndex > extIndex) {
            return path
        }
        path[0..<extIndex]
    }
}