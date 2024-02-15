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