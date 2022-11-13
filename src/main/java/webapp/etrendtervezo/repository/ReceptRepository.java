package webapp.etrendtervezo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webapp.etrendtervezo.entity.Alapanyag;
import webapp.etrendtervezo.entity.Recept;

import java.util.List;

public interface ReceptRepository extends JpaRepository<Recept, Long> {

    List<Recept> findByReceptnev(String receptnev);
}
