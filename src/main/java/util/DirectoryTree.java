package util;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;


/**
 *
 * Thank for https://github.com/GourdErwa/MyNote/blob/master/util/src/main/java/com/gourd/erwa/util/DirectoryTreeV1.java
 * */
public class DirectoryTree {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String VERTICAL = "│  ", INTERMEDIATE = "├─", END = "└─";
    private static final String SPACING = "\t";//目录间距

    private final StringBuilder result = new StringBuilder();

    private int deep = Integer.MAX_VALUE;
    private int directoryCount = 0, fileCount = 0;
    private String directory = "";
    private FileFilter fileFilter = pathname -> true;
    private List<Function<File, String>> appends = new LinkedList<>();

    public static DirectoryTree create(String directory) {
        return new DirectoryTree(directory);
    }

    private DirectoryTree(String directory) {
        this.directory = directory;
        appends.add(0, File::getName);
    }

    private DirectoryTree(String directory, FileFilter fileFilter) {
        this(directory);
        this.fileFilter = fileFilter;
    }

    private List<File> fetchFiles(File file) {
        return (file == null || !file.exists()) ? Collections.EMPTY_LIST : Arrays.asList(file.listFiles(fileFilter));
    }

    private void generateHandle(File file, String prefix, int deep) {
        final List<File> files = fetchFiles(file);
        if(files.isEmpty()) { return; }

        for(int i = 0, size = files.size() - 1; i <= size; ++i) {
            final File f = files.get(i);
            final boolean isLast = (size <= i);
            this.result.append(prefix).append(isLast ? END : INTERMEDIATE);
            this.appendDisplayContent(f);
            this.result.append(LINE_SEPARATOR);

            if(f.isDirectory()) { ++directoryCount; }
            else { ++fileCount; }

            if(f.isDirectory() && deep <= this.deep) {
                this.generateHandle(f, prefix + (isLast ? "" : VERTICAL) + SPACING, ++deep);
            }
        }
    }

    private void appendDisplayContent(File f) {
        List<Function<File, String>> appendContents = appends;
        for(Function<File, String> to : appendContents) {
            this.result.append(' ').append(to.apply(f));
        }
    }

    public String generate() {
        File file = new File(directory);
        if(!file.exists()) { return ""; }
        this.generateHandle(file, "", 0);

        this.result
                .append(directoryCount).append(" directories,").append(SPACING)
                .append(fileCount).append(" files").append(SPACING);
        return this.result.toString();
    }

    public DirectoryTree setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
        return this;
    }
}

