package com.berkebzlk.GraduationProject.repository;

import com.berkebzlk.GraduationProject.entity.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface PersonelRepository extends JpaRepository<Personel, Long> {
    Set<Personel> findByUserId(long userId);

    Set<Personel> findByHotelId(long hotelId);

    boolean existsByHotelIdAndUserIdAndRoleId(long hotelId, long userId, long roleId);

    @Transactional
    void deleteByHotelIdAndUserIdAndRoleId(long hotelId, long userId, long roleId);

}
