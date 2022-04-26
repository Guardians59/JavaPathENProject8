package ms.gpsutil.service;

import java.util.List;
import java.util.UUID;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

public interface IGPSUtilService {

    public VisitedLocation getUserLocation(UUID userId);

    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

}
