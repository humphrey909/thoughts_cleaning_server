package com.async.main.base.common;

import com.async.main.base.config.Constant;
// import org.apache.commons.lang3.RandomStringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Locale;


public class Util {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String nvl(Object s) {
        return s == null ? "" : s.toString();
    }

    public static String nvl(String s) {
        return s == null ? "" : s;
    }

    public static String nvl(String s, String t) {
        return nvl(s).isEmpty() ? t : s;
    }

    public static LocalDateTime nvl(LocalDateTime s, LocalDateTime t) {
        return s == null ? t : s;
    }

    public static int nvl_int(Object s, int t) {
        if (s == null) {
            return t;
        } else if (s instanceof Integer) {
            return (Integer)s;
        } else if (s instanceof String) {
            try {
                return Integer.parseInt((String)s);
            } catch (Exception e) {
                return t;
            }
        }

        return t;
    }

    public static long nvl_long(Object s, long t) {
        if (s == null) {
            return t;
        } else if (s instanceof Integer) {
            return ((Integer)s).longValue();
        } else {
            return (Long)s;
        }
    }

    public static boolean nvl_boolean(Object s) {
        if (s == null) {
            return false;
        } else if (s instanceof Boolean) {
            return (Boolean) s;
        } else if (s instanceof String) {
            return Boolean.parseBoolean((String)s);
        }

        return false;
    }

    public static double nvl_double(Object s, double t) {
        if (s == null) return t;

        if (s instanceof String) {
            return Double.parseDouble((String)s);
        } else if (s instanceof Integer) {
            return ((Integer)s).doubleValue();
        }

        return (double)s;
    }

    public static LocalDate nvl_date(Object s) {
        if (s == null) return null;

        return ((java.sql.Date)s).toLocalDate();
    }

    public static LocalDateTime nvl_dt(Object s) {
        if (s == null) return null;

        return ((java.sql.Timestamp)s).toLocalDateTime();
    }

    public static LocalTime nvl_time(Object s) {
        if (s == null) return null;

        return ((java.sql.Time)s).toLocalTime();
    }

    public static long evalMM(LocalDateTime dt) {
        return (dt.getYear() * 10000 + dt.getMonthValue() * 100 + dt.getDayOfMonth()) * 10000L
                + dt.getHour() * 100 + dt.getMinute();
    }

    // public static String getNewSession() {
    //     return getNewSession(false);
    // }

    // public static String getNewSession(boolean isTemp) {
    //     StringBuilder sb = new StringBuilder();

    //     if (isTemp) {
    //         sb.append('T');
    //     } else {
    //         String prefix = RandomStringUtils.randomAlphanumeric(1);
    //         if (prefix.equals("T")) {
    //             sb.append("A");
    //         } else {
    //             sb.append(prefix);
    //         }
    //     }

    //     sb.append(RandomStringUtils.randomAlphanumeric(15));

    //     return sb.toString();
    // }

    public static String dateString(LocalDate dt) {
        return dateString(dt, Constant.jsonDateFormat);
    }

