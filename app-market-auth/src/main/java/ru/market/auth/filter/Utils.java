package ru.market.auth.filter;

import java.util.Set;

final class Utils {
    private Utils(){}

    static boolean containsIdInPath(String servletPath, String pathIdentifier, Set<Long> idSet){
        int lastIndexOfSlash = servletPath.lastIndexOf(pathIdentifier);
        if(lastIndexOfSlash == -1){
            return false;
        }
        lastIndexOfSlash = lastIndexOfSlash + pathIdentifier.length();

        String possibleId = servletPath.substring(lastIndexOfSlash);

        if(!possibleId.matches("\\d+")){
            return false;
        }

        return idSet.contains(Long.parseLong(possibleId));
    }
}
