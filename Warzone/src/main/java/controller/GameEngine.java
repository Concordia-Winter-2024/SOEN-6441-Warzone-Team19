public String showMap() {
    if (!d_isGamePhase) {
        return d_gameMap.showMapEdit();
    }
    return d_gameMap.showMapPlay();
}