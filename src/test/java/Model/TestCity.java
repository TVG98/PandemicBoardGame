package Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author : Thimo van Velzen
 */

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
        atlanta.addNeighbour(newYork.getName());
        atlanta.addNeighbour(miami.getName());
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayCubeSizeIsEqualToTwo() {
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);

        int expectedCubeAmount = 2;
        int cubeAmount = atlanta.getCubes().size();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayCubeAmountIsEqualToZeroOnStart() {
        int expectedCubeAmount = 0;
        int cubeAmount = atlanta.getCubes().size();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayNewYorkIsANeighbourOfAtlanta() {
        boolean newYorkIsNeighbour = atlanta.checkCityForAdjacency(newYork);

        assertThat(newYorkIsNeighbour, is(true));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_SayChicagoIsNotANeighbourOfAtlanta() {
        boolean chicagoIsNeighbour = atlanta.checkCityForAdjacency(chicago);

        assertThat(chicagoIsNeighbour, is(false));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_returnFalseWhenVirusTypeIsNotBlueForAtlanta() {
        VirusType red = VirusType.RED;
        boolean atlantaHasRedVirus = atlanta.getVIRUS_TYPE().equals(red);

        assertThat(atlantaHasRedVirus, is(false));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_haveOneCubeWhenAddedThreeCubesAndRemovedTwoCubes() {
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.removeCube();
        atlanta.removeCube();

        int expectedCubeAmount = 1;
        int cubeAmount = atlanta.getCubes().size();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    /**
     * @author : Thimo van Velzen
     */
    @Test
    public void Should_haveNoCubesWhenAddedThreeCubesAndRemovedAllCubes() {
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.addCube(VirusType.BLUE);
        atlanta.removeAllCubes();

        int expectedCubeAmount = 0;
        int cubeAmount = atlanta.getCubes().size();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    /**
     * @author : Thimo van Velzen
     */
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

    /**
     * @author : Thimo van Velzen
     */
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
