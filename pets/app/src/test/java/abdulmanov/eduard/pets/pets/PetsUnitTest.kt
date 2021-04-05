package abdulmanov.eduard.pets.pets

import org.junit.Test

class PetsUnitTest {

    @Test
    fun getSortedPetsTest() {
        PetsData.interactor.getPets()
            .test()
            .assertValue { it == PetsData.sortedNamePets }
    }

    @Test
    fun getCurrentPetTest() {
        PetsData.interactor.getCurrentPet()
            .test()
            .assertValue { it == PetsData.currentPet }
    }
}