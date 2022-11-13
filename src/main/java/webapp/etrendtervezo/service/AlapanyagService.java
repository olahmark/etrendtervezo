package webapp.etrendtervezo.service;

import webapp.etrendtervezo.dto.AlapanyagDTO;


import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface AlapanyagService {

    List<AlapanyagDTO> findAll();

    AlapanyagDTO findByAlapanyagnev(String alapanyagnev);

    AlapanyagDTO create (AlapanyagDTO alapanyagDTO);


}
