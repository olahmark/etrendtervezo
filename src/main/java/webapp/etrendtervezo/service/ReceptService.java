package webapp.etrendtervezo.service;


import webapp.etrendtervezo.dto.ReceptDTO;
import webapp.etrendtervezo.entity.Recept;

import java.util.List;

public interface ReceptService {

    List<ReceptDTO> findAll();

    ReceptDTO create (ReceptDTO receptDTO);

    List<ReceptDTO> findByReceptnev(String receptnev);


}
