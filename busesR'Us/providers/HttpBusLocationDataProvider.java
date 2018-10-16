package ca.ubc.cs.cpsc210.translink.providers;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Stop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Wrapper for Translink Bus Location Data Provider
 */
public class HttpBusLocationDataProvider extends AbstractHttpDataProvider {
    private Stop stop;

    public HttpBusLocationDataProvider(Stop stop) {
        super();
        this.stop = stop;
    }

    @Override
    /**
     * Produces URL used to query Translink web service for locations of buses serving
     * the stop specified in call to constructor.
     *
     * @returns URL to query Translink web service for arrival data
     */
    protected URL getUrl() throws MalformedURLException {
        if (stop == null) {
            throw new MalformedURLException();
        }
        String apiKey = "Jq1mYvws9nJXoC3sDuW3";
        String url = "http://api.translink.ca/rttiapi/v1/buses?apikey=" + apiKey + "&stopNo=" + stop.getNumber();
        return new URL(url);
    }

    @Override
    public byte[] dataSourceToBytes() throws IOException {
        return new byte[0];
    }
}
