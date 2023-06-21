package com.tomspencerlondon.moviebooker.common.adapter.out.jpa;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "schedules")
public class ScheduleDbo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(length = 3)
    private String day;

    private int hour;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
