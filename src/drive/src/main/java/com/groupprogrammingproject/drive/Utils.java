package com.groupprogrammingproject.drive;

import java.nio.file.Paths;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utils {
    public static String createFilePath(String parentPath, String childPath) {
        return Paths.get(parentPath, childPath).toString().replace('\\', '/');
    }

    public static boolean isRootPath(String path) {
        return path.equals(".") || path.equals(".\\") || path.equals("./") || path.isBlank() || path == null;
    }

    public static String getFileKeyFromFullPath(String path) {
        if (!path.contains("/")) return path;
        return path.split("/")[path.split("/").length-1];
    } 

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(
                iterable.iterator(),
                Spliterator.ORDERED
            ),
            false
        );
    }
}