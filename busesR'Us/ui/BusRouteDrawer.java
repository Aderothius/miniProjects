package ca.ubc.cs.cpsc210.translink.ui;

import android.content.Context;
import ca.ubc.cs.cpsc210.translink.BusesAreUs;
import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.util.Geometry;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

// A bus route drawer
public class BusRouteDrawer extends MapViewOverlay {
    /**
     * overlay used to display bus route legend text on a layer above the map
     */
    private BusRouteLegendOverlay busRouteLegendOverlay;
    /**
     * overlays used to plot bus routes
     */
    private List<Polyline> busRouteOverlays;

    /**
     * Constructor
     *
     * @param context the application context
     * @param mapView the map view
     */
    public BusRouteDrawer(Context context, MapView mapView) {
        super(context, mapView);
        busRouteLegendOverlay = createBusRouteLegendOverlay();
        busRouteOverlays = new ArrayList<>();
    }

    /**
     * Plot each visible segment of each route pattern of each route going through the selected stop.
     */
    public void plotRoutes(int zoomLevel) {
        Stop ss = StopManager.getInstance().getSelected();
        Float lineWidth = getLineWidth(zoomLevel);
        updateVisibleArea();
        busRouteLegendOverlay.clear();
        busRouteOverlays.clear();
        if (ss != null) {
            Set<Route> routeSet = ss.getRoutes();

            for (Route r : routeSet) {
                List<RoutePattern> routePatternList = r.getPatterns();

                int routeColorNumber = busRouteLegendOverlay.add(r.getNumber());

                for (RoutePattern rp : routePatternList) {
                    List<LatLon> latLonList = rp.getPath();

                    // list size -1 necessary, if not indexOutOfBound exception thrown
                    for (int i = 0; i < (latLonList.size() - 1); i++) {
                        //Set here as a means to auto reset the list, without having to clear it
                        Polyline pline = new Polyline(context);
                        List<GeoPoint> listOfGeoPoints = new ArrayList<GeoPoint>();

                        if (Geometry.rectangleIntersectsLine(northWest, southEast, latLonList.get(i),
                                latLonList.get(i + 1))) {

                            listOfGeoPoints.add(Geometry.gpFromLatLon(latLonList.get(i)));
                            listOfGeoPoints.add(Geometry.gpFromLatLon(latLonList.get(i + 1)));

                            pline.setPoints(listOfGeoPoints);
                            pline.setWidth(lineWidth);
                            pline.setColor(routeColorNumber);

                            busRouteOverlays.add(pline);
                        }
                    }
                }
            }
        }
    }



    public List<Polyline> getBusRouteOverlays() {
        return Collections.unmodifiableList(busRouteOverlays);
    }

    public BusRouteLegendOverlay getBusRouteLegendOverlay() {
        return busRouteLegendOverlay;
    }


    /**
     * Create text overlay to display bus route colours
     */
    private BusRouteLegendOverlay createBusRouteLegendOverlay() {
        ResourceProxy rp = new DefaultResourceProxyImpl(context);
        return new BusRouteLegendOverlay(rp, BusesAreUs.dpiFactor());
    }

    /**
     * Get width of line used to plot bus route based on zoom level
     *
     * @param zoomLevel the zoom level of the map
     * @return width of line used to plot bus route
     */
    private float getLineWidth(int zoomLevel) {
        if (zoomLevel > 14) {
            return 7.0f * BusesAreUs.dpiFactor();
        } else if (zoomLevel > 10) {
            return 5.0f * BusesAreUs.dpiFactor();
        } else {
            return 2.0f * BusesAreUs.dpiFactor();
        }
    }
}
