package webapp.etrendtervezo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webapp.etrendtervezo.entity.Alapanyag;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface AlapanyagRepository extends JpaRepository<Alapanyag, String> {

Alapanyag findByAlapanyagnev(String alapanyagnev);

}
