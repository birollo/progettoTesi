package ARL.tesi.repository;


import ARL.tesi.modelobject.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HolidaysRepository extends JpaRepository<Holidays, String> {

    Holidays getByName(String name);
}
