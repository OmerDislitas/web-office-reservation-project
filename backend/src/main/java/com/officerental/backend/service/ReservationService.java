package com.officerental.backend.service;

import com.officerental.backend.model.dto.ReservationCreateRequest;
import com.officerental.backend.model.entity.Office;
import com.officerental.backend.model.entity.Reservation;
import com.officerental.backend.model.entity.User;
import com.officerental.backend.repository.OfficeRepository;
import com.officerental.backend.repository.ReservationRepository;
import com.officerental.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final OfficeRepository officeRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              OfficeRepository officeRepository,
                              UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.officeRepository = officeRepository;
        this.userRepository = userRepository;
    }

    public Reservation create(ReservationCreateRequest req, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Office office = officeRepository.findById(req.getOfficeId())
                .orElseThrow(() -> new RuntimeException("Office not found"));

        Reservation r = new Reservation();
        r.setUser(user);
        r.setOffice(office);
        r.setStartDate(LocalDate.parse(req.getStartDate()));
        r.setEndDate(LocalDate.parse(req.getEndDate()));
        r.setStatus("PENDING");

        return reservationRepository.save(r);
    }

    public List<Reservation> getAllPending() {
        return reservationRepository.findByStatus("PENDING");
    }

    public Reservation approve(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        r.setStatus("APPROVED");
        return reservationRepository.save(r);
    }

    public Reservation reject(Long id) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        r.setStatus("REJECTED");
        return reservationRepository.save(r);
    }

    public List<Reservation> getUserReservations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return reservationRepository.findByUserId(user.getId());
    }
}
