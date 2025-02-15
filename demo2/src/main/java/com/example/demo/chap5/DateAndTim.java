package com.example.demo.chap5;

import org.springframework.cglib.core.Local;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class DateAndTim {
    public static void main(String[] args) {
        //local date : chỉ chứa ngày
        System.out.println("local date : " + LocalDate.now());

        //local date time : chứa ngày và giờ
        System.out.println("local Date Time : " + LocalDateTime.now());

        //local time : chứa giờ
        System.out.println("local time :" + LocalTime.now());

        //Zone date time : chứa ngày , giờ , vùng hiện tại
        System.out.println("zone date time : " + ZonedDateTime.now());

        /**
         * How UTC works
         */
        //create ZoneDateTime
        ZonedDateTime localDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")); // Vietnam Time
        System.out.println("Local Time: " + localDateTime); // Viet Nam :2024-12-06T16:13:20

        //convert to UTC
        ZonedDateTime zonedDateTime = localDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        System.out.println("time in UTC:" + zonedDateTime); // Europe : 2024-12-06T09:13:20

        /**
         * Specific dates and times
         */
        LocalDate date1 = LocalDate.of(2015, Month.JANUARY, 20);
        // có thể chuyền số kiểu int
        LocalDate date2 = LocalDate.of(2015, 1, 20);

        /**
         *  Month :  là một lớp enum không phải là int , nên không thể so sánh được với dạng số
         */
        Month month = Month.MAY;
//        boolean b1 = month == 1; // Don't compile
        boolean b2 = month == Month.AUGUST;
        System.out.println(b2); // false

        /**
         * Mặc dù java bắt đầu từ 0 . Nhưng month,day là một exception , sẽ bắt đầu từ 1
         */
        LocalDate localDateYear = LocalDate.of(0, 1, 8);
        System.out.println(localDateYear); //0000-01-01
        LocalTime localTime123 = LocalTime.of(0, 0, 0, 0);
//        System.out.println(localTime123);
        LocalDate localDateMonth = LocalDate.of(0, 1, 1);
//        System.out.println(localDateMonth); //Exception : Invalid value for MonthOfYear (valid values 1 - 12): 0
//        LocalDate localDateDay = LocalDate.of(0, 1, 0);
//        System.out.println(localDateDay); //Exception :  Invalid value for DayOfMonth (valid values 1 - 28/31): 0

        /**
         * khi tạo mới một time, bạn có thể chỉ định chi tiết bạn muốn, thậm chí đến cả nano giây
         */
        // public static LocalTime of(int hour, int minute, int second, int nanos)

        LocalTime localTime = LocalTime.of(1, 1, 1, 1);
        LocalDateTime dateTime = LocalDateTime.of(1, 1, 1, 1, 1, 1, 1);
        LocalDate localDate = LocalDate.of(1, 1, 1); // chi áp dụng đến ngày
        /**
         * Combine dates và time trong cùng một object:
         */
        //cách 1
        LocalDateTime dateTime1 = LocalDateTime.of(2015, Month.JANUARY, 20, 6, 15, 30);
        System.out.println(dateTime1); //2015-01-20T06:15:30
        //cách 2
        LocalDate localDate1 = LocalDate.of(2025, 1, 20);
        LocalTime localTime1 = LocalTime.of(6, 15, 30);
        LocalDateTime dateTime12 = LocalDateTime.of(localDate1, localTime1);
        System.out.println(dateTime12);//2015-01-20T06:15:30

        /**
         * time zone
         */
        ZoneId zoneId = ZoneId.of("US/Eastern");
        //khai báo trực tiếp
        ZonedDateTime time = ZonedDateTime.of(2025, 1, 20, 6,
                15, 30, 200, zoneId);
        // khai báo localDate , localTime , ZoneId vào trong
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(date1, localTime, zoneId);
        //khai báo localDateTime
        ZonedDateTime zonedDateTime2 = ZonedDateTime.of(dateTime1, zoneId);

        /** finding time zone
         *
         */
        // Cách để tìm time zone của mình
        System.out.println("default " + ZoneId.systemDefault());

        // Cách để biết những time zone khác ( khuyến khích dùng US/Eastern time zone to America/New york)
        ZoneId.getAvailableZoneIds().stream().filter(z -> z.contains("US") || z.contains("America")).forEach(System.out::println);

        /**
         * the date and time class có private constructors , điều này bắt bạn phải dùng các static methods
         */
//        LocalDate localDate2=new LocalDate(); // not compile

        /**
         * Trường hợp nếu bạn truyền vào date or time không hợp lệ
         */
//        System.out.println( LocalDate.of(2015, 2, 32));  java.time.DateTimeException: Invalid value for DayOfMonth
// (valid values 1—28/31): 32


        /**
         * Manipulating dates and times
         */

        LocalDate localDate2 = LocalDate.of(2020, 2, 20);
        LocalTime localTime2 = LocalTime.of(5, 15);
        LocalDateTime dateTime2 = LocalDateTime.of(localDate2, localTime2);
        System.out.println(dateTime2);
        dateTime2 = dateTime2.minusDays(2);
        System.out.println("Minus Day :" + dateTime2);
        dateTime2 = dateTime2.minusHours(10);
        System.out.println("Minus hour : " + dateTime2);

        // Cách viết gắn ngọn gơn
        LocalDateTime dateTime3 = LocalDateTime.of(localDate2, localTime2).minusDays(1).minusHours(2);
        System.out.println("date time 3 : " + dateTime3);

        /** Working with periods
         * Dùng để đại diện một khoảng thời gian trong java
         * Period chỉ có thể chứa : years,months,days .
         * Không áp dụng cho time
         */
//        Period period=Period.of(1,1,1,1); // not compile
        LocalDate start = LocalDate.of(2015, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2015, Month.MARCH, 30);
        Period period = Period.ofMonths(1);
        Period period3 = Period.between(start, end);
        System.out.println("period 3:" + period3);
        performAnimalEnrichment(start, end, period);

        /**
         * way to create a Period class
         */
        Period period1 = Period.ofYears(1); // same for months,days,weeks
        Period period2 = Period.of(1, 0, 1);

        /**
         * bạn không thể xâu các chuỗi các phương thức với class Period
         *
         */
//        Period wrong = Period.ofYears(1).ofWeeks(1);
        Period wrong = Period.ofYears(1);
        wrong = Period.ofWeeks(1);
        System.out.println("wrong:" + wrong); //P7D

        LocalDate localDate3 = LocalDate.of(2015, 1, 20);
        LocalTime localTime3 = LocalTime.of(6, 15);
        LocalDateTime dateTime4 = LocalDateTime.of(localDate3, localTime3);
        Period period4 = Period.ofMonths(1);
        System.out.println("local date 3 :" + localDate3.plus(period4));
        System.out.println("dateTime 4 :" + dateTime4.plus(period4));
//        System.out.println("local time 3 :" + localTime3.plus(period4)); // UnsupportedTemporalTypeException

        /** Working with Durations
         * Dành cho các đơn vị liên quan đến time
         * Bạn có thể chỉ định số ngày,giờ,phút,giây,nano giây
         * Duration không có constructor mà chứa nhiều units => Bạn chỉ có thể khai báo 1 kiểu thời gian nhất định
         *
         */

        /**
         * Create a Duration
         */
        System.out.println("duration day : " + Duration.ofDays(2)); //PT48H
        System.out.println("duration hour : " + Duration.ofHours(2)); //PT2H

        /**
         * TemporalUnit trong Duration
         * Là một interface , được duy nhất enum class ChronoUnit kế thừa
         * Được sử dụng để mô phỏng : độ lệch thời gian , múi giờ , kỷ nguyên
         * Có nghĩa là thay vì khai báo cụ thể ofDays || ofHours , sẽ khai báo một số và chỉ định dạng thời gian
         */

        System.out.println("daily half day : " + Duration.of(1, ChronoUnit.HALF_DAYS));
        System.out.println("daily day : " + Duration.of(1, ChronoUnit.DAYS));
        System.out.println("daily minutes : " + Duration.of(1, ChronoUnit.MINUTES));
        System.out.println("daily millis : " + Duration.of(1, ChronoUnit.MILLIS));

        /** ChronoUnit for Differences
         *  Dùng để khoảng cách chênh lệch thời gian
         */
        LocalTime localTime4 = LocalTime.of(5, 30);
        LocalTime localTime5 = LocalTime.of(3, 20);
        System.out.println("khoang thoi gian  lech la : " + ChronoUnit.HOURS.between(localTime4, localTime5));
        System.out.println("khoang thoi gian phut  lech la : " + ChronoUnit.MINUTES.between(localTime4, localTime5));
        System.out.println("khoang thoi gian giay  lech la : " + ChronoUnit.SECONDS.between(localTime4, localTime5));

        /**
         * Using Duration
         */
        LocalDate date13 = LocalDate.of(2015, 1, 20);
        LocalTime time13 = LocalTime.of(6, 15);
        LocalDateTime dateTime13 = LocalDateTime.of(date13, time13);
        Duration duration = Duration.ofHours(6);
        System.out.println(dateTime13.plus(duration)); //2015-01-20T12:15
//        System.out.println(date13.plus(duration)); //Unsupported unit: Seconds

        /** Working with Instants
         * dùng để đại diện một khoảng khắc nhất định trong GMT time zone .
         */
        Instant instant = Instant.now();
        System.out.println("asasdsds");
        Instant later = Instant.now();
        System.out.println(" duration :" + Duration.between(instant, later).toMillis());

        LocalDate localDate4 = LocalDate.of(2015, 5, 5);
        LocalTime localTime6 = LocalTime.of(11, 55, 55);
        ZoneId zoneId1 = ZoneId.of("US/Eastern");
        ZonedDateTime zonedDateTime3 = ZonedDateTime.of(localDate4, localTime6, zoneId1);
        Instant instant1 = zonedDateTime3.toInstant();
        System.out.println(zonedDateTime3);
        System.out.println(instant1);
    }

    private static void performAnimalEnrichment(LocalDate start, LocalDate end, Period period) {
        LocalDate localDate = start;
        while (localDate.isBefore(end)) {
            System.out.println("give new toy :" + localDate);
            localDate = localDate.plus(period);
        }
    }
}
