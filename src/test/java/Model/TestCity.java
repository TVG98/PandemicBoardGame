package Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestCity {

    private final City atlanta = new City("Atlanta", VirusType.BLUE);
    private final City newYork = new City("New York", VirusType.BLUE);
    private final City miami = new City("Miami", VirusType.BLUE);
    private final City chicago = new City("Chicago", VirusType.BLUE);

    @Before
    public void setup() {
        makeAtlantaCity();
    }

    private void makeAtlantaCity() {
        ArrayList<City> neighbours = new ArrayList<>();
        neighbours.add(newYork);
        neighbours.add(miami);
        atlanta.initializeNeighbours(neighbours);
    }

    @Test
    public void Should_SayCubeSizeIsEqualToTwo() {
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);

        int expectedCubeAmount = 2;
        int cubeAmount = atlanta.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_SayCubeAmountIsEqualToZeroOnStart() {
        int expectedCubeAmount = 0;
        int cubeAmount = atlanta.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_SayNewYorkIsANeighbourOfAtlanta() {
        boolean newYorkIsNeighbour = atlanta.checkCityForAdjacency(newYork);

        assertThat(newYorkIsNeighbour, is(true));
    }

    @Test
    public void Should_SayChicagoIsNotANeighbourOfAtlanta() {
        boolean chicagoIsNeighbour = atlanta.checkCityForAdjacency(chicago);

        assertThat(chicagoIsNeighbour, is(false));
    }

    @Test
    public void Should_returnFalseWhenVirusTypeIsNotBlueForAtlanta() {
        VirusType red = VirusType.RED;
        boolean atlantaHasRedVirus = atlanta.getVirusType().equals(red);

        assertThat(atlantaHasRedVirus, is(false));
    }

    @Test
    public void Should_haveOneCubeWhenAddedThreeCubesAndRemovedTwoCubes() {
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.removeCube();
        atlanta.removeCube();

        int expectedCubeAmount = 1;
        int cubeAmount = atlanta.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_haveNoCubesWhenAddedThreeCubesAndRemovedAllCubes() {
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.removeAllCubes();

        int expectedCubeAmount = 0;
        int cubeAmount = atlanta.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_NotThrowAnIndexOutOfBoundsErrorWhenRemovingCubeWhenNoCubesAreOnCity() {
        boolean exceptionsThrown = false;

        try {
            atlanta.removeCube();
        } catch (IndexOutOfBoundsException npe) {
            exceptionsThrown = true;
        }

        assertThat(exceptionsThrown, is(false));
    }

    @Test
    public void Should_NotThrowAnIndexOutOfBoundsErrorWhenRemovingAllCubesWhenNoCubesAreOnCity() {
        boolean exceptionsThrown = false;

        try {
            atlanta.removeAllCubes();
        } catch (IndexOutOfBoundsException npe) {
            exceptionsThrown = true;
        }

        assertThat(exceptionsThrown, is(false));
    }
}
