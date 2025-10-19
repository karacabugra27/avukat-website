package com.avukatwebsite.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "appointment",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"lawyer_id", "appointment_date", "start_time"})
        }
)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    //eklenecek şeyler var.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private LawyerSchedule schedule;

}