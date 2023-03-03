package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FriendlyLink;
import seedu.address.model.ReadOnlyVolunteer;
import seedu.address.storage.volunteer.JsonVolunteerStorage;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalVolunteers;

public class JsonVolunteerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonVolunteerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readVolunteer_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readVolunteer(null));
    }

    private java.util.Optional<ReadOnlyVolunteer> readVolunteer(String filePath) throws Exception {
        return new JsonVolunteerStorage(Paths.get(filePath)).readVolunteer(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readVolunteer("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readVolunteer("notJsonFormatVolunteer.json"));
    }

    @Test
    public void readVolunteer_invalidPersonVolunteer_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readVolunteer("invalidVolunteer.json"));
    }

    @Test
    public void readVolunteer_invalidAndValidPersonVolunteer_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readVolunteer("invalidAndValidVolunteer.json"));
    }

    @Test
    public void readAndSaveVolunteer_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempVolunteer.json");
        FriendlyLink original = TestUtil.getTypicalFriendlyLink();
        JsonVolunteerStorage jsonVolunteerStorage = new JsonVolunteerStorage(filePath);

        // Save in new file and read back
        jsonVolunteerStorage.saveVolunteer(original, filePath);
        ReadOnlyVolunteer readBack = jsonVolunteerStorage.readVolunteer(filePath).get();
        assertEquals(original.getVolunteerList(), readBack.getVolunteerList());

        // Modify data, overwrite exiting file, and read back
        original.removeVolunteer(TypicalVolunteers.ALICE);
        original.addVolunteer(TypicalVolunteers.ALICE);
        jsonVolunteerStorage.saveVolunteer(original, filePath);
        readBack = jsonVolunteerStorage.readVolunteer(filePath).get();
        assertEquals(original.getVolunteerList(), readBack.getVolunteerList());

        // Save and read without specifying file path
        original.addVolunteer(TypicalVolunteers.AMY);
        jsonVolunteerStorage.saveVolunteer(original); // file path not specified
        readBack = jsonVolunteerStorage.readVolunteer().get(); // file path not specified
        assertEquals(original.getVolunteerList(), readBack.getVolunteerList());

    }

    @Test
    public void saveVolunteer_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveVolunteer(null, "SomeFile.json"));
    }

    /**
     * Saves {@code friendlyLink} at the specified {@code filePath}.
     */
    private void saveVolunteer(ReadOnlyVolunteer friendlyLink, String filePath) {
        try {
            new JsonVolunteerStorage(Paths.get(filePath))
                    .saveVolunteer(friendlyLink, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveVolunteer_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveVolunteer(new FriendlyLink(), null));
    }
}
