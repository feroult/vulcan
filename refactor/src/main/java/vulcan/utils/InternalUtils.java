package vulcan.utils;

import org.eclipse.jdt.internal.corext.util.JdtFlags;

public class InternalUtils {
    public static int getVisibilityCode(String visibilityString) {
        return JdtFlags.getVisibilityCode(visibilityString);
    }
}
