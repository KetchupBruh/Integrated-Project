package sit.int221.mydb.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
public class Interval {

    // Ver.5
    public static boolean isOverlap(Instant start, Instant end, Instant checkStart, Instant checkEnd) {
        if (start.toEpochMilli() <= checkStart.toEpochMilli() || start.toEpochMilli() <= checkEnd.toEpochMilli()) {
            if (checkStart.toEpochMilli() <= end.toEpochMilli()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap");
            }
        }
        return true;
    }

    // Ver.4
//    public static boolean isOverlap(Instant start, Instant end, Instant checkStart, Instant checkEnd) {
//        //overlap ของใหม่ไปชนหน้าของเก่า
//        if (start.toEpochMilli() <= checkStart.toEpochMilli()) {
//            if (checkStart.toEpochMilli() <= end.toEpochMilli()) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap1");
//            }
//        }
//        //overlap ของใหม่ไปชนหลังของเก่า
//        if (start.toEpochMilli() <= checkEnd.toEpochMilli()) {
//            if (checkStart.toEpochMilli() <= end.toEpochMilli()) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap2");
//            }
//        }
//        return true;
//    }

    // Ver.3 Fail
//    public static boolean isOverlap(Instant start, Instant end, Instant checkStart, Instant checkEnd) {
//        if (start.isBefore(checkStart) || start.equals(checkStart)
//                && end.isBefore(checkEnd) || checkStart.equals(end)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap1");
//        }
//        if (start.isBefore(checkEnd) || start.equals(checkEnd)
//                && checkEnd.isAfter(end) || end.equals(checkEnd)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap2");
//        }
//        return true;
//    }

    // Ver.1 Fail
//    public static boolean isOverlap(Instant start, Instant end, Instant checkStart, Instant checkEnd) {
////        if (start.isBefore(checkStart) || start.equals(checkStart)
////                && checkStart.isBefore(end) || checkStart.equals(end)) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap1");
////        }
//        if (start.isBefore(checkStart) || start.equals(checkStart)
//                && end.isAfter(checkEnd) || end.equals(end)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap1");
//        }
//        if (start.isBefore(checkEnd) || start.equals(checkEnd)
//                && checkEnd.isAfter(end) || end.equals(checkEnd)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap2");
//        }
////        if (start.isBefore(checkEnd) || start.equals(checkEnd)
////                && end.isAfter(checkStart) || end.equals(checkStart)) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap2");
////        }
//        return true;
//    }

    // Ver.2 Fail
//    public static boolean isOverlap(Instant start, Instant end, Instant checkStart, Instant checkEnd) {
////        if (start.isBefore(checkEnd) || start.equals(checkEnd)
////                && checkStart.isBefore(end) || checkStart.equals(end)
////                && start.isBefore(end) || start.equals(end)
////                && checkStart.isBefore(checkEnd) || checkStart.equals(checkEnd)) {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap1");
////        }
//        if (start.isBefore(checkEnd) || start.equals(checkEnd)
//                && start.isBefore(end) || start.equals(end)
//                && checkStart.isBefore(end) || checkStart.equals(end)
//                && checkStart.isBefore(checkEnd) || checkStart.equals(checkEnd)) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your date/time is overlap1");
//        }
//        return true;
//    }



}
