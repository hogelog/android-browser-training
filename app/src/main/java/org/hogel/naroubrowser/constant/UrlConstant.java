package org.hogel.naroubrowser.constant;

import java.util.regex.Pattern;

public class UrlConstant {
    public static final Pattern PATTERN_URL_INSIDE = Pattern.compile("^https?://(?:(?:\\w+)\\.)?syosetu\\.com(?:$|/.*)");

    public static final Pattern PATTERN_URL_DISABLE_JS = Pattern.compile("/rank/list/type/.+_total/$");

    public static final String URL_LAUNCH = "http://syosetu.com/favnovelmain/list/";
    public static final String URL_RANKING = "http://yomou.syosetu.com/rank/top/";
}