    public static String dateString(LocalDate dt, String format) {
        return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern(format));
    }

    public static String dtString(LocalDateTime dt) {
        return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern(Constant.jsonDatetimeFormat));
    }

    public static String dtString(LocalDateTime dt, String format) {
        return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern(format));
    }

    public static String timeString(LocalDateTime dt) {
        return dt == null ? "" : dt.format(DateTimeFormatter.ofPattern(Constant.jsonTimeFormat));
    }

    public static String timeString(LocalTime time) {
        return time == null ? "" : time.format(DateTimeFormatter.ofPattern(Constant.jsonTimeFormat));
    }

    public static String time2String(LocalTime t) {
        return t == null ? "" : t.format(DateTimeFormatter.ofPattern(Constant.jsonTime2Format));
    }

    public static String timeAPMString(LocalTime t, String _lang) {
        if (t == null) return "";

        if (Constant.LANG_KO.equals(_lang)) {
            return t.format(DateTimeFormatter.ofPattern("a h시 m분", Locale.KOREAN));
        } else {
            return t.format(DateTimeFormatter.ofPattern("K:mm a", Locale.ENGLISH));
        }
    }

    public static int time2Value(LocalDateTime dt) {
        return dt.getHour() * 100 + dt.getMinute();
    }

    public static int time2Value(LocalTime t) {
        return t.getHour() * 100 + t.getMinute();
    }

    public static int getDayValue(LocalDateTime dt) {
        return dt.getHour() * 60 + dt.getMinute();
    }

    public static int getDayValue(LocalTime t) {
        return t.getHour() * 60 + t.getMinute();
    }

    public static int getDayValue(int hh, int mm) {
        return hh * 60 + mm;
    }

    public static int getDaySeconds(LocalTime t) {
        return t.getHour() * 3600 + t.getMinute() * 60 + t.getSecond();
    }

    public static LocalDate parseDateThrow(String s) {
        return LocalDate.parse(s, DateTimeFormatter.ofPattern(Constant.jsonDateFormat));
    }

    public static LocalTime parseTimeThrow(String s) {
        return LocalTime.parse(s, DateTimeFormatter.ofPattern(Constant.jsonTimeFormat));
    }

    public static LocalTime parseTimeHHMM(String s) {
        return LocalTime.parse(s, DateTimeFormatter.ofPattern(Constant.jsonTimeFormat.substring(0, 4)));
    }

    public static LocalDate parseDate(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.BASIC_ISO_DATE);    // yyyyMMdd
        } catch (Exception e) {
            return LocalDate.now();
        }
    }

    public static LocalDateTime parseDtThrow(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(Constant.jsonDatetimeFormat));
    }

    public static LocalDateTime parseDt(String s) {
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(Constant.jsonDatetimeFormat));
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }

    public static LocalDateTime getTodayDt(int hh, int mm, int ss) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.of(hh, mm, ss));
    }

    public static LocalDateTime getRelativeDt(int hh, int mm, int ss) {
        return LocalDateTime.now().plusHours(hh).plusMinutes(mm).plusSeconds(ss);
    }

    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] toByte(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    /**
     *
     * @param v1
     * @param v2
     * @return 0: v1 == v2, -: v1 < v2, +: v1 > v2
     */
    public static int compareVersion(String v1, String v2) {
        String[] arr = v2.split("\\.");
        int[] nextVersion = new int[arr.length];

        for (int i=0; i<arr.length; i++) {
            nextVersion[i] = Integer.parseInt(arr[i]);
        }

        return compareVersion(v1, nextVersion);
    }

    public static int compareVersion(String v1, int[] nextVersion) {
        if (v1.isEmpty()) return 1;

        String[] v1List = v1.split("\\.");

        int[] prevVersion = new int[nextVersion.length];
        for (int i=0; i<nextVersion.length; i++) {
            prevVersion[i] = 0;
        }

        for (int i=0; i<nextVersion.length; i++) {
            if (i >= v1List.length) break;
            prevVersion[i] = Integer.parseInt(v1List[i]);
        }

        for (int i=0; i<nextVersion.length; i++) {
            if (prevVersion[i] == nextVersion[i]) continue;
            if (prevVersion[i] < nextVersion[i]) return -1;
            if (prevVersion[i] > nextVersion[i]) return 1;
        }
        // 버전이 같을 경우
        return 0;
    }

    public static boolean isOldVersion(String v1, String v2) {
        String[] arr = v2.split("\\.");
        int[] nextVersion = new int[arr.length];

        for (int i=0; i<arr.length; i++) {
            nextVersion[i] = Integer.parseInt(arr[i]);
        }

        return isOldVersion(v1, nextVersion);
    }

    public static boolean isOldVersion(String v1, int[] nextVersion) {
        if (v1.isEmpty()) return true;

        String[] v1List = v1.split("\\.");

        int[] prevVersion = new int[nextVersion.length];
        for (int i=0; i<nextVersion.length; i++) {
            prevVersion[i] = 0;
        }

        for (int i=0; i<nextVersion.length; i++) {
            if (i >= v1List.length) break;
            prevVersion[i] = Integer.parseInt(v1List[i]);
        }

        for (int i=0; i<nextVersion.length; i++) {
            if (prevVersion[i] == nextVersion[i]) continue;
            return prevVersion[i] < nextVersion[i];
        }
        // 버전이 같을 경우
        return false;
    }

    public static String base64Encode(byte[] s) {
        return new String(Base64.getEncoder().encode(s));
    }

    public static String base64Decode(String s) {
        return toHex(Base64.getDecoder().decode(s));
    }

    public static String getRealURL(String rootURL, String url) {
        if (nvl(url).isEmpty()) return "";
        if (url.startsWith("http://") || url.startsWith("https://")) return url;

        return rootURL + url;
    }

    public static int getTimezone(LocalDateTime userNow) {
        LocalDateTime now = LocalDateTime.now();

        int diff = (int)now.until(userNow, ChronoUnit.SECONDS) % 86400;
        int hourDiff = (int)Math.round(diff / 3600.0);

        return 9 + hourDiff;
    }

    public static LocalDateTime[] getDtRange(LocalDate _date) {
        LocalDateTime sDt = LocalDateTime.of(_date, LocalTime.MIN);
        LocalDateTime eDt = sDt.plusDays(1);

        return new LocalDateTime[] {sDt, eDt};
    }

    public static LocalDate[] getDateRange(LocalDate _date, LocalDate today) {
        LocalDate sDate = _date.withDayOfMonth(1);
        LocalDate eDate = sDate.plusMonths(1);

        if (eDate.isAfter(today)) {
            eDate = today.plusDays(1);
        }

        return new LocalDate[] {sDate, eDate};
    }

    public static String toRadix(int id) {
        return Integer.toString(id, Constant.RADIX);
    }

    public static int fromRadix(String s) {
        return Integer.parseInt(s, Constant.RADIX);
    }

    public static boolean isValidLoginId(String loginId) {
        if (loginId.length() > 20 || loginId.length() < 4 || !loginId.matches("^[a-z][a-z0-9]+")) {
            return false;
        }

        return true;
    }

    public static String getYN(boolean val) {
        return val ? "Y" : "N";
    }

    public static String getMaskingPhoneNo(String phoneNo) {
        return phoneNo.length() <= 4
            ? phoneNo
            : String.format("%s-****-%s", phoneNo.substring(0, 3), phoneNo.substring(phoneNo.length()-4));
    }


    //Zulu 타입 데이터 변환
    public static String transZuluType(String zuluData) {
        // Instant 객체로 변환
        Instant instant = Instant.parse(zuluData);

        // ZonedDateTime 객체로 변환 (UTC)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));

        //UTC -> 한국시간으로 변환
        ZonedDateTime kstNow = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));

        // 원하는 형식의 DateTimeFormatter 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 포맷된 문자열로 변환
        return kstNow.format(formatter);
    }
}
