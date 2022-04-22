package ms.gpsutil.service;

import java.util.List;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import ms.gpsutil.model.User;

public interface IGPSUtilService {

    public VisitedLocation getUserLocation(User user);

    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

}
