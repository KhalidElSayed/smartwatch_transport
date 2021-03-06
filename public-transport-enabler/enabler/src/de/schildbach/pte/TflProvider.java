/*
 * Copyright 2010-2013 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.pte;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import de.schildbach.pte.dto.Style;

/**
 * @author Andreas Schildbach
 */
public class TflProvider extends AbstractEfaProvider
{
	public static final NetworkId NETWORK_ID = NetworkId.TFL;
	private static final String API_BASE = "http://journeyplanner.tfl.gov.uk/user/";

	public TflProvider()
	{
		super(API_BASE);
	}

	public NetworkId id()
	{
		return NETWORK_ID;
	}

	@Override
	protected TimeZone timeZone()
	{
		return TimeZone.getTimeZone("Europe/London");
	}

	public boolean hasCapabilities(final Capability... capabilities)
	{
		for (final Capability capability : capabilities)
			if (capability == Capability.AUTOCOMPLETE_ONE_LINE || capability == Capability.DEPARTURES || capability == Capability.CONNECTIONS)
				return true;

		return false;
	}

	@Override
	protected String parseLine(final String mot, final String symbol, final String name, final String longName, final String trainType,
			final String trainNum, final String trainName)
	{
		if ("0".equals(mot))
		{
			if ("First Hull Trains".equals(trainName) || "=HT".equals(trainType))
				return "IHT" + trainNum;

			else if ("Southern".equals(trainName) || "=SN".equals(trainType))
				return "RSN" + trainNum;
			else if ("Southeastern".equals(trainName) || "=SE".equals(trainType))
				return "RSE" + trainNum;
			else if ("South West Trains".equals(trainName) || "=SW".equals(trainType))
				return "RSW" + trainNum;
			else if ("Greater Anglia".equals(trainName) || "=LE".equals(trainType))
				return "RLE" + trainNum;
			else if ("First Great Western".equals(trainName) || "=GW".equals(trainType))
				return "RGW" + trainNum;
			else if ("First Capital Connect".equals(trainName) || "=FC".equals(trainType))
				return "RFC" + trainNum;
			else if ("Northern Rail".equals(trainName) || "=NT".equals(trainType))
				return "RNT" + trainNum;
			else if ("Heathrow Connect".equals(trainName) || "=HC".equals(trainType))
				return "RHC" + trainNum;
			else if ("Heathrow Express".equals(trainName) || "=HX".equals(trainType))
				return "RHX" + trainNum;
			else if ("Gatwick Express".equals(trainName) || "=GX".equals(trainType))
				return "RGX" + trainNum;
			else if ("Merseyrail".equals(trainName) || "=ME".equals(trainType))
				return "RME" + trainNum;
			else if ("East Coast".equals(trainName) || "=GR".equals(trainType))
				return "RGR" + trainNum;
			else if ("Cross Country".equals(trainName) || "=XC".equals(trainType))
				return "RXC" + trainNum;
			else if ("East Midlands Trains".equals(trainName) || "=EM".equals(trainType))
				return "REM" + trainNum;
			else if ("Arriva Trains Wales".equals(trainName) || "=AW".equals(trainType))
				return "RAW" + trainNum;
			else if ("First TransPennine Express".equals(trainName) || "=TP".equals(trainType))
				return "RTP" + trainNum;
			else if ("ScotRail".equals(trainName) || "=SR".equals(trainType))
				return "RSR" + trainNum;
			else if ("London Midland".equals(trainName) || "=LM".equals(trainType))
				return "RLM" + trainNum;
			else if ("c2c".equals(trainName) || "=CC".equals(trainType))
				return "RCC" + trainNum;
			else if ("Grand Central".equals(trainName) || "=GC".equals(trainType))
				return "RGC" + trainNum;
			else if ("Virgin Trains".equals(trainName) || "=VT".equals(trainType))
				return "RVT" + trainNum;
			else if ("Island Line".equals(trainName) || "=IL".equals(trainType))
				return "RIL" + trainNum;
			else if ("Chiltern Railways".equals(trainName) || "=CH".equals(trainType))
				return "RCH" + trainNum;

			throw new IllegalStateException("cannot normalize mot='" + mot + "' symbol='" + symbol + "' name='" + name + "' long='" + longName
					+ "' trainType='" + trainType + "' trainNum='" + trainNum + "' trainName='" + trainName + "'");
		}
		else if ("3".equals(mot))
		{
			if ("London Overground".equals(trainName) || "=LO".equals(trainType))
				return "SLO" + (trainNum != null ? trainNum : "");

			throw new IllegalStateException("cannot normalize mot='" + mot + "' symbol='" + symbol + "' name='" + name + "' long='" + longName
					+ "' trainType='" + trainType + "' trainNum='" + trainNum + "' trainName='" + trainName + "'");
		}

		return super.parseLine(mot, symbol, name, longName, trainType, trainNum, trainName);
	}

	private static final Map<String, Style> LINES = new HashMap<String, Style>();

	static
	{
		// London
		LINES.put("UBakerloo", new Style(Style.parseColor("#9D5324"), Style.WHITE));
		LINES.put("UCentral", new Style(Style.parseColor("#D52B1E"), Style.WHITE));
		LINES.put("UCircle", new Style(Style.parseColor("#FECB00"), Style.BLACK));
		LINES.put("UDistrict", new Style(Style.parseColor("#007934"), Style.WHITE));
		LINES.put("UEast London", new Style(Style.parseColor("#FFA100"), Style.WHITE));
		LINES.put("UHammersmith & City", new Style(Style.parseColor("#C5858F"), Style.BLACK));
		LINES.put("UJubilee", new Style(Style.parseColor("#818A8F"), Style.WHITE));
		LINES.put("UMetropolitan", new Style(Style.parseColor("#850057"), Style.WHITE));
		LINES.put("UNorthern", new Style(Style.BLACK, Style.WHITE));
		LINES.put("UPicadilly", new Style(Style.parseColor("#0018A8"), Style.WHITE));
		LINES.put("UVictoria", new Style(Style.parseColor("#00A1DE"), Style.WHITE));
		LINES.put("UWaterloo & City", new Style(Style.parseColor("#76D2B6"), Style.BLACK));

		LINES.put("SDLR", new Style(Style.parseColor("#00B2A9"), Style.WHITE));
		LINES.put("SLO", new Style(Style.parseColor("#f46f1a"), Style.WHITE));

		LINES.put("TTramlink 1", new Style(Style.rgb(193, 215, 46), Style.WHITE));
		LINES.put("TTramlink 2", new Style(Style.rgb(193, 215, 46), Style.WHITE));
		LINES.put("TTramlink 3", new Style(Style.rgb(124, 194, 66), Style.BLACK));
	}

	@Override
	public Style lineStyle(final String line)
	{
		final Style style = LINES.get(line);
		if (style != null)
			return style;
		if (line.startsWith("SLO"))
			return LINES.get("SLO");
		return super.lineStyle(line);
	}
}
