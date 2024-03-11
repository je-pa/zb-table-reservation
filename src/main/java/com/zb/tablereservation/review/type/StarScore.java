package com.zb.tablereservation.review.type;

public enum StarScore {
    HALF_STAR(0.5, "매우 최악"),
    ONE_STAR(1.0, "최악"),
    ONE_AND_HALF_STAR(1.5, "매우 나쁨"),
    TWO_STAR(2.0, "나쁨"),
    TWO_AND_HALF_STAR(2.5, "나쁘지 않음"),
    THREE_STAR(3.0, "보통"),
    THREE_AND_HALF_STAR(3.5, "좋음"),
    FOUR_STAR(4.0, "매우 좋음"),
    FOUR_AND_HALF_STAR(4.5, "최고"),
    FIVE_STAR(5.0, "매우 최고");

    private final double value; // 별점 값
    private final String description; // 별점 설명

    StarScore(double value, String description) {
        this.value = value;
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}