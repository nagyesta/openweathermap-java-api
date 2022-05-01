/*
 * Copyright (c) 2021 Alexey Zinchenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.prominence.openweathermap.api;

import com.github.prominence.openweathermap.api.annotation.SubscriptionAvailability;
import com.github.prominence.openweathermap.api.conf.TimeoutSettings;
import com.github.prominence.openweathermap.api.request.RequestSettings;
import com.github.prominence.openweathermap.api.request.air.pollution.AirPollutionRequester;
import com.github.prominence.openweathermap.api.request.forecast.climatic.ClimaticForecastRequester;
import com.github.prominence.openweathermap.api.request.forecast.daily.DailyForecastRequester;
import com.github.prominence.openweathermap.api.request.forecast.free.FiveDayThreeHourStepForecastRequester;
import com.github.prominence.openweathermap.api.request.forecast.hourly.FourDaysHourlyForecastRequester;
import com.github.prominence.openweathermap.api.request.geocoding.GeocodingRequester;
import com.github.prominence.openweathermap.api.request.onecall.OneCallWeatherRequester;
import com.github.prominence.openweathermap.api.request.radiation.SolarRadiationRequester;
import com.github.prominence.openweathermap.api.request.roadrisk.RoadRiskRequester;
import com.github.prominence.openweathermap.api.request.weather.CurrentWeatherRequester;

import static com.github.prominence.openweathermap.api.enums.SubscriptionPlan.*;

/**
 * The main public API client to communicate with OpenWeatherMap services.
 * Requires API key for usage. More info on the website <a href="https://openweathermap.org/api">https://openweathermap.org/api</a>.
 */
public class OpenWeatherMapClient {
    private final String apiKey;
    private final TimeoutSettings timeoutSettings = new TimeoutSettings();

    /**
     * Created OpenWeatherMap client object.
     * @param apiKey API key obtained on <a href="https://home.openweathermap.org/api_keys">OpenWeatherMap site</a>.
     */
    public OpenWeatherMapClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        timeoutSettings.setConnectionTimeout(connectionTimeout);
    }

    public void setReadTimeout(int readTimeout) {
        timeoutSettings.setReadTimeout(readTimeout);
    }

    /**
     * Current Weather <a href="https://openweathermap.org/current">API</a>.
     * @return requester for retrieving current weather information.
     */
    @SubscriptionAvailability(plans = ALL)
    public CurrentWeatherRequester currentWeather() {
        return new CurrentWeatherRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * Hourly forecast <a href="https://openweathermap.org/api/hourly-forecast">API</a>.
     * @return requester for retrieving hourly weather forecast information for 4 days.
     */
    @SubscriptionAvailability(plans = { DEVELOPER, PROFESSIONAL, ENTERPRISE })
    public FourDaysHourlyForecastRequester forecastHourly4Days() {
        return new FourDaysHourlyForecastRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * One Call <a href="https://openweathermap.org/api/one-call-api">API</a>.
     * To get information about current weather, minute forecast for 1 hour, hourly forecast for 48 hours, daily forecast for 7 days and government weather alerts.
     * @return requester for retrieving one call weather information.
     */
    @SubscriptionAvailability(plans = ALL)
    public OneCallWeatherRequester oneCall() {
        return new OneCallWeatherRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * Daily forecast <a href="https://openweathermap.org/api/hourly-forecast">API</a>.
     * @return requester for retrieving daily weather forecast information for 16 days.
     */
    @SubscriptionAvailability(plans = PAID)
    public DailyForecastRequester forecastDaily16Days() {
        return new DailyForecastRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * Climatic forecast <a href="https://openweathermap.org/api/forecast30">API</a>.
     * @return requester for retrieving climatic weather forecast information for 30 days.
     */
    @SubscriptionAvailability(plans = { DEVELOPER, PROFESSIONAL, ENTERPRISE })
    public ClimaticForecastRequester climaticForecast30Days() {
        return new ClimaticForecastRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * Solar Radiation <a href="https://openweathermap.org/api/solar-radiation">API</a>.
     * @return requester for retrieving solar radiation information.
     */
    @SubscriptionAvailability(plans = SPECIAL)
    public SolarRadiationRequester solarRadiation() {
        return new SolarRadiationRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * 5 Day / 3 Hour Forecast <a href="https://openweathermap.org/forecast5">API</a>.
     * @return requester for retrieving 5 day/3-hour weather forecast information.
     */
    @SubscriptionAvailability(plans = ALL)
    public FiveDayThreeHourStepForecastRequester forecast5Day3HourStep() {
        return new FiveDayThreeHourStepForecastRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * Road Risk <a href="https://openweathermap.org/api/road-risk">API</a>.
     * @return requester for retrieving road risk information.
     */
    @SubscriptionAvailability(plans = SPECIAL)
    public RoadRiskRequester roadRisk() {
        return new RoadRiskRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    /**
     * Air Pollution <a href="https://openweathermap.org/api/air-pollution">API</a>.
     * Air Pollution API provides current, forecast and historical air pollution data for any coordinates on the globe.
     * @return requester for air pollution information retrieval.
     */
    @SubscriptionAvailability(plans = ALL)
    public AirPollutionRequester airPollution() {
        return new AirPollutionRequester(new RequestSettings(apiKey, timeoutSettings));
    }

    @SubscriptionAvailability(plans = ALL)
    public GeocodingRequester geocoding() {
        return new GeocodingRequester(new RequestSettings(apiKey, timeoutSettings));
    }
}
