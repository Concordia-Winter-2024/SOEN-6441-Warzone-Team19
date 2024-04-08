package com.warzone.elements.map;

import com.warzone.elements.GameMap;

/**
 * Adapter for map writer.
 *
 */
public class MapWriterAdapter extends MapWriter {
	private ConquestMapWriter d_conquestWriter;

	/**
	 * MapWriterAdapter constructor
	 * @param p_conquestWriter ConquestWriter object
	 * @param p_gameMap GameMap object
	 */
	public MapWriterAdapter(ConquestMapWriter p_conquestWriter, GameMap p_gameMap) {
		super(p_gameMap);
		d_conquestWriter = p_conquestWriter;
	}

	/**
	 * method that opens a file and writes the data to that file.
	 * @param p_filePath path to .map file
	 * @return true if file saved successfully, else false
	 */
	@Override
	public boolean writeFullMap(String p_filePath) {
		return d_conquestWriter.writeFullMap(p_filePath);
	}	
}
