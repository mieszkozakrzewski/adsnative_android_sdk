package com.adsnative.android.sdk.request;

import android.util.Log;

import com.adsnative.android.sdk.Constants;
import com.adsnative.android.sdk.device.DeviceInfo;
import com.github.kevinsawicki.http.HttpRequest;

/**
 * Builds url for sponsored story request and makes GET call to API
 */
public class GetSponsoredStoryRequest {

    private AdRequest adRequest;
    private String uuid;
    private DeviceInfo deviceInfo;

    /**
     * Constructor
     *
     * @param adRequest base request with AdUnitID
     * @param uuid value of AdvertisingId
     * @param deviceInfo object with all device info data
     */
    public GetSponsoredStoryRequest(AdRequest adRequest, String uuid, DeviceInfo deviceInfo) {
        this.adRequest = adRequest;
        this.uuid = uuid;
        this.deviceInfo = deviceInfo;
    }

    /**
     * Gets parameters from properties objects and builds parameters part of url.
     *
     * @return parameters part of url
     */
    private String getParams() {
        String zid = adRequest.getAdUnitID();
        String ua = deviceInfo.getUserAgent();
        String al = deviceInfo.getLocale();
        String tz = deviceInfo.getTimeZone();
        String bd = deviceInfo.getConnectionType();

        String params = "zid=" + zid + "&app=1" + "&ua=" + ua + "&al=" + al + "&tz=" + tz +
                "&uuid=" + uuid + "&bd=" + bd + "&prefetch=1";

        params = params.replaceAll(" ", "%20");

        return params;
    }

    /**
     * Builds complete url for getting sponsored story request
     *
     * @return complete url for request
     */
    private String getUrl() {
        return "http://" + Constants.URL_HOST + "/" + Constants.VERSION + "/ad.json?" + getParams();
    }

    /**
     * Performs GET sponsored story request operation on API and returns response body
     *
     * @return response body
     */
    public String get() {
        try {
            return HttpRequest.get(getUrl()).body();
        } catch (HttpRequest.HttpRequestException exception) {
            Log.e(Constants.ERROR_TAG, exception.getMessage());
            return null;
        }
    }
}
