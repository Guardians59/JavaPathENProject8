package ms.gpsutil.service;

import java.util.List;
import java.util.UUID;

import ms.gpsutil.model.Attraction;
import ms.gpsutil.model.VisitedLocation;

public interface IGPSUtilService {

    public VisitedLocation getUserLocation(UUID userId);

    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

}
