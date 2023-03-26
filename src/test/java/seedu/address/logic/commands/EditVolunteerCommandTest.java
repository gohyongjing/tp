package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_VOLUNTEER;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NO_FIELD_PROVIDED;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SINGLE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showVolunteerAtIndex;
import static seedu.address.testutil.TestUtil.getTypicalModelManager;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.util.EditDescriptor;
import seedu.address.model.Model;
import seedu.address.model.person.Volunteer;
import seedu.address.testutil.EditDescriptorBuilder;
import seedu.address.testutil.ModelManagerBuilder;
import seedu.address.testutil.VolunteerBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditVolunteerCommand.
 */
public class EditVolunteerCommandTest {

    private final Model model = getTypicalModelManager();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredVolunteerList_success() {
        Volunteer editedVolunteer = new VolunteerBuilder().build();
        EditDescriptor descriptor = new EditDescriptorBuilder(editedVolunteer).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                editedVolunteer);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setVolunteer(model.getFilteredVolunteerList().get(0), editedVolunteer);

        assertCommandSuccess(editVolunteerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredVolunteerList_success() {
        Index indexLastVolunteer = Index.fromOneBased(model.getFilteredVolunteerList().size());
        Volunteer lastVolunteer = model.getFilteredVolunteerList().get(indexLastVolunteer.getZeroBased());

        VolunteerBuilder volunteerInList = new VolunteerBuilder(lastVolunteer);
        Volunteer editedVolunteer = volunteerInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_SINGLE).build();

        EditDescriptor descriptor = new EditDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_SINGLE).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(indexLastVolunteer, descriptor);

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                editedVolunteer);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setVolunteer(lastVolunteer, editedVolunteer);

        assertCommandSuccess(editVolunteerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredVolunteerList_failure() {
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                new EditDescriptor());

        String expectedMessage = MESSAGE_NO_FIELD_PROVIDED;

        assertCommandFailure(editVolunteerCommand, model, expectedMessage);
    }

    @Test
    public void execute_filteredVolunteerList_success() {
        showVolunteerAtIndex(model, INDEX_FIRST_PERSON);

        Volunteer volunteerInFilteredList = model.getFilteredVolunteerList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Volunteer editedVolunteer = new VolunteerBuilder(volunteerInFilteredList).withName(VALID_NAME_BOB).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                new EditDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditVolunteerCommand.MESSAGE_EDIT_VOLUNTEER_SUCCESS,
                editedVolunteer);

        Model expectedModel = new ModelManagerBuilder()
                .withFriendlyLink(model.getFriendlyLink())
                .build();
        expectedModel.setVolunteer(model.getFilteredVolunteerList().get(0), editedVolunteer);

        assertCommandSuccess(editVolunteerCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateVolunteerUnfilteredVolunteerList_failure() {
        Volunteer firstVolunteer = model.getFilteredVolunteerList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditDescriptor descriptor = new EditDescriptorBuilder(firstVolunteer).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editVolunteerCommand, model, MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_duplicateVolunteerFilteredVolunteerList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST_PERSON);

        // edit volunteer in filtered list into a duplicate in FriendlyLink
        Volunteer volunteerInList = model.getFriendlyLink().getVolunteerList()
                .get(INDEX_SECOND_PERSON.getZeroBased());
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON,
                new EditDescriptorBuilder(volunteerInList).build());

        assertCommandFailure(editVolunteerCommand, model, MESSAGE_DUPLICATE_VOLUNTEER);
    }

    @Test
    public void execute_invalidVolunteerIndexUnfilteredVolunteerList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredVolunteerList().size() + 1);
        EditDescriptor descriptor = new EditDescriptorBuilder()
                .withName(VALID_NAME_BOB).build();
        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(outOfBoundIndex,
                descriptor);

        assertCommandFailure(editVolunteerCommand, model, MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address PERSON
     */
    @Test
    public void execute_invalidVolunteerIndexFilteredList_failure() {
        showVolunteerAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFriendlyLink().getVolunteerList().size());

        EditVolunteerCommand editVolunteerCommand = new EditVolunteerCommand(outOfBoundIndex,
                new EditDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editVolunteerCommand, model, MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditVolunteerCommand standardCommand = new EditVolunteerCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditDescriptor copyDescriptor = new EditDescriptor(DESC_AMY);
        EditVolunteerCommand commandWithSameValues = new EditVolunteerCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());

        // different index -> returns false
        assertNotEquals(standardCommand, new EditVolunteerCommand(INDEX_SECOND_PERSON, DESC_AMY));

        // different descriptor -> returns false
        assertNotEquals(standardCommand, new EditVolunteerCommand(INDEX_FIRST_PERSON, DESC_BOB));
    }

}