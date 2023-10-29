package com.berkebzlk.GraduationProject.repository;

import com.berkebzlk.GraduationProject.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PersonelRepository extends JpaRepository<Personel, Long> {
    Set<Personel> findByUserId(long userId);

    Set<Personel> findByHotelId(long hotelId);
}
