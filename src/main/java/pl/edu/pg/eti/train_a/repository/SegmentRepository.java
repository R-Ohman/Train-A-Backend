package pl.edu.pg.eti.train_a.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.eti.train_a.entity.Segment;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Integer> {
}
