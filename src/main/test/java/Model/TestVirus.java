package Model;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestVirus {

    private Virus virus;

    @Before
    public void setup() {
        virus = new Virus(VirusType.BLUE);
    }

    @Test
    public void Should_Return24CubesWhenInitialized() {
        int expectedCubeAmount = 24;
        int cubeAmount = virus.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_Return22CubesWhenDecreaseCubesIsCalledTwice() {
        virus.decreaseCubeAmount(1);
        virus.decreaseCubeAmount(1);

        int expectedCubeAmount = 22;
        int cubeAmount = virus.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_Return23CubesWhenDecreaseCubesIsCalledTwiceAndIncreaseCubeIsCalledOnce() {
        virus.decreaseCubeAmount(1);
        virus.decreaseCubeAmount(1);
        virus.increaseCubeAmount(1);

        int expectedCubeAmount = 23;
        int cubeAmount = virus.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_Return24CubesWhenCalledIncreaseCubesWhenAlready24CubesAreExisting() {
        virus.increaseCubeAmount(1);

        int expectedCubeAmount = 24;
        int cubeAmount = virus.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }

    @Test
    public void Should_Return0CubesWhenCalledDecreaseCubesIsCalledWhen0CubesAreExisting() {
        virus.decreaseCubeAmount(24);
        virus.decreaseCubeAmount(1);

        int expectedCubeAmount = 0;
        int cubeAmount = virus.getCubeAmount();

        assertThat(cubeAmount, is(expectedCubeAmount));
    }
}