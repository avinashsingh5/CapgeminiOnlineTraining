package StreamApi.RealWorldScenarios;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class RoomBooking {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String roomType;
    private String guestName;

    public RoomBooking(
            LocalDate checkInDate,
            LocalDate checkOutDate,
            String roomType,
            String guestName
    ) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomType = roomType;
        this.guestName = guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getGuestName() {
        return guestName;
    }
}

public class HotelBookingAnalysis {

    public static void main(String[] args) {

        List<RoomBooking> bookings = List.of(

                new RoomBooking(
                        LocalDate.of(2026, 8, 1),
                        LocalDate.of(2026, 8, 4),
                        "Deluxe",
                        "Avinash"
                ),

                new RoomBooking(
                        LocalDate.of(2026, 8, 2),
                        LocalDate.of(2026, 8, 4),
                        "Standard",
                        "Rahul"
                ),

                new RoomBooking(
                        LocalDate.of(2026, 8, 5),
                        LocalDate.of(2026, 8, 7),
                        "Deluxe",
                        "Neha"
                ),

                new RoomBooking(
                        LocalDate.of(2026, 8, 6),
                        LocalDate.of(2026, 8, 10),
                        "Suite",
                        "Priya"
                ),

                new RoomBooking(
                        LocalDate.of(2026, 8, 10),
                        LocalDate.of(2026, 8, 11),
                        "Deluxe",
                        "Amit"
                )
        );

        Map<String, Double> pricePerNight = Map.of(
                "Standard", 2000.0,
                "Deluxe", 3500.0,
                "Suite", 5000.0
        );

        Map<String, Long> bookingsByRoomType = bookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getRoomType(),
                        Collectors.counting()
                ));

        Map.Entry<String, Long> mostPopularRoomType =
                bookingsByRoomType.entrySet()
                        .stream()
                        .max((firstRoom, secondRoom) ->
                                Long.compare(
                                        firstRoom.getValue(),
                                        secondRoom.getValue()
                                )
                        )
                        .orElse(null);

        Map<String, Double> revenueByRoomType = bookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getRoomType(),
                        Collectors.summingDouble(booking -> {

                            long numberOfNights = ChronoUnit.DAYS.between(
                                    booking.getCheckInDate(),
                                    booking.getCheckOutDate()
                            );

                            double roomPrice =
                                    pricePerNight.get(booking.getRoomType());

                            return numberOfNights * roomPrice;
                        })
                ));

        if (mostPopularRoomType != null) {
            System.out.println(
                    "Most popular room type: "
                            + mostPopularRoomType.getKey()
                            + " with "
                            + mostPopularRoomType.getValue()
                            + " bookings"
            );
        }

        System.out.println("\nRevenue by room type:");

        revenueByRoomType.forEach((roomType, totalRevenue) ->
                System.out.println(roomType + ": ₹" + totalRevenue)
        );
    }
}