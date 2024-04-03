package com.warzone.elements.map;

import com.warzone.elements.GameMap;

/**
 * Adapter for map reader.
 *
 */
public class MapReaderAdapter extends MapReader {
	private ConquestMapReader d_conquestReader;
	
	/**
	 * MapReaderAdapter constructor
	 * @param p_conquestReader ConquestReader object
	 * @param p_gameMap GameMap object
	 */
	public MapReaderAdapter(ConquestMapReader p_conquestReader, GameMap p_gameMap) {
		super(p_gameMap);
		d_conquestReader = p_conquestReader;
	}
	
	/**
	 * function to read a file feed data to GameMap object.
	 * @param p_filePath path to .map file
	 * @return true if file load successfully, else false
	 */
	@Override
	public boolean readFullMap(String p_filePath) {
		return d_conquestReader.readFullMap(p_filePath); 
	}
}
