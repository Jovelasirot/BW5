package team5.BW5.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team5.BW5.entities.Town;
import team5.BW5.exceptions.NotFoundException;
import team5.BW5.repositories.TownDAO;

@Service
public class TownService {

    @Autowired
    private TownDAO tDAO;

    public Town findTownIdByTownName(String townName) {
        return tDAO.findTownIdByTownName(townName).orElseThrow(() -> new NotFoundException("Town: " + townName + " not found"));
    }

    public Town findById(Long townId) {
        return this.tDAO.findById(townId).orElseThrow(() -> new NotFoundException(townId));
    }

}
