/**
 * method to display map from the map file in a particular representation during edit phase
 * print continents, countries, and corresponding neighbours
 *
 * @return array list containing data in string format
 **/
public String showMapEdit() {
    String[] l_index = { "Country", "Continent; Control Value", "Neighbors" };
    Object[][] l_values = new Object[d_countries.size()][l_index.length];
    Country l_country;
    TextTable l_textTable;
    final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
    String l_final_value;

    int l_count = 0;

    for (HashMap.Entry<Integer, Country> l_item : d_countries.entrySet()) {
        l_country = l_item.getValue();
        l_values[l_count] = fillCountryData(l_country, true);
        l_count++;
    }

    l_textTable = new TextTable(l_index, l_values);
    l_textTable.setAddRowNumbering(false);
    l_textTable.setSort(0);

    try (PrintStream l_printStream = new PrintStream(l_baos, true, "UTF-8")) {
        l_textTable.printTable(l_printStream, 0);

    } catch (UnsupportedEncodingException p_e) {

        p_e.printStackTrace();
    }

    l_final_value = new String(l_baos.toByteArray(), StandardCharsets.UTF_8);
    return l_final_value;
}

public String showMapPlay() {
    String[] l_index = {"Country", "Continent; Control Value", "Owner", "Armies", "Neighbors"};
    Object[][] l_values = new Object[d_countries.size()][l_index.length];
    Country l_country;
    TextTable l_textTable;
    final ByteArrayOutputStream l_baos = new ByteArrayOutputStream();
    String l_final_value;
    int l_count = 0;

    for (HashMap.Entry<Integer, Country> l_item : d_countries.entrySet()) {
        l_country = l_item.getValue();
        l_values[l_count] = fillCountryData(l_country, false);
        l_count++;
    }

    l_textTable = new TextTable(l_index, l_values);
    l_textTable.setAddRowNumbering(false);
    l_textTable.setSort(0);
    try (PrintStream l_printStream = new PrintStream(l_baos, true, "UTF-8")) {
        l_textTable.printTable(l_printStream, 0);

    } catch (UnsupportedEncodingException p_e) {
        p_e.printStackTrace();
    }
    l_final_value = new String(l_baos.toByteArray(), StandardCharsets.UTF_8);
    return l_final_value;
}

public String[] fillCountryData(Country p_country, boolean p_isEdit) {
    ArrayList<String> l_result = new ArrayList<String>();
    int l_id;
    String l_neighboursToCsv = p_country.getNeighbourCountries().stream().map(Country::getId)
                    .collect(Collectors.toSet()).toString();

    l_id = p_country.getId();
    l_result.add(l_id + "");
    l_result.add(p_country.getContinent().getId() + "; " + p_country.getContinent().getControlValue());

    if (!p_isEdit) {
        l_result.add(p_country.getPlayer() != null ? p_country.getPlayer().getName() : "");
        l_result.add(p_country.getNumberOfArmiesPresent() + "");
    }

    l_result.add(l_neighboursToCsv);
    return l_result.toArray(new String[0]);
}