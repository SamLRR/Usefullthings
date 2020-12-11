package com.infotrans.osk.usefullthings.repo;

import com.infotrans.osk.usefullthings.domain.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStationRepo extends JpaRepository<WorkStation, Long> {
}
