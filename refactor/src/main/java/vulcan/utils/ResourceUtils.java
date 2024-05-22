package vulcan.utils;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.FrameworkUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;


public class ResourceUtils {

    public static void copyFolder(String from, String to) {
        Path sourceFolder = Paths.get(relativePathname(from));
        Path targetFolder = Paths.get(relativePathname(to));

        try {
            Files.walkFileTree(sourceFolder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = targetFolder.resolve(sourceFolder.relativize(dir));
                    Files.createDirectories(targetDir);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, targetFolder.resolve(sourceFolder.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFolder(String pathname) {
        try {
            Path folder = Paths.get(relativePathname(pathname));
            if (Files.exists(folder)) {
                Files.walk(folder).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String relativePathname(String pathname) {
        URL resource = ResourceUtils.class.getClassLoader().getResource(".");
        if (resource != null) {
            return resource.getPath() + "../../" + pathname;
        }
        File bundleFolder = FileLocator.getBundleFileLocation(FrameworkUtil.getBundle(ResourceUtils.class)).get();
        return bundleFolder.getParentFile().getPath() + "/../../../../../../../../../" + pathname;
    }

    public static String readFileAsString(String pathname) {
        try {
            String fileContent = Files.readString(Paths.get(relativePathname(pathname)));
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean exists(String pathname) {
        return new File(relativePathname(pathname)).exists();
    }
}
