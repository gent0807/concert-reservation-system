package io.dev.concertreservationsystem.common.config.kafka;

public class KafkaKey {
    public static final String CONSUMER_GROUP_ID = "CONSUMER_GROUP_ID";

    public static final String POINT_USE_HISTORY_CREATE_EVENT = "POINT_USE_HISTORY_CREATE_EVENT";

    public static final String POINT_CHARGE_HISTORY_CREATE_EVENT = "POINT_CHARGE_HISTORY_CREATE_EVENT";

    public static final String CONCERT_BASIC_EVENT = "CONCERT_BASIC_EVENT";

    public static final String CONCERT_DETAIL_STATUS_UPDATE_EVENT = "CONCERT_DETAIL_STATUS_UPDATE_EVENT";

    public static final String SEAT_STATUS_UPDATE_EVENT = "SEAT_STATUS_UPDATE_EVENT";

    public static final String RESERVATION_CREATE_EVENT = "RESERVATION_CREATE_EVENT";

    public static final String RESERVATION_STATUS_UPDATE_EVENT = "RESERVATION_STATUS_UPDATE_EVENT";

    public static final String PAYMENT_CREATE_EVENT = "PAYMENT_CREATE_EVENT";

    public static final String PAYMENT_STATUS_UPDATE_EVENT = "PAYMENT_STATUS_UPDATE_EVENT";
}
