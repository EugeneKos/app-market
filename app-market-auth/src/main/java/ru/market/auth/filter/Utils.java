package ru.market.auth.filter;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Utils {
    private Utils(){}

    static boolean checkIdInServletPath(String servletPath, String pathRegexp, Set<Long> idSet){
        String[] parse = groupingByRegexp(servletPath, pathRegexp, 1);

        String possibleId = parse[0];

        if(!possibleId.matches("\\d+")){
            return false;
        }

        return idSet.contains(Long.parseLong(possibleId));
    }

    static String[] groupingByRegexp(String item, String regexp, int groupSize){
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(item);

        String[] groups = new String[groupSize];

        int currentGroupNum = 1;

        if(matcher.find()){
            while (currentGroupNum <= groupSize){
                String currentGroup = matcher.group(currentGroupNum);
                groups[currentGroupNum - 1] = currentGroup;
                currentGroupNum ++;
            }
        }

        return groups;
    }
}
